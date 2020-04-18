package smirnygatotoshka.ru.simplechemistry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class PeriodTabActivity extends AppCompatActivity implements OnClickListener {


    private final int startID = 330;
    private TableRow[] rows_short_table = new TableRow[13];
    private TableRow[] rows_long_table = new TableRow[10];

	private LayoutInflater inflater;
	private TableLayout table;
    private int num_element = 1;
	private boolean isShort = true;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ptce);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		table = (TableLayout) findViewById(R.id.table);
        inflater = getLayoutInflater();

        createShortTable();
        showTable();
        isShort = false;
        num_element = 1;
        createLongTable();
        isShort = true;
	}

private void showTable()
{
    if(isShort)
        for(int i = 0;i < rows_short_table.length;i++)
            table.addView(rows_short_table[i]);
    else
        for(int i = 0;i < rows_long_table.length;i++)
            table.addView(rows_long_table[i]);
}

private void createShortTable()
    {
        for(int i = 0;i < rows_short_table.length;i++)
        {
            TableRow row = createTableRow(i);
            rows_short_table[i] = row;

            switch(i)
            {
                case 0:
                    createFirstPeriod(row);
                    break;
                case 1:case 2:
                createSmallPeriod(row);
                break;
                case 3:case 4:case 5:case 6:case 7:case 8:case 9:
                createBigPeriod(row);
                break;
                case 10:
                    createTextView(row);
                    break;
                default:
                    createExtra(row);
                    break;
            }
        }
    }


private void createExtra(TableRow row)
{
    try
    {
        if(isShort)
        {
            if(row.getId() == 11)
            {
                num_element = 58;
            }
            else
                num_element = 90;

            for(int i = 0;i < 14;i++)
                createButton(row);
        }
        else
        {
            createTextView(row);
            createTextView(row);
            if(row.getId() == 8)
            {
                num_element = 57;
            }
            else
                num_element = 89;

            for(int i = 0;i < 15;i++)
                createButton(row);
        }
    }
    catch(ChemistryException e)
    {
        Log.d(SimpleChemistry.TAG,e.getErrorMessage(this));
        e.printStackTrace();
    }
    catch(NullPointerException n)
    {
        n.printStackTrace();
    }
}

private void createBigPeriod(TableRow row)
{
    try
    {
        if(isShort)
        {
            if(row.getId() % 2 == 1)
            {

                for(int i = 0;i < 10;i++)
                {
                    createButton(row);
                    if(row.getId() == 7 & num_element == 58)
                    {
                        num_element += 14;
                    }
                    if(row.getId() == 9 & num_element == 90)
                    {
                        num_element += 14;
                    }
                }
            }
            else
            {
                createSmallPeriod(row);
            }
        }
        else
        {
            for(int i = 0;i < 18;i++)
            {
                if((num_element == 57)|(num_element == 89))
                {
                    createTextView(row);
                    num_element += 15;
                    continue;
                }
                else
                    createButton(row);

            }
        }
    }
    catch(ChemistryException e)
    {
        Log.d(SimpleChemistry.TAG,e.getErrorMessage(this));
        e.printStackTrace();
    }
    catch(NullPointerException n)
    {
        n.printStackTrace();
    }

}

private void createSmallPeriod(TableRow tableRow)
{
    try
    {
        if(isShort)
        {
            for(int i = 0;i < 7;i++)
                createButton(tableRow);
            for(int i = 0;i < 3;i++)
                createTextView(tableRow);
            createButton(tableRow);
        }
        else
        {
            createButton(tableRow);
            createButton(tableRow);
            for(int i = 0;i < 10;i++)
                createTextView(tableRow);
            for(int i = 0;i < 6;i++)
                createButton(tableRow);
        }
    }
    catch(ChemistryException e)
    {
        Log.d(SimpleChemistry.TAG,e.getErrorMessage(this));
        e.printStackTrace();
    }
    catch(NullPointerException n)
    {
        n.printStackTrace();
    }
}

private void createFirstPeriod(TableRow tableRow)
{
    try
    {
        int space;
        if (isShort)
            space = 9;
        else
            space = 16;
        createButton(tableRow);
       for(int i = 0;i < space;i++)
        {
            createTextView(tableRow);
        }
        createButton(tableRow);
    }
    catch(ChemistryException e)
    {
        Log.d(SimpleChemistry.TAG,e.getErrorMessage(this));
        e.printStackTrace();
    }
    catch(NullPointerException n)
    {
        n.printStackTrace();
    }

}

public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_period_tab, menu);
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
		else if(item.getItemId() == R.id.change_table)
        {
            isShort = !isShort;
            item.setTitle(isShort ? R.string.long_table : R.string.short_table);
            table.removeAllViews();
            num_element = 1;
            if(isShort)
                createShortTable();
            else
                createLongTable();

            showTable();
        }
		else
			finish();
		return super.onOptionsItemSelected(item);
	}

private void createLongTable()
{
    for(int i = 0;i < rows_long_table.length;i++)
    {
        TableRow row = createTableRow(i);
        rows_long_table[i] = row;
        switch(i)
        {
            case 0:
                createFirstPeriod(row);
                break;
            case 1:case 2:
                createSmallPeriod(row);
                break;
            case 3:case 4:case 5:case 6:
                 createBigPeriod(row);
                 break;
            case 7:
                createTextView(row);
                break;
            default:
                createExtra(row);
                break;
        }
    }
}

@Override
	public void onClick(View v) {
		Intent i = new Intent(this, AboutElementActivity.class);
		startActivity(i);
		smirnygatotoshka.ru.simplechemistry.AboutElementActivity.element = (Element) v.getTag();
	}

	private void createButton(TableRow row) throws ChemistryException
    {
        Element e = DatabaseElements.getFromData(num_element,SimpleChemistry.dataElements);
        if (e == null) throw new ChemistryException(R.string.not_found_element);

        View item = inflater.inflate(R.layout.item_button,row,false);
        Button button = (Button)  item.findViewById(R.id.button);
        button.setText(e.serial_number+"\n"+e.name+ "  "+ e.symbol+"\n"+ Math.abs(e.atomic_weight));
        button.setTypeface(SimpleChemistry.adana_script);
        button.setTag(e);
        button.setId(startID - num_element);
        button.setOnClickListener(this);
        Log.d(SimpleChemistry.TAG,"Кнопка " + e.symbol + " создана");
        num_element++;
        row.addView(item);
    }

    private void createTextView(TableRow row)
    {
        View v = inflater.inflate(R.layout.item_textview,row,false);
        TextView textView = (TextView) v.findViewById(R.id.text);
        textView.setVisibility(View.INVISIBLE);
        row.addView(v);
    }

    private TableRow createTableRow(int num)
    {
        if ((num >= rows_short_table.length & isShort)|(num >= rows_long_table.length & !isShort)| num < 0)
            return null;
        TableRow row = new TableRow(this);
        row.setId(num);
        return row;
    }



	
}
