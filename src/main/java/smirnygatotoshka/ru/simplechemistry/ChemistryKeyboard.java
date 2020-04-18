package smirnygatotoshka.ru.simplechemistry;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ChemistryKeyboard
{

private KeyboardView keyboard;
private Activity host_activity;
private boolean isDecimalKeyboard;

private final int KEYBOARD_123 = 46;
private final int KEYBOARD_1_55 = 44;
private final int KEYBOARD_56_110 = 45;
private final int SPACE = 32;
private final int DELETE = -5;
private final int OK = -4;
private final int DeltaElementId = 100;

private KeyboardView.OnKeyboardActionListener OnKeyboardActionListener = new OnKeyboardActionListener()
{
@Override
public void onKey(int primaryCode, int[] keyCodes)
{
	View focus_current = host_activity.getWindow().getCurrentFocus();
	if (focus_current == null || (focus_current.getClass() != android.support.v7.widget.AppCompatEditText.class)) return;
	
	EditText edittext = (EditText) focus_current;
	Editable editable = edittext.getText();
	
	int start = edittext.getSelectionStart();
	int end = edittext.getSelectionEnd();
	edittext.setSelection(editable.length());
	if (end > start) editable.delete(start, end);
	
	switch (primaryCode)
	{
		case KEYBOARD_123:
			keyboard.setKeyboard(new Keyboard(host_activity, R.xml.decimal_keyboard));
			isDecimalKeyboard = true;
		break;
		case KEYBOARD_1_55:
			keyboard.setKeyboard(new Keyboard(host_activity, R.xml.formula_keyboard));
			isDecimalKeyboard = false;
		break;
		case KEYBOARD_56_110:
			keyboard.setKeyboard(new Keyboard(host_activity, R.xml.formula_keyboard1));
			isDecimalKeyboard = false;
		break;
		case SPACE:
			editable.insert(start, " ");
		break;
		case DELETE:
			if ((editable != null) && (start > 0)) editable.delete(start - 1, start);

		break;
		case OK:
			hideKeyboard();
		break;
		default:
			if (isDecimalKeyboard)
				editable.insert(start, Character.toString((char) primaryCode));
			else
				try {
					String symbol = DatabaseElements.getFromData(primaryCode - DeltaElementId,SimpleChemistry.dataElements).symbol;
					Log.d(SimpleChemistry.TAG,"symbol="+symbol);
					editable.insert(start, symbol);//isolate try-catch
				} catch (ChemistryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		break;
		
	}
	
}

@Override
public void onPress(int primaryCode)
{

}

@Override
public void onRelease(int primaryCode)
{
	// TODO Auto-generated method stub
	
}

@Override
public void onText(CharSequence text)
{
	// TODO Auto-generated method stub
	
}

@Override
public void swipeLeft()
{
	// TODO Auto-generated method stub
	
}

@Override
public void swipeRight()
{
	// TODO Auto-generated method stub
	
}

@Override
public void swipeDown()
{
	// TODO Auto-generated method stub
	
}

@Override
public void swipeUp()
{
	// TODO Auto-generated method stub
	
}
};

public ChemistryKeyboard(Activity host, int view_id, int keyboard_layout_id, boolean decimal_keyboard)
{
	host_activity = host;
	isDecimalKeyboard = decimal_keyboard;
	keyboard = (KeyboardView) host_activity.findViewById(view_id);
	keyboard.setKeyboard(new Keyboard(host_activity, keyboard_layout_id));
	keyboard.setPreviewEnabled(false);
	keyboard.setOnKeyboardActionListener(OnKeyboardActionListener);
	host_activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
}

public ChemistryKeyboard(Activity host, int view_id, int keyboard_layout_id, boolean decimal_keyboard,View view)
{
	host_activity = host;
	isDecimalKeyboard = decimal_keyboard;
	keyboard = (KeyboardView) view.findViewById(view_id);
	keyboard.setKeyboard(new Keyboard(host_activity, keyboard_layout_id));
	keyboard.setPreviewEnabled(false);
	keyboard.setOnKeyboardActionListener(OnKeyboardActionListener);
	host_activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

}

/**���������� �� � ������ ������ ����������*/
public boolean isVisible()
{
	return keyboard.getVisibility() == View.VISIBLE;
}
/**���������� ����������*/
public void showKeyboard(View v)
{

	keyboard.setVisibility(View.VISIBLE);
	keyboard.setEnabled(true);
	keyboard.bringToFront();
	if (v != null) ((InputMethodManager) host_activity.getSystemService(Activity.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(v.getWindowToken(), 0);
}
/**"��������" ����������*/
public void hideKeyboard()
{
	keyboard.setVisibility(View.GONE);
	keyboard.setEnabled(false);
}
/**�������� ���������� �� ������� �� EditText*/
public void registerEditText(int res_id)
{
	final EditText et = (EditText) host_activity.findViewById(res_id);
	
    registerEditText(et);
	
}

public void registerEditText(EditText editText)
{
	editText.setOnTouchListener(new OnTouchListener()
	{

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			showKeyboard(v);
			EditText e = (EditText) v;
			int inType = e.getInputType();
			e.setInputType(InputType.TYPE_NULL);
			e.onTouchEvent(event);
			e.setInputType(inType);
			e.setCursorVisible(true);
			return true;
		}
	});

	editText.setInputType(editText.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
}


}
