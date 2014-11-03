package ansexp;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteJDBC {

    private static SQLiteJDBC instance = new SQLiteJDBC();
    private File dbFile;
    private Connection connection = null;

    private SQLiteJDBC() {

    }

    public static SQLiteJDBC getInstance() {
        return instance;
    }

    public void setSourceFile(File file) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        dbFile = file;
    }

    public String[] getItemsList(String tableName, String columnName) throws SQLException, UndefinedDBFile {
        try {
            List<String> items = new ArrayList<>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select " + columnName + " from " + tableName + ";");

            while (rs.next()) {
                String currString = rs.getString(columnName);
                items.add(currString);
            }

            String[] result = new String[items.size()];
            items.toArray(result);
            return result;
        } catch (NullPointerException ex) {
            throw new UndefinedDBFile(ex);
        }
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
