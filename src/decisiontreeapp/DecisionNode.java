package decisiontreeapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adriadc
 */

public class DecisionNode {
   
    public int col;
    public String value;
    public HashMap results;
    public DecisionNode tb;
    public DecisionNode fb;

    DecisionNode() {
        this.col = -1;
        this.value = null;
        this.results = null;
        this.tb = null;
        this.fb = null;
    }

    public DecisionNode(HashMap results) {
        this.col = -1;
        this.value = null;
        this.results = results;
        this.tb = null;
        this.fb = null;
    }

    public DecisionNode(int col, String value, DecisionNode tb, DecisionNode fb) {
        this.col = col;
        this.value = value;
        this.results = null;
        this.tb = tb;
        this.fb = fb;
    }

    public DecisionNode(int col, String value, HashMap results, DecisionNode tb, DecisionNode fb) {
        this.col = col;
        this.value = value;
        this.results = results;
        this.tb = tb;
        this.fb = fb;
    }

    public ArrayList readTreeDescription(String file_name){
       
        BufferedReader bufferedReader = null;
        ArrayList data = new ArrayList();
        ArrayList element = null;

        try {
            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(file_name));

            String line = null;

            while((line = bufferedReader.readLine())!=null){

                    element = new ArrayList();
                    String[] part = line.split("\\s+");
                    for(String s : part){
                        element.add(s);
                        //System.out.println(s);
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

    public HashMap uniqueCounts(ArrayList data){

            HashMap hm = new HashMap();
            ArrayList element = null;

            for (int i = 0; i < data.size(); i++){
               element = (ArrayList)data.get(i);
               
               if(!hm.containsKey(element.get(element.size()-1))){
                   hm.put(element.get(element.size()-1), 0);
               }
               
               hm.put(element.get(element.size()-1),(Integer)hm.get(element.get(element.size()-1))+1);
               
            } 
            
            return hm;
    }
    
    public float giniImpurity(ArrayList data){ 
        
        int total = data.size();
        
        float imp = 0.0f;
        float p1 = 0.0f;
        float p2 = 0.0f;
        
        HashMap res = this.uniqueCounts(data);
        
        Iterator iter1 = res.entrySet().iterator();
        Iterator iter2 = res.entrySet().iterator();
        
        Map.Entry e1;
        Map.Entry e2;
        
        while (iter1.hasNext()) {
            e1 = (Map.Entry)iter1.next();
            while (iter2.hasNext()){
                e2 = (Map.Entry)iter2.next();
                
                p1 = Float.parseFloat(e1.getValue().toString())/total;
                p2 = Float.parseFloat(e2.getValue().toString())/total;
                
                imp += p1*p2;
            }
            
        }

        return imp;
    }
    
    public float log2(float x){
        return (float)(Math.log(x)/Math.log(2));
    }
     
    public float entropy(ArrayList data){
        
        float total = data.size();
        float p;
               
        HashMap res = this.uniqueCounts(data);
       
        float imp = 0.0f;
       
        Iterator iter = res.entrySet().iterator();
        Map.Entry e1;
        
        while(iter.hasNext()){
            e1 = (Map.Entry)iter.next();
            p = Float.parseFloat(e1.getValue().toString())/total;
            imp +=  p * this.log2(p);
        }
        return -imp;
        
    }

    // S'HA DE FINALITZAR
    public DecisionNode buildTree(ArrayList data, String scoref, float beta){

        if(data.size()==0){
            return new DecisionNode();
        }

        float current_score = 0.0f;
        float best_result = 0.0f;
        float result = 0.0f;
        String val;
        float p = 0.0f;

        ArrayList partition_criteria = null;
        ArrayList partition = null;
        HashMap col_values = null;
        HashMap res = null;
        DecisionNode trueBranch = null;
        DecisionNode falseBranch = null;

        DecisionNode dn = null;
        Method m = null;

        try {
            m = DecisionNode.class.getMethod(scoref, ArrayList.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            dn = new DecisionNode();
            current_score = (Float)m.invoke(dn, data);            
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(int cols = 0; cols < ((ArrayList)data.get(0)).size(); cols++){
            
            col_values = new HashMap();
            for(int j=0; j<data.size(); j++){
                 if(!col_values.containsKey(((ArrayList)data.get(j)).get(cols))){
                     col_values.put(((ArrayList)data.get(j)).get(cols), 1);
                 }
                 
            }
            
            Iterator i = col_values.keySet().iterator();
            while(i.hasNext()){
            
                val = i.next().toString();
                partition = this.divideSet(data, cols, val);
                p = ((ArrayList)partition.get(0)).size() / Float.parseFloat(String.valueOf(data.size()));
                
                try {
                    result = current_score - (p * (Float) m.invoke(dn, partition.get(0))) - ((1-p) * (Float) m.invoke(dn, partition.get(1)));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(DecisionNode.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public boolean isNumeric(String value){

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
    
    public ArrayList divideSet(ArrayList part, int column, String val){
       
        ArrayList sets = new ArrayList();
        ArrayList set1 = new ArrayList();
        ArrayList set2 = new ArrayList();


        if(this.isNumeric(val)){
            float num = Float.parseFloat(val);
            
            for(int i=0; i < part.size(); i++){

                if(Float.parseFloat(((ArrayList)part.get(i)).get(column).toString()) >= num){
                    set1.add(part.get(i));
                }else{
                    set2.add(part.get(i));
                } 
            }

        }else{
            for(int i=0; i < part.size(); i++){
                if((((ArrayList)part.get(i)).get(column).toString()).equalsIgnoreCase(val)){
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
    
   public HashMap classify(ArrayList obj, DecisionNode tree){
        
        String val;
        DecisionNode branch = null;

        if(tree.results != null){
            return tree.results;
        }
        else{
            
            val = obj.get(tree.col).toString();

            branch = null;

            if(this.isNumeric(val)){
                
                float num = Float.parseFloat(val);
                
                if(num >= Float.parseFloat(tree.value)){
                    branch = tree.tb;
                }
                else{
                    branch = tree.fb;
                }
            }
            else{
                if(val.equalsIgnoreCase(tree.value)){
                    branch = tree.tb;
                }
                else{
                    branch = tree.fb;
                }
            }

            return this.classify(obj, branch);
        }
    }
    
    public float testPerformance(ArrayList test_set, ArrayList training_set){
        
        DecisionNode tree = this.buildTree(training_set, "entropy", 0.5f);
        
        float num_errors = 0.0f;
        HashMap a = null;

        for(int i=0; i < training_set.size(); i++){

            a = this.classify((ArrayList)(training_set.get(i)), tree);
            if(a.toString().contains(((ArrayList)(training_set.get(i))).get(4).toString())){
                num_errors += 1.0f;
            }
        }
        return num_errors / Float.parseFloat(String.valueOf(test_set.size()));
    }

    
}