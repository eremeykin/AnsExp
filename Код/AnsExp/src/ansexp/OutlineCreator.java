/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
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
    private Node root;

    public OutlineCreator(Node root) {

        TreeModel treeMdl = new Models.PropertiesModel(root);
        OutlineModel mdl = DefaultOutlineModel.createOutlineModel(treeMdl, new Models.TreeTableRowModel(), true, "Название");

        outline = new Outline() {

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                ArrayList<DefaultCellEditor> editors = new ArrayList<>();
                Node.getEditors(editors, root);
                return editors.get(row);

//                if (modelColumn == 1 && row < 3) {
//                    return null;//editors.get(row);
//                } else {
//                    return super.getCellEditor(row, column);
//                }
            }
        };
        outline.setRootVisible(false);
        outline.setModel(mdl);
    }

    public Outline getOutline() {
        return outline;
    }

}
