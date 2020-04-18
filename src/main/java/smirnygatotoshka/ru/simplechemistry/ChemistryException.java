package smirnygatotoshka.ru.simplechemistry;

import android.content.Context;

/**
 * ===================================================================================
 * SimpleChemistry : Утилита для химиков под OS Android
 * ===================================================================================
 * 
 * @author SmirnygaTotoshka
 * @date 25.08.2014-09.04.2016(12.03.2017)
 * @version 1.5
 * Исключение,бросаемое в случае когда алгоритмы не справляются с работой.
 */
public class ChemistryException extends Exception {

	public ChemistryException(int string) {
		this.error_message = string;
	}


	private static final long serialVersionUID = 2292736362262922132L;
	/**id сообщения из ресурсов(res/chemistry_exception_message)*/
	private int error_message;
	
public String getErrorMessage(Context ctx)
{
	return ctx.getResources().getString(error_message);
}

}
