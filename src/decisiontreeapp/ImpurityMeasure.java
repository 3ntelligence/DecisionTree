/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.util.HashMap;

/**
 * Classe mare per calcular la impuresa d'un determinat conjunt de manera dinàmica
 * 
 * @author 3ntelligence
 */
public interface ImpurityMeasure {

    /**
     * Funció que serà reutilitzada per les subclasses per diferenciar entre les
     * impureses Gini i Entropia
     * 
     * @param setOfData Diccionari amb dades ja classificades
     * @param data_length Nombre de tuples que formen el conjunt de dades processades
     * @return Float amb valor 1 si només hi ha un element i més petit 
     * com més desordre hi ha
     */
    public float getImpurityScore(HashMap setOfData, int data_length);
}
