package decisiontreeapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe que representa cada node de l'arbre de decisió
 * 
 * @author 3ntelligence
 */

public class DecisionNode {
    
    public int col;             // columna tractada en la partició
    public String value;        // valor de la columna per on es particiona
    public HashMap results;     // ubicat en les fulles de l'arbre, indica la
                                // distribució dels casos que compleixen les 
                                // condicions dels nodes intermitjos
    public DecisionNode tb;     // següent node que compleix la condició de divisió
    public DecisionNode fb;     // següent node que no compleix la condició de divisió

     /**
     * Constructor per defecte
     * 
     * @param results Distribució dels casos al recorrer tot l'arbre
     */
    DecisionNode() {
        this.col = -1;
        this.value = null;
        this.results = null;
        this.tb = null;
        this.fb = null;
    }

    /**
     * Constructor on s'inserta la col·lecció de resultats, emprat per crear fulles
     * 
     * @param results Distribució dels casos al recorrer tot l'arbre
     */
    public DecisionNode(HashMap results) {
        this.col = -1;
        this.value = null;
        this.results = results;
        this.tb = null;
        this.fb = null;
    }

    /**
     * Constructor per crear nodes intermitjos, sense l'atribut 'results'
     * 
     * @param col Columna tractada en la partició
     * @param value Valor de la columna per on es particiona
     * @param tb Següent node que compleix la condició de divisió
     * @param fb Següent node que no compleix la condició de divisió
     */
    public DecisionNode(int col, String value, DecisionNode tb, DecisionNode fb) {
        this.col = col;
        this.value = value;
        this.results = null;
        this.tb = tb;
        this.fb = fb;
    }

    /**
     * Constructor complet amb tots els atributs
     * 
     * @param col Columna tractada en la partició
     * @param value Valor de la columna per on es particiona
     * @param results Distribució dels casos al recorrer tot l'arbre
     * @param tb Següent node que compleix la condició de divisió
     * @param fb Següent node que no compleix la condició de divisió
     */
    public DecisionNode(int col, String value, HashMap results, DecisionNode tb, DecisionNode fb) {
        this.col = col;
        this.value = value;
        this.results = results;
        this.tb = tb;
        this.fb = fb;
    }

    /**
     * Funció que realitza un recompte del nombre d’entrades (tuples)
     * classificades per cada classe.
     * 
     * @param data Dades processades amb readTreeDescription(file_name)
     * @return Diccionari amb cada classe o prototip per clau i el número de 
     * tuples de cada prototip per valor
     */
    public HashMap uniqueCounts(ArrayList data){

            HashMap hm = new HashMap();
            ArrayList element;

            for (int i = 0; i < data.size(); i++){
               element = (ArrayList)data.get(i);
               
               if(!hm.containsKey(element.get(element.size()-1))){
                   hm.put(element.get(element.size()-1), 0);
               }
               
               // Valor Basic, None o Premium
               String element_value = (String) element.get(element.size()-1);
               // Comptador dels cops que apareix el value anterior
               int element_count = (Integer)hm.get(element.get(element.size()-1))+1;
               hm.put(element_value, element_count);
               
            } 
            
            return hm;
    }
    
