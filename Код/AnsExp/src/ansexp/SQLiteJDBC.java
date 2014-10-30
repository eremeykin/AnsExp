package ansexp;

import java.sql.*;

public class SQLiteJDBC {

    public static void test() throws Exception {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/eremeykin/NetBeansProjects/AnsExp/src/database/test.sqlite");
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
}
