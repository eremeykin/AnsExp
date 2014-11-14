/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.model;

/**
 *
 * @author eremeykin
 */
//ToDo написать метод String getValueByUniqueDescriptor(String descriptor)
public interface DataSource {
    String getValueById(String id);
    void setValueById(String id,String value);
}
