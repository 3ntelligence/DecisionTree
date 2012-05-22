/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe que permet calcular el factor d'impuresa Gini d'un conjunt
 * 
 * @author 3ntelligence
 */
public class GiniImpurity implements ImpurityMeasure{  
    
    /**
     * Funció que calcula l’error esperat si es realitza la classificació d'un
     * camp aleatòriament a un possible prototip
     * 
     * @param setOfData Diccionari amb dades ja classificades
     * @param data_length Nombre de tuples que formen el conjunt de dades processades
     * @return Float amb valor 1 si només hi ha un element i més petit 
     * com més desordre hi ha
     */
    public float getImpurityScore(HashMap setOfData, int data_length) {
        
        float impurity = 0.0f;
        float partial_1 = 0.0f;
        float partial_2 = 0.0f;
        
        Iterator iter1 = setOfData.entrySet().iterator();
        Iterator iter2 = setOfData.entrySet().iterator();
        
        Map.Entry element_1;
        Map.Entry element_2;
        
        while (iter1.hasNext()) {
            element_1 = (Map.Entry)iter1.next();
            while (iter2.hasNext()){
                element_2 = (Map.Entry)iter2.next();
                
                try{
                    partial_1 = Float.parseFloat(element_1.getValue().toString())/data_length;
                    partial_2 = Float.parseFloat(element_2.getValue().toString())/data_length;
                }catch(ArithmeticException e){
                    System.out.println("No existeixen dades en el conjunt de data");
                    System.exit(0);
                }
                
                impurity += partial_1*partial_2;
            }
            
        }

        return impurity;
        
    }
    
}
