/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Oriol
 */
public class DecisionNodeTest {
    
    public DecisionNodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /*
     * Realitza una prova senzilla per formar un arbre buit entrant un conjunt
     * de dades també buit
     */
    @Test
    public void testBuildTree() {
        ArrayList data = new ArrayList();
        ArrayList expResultArray = new ArrayList();
        ArrayList resultArray = new ArrayList();
        System.out.println("buildTree");
        String scoref = "entropy";
        float beta = 0.5f;
        DecisionNode expResult = new DecisionNode();
        DecisionNode result = new DecisionNode();
        result = result.buildTree(data, scoref, beta);
        
        expResultArray.add(expResult.col);expResultArray.add(expResult.value);expResultArray.add(expResult.results);
        expResultArray.add(expResult.tb);expResultArray.add(expResult.fb);
        
        resultArray.add(result.col);resultArray.add(result.value);resultArray.add(result.results);
        resultArray.add(result.tb);resultArray.add(result.fb);
        
        for(int i = 0; i < expResultArray.size(); i++){
            if (expResultArray.get(i) == null)
                i = i;
            else if (!expResultArray.get(i).equals(resultArray.get(i)))
                assertFalse(true);
        }
        assertTrue(true);
    }

     /*
     *
     * Test sobre una funció molt important, ja que s'encarrega de separar un conjunt
     * d'informació en dos conjunts resultants segons el criteri de partició.
     * #Entrada: ArrayList
     * #Sortida: ArrayList format per dues ArrayList; una per cada conjunt resultant.
     */
    @Test
    public void testDivideSet() {
        System.out.println("divideSet");
        // dades inicials i buffers
        ArrayList part = new ArrayList();        
        ArrayList dataBuffer1 = new ArrayList();
        ArrayList dataBuffer2 = new ArrayList();
        ArrayList dataBuffer3 = new ArrayList();
        ArrayList expBuffer1 = new ArrayList();
        ArrayList expBuffer2 = new ArrayList();
        
        // declaració dades inicials
        dataBuffer1.add("slashdot"); dataBuffer1.add("USA"); dataBuffer1.add("yes");
        dataBuffer1.add("18"); dataBuffer1.add("None");
        dataBuffer2.add("google"); dataBuffer2.add("France"); dataBuffer2.add("yes");
        dataBuffer2.add("23"); dataBuffer2.add("Premium");
        dataBuffer3.add("google"); dataBuffer3.add("Spain"); dataBuffer3.add("yes");
        dataBuffer3.add("20"); dataBuffer3.add("Premium");
        part.add(dataBuffer1);
        part.add(dataBuffer2);
        part.add(dataBuffer3);
        
        // dades esperades
        ArrayList expResult = new ArrayList();
        expBuffer1.add(dataBuffer2);
        expBuffer1.add(dataBuffer3);
        expBuffer2.add(dataBuffer1);
        expResult.add(expBuffer1);
        expResult.add(expBuffer2);

        int column = 0;
        String value = "google";
        DecisionNode instance = new DecisionNode();
        ArrayList result = instance.divideSet(part, column, value);
        
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
}
