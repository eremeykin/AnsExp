/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.calculator;

/**
 *
 * @author eremeykin
 */
public class DefaultCalculator implements Calculator {

    enum Vars {

        INNER_RADIUS("Модель", "Деталь", "Внешний радиус"), 
        OUTER_RADIUS("Модель", "Деталь", "Внeутренний радиус"), 
        LENGTH("Модель", "Деталь", "Внешний радиус"),
        PART_MATERIAL_NAME("Модель","Деталь","Материал","Название"),
        PART_E_MODULUS("Модель","Деталь","Материал","Модуль упругости"), 
        PART_POISSON("Модель","Деталь","Материал","Коэффициент Пуассона"), 
        JAW_DELTA("Модель","Кулачки","Величина смещения"), 
        JAW_LENGTH("Модель","Кулачки","Размеры","Длина"), 
        JAW_WIDTH("Модель","Кулачки","Размеры","Ширина"), 
        JAW_HEIGHT("Модель","Кулачки","Размеры","Высота"), 
        JAW_E_MODULUS("Модель","Кулачки","Материал","Модуль упругости"), 
        JAW_POISSON("Модель","Кулачки","Материал","Коэффициент Пуассона"),
        FORCE_TAN("Модель","Силы резания","Проекции",""),
        FORCE_RAD("Модель"),
        FORCE_AX("Модель"),
        FORCE_POS("Модель");
        
        private final String value;
        
        Vars(String... path){
            this.value = null;//!!!
            //this.value = source.getValueByPath(path);
        }
               
    }
    static DataSource source;


    @Override
    public DataSource calculate(DataSource root) {
       // String innerRadiusStr = root.getValueByPath();
        return  null;
    }

}
