/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeapp;

import java.util.ArrayList;
import java.util.ResourceBundle;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Oriol
 *
 * S'ha realitzat el test sobre la funció ReadDataProblem ja que és una funció important perquè
 * realitza tot el processament de dades sobre el fitxer d'entrada.
 *
 * #Entrada: text simple
 * #Sortida: ArrayList d’objectes de tipus string
 */

public class ReadDataTest {
    
    public ReadDataTest() {
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
    public void testReadDataProblem() {
        String configuration_file = "decisiontreeapp.configuration";
        ResourceBundle rb = ResourceBundle.getBundle(configuration_file);
        
        System.out.println("ReadDataProblem");
        ReadData instance = new ReadData(rb.getString("test_file"));
        ArrayList expResult = new ArrayList();
        ArrayList buffer1 = new ArrayList();
        ArrayList buffer2 = new ArrayList();
        
        buffer1.add("slashdot"); buffer1.add("USA"); buffer1.add("yes");
        buffer1.add("18"); buffer1.add("None");
        buffer2.add("google"); buffer2.add("France"); buffer2.add("yes");
        buffer2.add("23"); buffer2.add("Premium");
        
        expResult.add(buffer1);
        expResult.add(buffer2);
        
        ArrayList result = instance.ReadDataProblem();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
}
