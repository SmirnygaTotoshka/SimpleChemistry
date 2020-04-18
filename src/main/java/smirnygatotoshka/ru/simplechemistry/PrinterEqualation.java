/**===================================================================================
 * Chemistry :  ,       Android
 *===================================================================================
 * @author SmirnygaTotoshka( )
 * 
 * ,      . - Decide_Urav,Decide_simple_Problem. 
 */
package smirnygatotoshka.ru.simplechemistry;

import android.content.res.Resources;

import java.util.ArrayList;

public class PrinterEqualation extends Printer
{
	private ArrayList<reagent> reagents;

	public PrinterEqualation(ArrayList<reagent> reagents)
	{
		super();
		this.reagents = reagents;
	}


@Override
public void formResult()
{
	String res = "";
	for (int i = 0; i < reagents.size(); i++)
		if (reagents.get(i).ko != 1)
			reagents.get(i).formula = Integer.toString(reagents.get(i).ko) + reagents.get(i).formula;

	for (int i1 = 0; i1 < reagents.size(); i1++)
	{
		if (i1 == ParserEqualation.borderParts - 1)
			res += reagents.get(i1).formula + "= ";
		else if (i1 == reagents.size() - 1)
			res += reagents.get(i1).formula;
		else
			res += reagents.get(i1).formula + "+ ";
	}
	result.add(res);
}
}
