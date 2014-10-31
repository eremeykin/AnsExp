/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author eremeykin
 */
public class XMLParser {

    private static XMLParser parser = new XMLParser();
    private File xmlFile;
    private Node result;
    private Document document;

    private XMLParser() {
        result = new Node("Root", "", "", null);
    }

    public static XMLParser getInstance(File file) throws XMLParsingException {
        parser.xmlFile = file;
        parser.document = parser.getDocument();
        return parser;
    }

    public void parseToResult(org.w3c.dom.Node xmlNode, Node node) {
        class Helper {

            public DefaultCellEditor makeEditorForXMLNode(org.w3c.dom.Node node) {
                String[] items1 = {"Red", "Blue", "Green"};
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    if (node.getChildNodes().item(i).getNodeName().equals("value")) {
                        String editorType =getAttribute(node.getChildNodes().item(i), "editor");
                        if(editorType!=null && editorType.equals("comboBox"))
                            return new DefaultCellEditor(new JComboBox(items1));
                    }
                }

                return null;
            }
        }
        //Для всех потомков узла
        for (int i = 0; i < xmlNode.getChildNodes().getLength(); i++) {
            //Устанавливаем текущий XML узел
            org.w3c.dom.Node currXMLNode = xmlNode.getChildNodes().item(i);
            //Если этот узел типа элемент (комментарии и текст отбрасывается)
            if (currXMLNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                if (currXMLNode.getNodeName().equals("node") || currXMLNode.getNodeName().equals("root")) {

                    String name = getSign(currXMLNode, "name");
                    String description = getSign(currXMLNode, "description");
                    String value = getSign(currXMLNode, "value");

                    DefaultCellEditor editor = new Helper().makeEditorForXMLNode(currXMLNode);

                    Node currNode = new Node(name, description, value, editor);
                    //Добавляем текущий TreeTable узел в соответствующий пердыдущий узел TreeTable
                    node.getChildren().add(currNode);
                    //Вызываем для метод для текущих узлов
                    parseToResult(currXMLNode, currNode);
                }
            }
        }
    }

    private String getAttribute(org.w3c.dom.Node node, String attrName) {
        for (int i = 0; i < node.getAttributes().getLength(); i++) {
            if (node.getAttributes().item(i).getNodeName().equals(attrName)) {
                return node.getAttributes().item(i).getTextContent();
            }
        }
        return null;
    }

    private String getSign(org.w3c.dom.Node node, String signName) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (signName.equals(children.item(i).getNodeName())) {
                return children.item(i).getTextContent();
            }
        }
        return "undefined";
    }

    private Document getDocument() throws XMLParsingException {
        Document doc = null;
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            doc = builder.parse(this.xmlFile);
        } catch (Exception e) {

            throw new XMLParsingException(e);
        }
        return doc;
    }

    public Node getResultNode() {
        result = new Node("Root", "", "", null);
        parseToResult(document, result);
        return result;
    }

    class XMLParsingException extends Exception {

        XMLParsingException(Throwable e) {
            super(e);
        }
    }
}
