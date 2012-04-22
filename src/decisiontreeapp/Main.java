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
 * @author adriadc
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

        al = dn.readTreeDescription(rb.getString("file"));
        
        //Iterator i = al.iterator();
       /*while(i.hasNext()){
         System.out.println(i.next().toString());
        
       }*/
       //System.out.println(al);
        
        //HashMap hm = new HashMap();
        //hm = dn.uniqueCounts(al); 
        
        //System.out.println(hm);
        
        //Float a = dn.giniImpurity(al);
        //System.out.println(a);
        
        //Float b = dn.entropy(al);
        //System.out.println(b);
        
        //dn.divideset(al, 3, "18");
        //System.out.println(dn.divideSet(al, 3, "18"));

        //DecisionNode dn2 = new DecisionNode();
        //dn2 = dn.buildTree(al, "entropy", 0.5f);
        //dn.printTree(dn2, "");

        /* Codi exemple per a la identificació de l'error que presenta la      
           funció classify */
        
        //ArrayList obj = new ArrayList();
        //obj.add("google");
        //obj.add("France");
        //obj.add("yes");
        //obj.add("23");
        //dn.classify(obj, dn2);

        //ArrayList training_set = dn.readTreeDescription(rb.getString("file"));
        //ArrayList test_set = dn.readTreeDescription(rb.getString("test_file"));
        //float tpc = dn.testPerformance(test_set, training_set);
        //System.out.println(tpc);
    }

}
