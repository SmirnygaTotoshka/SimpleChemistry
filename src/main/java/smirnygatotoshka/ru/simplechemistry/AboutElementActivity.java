package smirnygatotoshka.ru.simplechemistry;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ===================================================================================
 * SimpleChemistry : Утилита для химиков под OS Android
 * ===================================================================================
 * 
 * @author SmirnygaTotoshka
 * @date 25.08.2014-09.04.2016
 * @version 1.5(12.03.2016)
 * Стартовое окно приложение
 */

public class AboutElementActivity extends AppCompatActivity
{

	//    
	private final String[] electro_config = { "1s(", ")2s(", ")2p(", ")3s(", ")3p(",
			")4s(", ")3d(", ")4p(", ")5s(", ")4d(", ")5p(", ")6s(", ")4f(",
			")5d(", ")6p(", ")7s(", ")5f(", ")6d" };
	static Element element;
TextView message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_element);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		message = (TextView) findViewById(R.id.message);
		message.setTypeface(SimpleChemistry.adana_script);
		message.setTextSize(18);
		try
		{
			message.setText(getElementInformation());
		}
		catch(NullPointerException e)
		{
			Log.d(SimpleChemistry.TAG,e.getMessage());
			message.setText(e.getMessage());
		}

	}
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
	}

	private String getElementInformation() {
		
		
		String name = getResources().getString(R.string.ae_name) + " == "+ element.name + "\n";
		String serial_number = getResources().getString(R.string.ae_serial_number) + " == "	+  element.serial_number + "\n";
		String symbol = getResources().getString(R.string.ae_symbol) + " == "	+ element.symbol + "\n";
		String atomic_weight = getAtomicWeight();
		String el_config = getConfig();
		String koordinat =getResources().getString(R.string.ae_ptce) + " == "+ element.group + " "+getResources().getString(R.string.ae_group)
				+ getUndergroup() + element.period
				+ " " + getResources().getString(R.string.ae_period) + "\n";
		String class_element = getResources().getString(R.string.ae_class_element) + " == " + element.class_element
				+ "\n";
		String phasa = getResources().getString(R.string.ae_phasa) + " == " + element.phasa + "\n";
		String proton = getResources().getString(R.string.ae_proton) + " == "
				+ element.serial_number + "\n";
		String neitron = getResources().getString(R.string.ae_neitron)	+ " == "+ Math.abs(Math.round(Math.round(Math.abs(element.atomic_weight)- element.serial_number)))
				+ "\n";
		String electron = getResources().getString(R.string.ae_electron) + " == "+element.serial_number + "\n";
		String maxstepen = getResources().getString(R.string.ae_max) + " == "+ element.maxstepen + "\n";
		String minstepen = getResources().getString(R.string.ae_min) + " == "	+ element.minstepen + "\n";
		String termodinamic = getTempreature();

		return name + serial_number + symbol + atomic_weight
				+ el_config + koordinat + class_element + phasa + proton
				+ neitron + electron + maxstepen + minstepen + termodinamic;

	}

	private String getTempreature() {
		if (element.temprature_plav == 0)
			
		    return getResources().getString(R.string.ae_plav) + " == " + getResources().getString(R.string.ae_sublim)
					+ "\n" + getResources().getString(R.string.ae_kip) + " == "
					+ element.temprature_kip + ""
					+ "\n";
		
		else if ((element.temprature_plav == 0)&& (element.temprature_kip == 0))
			
		    return getResources().getString(R.string.ae_plav) + " == " + getResources().getString(R.string.ae_ing) + "\n"
					+ getResources().getString(R.string.ae_kip) + " == " + getResources().getString(R.string.ae_ing) + "\n";
		
		else if (element.temprature_kip == 0)
		    
			return getResources().getString(R.string.ae_plav) + " == "
					+ element.temprature_plav + ""
					+ "\n" + getResources().getString(R.string.ae_kip) + " == " + getResources().getString(R.string.ae_ing)
					+ "\n";
		
		else
			
		    return getResources().getString(R.string.ae_plav) + " == "
					+ element.temprature_plav + ""
					+ "\n" + getResources().getString(R.string.ae_kip) + " == "
					+ element.temprature_kip + ""
					+ "\n";
	}

	private String getAtomicWeight() {
		if (element.atomic_weight > 0)
			return getResources().getString(R.string.ae_atomic_weight) + " == "
					+ element.atomic_weight + "\n";
		else
			return getResources().getString(R.string.ae_atomic_weight)
					+ " == "
					+ Math.abs(element.atomic_weight)
					+ "\n"
					+ getResources().getString(R.string.ae_weight_radio)
					+ "\n";
	}

	private String getUndergroup() {
		if (element.under_group == 1)
			return " " + getResources().getString(R.string.ae_main_subg) + " ";
		else
			return " " + getResources().getString(R.string.ae_adverse_subg) + " ";
	}

	private String getConfig() {
		String el_config = "";
		byte[] a = element.elctro_config;
		for (int i = 0; i < a.length; i++)
			el_config += (electro_config[i] + a[i]);
		return el_config + ")" + "\n";
	}


	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	            finish();
	            return true;
	    }

}
