package smirnygatotoshka.ru.simplechemistry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;

/**
 * Activity, выполняющее обработку сценария {@link smirnygatotoshka.ru.simplechemistry.MainActivity.Scenario#MOLAR_CALCULATE}
 * @author SmirnygaTotoshka
 * */
public class MolarCalculatorActivity extends AppCompatActivity implements OnClickListener
{
	private Button calculate;
	private TextView result;
	private EditText formula;
	private TextView title;
	private ChemistryKeyboard keyboard;

	private reagent reagent = new reagent();
	private String error_message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inquiry);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		calculate = (Button) findViewById(R.id.send_inquiry);
		calculate.setOnClickListener(this);
		calculate.setTypeface(SimpleChemistry.adana_script);
		calculate.setText(getString(R.string.mc_button));

		result = (TextView) findViewById(R.id.result);
		result.setTypeface(SimpleChemistry.adana_script);

		keyboard = new ChemistryKeyboard(this, R.id.inq_keyboardview, R.xml.formula_keyboard, false);

		formula = (EditText) findViewById(R.id.write_inquiry);
		keyboard.registerEditText(R.id.write_inquiry);
		formula.setTypeface(SimpleChemistry.adana_script);

		title = (TextView) findViewById(R.id.title_inquiry);
		title.setTypeface(SimpleChemistry.adana_script);
		title.setText(getString(R.string.mc_title));
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
				Intent intent = new Intent(this, ContactsActivity.class);
				startActivity(intent);
				MainActivity.currentScenario = MainActivity.Scenario.CONTACTS;
				break;
			default:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
		@Override
		public void onClick(View v) {

			if (!TextUtils.isEmpty(formula.getText().toString()))
			{
				try {
					result.setText(countAtomicWeight(formula.getText().toString()) + getResources().getString(R.string.mc_value));
				}
				catch(ChemistryException ce)
				{
					error_message = ce.getErrorMessage(this);
					YandexMetrica.reportError(error_message, ce);
					showDialog(0);

				}
				catch(Exception e)
				{
					error_message = e.getLocalizedMessage().toString();
					YandexMetrica.reportError(error_message, e);
					showDialog(0);
				}
				catch(Error e)
				{
					error_message = e.getLocalizedMessage().toString();
					YandexMetrica.reportError(error_message, e);
					showDialog(0);
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


		private double countAtomicWeight(String formula)throws ChemistryException {
			ParserEqualation pe = new ParserEqualation();
			ParserIndexReagent parser_index_reagent = new ParserIndexReagent();
			reagent = pe.parseReagent(formula);
			parser_index_reagent.countIndexReagent(reagent);
			return reagent.getMolecularWeight();
		}

		protected Dialog onCreateDialog(int id) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setTitle(getResources().getString(R.string.ce_title));
			adb.setMessage(error_message);
			adb.setIcon(android.R.drawable.ic_dialog_alert);
			adb.setNeutralButton(getResources().getString(R.string.ce_good), myClickListener);
			return adb.create();

		}

		android.content.DialogInterface.OnClickListener myClickListener = new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				reagent = new reagent();
			}

		};



}