    /**
     * Forma un arbre de decisió format per elements del tipus DecisionNode. Els
     * nodes intermitjos representen preguntes (divisions) i els nodes fulla 
     * contenen la divisió resultant
     * 
     * @param data Dades processades amb readTreeDescription(file_name)
     * @param scoref Funció per calcular l'impuresa ('entropy' / 'giniImpurity')
     * @param beta Criteri de parada, requisit mínim per realitzar una divisió
     * @return Retorna el primer node (arrel) de l'arbre
     */
    public DecisionNode buildTree(ArrayList data, String scoref, float beta){

        if(data.isEmpty()){
            return new DecisionNode();
        }

        float current_score;
        float best_result = 0.0f;
        float result;
        float p = 0.0f;

        ArrayList partition_criteria = null;
        ArrayList partition = null;
        HashMap col_values;
        HashMap res;
        String val;
        DecisionNode trueBranch;
        DecisionNode falseBranch;


        /* Comprovem quina mesura d'impuresa s'escolleix per a realitzar
         * l'arbre.
         */
        ImpurityMeasure method = null;
       
        if(scoref.equalsIgnoreCase("entropy")){
            method = new Entropy();
        }
        else if(scoref.equalsIgnoreCase("gini_impurity")){
            method = new GiniImpurity();
        }
        else{
            System.out.println("No existeix aquesta mesura d'impuresa");
            System.exit(0);                      
        }

        current_score = method.getImpurityScore(this.uniqueCounts(data), data.size());
       
        for(int cols = 0; cols < ((ArrayList)data.get(0)).size(); cols++){
            
            col_values = new HashMap();
            for(int j=0; j<data.size(); j++){
                 if(!col_values.containsKey(((ArrayList)data.get(j)).get(cols))){
                     col_values.put(((ArrayList)data.get(j)).get(cols), 1);
                 }
                 
            }
            
            Iterator i = col_values.keySet().iterator();
            HashMap partition_one, partition_two;
            int partition_one_length, partition_two_length;
            while(i.hasNext()){
            
                val = i.next().toString();
                partition = this.divideSet(data, cols, val);
                
                partition_one = this.uniqueCounts((ArrayList)partition.get(0));
                partition_two = this.uniqueCounts((ArrayList)partition.get(1));
                partition_one_length = ((ArrayList)partition.get(0)).size();
                partition_two_length = ((ArrayList)partition.get(1)).size(); 
               
                result = current_score - (p * method.getImpurityScore(partition_one, partition_one_length)) 
                                       - ((1-p) * method.getImpurityScore(partition_two, partition_two_length));
                
                try{
                    p = partition_one_length / Float.parseFloat(String.valueOf(data.size()));
                }catch(ArithmeticException e){
                    System.out.println("No existeixen dades en el conjunt de dades data");
                    System.exit(0);
                }
                
                if(result > best_result && ((ArrayList)partition.get(0)).size() > 0 && ((ArrayList)partition.get(1)).size() > 0 ){

                    best_result = result;
                    partition_criteria = new ArrayList();
                    partition_criteria.add(cols);
                    partition_criteria.add(val);

                }
            } 
        }
        
        if (best_result > 0.0f){
            trueBranch = this.buildTree((ArrayList)partition.get(0), scoref, 0.5f);
            falseBranch = this.buildTree((ArrayList)partition.get(1), scoref, 0.5f);
            return new DecisionNode(Integer.parseInt(partition_criteria.get(0).toString()), partition_criteria.get(1).toString(), trueBranch, falseBranch);
        }else{
            res = this.uniqueCounts(data);            
            return new DecisionNode(res);
        }

    }
    
    /**
     * Funció booleana per saber si un caràcter o cadena de caràcters és un número
     * 
     * @param value Possible número en format de cadena de caràcters
     * @return Retorna cert si el número és un enter o un real, fals altrament
     */
    public boolean isNumeric(String value){
        /* Comprovació de si un símbol és un dígit o un caràcter */
            try{
                Integer.parseInt(value);
                return true;
            }catch(NumberFormatException nfe){
                try{
                    Float.parseFloat(value);
                    return true;
                }catch(NumberFormatException nfe2){
                    return false;   
                }
            }
    }
    
