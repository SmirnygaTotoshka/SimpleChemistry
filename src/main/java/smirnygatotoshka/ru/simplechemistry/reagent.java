
package smirnygatotoshka.ru.simplechemistry;

import java.util.ArrayList;

/**
 * ===================================================================================
 * SimpleChemistry : Утилита для химиков под OS Android
 * ===================================================================================
 * 
 * @author SmirnygaTotoshka
 * @date 25.08.2014-09.04.2016(14.03.2017)
 * @version 1.5
 * Класс,описывающий вещество.
 */
public class reagent {

	public reagent() {
	}

public reagent(ArrayList<Element> elements, String formula)
{
	new reagent();
	this.elements = elements;
	this.formula = formula;
}

/**Перечень элементов в веществе*/
	public ArrayList<Element> elements = new ArrayList<Element>();
	public String formula = "";
	/**Коэффициент перед уравнением*/
	public int ko = 1;

	public double getMolecularWeight()
	{
		double w = 0;
		for (int i = 0;i < elements.size();i++)
			w += (Math.abs(elements.get(i).atomic_weight) * elements.get(i).index);
		return w;
	}

}