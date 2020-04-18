package smirnygatotoshka.ru.simplechemistry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;

public class DecideEqualationFragment extends Fragment implements View.OnClickListener
{
private Button equalize;
private EditText equaltion_text;
private TextView text_result;
ChemistryKeyboard keyboard;

public DeciderEqualation getDecider()
{
    return decider;
}

private DeciderEqualation decider;

public PrinterEqualation getPrinter()
{
    return printer;
}

private PrinterEqualation printer;
private Activity host;
private String error_message;

/**
 * Called to have the fragment instantiate its user interface view.
 * This is optional, and non-graphical fragments can return null (which
 * is the default implementation).  This will be called between
 * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
 * <p>
 * <p>If you return a View from here, you will later be called in
 * {@link #onDestroyView} when the view is being released.
 *
 * @param inflater           The LayoutInflater object that can be used to inflate
 *                           any views in the fragment,
 * @param container          If non-null, this is the parent view that the fragment's
 *                           UI should be attached to.  The fragment should not add the view itself,
 *                           but this can be used to generate the LayoutParams of the view.
 * @param savedInstanceState If non-null, this fragment is being re-constructed
 *                           from a previous saved state as given here.
 * @return Return the View for the fragment's UI, or null.
 */
@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState)
{
   View root = inflater.inflate(R.layout.equalation, container, false);

    host = getActivity();
    keyboard = new ChemistryKeyboard(host, R.id.keyboardview, R.xml.formula_keyboard, false,root);

    equalize = (Button) root.findViewById(R.id.equal);
    equalize.setOnClickListener(this);
    equalize.setTypeface(SimpleChemistry.adana_script);

    equaltion_text = (EditText) root.findViewById(R.id.equaltion);
    equaltion_text.setOnClickListener(this);
    equaltion_text.setTypeface(SimpleChemistry.adana_script);
    keyboard.registerEditText(equaltion_text);

    text_result = (TextView) root.findViewById(R.id.result);
    text_result.setTypeface(SimpleChemistry.adana_script);

    decider = new DeciderEqualation();
    return root;
}


@Override
public void onClick(View v)
{
    try
    {
        if((v.getId() == R.id.equal) /*& (!TextUtils.isEmpty(equaltion_text.getText().toString()))*/)
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
        Log.d(SimpleChemistry.TAG,ce.getErrorMessage(host));

        error_message = ce.getErrorMessage(host);
        YandexMetrica.reportError(error_message, ce);
        getActivity().showDialog(0);

    }
    catch(Exception e)
    {
        Log.d(SimpleChemistry.TAG,"---------------------------");
        Log.d(SimpleChemistry.TAG,e.toString());
        error_message = e.getLocalizedMessage();
        YandexMetrica.reportError(error_message, e);
        host.showDialog(0);
    }
    catch(Error e)
    {
        Log.d(SimpleChemistry.TAG,"---------------------------");
        Log.d(SimpleChemistry.TAG,e.toString());
        error_message = e.getLocalizedMessage();
        YandexMetrica.reportError(error_message, e);
        host.showDialog(0);
    }

}


public String getErrorMessage()
{
    return error_message;
}
}
