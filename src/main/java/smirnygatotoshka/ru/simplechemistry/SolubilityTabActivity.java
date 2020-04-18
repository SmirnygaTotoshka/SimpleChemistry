
package smirnygatotoshka.ru.simplechemistry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;


/**
 * Activity, выполняющее обработку сценария {@link MainActivity.Scenario#TAB_RASTVOR}
 * @author SmirnygaTotoshka
 * */
public class SolubilityTabActivity extends AppCompatActivity implements OnClickListener
{
	//Возможные результаты запросов,значение равно положению в массиве строк с соответствующим результатом
	/**Вещество является растворителем(водой)*/
	private final int AQUA = 0;
	/**Вещество хорошо растворяется(>1г на 100г)*/
	private final int GOOD = 1;
	/**Вещество хорошо растворяется(от 0,1 до 1 г на 100 г)*/
	private final int BAD = 2;
	/**Вещество хорошо растворяется(<1г на 100г)*/
	private final int NO = 3;
	/**Вещество подвергается гидролизу*/
	private final int DESTROY = 4;
	/** Нет достоверных сведений о существовании соединения*/
	private final int UNKNOWN = 5;
	/** Соединение с данной формулой не найдено в базе*/
	private final int NOT_FOUND = 6;

	private Button make_inquiry;
	private EditText text_inquiry;
	private TextView answer_inquiry;
	private TextView title;
	private ChemistryKeyboard keyboard;

	private String[] results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inquiry);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		YandexMetrica.reportEvent(MainActivity.currentScenario.name());

		title = (TextView) findViewById(R.id.title_inquiry);
		title.setTypeface(SimpleChemistry.adana_script);
		title.setText(getString(R.string.tr_title));

		make_inquiry = (Button) findViewById(R.id.send_inquiry);
		make_inquiry.setTypeface(SimpleChemistry.adana_script);
		make_inquiry.setOnClickListener(this);
		make_inquiry.setText(getString(R.string.tr_button));

		keyboard = new ChemistryKeyboard(this, R.id.inq_keyboardview, R.xml.formula_keyboard, false);
		keyboard.registerEditText(R.id.write_inquiry);

		text_inquiry = (EditText) findViewById(R.id.write_inquiry);
		text_inquiry.setTypeface(SimpleChemistry.adana_script);

		answer_inquiry = (TextView) findViewById(R.id.result);
		answer_inquiry.setTypeface(SimpleChemistry.adana_script);

		results = getResources().getStringArray(R.array.rastvor_otvet);
	}

	@Override
	public void onClick(View v) {
		if (!TextUtils.isEmpty(text_inquiry.getText().toString()))
		{
			try {
				int result = DatabaseSolubility.getFromData(text_inquiry.getText().toString());
				answer_inquiry.setText(results[result]);
			}
			catch (ChemistryException e) {
				answer_inquiry.setText(results[NOT_FOUND]);
				Log.d(SimpleChemistry.TAG,e.getErrorMessage(this));
			}
		}
	}
	@Override
	public void onBackPressed()
	{
	   if(keyboard!=null && keyboard.isVisible() )
	        keyboard.hideKeyboard();
	   else
		   super.onBackPressed();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.contacts)
		{
			Intent intent = new Intent(this, ContactsActivity.class);
			startActivity(intent);
			MainActivity.currentScenario = MainActivity.Scenario.CONTACTS;
		}
		else if(keyboard!=null && keyboard.isVisible() )
			 keyboard.hideKeyboard();
		else
			finish();

		return super.onOptionsItemSelected(item);
	}
}
