/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import ansexp.model.Node;
import ansexp.model.XMLParser;
import ansexp.model.SQLiteJDBC;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author eremeykin
 */
public class XMLParserTest {
    
    public XMLParserTest() {
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
     * Test of getInstance method, of class XMLParser.
     */
//    @Test
//    public void testGetInstance() throws Exception {
//        System.out.println("getInstance");
//        File file = null;
//        XMLParser expResult = null;
//        XMLParser result = XMLParser.getInstance(file);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getResultNode method, of class XMLParser.
     */
    @Test
    public void testGetResultNode() throws Exception {
        System.out.println("getResultNode");
        SQLiteJDBC db = new SQLiteJDBC(new File("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\test\\ansexp\\testDB.sqlite"));
        XMLParser instance =new  XMLParser(new File("C:\\Users\\Пётр\\Desktop\\Курсовой\\AnsExp\\Код\\AnsExp\\test\\ansexp\\testmodel.xml"),db);
        Node result = instance.getResultNode();
    }
    
    private int countChildren(Node root) {
        int c = 1;
        for (Node node : root.getChildren()) {
            c+=countChildren(node);
        }
        return c;
    }
    
}
