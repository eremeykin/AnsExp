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

    public void test() throws Exception {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select * from test_table;");
            System.err.println("");
            int x = rs.getMetaData().getColumnCount();
            //Resultset.getMetaData() получаем информацию
            //результирующей таблице
            while (rs.next()) {
                for (int i = 1; i <= x; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw e;
        }
        System.out.println("Opened database successfully");
    }

    class UndefinedDBFile extends Exception {

        public UndefinedDBFile(Throwable e) {
            super(e);
        }
    }
}
