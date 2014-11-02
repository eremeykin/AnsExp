/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author eremeykin
 */
public class Node {

    private final String name;
    private String value;
    private final String description;
    private DefaultCellEditor editor;

    private final List<Node> children = new ArrayList<>();

    public Node(String name, String description, String value,DefaultCellEditor editor) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.editor = editor;
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
    
    public static void getEditors(ArrayList<DefaultCellEditor> res,Node node){
        //ArrayList<DefaultCellEditor> result = new ArrayList<>();
        for (Node n : node.children){
            res.add(n.editor);
            getEditors(res,n);
        }
    }
}
