/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import ansexp.calculator.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author eremeykin
 */
public class Node implements DataSource {

    private final String name;
    private String value;
    private final String description;
    private final DefaultCellEditor editor;
    private final String id;

    private final List<Node> children = new ArrayList<>();

    public Node(String name, String description, String value, String id, DefaultCellEditor editor) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.editor = editor;
        this.id = id;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getDescription() {
        return this.description;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * Generates a string representation of the node in the tree view
     *
     * @param node
     * @param count
     * @return string representation
     */
    public static String printChildren(Node node, int count) {
        class Helper {

            private String printSpace(int count) {
                String result = "";
                for (int i = 0; i < count; i++) {
                    result += "  ";
                }
                return result;
            }
        }
        String result = node.name + "(" + node.value + ")";
        count++;
        for (Node n : node.getChildren()) {
            result += "\n " + new Helper().printSpace(count) + printChildren(n, count);
        }
        return result;
    }

    /**
     * Copies all the editors of the nodes into the specified ArrayList
     *
     * @param res result ArrayList with all the editors
     * @param node the root Node
     */
    public static void getEditors(List<DefaultCellEditor> res, Node node) {
        for (Node n : node.children) {
            res.add(n.editor);
            getEditors(res, n);
        }
    }

    /**
     * Copies all the nodes into the specified ArrayList
     *
     * @param res result ArrayList with all the Nodes
     * @param root the root Node
     */
    private static void getNodes(List<Node> res, Node root) {
        for (Node n : root.children) {
            res.add(n);
            getNodes(res, n);
        }
    }

    private Map<String, String> spreadToMap() {
        Map<String, String> result = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        getNodes(nodes, this);
        for (Node node : nodes) {
            result.put(node.id, node.value);
        }
        return result;
    }

    public String getValueByPath(String... path) {
        Node root = this;
        boolean found = true;
        for (String currNodeName : path) {
            for (Node currNode : root.children) {
                if (currNode.name.equals(currNodeName)) {
                    root = currNode;
                    found = true;
                    break;
                }
                found = false;
            }
        }
        if (found) {
            return root.value;
        } else {
            return null;
        }
    }

    @Override
    public String getValueById(String id) {
        Map<String, String> idMap =  this.spreadToMap() ;
        return idMap.get(id);
    }
}
