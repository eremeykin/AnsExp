/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import java.sql.SQLException;
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
public class NodeTest {

    public NodeTest() {
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
     * Test of getValueById method, of class Node.
     */
    @Test
    public void testGetValueById() throws ClassNotFoundException, SQLException, XMLParser.XMLParsingException, SQLiteJDBC.UndefinedDBFile {
        System.out.println("getValueById");

        SQLiteJDBC.getInstance().setSourceFile(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testDB.sqlite"));
        XMLParser parser = XMLParser.getInstance(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testmodel2.xml"));
        Node instance = parser.getResultNode();
        String id = "INNER_RADIUS";
        String expResult = "3";
        String result = instance.getValueById(id);
        assertEquals(expResult, result);

        id = "PART_POISSON";
        expResult = "9";
        result = instance.getValueById(id);
        assertEquals(expResult, result);

        id = "JAW_DELTA";
        expResult = "12";
        result = instance.getValueById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of setValueById method, of class Node.
     */
    @Test
    public void testsetValueById() throws ClassNotFoundException, SQLException, XMLParser.XMLParsingException, SQLiteJDBC.UndefinedDBFile {
        System.out.println("getValueById");

        SQLiteJDBC.getInstance().setSourceFile(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testDB.sqlite"));
        XMLParser parser = XMLParser.getInstance(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testmodel2.xml"));
        Node instance = parser.getResultNode();
        String id = "INNER_RADIUS";
        instance.setValueById(id, "test");
        String expResult = "test";
        String result = instance.getValueById(id);
        assertEquals(expResult, result);
        id = "PART_POISSON";
        instance.setValueById(id, "12354");
        expResult = "12354";
        result = instance.getValueById(id);
        assertEquals(expResult, result);

    }

}
