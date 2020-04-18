package smirnygatotoshka.ru.simplechemistry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by SmirnygaTotoshka on 21.06.2017.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseElementsTest {

   private SQLiteDatabase database;
   private DatabaseElements data;

    public DatabaseElementsTest() {
        super();
    }

    @Before
    public void createData()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        data = new DatabaseElements(appContext);
        database = data.getWritableDatabase();
        data.fillStartingData(database);

    }

    @Test
    public void testNotNullDatabase()
    {
        assertTrue(database != null);
    }

    @Test
    public void testCountRows()
    {
        final int expected = 110;
        Cursor cursor = database.query(DatabaseElements.TABLE_NAME, null, null, null, null, null, null);
        int actual = cursor.getCount();
        assertTrue(actual == expected);
    }

    @Test
    public void testQuery()
    {
        Cursor cursor = database.query(DatabaseElements.TABLE_NAME, null, null, null, null, null, null);
        String actual = cursor.getString(cursor.getColumnIndex(data.NAME_COLUMN[1]));
        String expected = "H";
        assertEquals(actual,expected);
    }

    @Test
    public void testElements(){
        reagent r = new reagent();
        Parser_Equalation parser = new Parser_Equalation();
        try {
            r = parser.parseReagent("K2HPO4");
        } catch (ChemistryException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("K",r.elements.get(0));
        Assert.assertEquals("H",r.elements.get(1));
        Assert.assertEquals("P",r.elements.get(2));
        Assert.assertEquals("O",r.elements.get(3));
    }



}
