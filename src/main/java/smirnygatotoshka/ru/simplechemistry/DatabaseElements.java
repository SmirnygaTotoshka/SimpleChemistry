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

public class DatabaseElements extends SQLiteOpenHelper
{
    final static int DB_VER = 5;
    final static String DB_NAME = "chemistryelements.db";
    final static String TABLE_NAME = "chemistryelements";
    /**Данные столбцов БД*/
    private String[] symbols,names,groups,periods,classes,configurations,
            maxstepens,minstepens,kip,plav,weights;//Информация загружается с файлов ресурсов
    final int numberElements;
    /**Имена столбцов БД */
    static String[] NAME_COLUMN;
    private Context ctx;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database

     */
    public DatabaseElements(Context context) {
        super(context, DB_NAME, null, DB_VER);
        ctx = context;
        NAME_COLUMN = ctx.getResources().getStringArray(R.array.db_el_name_column);
        symbols = context.getResources().getStringArray(R.array.elements_symbol);
        numberElements = symbols.length;
        names = context.getResources().getStringArray(R.array.element_name);
        groups = context.getResources().getStringArray(R.array.elements_group);
        periods = context.getResources().getStringArray(R.array.elements_period);
        classes = context.getResources().getStringArray(R.array.elements_classes);
        configurations = context.getResources().getStringArray(R.array.configurations);
        maxstepens = new String[symbols.length];
        minstepens = new String[symbols.length];
        plav = new String[symbols.length];
        kip = new String[symbols.length];
        weights = new String[symbols.length];

        String[] data = context.getResources().getStringArray(R.array.el_data);
        for (int i = 0;i < data.length;i++)
        {
            String[] a = data[i].split(",");
            maxstepens[i] = a[0];
            minstepens[i] = a[1];
            plav[i] = a[2];
            kip[i] = a[3];
            weights[i] = a[4];
        }
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(SimpleChemistry.TAG, " DatabaseElements onCreate()");
        // создаем таблицу с полями
        db.execSQL("create table if not exists "+TABLE_NAME+" (" +"id integer primary key autoincrement,"+NAME_COLUMN[0]+ " integer,"+NAME_COLUMN[1]+ " text,"+NAME_COLUMN[2]+ " text,"+NAME_COLUMN[3] + " integer,"+
                NAME_COLUMN[4]+ " integer, "+NAME_COLUMN[5] + " integer, "+NAME_COLUMN[6] + " text," +NAME_COLUMN[7]+
                " blob,"+NAME_COLUMN[8] + " text," +NAME_COLUMN[9]+ " integer,"+NAME_COLUMN[10] + " integer,"+NAME_COLUMN[11] + " double,"+NAME_COLUMN[12] + " double," + NAME_COLUMN[13]+" double);");
    fillStartingData(db);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != DB_VER) {
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
            fillStartingData(db);
        }
    }

    /**Заполняет строки начальной БД.
     * */
    void fillStartingData(SQLiteDatabase db)
    {
        try{
            ContentValues contentValues = new ContentValues();
            for(int number = 0;number < numberElements;number++) {
                contentValues.put(NAME_COLUMN[0], number +1);
                contentValues.put(NAME_COLUMN[1], symbols[number]);
                contentValues.put(NAME_COLUMN[2], names[number]);
                contentValues.put(NAME_COLUMN[3], groups[number]);
                contentValues.put(NAME_COLUMN[4], getUndergroup(number));
                contentValues.put(NAME_COLUMN[5], periods[number]);
                contentValues.put(NAME_COLUMN[6], classes[number]);
                contentValues.put(NAME_COLUMN[7], parseElectroConfig(configurations[number]));
                contentValues.put(NAME_COLUMN[8], getPhase(number));
                contentValues.put(NAME_COLUMN[9], Integer.parseInt(maxstepens[number]));
                contentValues.put(NAME_COLUMN[10], Integer.parseInt(minstepens[number]));
                contentValues.put(NAME_COLUMN[12], Integer.parseInt(kip[number]));
                contentValues.put(NAME_COLUMN[11], Integer.parseInt(plav[number]));
                contentValues.put(NAME_COLUMN[13], Double.parseDouble(weights[number]));

                long rowID = db.insert(TABLE_NAME, null, contentValues);
                Log.d(SimpleChemistry.TAG, "row inserted, ID = " + rowID);
                contentValues.clear();
            }
        }
        catch (Exception e)
        {
            Log.d(SimpleChemistry.TAG,e.getMessage());
        }
        catch (Error e)
        {
            Log.d(SimpleChemistry.TAG,e.getMessage());
        }

    }

    private byte[] parseElectroConfig(String configurations) {
        String[] z = configurations.split(",");
        byte[] a = new byte[z.length];
        for (int i = 0;i < a.length;i++)
        {
            a[i] = Byte.parseByte(z[i]);
        }
        return a;
    }

    private int getUndergroup(int number) {
        if ((number >= 21 & number <= 30)|(number >= 39 & number <= 48)|(number >= 57 & number <= 80)
                |(number >= 89 & number <= 110))
            return 0;
        else
            return 1;
    }

    private String getPhase(int number)
    {
        if (number == 35 | number == 80)
            return ctx.getResources().getString(R.string.el_liquid);
        else if (number == 1 | number == 2 | (number >= 7 & number <= 10) | number == 17 | number == 18
                | number == 36 | number == 54 | number == 86)
            return ctx.getResources().getString(R.string.el_gas);
        else
            return ctx.getResources().getString(R.string.el_solid);
    }


    /**Возращает экземпляр объекта из базы данных по положению в периодической таблице
     * @param position - Порядковый номер в таблице Менделеева
     * */
    public static Element getFromData(int position,SQLiteDatabase db)throws ChemistryException
    {
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(c.getColumnIndex(NAME_COLUMN[0])) == position)
                {
                    int ser_num = c.getInt(c.getColumnIndex(NAME_COLUMN[0]));
                    String sym = c.getString(c.getColumnIndex(NAME_COLUMN[1]));
                    String name = c.getString(c.getColumnIndex(NAME_COLUMN[2]));
                    int group = c.getInt(c.getColumnIndex(NAME_COLUMN[3]));
                    int un_group = c.getInt(c.getColumnIndex(NAME_COLUMN[4]));
                    int period = c.getInt(c.getColumnIndex(NAME_COLUMN[5]));
                    String clas = c.getString(c.getColumnIndex(NAME_COLUMN[6]));
                    byte[] config = c.getBlob(c.getColumnIndex(NAME_COLUMN[7]));
                    String phasa = c.getString(c.getColumnIndex(NAME_COLUMN[8]));
                    int max = c.getInt(c.getColumnIndex(NAME_COLUMN[9]));
                    int min = c.getInt(c.getColumnIndex(NAME_COLUMN[10]));
                    int plav = c.getInt(c.getColumnIndex(NAME_COLUMN[11]));
                    int kip = c.getInt(c.getColumnIndex(NAME_COLUMN[12]));
                    double weight = c.getDouble(c.getColumnIndex(NAME_COLUMN[13]));

                    Element e = new Element(ser_num,sym,name,group,un_group,period,clas,config,phasa,max,min,plav,kip,weight);
                    c.close();
                    return e;
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

    /**Возращает экземпляр объекта из базы данных по символу ХЭ в периодической таблице
     * @param symbol - Символ элемента в таблице Менделеева
     * */
    public static Element getFromData(String symbol,SQLiteDatabase db)throws ChemistryException
    {
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(NAME_COLUMN[1])).equals(symbol))
                {
                    int ser_num = c.getInt(c.getColumnIndex(NAME_COLUMN[0]));
                    String sym = c.getString(c.getColumnIndex(NAME_COLUMN[1]));
                    String name = c.getString(c.getColumnIndex(NAME_COLUMN[2]));
                    int group = c.getInt(c.getColumnIndex(NAME_COLUMN[3]));
                    int un_group = c.getInt(c.getColumnIndex(NAME_COLUMN[4]));
                    int period = c.getInt(c.getColumnIndex(NAME_COLUMN[5]));
                    String clas = c.getString(c.getColumnIndex(NAME_COLUMN[6]));
                    byte[] config = c.getBlob(c.getColumnIndex(NAME_COLUMN[7]));
                    String phasa = c.getString(c.getColumnIndex(NAME_COLUMN[8]));
                    int max = c.getInt(c.getColumnIndex(NAME_COLUMN[9]));
                    int min = c.getInt(c.getColumnIndex(NAME_COLUMN[10]));
                    int plav = c.getInt(c.getColumnIndex(NAME_COLUMN[11]));
                    int kip = c.getInt(c.getColumnIndex(NAME_COLUMN[12]));
                    double weight = c.getDouble(c.getColumnIndex(NAME_COLUMN[13]));

                    Element e = new Element(ser_num,sym,name,group,un_group,period,clas,config,phasa,max,min,plav,kip,weight);
                    c.close();
                    return e;
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
