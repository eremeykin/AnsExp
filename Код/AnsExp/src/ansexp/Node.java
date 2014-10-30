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

    public Node(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public List<Node> getChildren() {
        return children;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getValue(){
        return  this.value;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
