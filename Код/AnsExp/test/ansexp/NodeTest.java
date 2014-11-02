/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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

//    /**
//     * Test of getChildren method, of class Node.
//     */
//    @Test
//    public void testGetChildren() {
//        System.out.println("getChildren");
//        Node instance = null;
//        List<Node> expResult = null;
//        List<Node> result = instance.getChildren();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDescription method, of class Node.
//     */
//    @Test
//    public void testGetDescription() {
//        System.out.println("getDescription");
//        Node instance = null;
//        String expResult = "";
//        String result = instance.getDescription();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getValue method, of class Node.
//     */
//    @Test
//    public void testGetValue() {
//        System.out.println("getValue");
//        Node instance = null;
//        String expResult = "";
//        String result = instance.getValue();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setValue method, of class Node.
//     */
//    @Test
//    public void testSetValue() {
//        System.out.println("setValue");
//        String value = "";
//        Node instance = null;
//        instance.setValue(value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toString method, of class Node.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        Node instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isLeaf method, of class Node.
//     */
//    @Test
//    public void testIsLeaf() {
//        System.out.println("isLeaf");
//        Node instance = null;
//        boolean expResult = false;
//        boolean result = instance.isLeaf();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of printChildren method, of class Node.
//     */
//    @Test
//    public void testPrintChildren() {
//        System.out.println("printChildren");
//        Node node = null;
//        int count = 0;
//        String expResult = "";
//        String result = Node.printChildren(node, count);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEditors method, of class Node.
//     */
//    @Test
//    public void testGetEditors() {
//        System.out.println("getEditors");
//        List<DefaultCellEditor> res = null;
//        Node node = null;
//        Node.getEditors(res, node);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of spreadToMap method, of class Node.
     */
    @Test
    public void testSpreadToMap() throws XMLParser.XMLParsingException, SQLException, SQLiteJDBC.UndefinedDBFile, ClassNotFoundException {
        System.out.println("spreadToMap");
        SQLiteJDBC.getInstance().setSourceFile(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testDB.sqlite"));
        XMLParser parser = XMLParser.getInstance(new File("/home/eremeykin/Курсовой /Код/AnsExp/test/ansexp/testmodel.xml"));
        Node instance = parser.getResultNode();
        Map<String, String> expResult = new HashMap<>();
        expResult.put("Модель", "");
        expResult.put("Деталь", "");
        expResult.put("Внешний радиус", "");
        expResult.put("Внутренний радиус", "");
        expResult.put("Длина", "");
        expResult.put("Материал", "");
        expResult.put("Название", "");
        expResult.put("Модуль упругости", "");
        expResult.put("Коэффициент Пуассона", "");
        expResult.put("Кулачки", "");
        expResult.put("Количество", "");
        expResult.put("Величина смещения", "");
        expResult.put("Размеры", "");
        expResult.put("Длина", "");
        expResult.put("Высота", "");
        expResult.put("Ширина", "");
        expResult.put("Материал", "");
        expResult.put("Модуль упругости", "");
        expResult.put("Коэффициент Пуассона", "");
        expResult.put("Силы резания", "");
        expResult.put("Проекции", "");
        expResult.put("Касательная, Ftan", "");
        expResult.put("Радиальная, Frad", "");
        expResult.put("Осевая, Fax", "");
        expResult.put("Угловое положение", "");
        Map<String, String> result = instance.spreadToMap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