    /**
     * Funció que separa un conjunt d'informació en dos conjunts en funció
     * d'un determinat criteri de partició
     * 
     * @param part Dades processades amb readTreeDescription(file_name)
     * @param column Columna de dades per on realitzar la divisió
     * @param value Valor referència de la columna a partir
     * @return 
     */
    public ArrayList divideSet(ArrayList part, int column, String value){
       
        ArrayList sets = new ArrayList();
        ArrayList set1 = new ArrayList();
        ArrayList set2 = new ArrayList(); 

        if(this.isNumeric(value)){
            float num = Float.parseFloat(value);
            
            for(int i=0; i < part.size(); i++){

                if(Float.parseFloat(((ArrayList)part.get(i)).get(column).toString()) >= num){
                    set1.add(part.get(i));
                }else{
                    set2.add(part.get(i));
                } 
            }

        }else{
            for(int i=0; i < part.size(); i++){
                if((((ArrayList)part.get(i)).get(column).toString()).equalsIgnoreCase(value)){
                    set1.add(part.get(i));
                }else{
                        set2.add(part.get(i));
                }
            }

        }

        sets.add(set1);
        sets.add(set2);
        
        return sets;
    }

    /**
     * Funció per mostrar per pantalla un arbre de decisió creat amb la 
     * funció buildTree()
     * 
     * @param dn DecisionNode arrel de l'arbre
     * @param symbol Caràcter de separació
     */
    public void printTree(DecisionNode dn, String symbol){

        if(dn.results != null){
            System.out.println(dn.results);
        }else{
            System.out.println(dn.col+":"+dn.value+"?");
            System.out.println(symbol+"T->");
            this.printTree(dn.tb, symbol+"   ");
            System.out.println(symbol+"F->");
            this.printTree(dn.fb, symbol+"   ");
        }      
    }
    
   /**
     * Funció recursiva que recorre un arbre de decisió donat comparant en cada
     * cas el valor corresponent de la tupla amb el respectiu de l'arbre. Quan
     * s'acaba de comparar és que s'ha arribat a una fulla i, per comparació de
     * nou, es decideix a quin prototip pertany
     * 
     * @param obj Estructura de dades (ArrayList) format per una sola tupla amb
     * informació i el node inicial de l’arbre classificador
     * @param tree Arbre de decisió sobre el que efectuar la classificació
     * @return 
     */
   public HashMap classify(ArrayList obj, DecisionNode tree){
        
        String value_element;
        DecisionNode branch;

        if(tree.results != null){
            return tree.results;
        }
        else{
            
            value_element = obj.get(tree.col).toString();

            if(this.isNumeric(value_element)){
                
                float num = Float.parseFloat(value_element);
                
                if(num >= Float.parseFloat(tree.value)){
                    branch = tree.tb;
                }
                else{
                    branch = tree.fb;
                }
            }
            else{
                if(value_element.equalsIgnoreCase(tree.value)){
                    branch = tree.tb;
                }
                else{
                    branch = tree.fb;
                }
            }

            return this.classify(obj, branch);
        }
    }
    
   /**
    * Funció que analitza quin tant per cent d'error presenta un arbre de decisió
    * pel que fa a la classificació de noves entrades
    * 
    * @param test_set Estructura de dades amb la informació a classificar
    * @param training_set Estructura de dades amb la informació necessària per
    * crear l'arbre
    * @return Percentatge d'errors en la classificació
    */
    public float testPerformance(ArrayList test_set, ArrayList training_set){
        
        DecisionNode tree = this.buildTree(training_set, "entropy", 0.5f);
        
        float num_errors = 0.0f;
        HashMap a;

        for(int i=0; i < training_set.size(); i++){

            a = this.classify((ArrayList)(training_set.get(i)), tree);
            if(a.toString().contains(((ArrayList)(training_set.get(i))).get(4).toString())){
                num_errors += 1.0f;
            }
        }
        
        float ratio_errors = 0.0f;
        
        /* Comprovació del nombre de fallades en la classificació dels nous element */
        try{
           ratio_errors = num_errors / Float.parseFloat(String.valueOf(test_set.size()));
        }catch(ArithmeticException e){
            System.out.println("No existeixen dades en el conjunt de test");
            System.exit(0);
        }
        
        return ratio_errors;
    }

    
}