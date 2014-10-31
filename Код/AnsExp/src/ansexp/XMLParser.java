/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
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
        result = new Node("Root");
    }

    public static XMLParser getInstance(File file) throws XMLParsingException {
        parser.xmlFile = file;
        parser.document = parser.getDocument();
        return parser;
    }

    public void parseToResult(org.w3c.dom.Node xmlNode, Node node) {
        //Для всех потомков узла
        for (int i = 0; i < xmlNode.getChildNodes().getLength(); i++) {
            //Устанавливаем текущий XML узел
            org.w3c.dom.Node currXMLNode = xmlNode.getChildNodes().item(i);
            //Если этот узел типа элемент (комментарии и текст отбрасывается)
            if (currXMLNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                if (currXMLNode.getNodeName().equals("node") || currXMLNode.getNodeName().equals("root")) {
                    //Пере устанавливаем текущий XML узел
                    //currXMLNode = xmlNode.getChildNodes().item(i);
//                Устанавливаем текущий ТreeTable узел
//                String name = getAttribute(currXMLNode, "name");
//                String description = getAttribute(currXMLNode, "description");
//                String value = getAttribute(currXMLNode, "value");
                    String name = getSign(currXMLNode, "name");
                    String description = getSign(currXMLNode, "description");
                    String value = getSign(currXMLNode, "value");

                    Node currNode = new Node(name, description, value);
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

    public Node getResult() {
        result = new Node("Root");
        parseToResult(document, result);
        return result;
    }

    class XMLParsingException extends Exception {

        XMLParsingException(Throwable e) {
            super(e);
        }
    }
}
