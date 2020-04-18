package smirnygatotoshka.ru.simplechemistry;

import android.app.Application;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;

import com.yandex.metrica.YandexMetrica;

public class SimpleChemistry extends Application {

static final String TAG = "myLogs";
static SQLiteDatabase dataElements,dataSolubility;
static Typeface adana_script;

    @Override
    public void onCreate() {
        super.onCreate();
        //start AppMetrica SDK
        YandexMetrica.activate(getApplicationContext(),"f6b8b830-8a35-40de-b6f2-e4961097d570");
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetrica.setReportCrashesEnabled(true);
    	YandexMetrica.reportEvent("App start.");
        adana_script = Typeface.createFromAsset(getAssets(),"segoescb.ttf");


        DatabaseElements databaseElements = new DatabaseElements(this);
        SimpleChemistry.dataElements = databaseElements.getWritableDatabase();

        DatabaseSolubility databaseSolubility = new DatabaseSolubility(this);
        SimpleChemistry.dataSolubility = databaseSolubility.getWritableDatabase();
    }

}

