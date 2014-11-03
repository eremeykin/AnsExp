/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eremeykin
 */
public class SQLiteJDBCTest {

    public SQLiteJDBCTest() {
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

    /**
     * Test of getInstance method, of class SQLiteJDBC.
     */
//    @Test
//    public void testGetInstance() {
//        System.out.println("getInstance");
//        SQLiteJDBC expResult = new SQLiteJDBC();
//        SQLiteJDBC result = SQLiteJDBC.getInstance();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of setSourceFile method, of class SQLiteJDBC.
     */
//    @Test
//    public void testSetSourceFile() throws Exception {
//        System.out.println("setSourceFile");
//        File file = new File("");
//        SQLiteJDBC instance = null;
//        instance.setSourceFile(file);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getItemsList method, of class SQLiteJDBC.
     */
    @Test
    public void testGetItemsList() throws Exception {
        System.out.println("getItemsList");
        String tableName = "part_material";
        String columnName = "name";
        SQLiteJDBC instance = SQLiteJDBC.getInstance();
        instance.setSourceFile(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testDB.sqlite"));
        String[] expResult = new String[]{
            "Сталь легированная",
            "Сталь углеродистая",
            "Чугун белый",
            "Чугун ковкий",
            "Чугун серый"};
        String[] result = instance.getItemsList(tableName, columnName);
        assertArrayEquals(expResult, result);
    }
    
    
        /**
     * Test of getValue method, of class SQLiteJDBC.
     */
    @Test
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        
        SQLiteJDBC instance = SQLiteJDBC.getInstance();
        instance.setSourceFile(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testDB.sqlite"));
        String table="part_material";
        String keyColumn="name";
        String valueColumn="E_modulus";
        String key="Сталь углеродистая";
        String expResult = "205000.0";
        String result = instance.getValue(table, keyColumn, valueColumn, key);
        assertEquals(expResult, result);
        
        table="part_material";
        keyColumn="name";
        valueColumn="E_modulus";
        key="Чугун серый";
        expResult = "115000.0";
        result = instance.getValue(table, keyColumn, valueColumn, key);
        assertEquals(expResult, result);
        
        table="test_table";
        keyColumn="col1";
        valueColumn="col2";
        key="string123";
        expResult = "string321";
        result = instance.getValue(table, keyColumn, valueColumn, key);
        assertEquals(expResult, result);
        
    }
    

    /**
     * Test of test method, of class SQLiteJDBC.
     */
//    @Test
//    public void testTest() throws Exception {
//        System.out.println("test");
//        SQLiteJDBC instance = null;
//        instance.test();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
