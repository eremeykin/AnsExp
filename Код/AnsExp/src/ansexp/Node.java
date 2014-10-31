/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eremeykin
 */
public class Node {

    private final String name;
    private String value;
    private String description;

    private List<Node> children = new ArrayList<>();

    public Node(String name) {
        this.name = name;
        this.description = "";
    }

    public Node(String name, String description, String value) {
        this.name = name;
        this.description = description;
        this.value = value;
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

    private static String printSpace(int count) {
        String result = "";
        for (int i =0;i<count;i++)
            result+="  ";
        return result;
    }

    public static String printChildren(Node node,int count) {
        String result = node.name;
        count++;
        for (Node n : node.getChildren()) {
            result +="\n "+printSpace(count) +printChildren(n,count);
        }
        return result;
    }
}
