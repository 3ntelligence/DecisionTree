/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 *
 * @author Oriol
 *
 * Test sobre una de les funcions que s'utilitzen per calcular el desordre d’un determinat
 * conjunt i que marca el criteri de particionament.
 * 
 * #Entrada: ArrayList amb informació
 * #Sortida: número real
 */

public class EntropyTest {
    
    public EntropyTest() {
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

    @Test
    public void testGetImpurityScoreEquals() {        
        DecisionNode dn = new DecisionNode();
        ArrayList al = new ArrayList();        
        
        ArrayList buffer1 = new ArrayList();
        ArrayList buffer2 = new ArrayList();
        
        buffer1.add("slashdot"); buffer1.add("USA"); buffer1.add("yes");
        buffer1.add("18"); buffer1.add("None");
        buffer2.add("google"); buffer2.add("France"); buffer2.add("yes");
        buffer2.add("23"); buffer2.add("None");
        
        al.add(buffer1);
        al.add(buffer2);
        
        System.out.println("getImpurityScore == 0 case");
        HashMap setOfData = dn.uniqueCounts(al);
        int data_length = al.size();
        Entropy instance = new Entropy();
        float expResult = 0.0f;
        float result = instance.getImpurityScore(setOfData, data_length);
        assertEquals(expResult, result, 0.0f);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testGetImpurityScoreNotEquals() {
        DecisionNode dn = new DecisionNode();
        ArrayList al = new ArrayList();        
        
        ArrayList buffer1 = new ArrayList();
        ArrayList buffer2 = new ArrayList();
        
        buffer1.add("slashdot"); buffer1.add("USA"); buffer1.add("yes");
        buffer1.add("18"); buffer1.add("None");
        buffer2.add("google"); buffer2.add("France"); buffer2.add("yes");
        buffer2.add("23"); buffer2.add("Basic");
        
        al.add(buffer1);
        al.add(buffer2);       
        
        System.out.println("getImpurityScore > 0.0 case");
        HashMap setOfData = dn.uniqueCounts(al);
        int data_length = al.size();
        Entropy instance = new Entropy();
        float expResult = 1.0f;
        float result = instance.getImpurityScore(setOfData, data_length);
        assertEquals(expResult, result, 0.000001f);
        //fail("The test case is a prototype.");
    }
}
