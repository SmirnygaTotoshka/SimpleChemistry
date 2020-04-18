package smirnygatotoshka.ru.simplechemistry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;

import smirnygatotoshka.ru.simplechemistry.MainActivity.Scenario;

/**
 * Activity, выполняющее обработку сценария {@link Scenario#DECIDE_URAV}
 * @author SmirnygaTotoshka
 * */
/*public class DecideEqualationActivity extends AppCompatActivity
{

private DecideEqualationFragment fragment;
private DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener(){

	/**
	 * This method will be invoked when a button in the dialog is clicked.
	 *
	 * @param dialog The dialog that received the click.
	 * @param which  The button that was clicked (e.g.
	 *               {@link DialogInterface#BUTTON1}) or the position
	 */
/*	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		fragment.getDecider().dispose();
	}
};

@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.decide_equalation);

	fragment = (DecideEqualationFragment) getSupportFragmentManager().findFragmentById(R.id.decide_equalation_fragment);
	android.support.v7.app.ActionBar actionBar = getSupportActionBar();
	actionBar.setDisplayHomeAsUpEnabled(true);

}
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

@Override
public void onBackPressed()
{
	if (fragment.keyboard != null & fragment.keyboard.isVisible())
	{
		fragment.keyboard.hideKeyboard();
	}
	else
		super.onBackPressed();
}

@Override
public boolean onOptionsItemSelected(MenuItem item)
{
	if (fragment.keyboard != null && fragment.keyboard.isVisible())
		fragment.keyboard.hideKeyboard();
	if (item.getItemId() == R.id.contacts)
	{
		Intent intent = new Intent(this, ContactsActivity.class);
		startActivity(intent);
		MainActivity.currentScenario = Scenario.CONTACTS;
	}
	else
		finish();
	return true;
}

@Override
protected Dialog onCreateDialog(int id) {
	AlertDialog.Builder adb = new AlertDialog.Builder(this);
	// ?????????
	adb.setTitle(getResources().getString(R.string.ce_title));
	// ?????????
	adb.setMessage(fragment.getErrorMessage());
	// ??????
	adb.setIcon(android.R.drawable.ic_dialog_info);
	// ?????? ???????????? ??????
	adb.setNeutralButton(getResources().getString(R.string.ce_good), myClickListener);
	// ??????? ??????
	return adb.create();

}
}*/
public class DecideEqualationActivity extends AppCompatActivity implements View.OnClickListener{
private Button equalize;
private EditText equaltion_text;
private TextView text_result;
private ChemistryKeyboard keyboard;
private DeciderEqualation decider;
private PrinterEqualation printer;
private String error_message;
@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.equalation);

	android.support.v7.app.ActionBar actionBar = getSupportActionBar();
	actionBar.setDisplayHomeAsUpEnabled(true);

	equalize = (Button) findViewById(R.id.equal);
	equalize.setOnClickListener(this);
	equalize.setTypeface(SimpleChemistry.adana_script);

	equaltion_text = (EditText) findViewById(R.id.equaltion);
	equaltion_text.setOnClickListener(this);
	equaltion_text.setTypeface(SimpleChemistry.adana_script);
	keyboard = new ChemistryKeyboard(this, R.id.keyboardview, R.xml.formula_keyboard, false);
	keyboard.registerEditText(equaltion_text);

	text_result = (TextView) findViewById(R.id.result);
	text_result.setTypeface(SimpleChemistry.adana_script);

	decider = new DeciderEqualation();

}
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu, menu);
	return super.onCreateOptionsMenu(menu);
}
@Override
public void onClick(View v)
{
	try
	{
		if((v.getId() == R.id.equal) & (!TextUtils.isEmpty(equaltion_text.getText().toString())))
		{

			decider.equalize(equaltion_text.getText().toString());
			if(MainActivity.currentScenario == MainActivity.Scenario.DECIDE_URAV)
			{
				printer = new PrinterEqualation(decider.getEqualation());
				printer.formResult();
				String result = printer.getResult().get(0);
				text_result.setText(result);
				YandexMetrica.reportEvent(result);
				printer.dispose();
			}
			decider.dispose();
		}
	}
	catch(ChemistryException ce)
	{
		Log.d(SimpleChemistry.TAG, "---------------------------");
		Log.d(SimpleChemistry.TAG,ce.getErrorMessage(this));

		error_message = ce.getErrorMessage(this);
		YandexMetrica.reportError(error_message, ce);
		showDialog(0);

	}
	catch(Exception e)
	{
		Log.d(SimpleChemistry.TAG,"---------------------------");
		Log.d(SimpleChemistry.TAG,e.toString());
		error_message = e.getLocalizedMessage();
		YandexMetrica.reportError(error_message, e);
		showDialog(0);
	}
	catch(Error e)
	{
		Log.d(SimpleChemistry.TAG,"---------------------------");
		Log.d(SimpleChemistry.TAG,e.toString());
		error_message = e.getLocalizedMessage();
		YandexMetrica.reportError(error_message, e);
		showDialog(0);
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
protected Dialog onCreateDialog(int id) {
	AlertDialog.Builder adb = new AlertDialog.Builder(this);
	adb.setTitle(getResources().getString(R.string.ce_title));
	adb.setMessage(error_message);
	adb.setIcon(android.R.drawable.ic_dialog_alert);
	return adb.create();

}
}