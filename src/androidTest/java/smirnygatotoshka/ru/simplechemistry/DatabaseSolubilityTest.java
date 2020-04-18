package smirnygatotoshka.ru.simplechemistry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by SmirnygaTotoshka on 21.06.2017.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseSolubilityTest {
    private SQLiteDatabase database;

    public DatabaseSolubilityTest() {
        super();
    }

    @Test
    public void createDatabase()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DatabaseSolubility data = new DatabaseSolubility(appContext);


        try {
            assertEquals(0,data.getFromData("HOH"));
            assertEquals(0,data.getFromData("H2O"));
            assertEquals(1,data.getFromData("NH4Cl"));
            assertEquals(1,data.getFromData("K2CO3"));
            assertEquals(3,data.getFromData("PbSO4"));
            assertEquals(2,data.getFromData("BaF2"));
            assertEquals(4,data.getFromData("Al2S3"));
            assertEquals(5,data.getFromData("Fe2(SO3)3"));
            assertEquals(5,data.getFromData("(NH4)2SiO3"));
        } catch (ChemistryException e) {
            e.printStackTrace();
        }
    }
}
