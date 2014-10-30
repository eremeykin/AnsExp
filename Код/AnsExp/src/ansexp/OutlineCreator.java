/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeModel;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;

/**
 *
 * @author eremeykin
 */
public class OutlineCreator {

    private Outline outline;

    public OutlineCreator(Node root) {

        TreeModel treeMdl = new Models.TreeTableModel(root);
        OutlineModel mdl = DefaultOutlineModel.createOutlineModel(treeMdl, new Models.TreeTableRowModel(), true, "Название");

        outline = new Outline();
        outline.setRootVisible(false);
        outline.setModel(mdl);
    }
    
    public Outline getOutline(){
        return outline;
    }

}
