/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.zip.ZipFile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pete
 */
public class ModelTest {

    public ModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() throws ClassNotFoundException, SQLException, SQLiteJDBC.UndefinedDBFile, IOException {
        Model m = new Model(new ZipFile("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\models\\model1.zip"));
        System.out.println(m.getCalcFile());
        System.out.println(m.getDbFile());
        System.out.println(m.getXmlFile());
        SQLiteJDBC jdbc = SQLiteJDBC.getInstance();
        jdbc.setSourceFile(m.getDbFile());
        System.err.println(jdbc.getItemsList("part_material", "name")[0]);

    }

}
