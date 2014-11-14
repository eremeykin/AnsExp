/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.calculator;

import ansexp.model.Node;
import ansexp.model.SQLiteJDBC;
import ansexp.model.XMLParser;
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
public class DefaultCalculatorTest {

    public DefaultCalculatorTest() {
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
     * Test of printAllVars method, of class DefaultCalculator.
     */
    @Test
    public void testPrintAllVars() throws ClassNotFoundException, SQLException, XMLParser.XMLParsingException, SQLiteJDBC.UndefinedDBFile {
        System.out.println("printAllVars");
        File dataBase = new File("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\test\\ansexp\\testDB.sqlite");
        SQLiteJDBC db = new SQLiteJDBC(dataBase);
        XMLParser parser = new XMLParser(new File("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\models\\model2.xml"),db);
        //XMLParser parser = XMLParser.getInstance(new File("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\test\\ansexp\\testmodel2.xml"));

        Node root = parser.getResultNode();
        DefaultCalculator instance = new DefaultCalculator(root,dataBase,null);
        Node.printChildren(root, 0);
        String result = instance.printAllVars();
        String expResult = "INNER_RADIUS=\n"
                + "OUTER_RADIUS=\n"
                + "LENGTH=\n"
                + "PART_MATERIAL_NAME=\n"
                + "PART_E_MODULUS=\n"
                + "PART_POISSON=\n"
                + "JAW_DELTA=\n"
                + "JAW_LENGTH=\n"
                + "JAW_WIDTH=\n"
                + "JAW_HEIGHT=\n"
                + "JAW_MATERIAL_NAME=\n"
                + "JAW_E_MODULUS=\n"
                + "JAW_POISSON=\n"
                + "FORCE_TAN=\n"
                + "FORCE_RAD=\n"
                + "FORCE_AX=\n"
                + "FORCE_POS=\n";
        assertEquals(expResult, result);
        parser = new XMLParser(new File("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\test\\ansexp\\testmodel2.xml"),db);
        root = parser.getResultNode();
        //System.out.println(Node.printChildren(root, 0));
        instance = new DefaultCalculator(root,dataBase,null);
        expResult = "INNER_RADIUS=3\n"
                + "OUTER_RADIUS=4\n"
                + "LENGTH=5\n"
                + "PART_MATERIAL_NAME=7\n"
                + "PART_E_MODULUS=8\n"
                + "PART_POISSON=9\n"
                + "JAW_DELTA=12\n"
                + "JAW_LENGTH=14\n"
                + "JAW_WIDTH=15\n"
                + "JAW_HEIGHT=16\n"
                + "JAW_MATERIAL_NAME=18\n"
                + "JAW_E_MODULUS=19\n"
                + "JAW_POISSON=20\n"
                + "FORCE_TAN=23\n"
                + "FORCE_RAD=24\n"
                + "FORCE_AX=25\n"
                + "FORCE_POS=26\n";
        result = instance.printAllVars();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calculate method, of class DefaultCalculator.
     */
//    @Test
//    public void testCalculate() {
//        System.out.println("calculate");
//        DataSource root = null;
//        DefaultCalculator instance = null;
//        DataSource expResult = null;
//        DataSource result = instance.calculate(root);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
