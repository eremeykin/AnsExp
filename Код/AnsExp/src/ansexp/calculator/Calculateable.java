/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.calculator;

import ansexp.DataSource;
import java.io.File;

/**
 *
 * @author eremeykin
 */
public interface Calculateable {
    public DataSource calculate(DataSource root);
    public File printToFile();
}
