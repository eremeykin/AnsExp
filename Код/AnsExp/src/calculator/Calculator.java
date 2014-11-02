/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.Map;

/**
 *
 * @author eremeykin
 */
public interface Calculator {
    public Map<String,String> calculate(Map<String,String> root);
}
