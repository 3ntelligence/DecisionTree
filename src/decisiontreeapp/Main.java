/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package decisiontreeapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 *
 * @author 3ntelligence
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String configuration_file = "decisiontreeapp.configuration";
        ResourceBundle rb = ResourceBundle.getBundle(configuration_file);

        DecisionNode dn = new DecisionNode();
        ArrayList al = new ArrayList();
        
        ReadData data_problem = new ReadData(rb.getString("file"));
        al = data_problem.ReadDataProblem();
        /* Iterator i = al.iterator();
        * while(i.hasNext()){
        * System.out.println(i.next().toString());
        *  }
        */
        System.out.println("El fitxer de descripció s'ha llegit: \n"+al);
        
        HashMap hm = dn.uniqueCounts(al); 
        System.out.println("Classificació de la informació: \n"+hm);

        ImpurityMeasure method = new GiniImpurity();
        float impurity = method.getImpurityScore(hm, al.size());
        System.out.println("La impuresa de Gini de la informació és: "+impurity);
        method = new Entropy();
        impurity = method.getImpurityScore(hm, al.size());
        System.out.println("L'entropia de la informació és: "+impurity);

        ArrayList division = dn.divideSet(al, 3, "18");
        System.out.println("La divisió de la informació és: \n"+division);

        DecisionNode dn2 = new DecisionNode();
        dn2 = dn.buildTree(al, "entropy", 0.5f);
        System.out.println("La funció printTree dibuixa: \n");
        dn.printTree(dn2, "");

        /* Codi exemple per a la identificació de l'error que presenta la      
           funció classify */
        
        /*ArrayList obj = new ArrayList();
        obj.add("google");
        obj.add("France");
        obj.add("yes");
        obj.add("23");
        dn.classify(obj, dn2);

        
        ReadData data_problem2 = new ReadData(rb.getString("file"));
        ArrayList training_set = data_problem.ReadDataProblem();
        data_problem2 = new ReadData(rb.getString("test_file"));
        ArrayList test_set = data_problem.ReadDataProblem();

        float tpc = dn.testPerformance(test_set, training_set);
        System.out.println("Test performance: \n"+tpc);*/
    }

}
