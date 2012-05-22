/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que permet llegir un conjunt de tuples de dades d'un fitxer de text i
 * ubicar-les en estructures de dades per ser processades
 * 
 * @author 3ntelligence
 */
public class ReadData {

    private String file_name;       // nom del fitxer d'entrada
    
    /**
     * Constructor per reconèixer el fitxer d'entrada
     * 
     * @param file_name Nom del fitxer d'entrada
     */
    ReadData(String file_name) {
        this.file_name = file_name;
    }

    /**
     * Funció per donar format a les dades d'entrada amb l'objectiu de 
     * preparar-les per a ser processades per les funcions de la classe
     * 
     * @return Retorna un ArrayList format per String amb les metadades processades
     * i preparades per treballar-hi
     */
    public ArrayList ReadDataProblem() {
    
        BufferedReader bufferedReader = null;
        ArrayList data = new ArrayList();
        ArrayList element = null;

        try {
            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(this.file_name));

            String line = null;

            while((line = bufferedReader.readLine())!=null){

                    element = new ArrayList();
                    String[] part = line.split("\\s+");
                    for(String s : part){
                        element.add(s);
                    }
                data.add(element);
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            
            //Close the BufferedReader
            try {
                if (bufferedReader != null)  bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        data.remove(0);
        return data;
    }
    
    
    
}
