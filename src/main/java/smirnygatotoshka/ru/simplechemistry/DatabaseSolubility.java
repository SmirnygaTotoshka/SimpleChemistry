package smirnygatotoshka.ru.simplechemistry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SmirnygaTotoshka on 20.06.2017.
 */
public class DatabaseSolubility extends SQLiteOpenHelper {
    final static int DB_VER = 4;
    final static String DB_NAME = "solubility.db";
    final static String TABLE_NAME = "solubility";
    private String[] formuls;
    final int numberFormuls;
    private int[] solubility;
    public DatabaseSolubility(Context context) {
        super(context, DB_NAME, null, DB_VER);
        formuls = context.getResources().getStringArray(R.array.formul_solubility);
        numberFormuls = formuls.length;
        String[] solub = context.getResources().getStringArray(R.array.solubility_data);
        solubility = new int[solub.length];
        for(int i = 0;i < formuls.length;i++)
        {
            solubility[i] =  Integer.parseInt(solub[i]);
        }
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(SimpleChemistry.TAG, " DatabaseSolubility onCreate()");
        // создаем таблицу с полями
        db.execSQL("create table if not exists "+TABLE_NAME+"("+" id integer primary key,"+ "formula text," + "solubility integer"+");");
    fillStartingData(db);
    }

    boolean fillStartingData(SQLiteDatabase db)
    {
        try{
            ContentValues contentValues = new ContentValues();

            for(int i = 0;i < numberFormuls;i++)
            {
                contentValues.put("formula",formuls[i]);
                contentValues.put("solubility",solubility[i]);
                long rowID = db.insert(TABLE_NAME, null, contentValues);
                Log.d(SimpleChemistry.TAG, "row inserted, ID = " + rowID);
                contentValues.clear();
            }
            return true;
        }
        catch (Exception e)
        {
            Log.d(SimpleChemistry.TAG,e.getMessage());
            return false;
        }
        catch (Error e)
        {
            Log.d(SimpleChemistry.TAG,e.getMessage());
            return false;
        }

    }
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != DB_VER) {
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
            fillStartingData(db);

        }

    }

    /**Возращает экземпляр объекта из базы данных по символу ХЭ в периодической таблице
     * @param symbol - Символ элемента в таблице Менделеева
     * */
    public static int getFromData(String symbol)throws ChemistryException
    {
        Cursor c = SimpleChemistry.dataSolubility.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex("formula")).replace(" ","").equals(symbol))
                {
                    int sol = c.getInt(c.getColumnIndex("solubility"));

                    return sol;
                }
            } while (c.moveToNext());
            c.close();
            throw new ChemistryException(R.string.not_found_element);
        }
        else
        {
            c.close();
            throw new ChemistryException(R.string.empty_data);

        }

    }

}
