/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

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

        TreeModel treeMdl = new Models.PropertiesModel(root);
        OutlineModel mdl = DefaultOutlineModel.createOutlineModel(treeMdl, new Models.TreeTableRowModel(), true, "Название");
        outline = new Outline() {

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                //int modelColumn = convertColumnIndexToModel(column);
                int modelRow = convertRowIndexToModel(row);
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn != 2) {
                    return null;
                }
                    Node selected = (Node) (this.getModel().getValueAt(modelRow, 0));
                    return selected.getEditor();
            }
        };
        outline.setRootVisible(false);
        outline.setModel(mdl);
        RendererForChangeable rfc = new RendererForChangeable();
        outline.setDefaultRenderer(String.class, rfc);
    }

    public Outline getOutline() {
        return outline;
    }

}
