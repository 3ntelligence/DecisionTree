/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe que permet calcular el factor d'entropia o de desordre d'un conjunt
 * 
 * @author adriadc
 */
public class Entropy implements ImpurityMeasure{

    /**
     * Càlcul de l'entropia del sistema. L'entropia mesura la quantitat de
     * desordre relatiu d'un conjunt.
     * 
     * @param setOfData Diccionari amb dades ja classificades
     * @param data_length Nombre de tuples que formen el conjunt de dades processades
     * @return Float amb valor 0.0 si el conjunt està uniformement repartit
     * i un valor superior com més desigualtat
     */
    public float getImpurityScore(HashMap setOfData, int data_length) {
        
        float p = 0.0f;
        float imp = 0.0f;
       
        Iterator iter = setOfData.entrySet().iterator();
        Map.Entry e1;
        
        while(iter.hasNext()){
            
            e1 = (Map.Entry)iter.next();
	    try{
                 p = Float.parseFloat(e1.getValue().toString())/data_length;
            }catch(ArithmeticException e){
                System.out.println("No existeixen dades en el conjunt de data");
                System.exit(0);
            }
            
            imp +=  p * (Math.log(p)/Math.log(2));
        }
        return -imp;
        
    }
  
}
