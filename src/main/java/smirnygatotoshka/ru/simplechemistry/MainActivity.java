
package smirnygatotoshka.ru.simplechemistry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * ===================================================================================
 * SimpleChemistry : Утилита для химиков под OS Android
 * ===================================================================================
 * 
 * @author SmirnygaTotoshka
 * @date 25.08.2014-09.04.2016(12.03.2017)
 * @version 1.5
 * Стартовое окно приложение
 */
public class MainActivity extends AppCompatActivity implements OnClickListener
{
/** Возможные сценарии,выполняемые приложением */
enum Scenario
{
    /** Вычисление коэффициентов химического уравнения */
    DECIDE_URAV,
    /** Таблица Менделеева */
    PERIOD_TAB,
    /** Таблица растворимости кислот,оснований и солей */
    TAB_RASTVOR,
    /** Молярный калькулятор */
    MOLAR_CALCULATE,
    /** Контакты */
    CONTACTS
};

static Scenario currentScenario;
private Button decide_equal,period_tab,solub_tab,molar_calc;


@Override
protected void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	Log.d(SimpleChemistry.TAG, "Start program" + getDate());
	setContentView(R.layout.main);



	TextView motto = (TextView) findViewById(R.id.motto);
	motto.setTypeface(SimpleChemistry.adana_script);

	decide_equal = (Button) findViewById(R.id.decide_urav);
	setupButton(decide_equal);

	period_tab = (Button) findViewById(R.id.period_tab);
	setupButton(period_tab);

	solub_tab = (Button) findViewById(R.id.solubility_tab);
	setupButton(solub_tab);

	molar_calc = (Button) findViewById(R.id.molar_calculator);
	setupButton(molar_calc);

}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.contacts:
				Intent intent = new Intent(this,ContactsActivity.class);
				startActivity(intent);
				currentScenario = Scenario.CONTACTS;
				break;
		/* TODO	case R.id.rate_app:
				Uri address = Uri.parse("http://developer.alexanderklimov.ru");
				Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
				startActivity(openlinkIntent);
				break;
			case R.id.delete_ad:
				Uri address = Uri.parse("http://developer.alexanderklimov.ru");
				Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
				startActivity(openlinkIntent);
				break;*/
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
public void onClick(View v)
{
	switch (v.getId())
	{
		case R.id.decide_urav:
			Intent intent = new Intent(this, DecideEqualationActivity.class);
			startActivity(intent);
			currentScenario = Scenario.DECIDE_URAV;
		break;

		case R.id.period_tab:
			Intent intent6 = new Intent(this, PeriodTabActivity.class);
			startActivity(intent6);
			currentScenario = Scenario.PERIOD_TAB;
		break;
		
		case R.id.solubility_tab:
			Intent intent2 = new Intent(this, SolubilityTabActivity.class);
			startActivity(intent2);
			currentScenario = Scenario.TAB_RASTVOR;
		break;

		case R.id.molar_calculator:
			Intent intent3 = new Intent(this, MolarCalculatorActivity.class);
			startActivity(intent3);
			currentScenario = Scenario.MOLAR_CALCULATE;
		break;
		
	}
	Log.d(SimpleChemistry.TAG, "Our scenario = " + currentScenario);
//	YandexMetrica.reportEvent(currentScenario.toString());
	Log.d(SimpleChemistry.TAG, "-------------------------------------------------------");
}

//TODO - set method name
private void setupButton(Button button)
{
	int width = getWindowManager().getDefaultDisplay().getWidth();

	button.setTypeface(SimpleChemistry.adana_script);
	button.setWidth(width / 2 - 20);
	button.setOnClickListener(this);
}

private String getDate()
{
	Date date = new Date();
	int month = date.getMonth() + 1;
	int year = date.getYear() + 1900;
	return date.getDate() + "." + month + "." + year + " " + date.getHours() + ":" + date.getMinutes();
}

}
