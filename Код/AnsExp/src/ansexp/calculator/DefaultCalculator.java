/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.calculator;

import java.io.File;
import java.util.EnumMap;

/**
 *
 * @author eremeykin
 */
public class DefaultCalculator implements Calculator{

    DataSource source;


    enum Vars {

        INNER_RADIUS,
        OUTER_RADIUS,
        LENGTH,
        PART_MATERIAL_NAME,
        PART_E_MODULUS,
        PART_POISSON,
        JAW_DELTA,
        JAW_LENGTH,
        JAW_WIDTH,
        JAW_HEIGHT,
        JAW_MATERIAL_NAME,
        JAW_E_MODULUS,
        JAW_POISSON,
        FORCE_TAN,
        FORCE_RAD,
        FORCE_AX,
        FORCE_POS;
    }
    
    EnumMap<Vars,String> v = new EnumMap<>(Vars.class);
  

    public DefaultCalculator(DataSource root) {
        source = root;
        for (Vars var : Vars.values()){
            this.v.put(var, source.getValueById(var.name()));
        }
    }

    public String printAllVars() {
        
        String result="";
        for (Vars var :Vars.values()){
            result+=var.name()+"="+this.v.get(var)+"\n";
        }
        return result;
    }
    
    @Override
    public DataSource calculate(DataSource root) {
        return null;
    }

    @Override
    public File printToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
