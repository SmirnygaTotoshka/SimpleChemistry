package smirnygatotoshka.ru.simplechemistry;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by SmirnygaTotoshka on 12.07.2017.
 */

public class Parser_EqualationTest {
    Parser_Equalation parser = new Parser_Equalation();
    final String equalation = "NO2+P2O3 + KOH= NO+K2HPO4+H2O ";
    ArrayList<reagent> reagents;

    @Before
    public void parse()
    {
        try
        {
            reagents =  parser.parseFormulsFromEqualation(equalation);
        }
        catch (ChemistryException e)
        {
            Log.d(SimpleChemistry.TAG, e.getMessage());
        }
    }

    @Test
    public void testSplit()
    {
        assertTrue(reagents.size() == 6);
    }

    @Test
    public void testFormula()
    {
        assertEquals("NO2",reagents.get(0).formula);
        assertEquals("P2O3",reagents.get(1).formula);
        assertEquals("KOH",reagents.get(2).formula);
        assertEquals("NO",reagents.get(3).formula);
    }


}
