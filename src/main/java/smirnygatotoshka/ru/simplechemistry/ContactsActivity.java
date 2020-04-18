/**===================================================================================
 * Chemistry : ���������� , �������� �������� ���������� ������ ��� ��������� Android
 *===================================================================================
 * @author ����� �������
 * 
 * ��� ��������� � �������. ��������-CONTACTS
 */
package smirnygatotoshka.ru.simplechemistry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;


public class ContactsActivity extends AppCompatActivity{
	TextView contacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.contacts);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	
		YandexMetrica.reportEvent(MainActivity.currentScenario.name());
		contacts = (TextView) findViewById(R.id.my_contacts);
		contacts.setText("vk.com/smirnyga" + "\n" + "\n"
				+ "anton.smirnov.9910@gmail.com");
	}
	
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	            finish();
	            return true;
	    }
//Todo help menu
}
