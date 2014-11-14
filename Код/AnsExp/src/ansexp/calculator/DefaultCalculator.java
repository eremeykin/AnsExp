/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.calculator;

import ansexp.model.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eremeykin
 */
public class DefaultCalculator implements Calculateable {

    DataSource source;
    File dataBase;
    File outputFile;

    enum Vars {

        INNER_RADIUS,
        OUTER_RADIUS,
        LENGTH,
        PART_MATERIAL_NAME,
        PART_E_MODULUS,
        PART_POISSON,
        JAW_DELTA,
        JAW_LENGTH,
        JAW_WIDTH,
        JAW_HEIGHT,
        JAW_MATERIAL_NAME,
        JAW_E_MODULUS,
        JAW_POISSON,
        FORCE_TAN,
        FORCE_RAD,
        FORCE_AX,
        FORCE_POS;
    }

    EnumMap<Vars, String> v = new EnumMap<>(Vars.class);

    public DefaultCalculator(DataSource root, File dataBase, File outFile) {
        source = root;
        this.outputFile = outFile;
        this.dataBase = dataBase;
        for (Vars var : Vars.values()) {
            this.v.put(var, source.getValueById(var.name()));
        }
    }

    public String printAllVars() {

        String result = "";
        for (Vars var : Vars.values()) {
            result += var.name() + "=" + this.v.get(var) + "\n";
        }
        return result;
    }

    @Override
    public DataSource calculate(DataSource root) {
        try {
            SQLiteJDBC db = SQLiteJDBC.getInstance(dataBase);

            String materialName = v.get(Vars.PART_MATERIAL_NAME);

            String partEmodulus = db.getValue("part_material", "name", "E_modulus", materialName);
            v.put(Vars.PART_E_MODULUS, partEmodulus);
            String partPoissonModulus = db.getValue("part_material", "name", "poisson_ratio", materialName);
            v.put(Vars.PART_POISSON, partPoissonModulus);

            String jawMaterialName = v.get(Vars.JAW_MATERIAL_NAME);

            String jawEmodulus = db.getValue("part_material", "name", "E_modulus", jawMaterialName);
            v.put(Vars.JAW_E_MODULUS, jawEmodulus);
            String jawPoissonModulus = db.getValue("part_material", "name", "poisson_ratio", jawMaterialName);
            v.put(Vars.JAW_POISSON, jawPoissonModulus);
        } catch (ClassNotFoundException | SQLException | SQLiteJDBC.UndefinedDBFile ex) {
        }
        for (Vars var : Vars.values()) {
            source.setValueById(var.name(), v.get(var));
        }
        return source;
    }

    @Override
    public File printToFile() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(outputFile);
            StringBuffer sBuffer = new StringBuffer();
            int c=0;
            while (c != -1) {
                c = inputStream.read();
                sBuffer.append((char)c);
            }
            String s = new String(sBuffer);
            for (Vars var : Vars.values()) {
                String id = var.name();
                s = s.replace("<"+id+">", source.getValueById(id));
            }
            try (FileOutputStream fout = new FileOutputStream(outputFile, false)) {
                fout.write(s.getBytes());
            }
        } catch (Exception ex) {
            Logger.getLogger(DefaultCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

    private static class SQLiteJDBC {

        private static DefaultCalculator.SQLiteJDBC instance = new DefaultCalculator.SQLiteJDBC();
        private Connection connection = null;

        private SQLiteJDBC() {

        }

        public static SQLiteJDBC getInstance(File file) throws ClassNotFoundException, SQLException {
            Class.forName("org.sqlite.JDBC");
            instance.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            return instance;

        }

        public String getValue(String table, String keyColumn, String valueColumn, String key) throws UndefinedDBFile, SQLException {
            try {

                PreparedStatement ps = connection.prepareStatement("select " + valueColumn + " from " + table + " where " + keyColumn + "= ?");
                ps.setString(1, key);

                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getString(1);

            } catch (NullPointerException ex) {
                throw new UndefinedDBFile(ex);
            }
        }

        public class UndefinedDBFile extends Exception {

            public UndefinedDBFile(Throwable e) {
                super(e);
            }
        }
    }

}
