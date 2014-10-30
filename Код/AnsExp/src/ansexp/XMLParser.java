/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import javax.swing.JOptionPane;
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

    public static XMLParser getInstance(File file) {
        parser.xmlFile = file;
        parser.document = parser.getDocument();
        return parser;
    }

    public void parseToResult(org.w3c.dom.Node xmlNode, Node node) {
        //Для всех потомков узла
        for (int i = 0; i < xmlNode.getChildNodes().getLength(); i++) {
            //Если этот узел типа элемент (комментарии и текст отбрасывается)
            if (xmlNode.getChildNodes().item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                //Устанавливаем текущий XML узел
                org.w3c.dom.Node currXMLNode = xmlNode.getChildNodes().item(i);
                //Устанавливаем текущий ТreeTable узел

                String name = getNameAttribute(currXMLNode);
                Node currNode = new Node(name);
                //Добавляем текущий TreeTable узел в соответствующий пердыдущий узел TreeTable
                node.getChildren().add(currNode);
                //Вызываем для метод для текущих узлов
                parseToResult(currXMLNode, currNode);
            }
        }
    }

    private String getNameAttribute(org.w3c.dom.Node node) {
        for (int i = 0; i < node.getAttributes().getLength(); i++) {
            if (node.getAttributes().item(i).getNodeName().equals("name")) {
                return node.getAttributes().item(i).getTextContent();
            }
        }
        return null;
    }

    private Document getDocument() {
        Document doc = null;
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            doc = builder.parse(this.xmlFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Произошла ошибка при чтении XML файла."
                    + "Программа будет закрыта");
            JOptionPane.showMessageDialog(null, e);
            System.exit(-1);
        }
        return doc;
    }

    public Node getResult() {
        result = new Node("Root");
        this.parseToResult(document, result);
        return result;
    }

}
