package smirnygatotoshka.ru.simplechemistry;

import java.util.ArrayList;

/**
 * @author SmirnygaTotoshka
 * @date 25.08.2014-09.04.2016(07.04.2017)
 * @version 1.5
 * Считывает количественную информацию об элементах из формул
 */
public class ParserIndexReagent
{
	/**
	 * Цифра перед символом элемента - индекс,показывающий, сколько атомов элемента в молекуле.
	 * У элементов,объединённых в круглые(C) или квадратные(Q) скобки,общий индекс,стоящий после закрывающей скобки
	 * Все данные заносятся в {@link reagent#elements}.После всего экземпляры одинаковых элементов объединяются в один.
	 * @param r - реагирующее вещество*/
public void countIndexReagent(reagent r)throws ChemistryException
{
	if(isCloseAllBrackets(r.formula))
	{
		parseIndexCompound(r);
		boolean Q = searchGroup(r.formula, '[');
		boolean C = searchGroup(r.formula, '(');

		if((Q) & (C))
		{
			countIndexGroup(r, '[', ']');
			countIndexGroup(r, '(', ')');
		}
		else if(Q)
			countIndexGroup(r, '[', ']');

		else if(C)
			countIndexGroup(r, '(', ')');


		if (r.elements.size() > 1)
			combineDuplicates(r.elements);
	}
	else
		throw new ChemistryException(R.string.ce_not_couple_bracket);
}

private boolean isCloseAllBrackets(String s) {
	int brackets_left = 0, brackets_right = 0;
	for (int i = 0; i < s.length(); i++) {
		char a = s.charAt(i);

		if ((a == '[') || (a == '('))
			brackets_left++;
		if ((a == ']') || (a == ')'))
			brackets_right++;
	}
	if (brackets_left == brackets_right)
		return true;
	else
		return false;
}
/**Получение индекса каждого элемента реагента*/
private void parseIndexCompound(reagent a)
{
	String s;
	
	searchBorderElement(a);
	
	for (int j = 0;j + 1 < a.elements.size();j++)
	{
		s = a.formula.substring(a.elements.get(j).end_symbol + 1, a.elements.get(j + 1).start_symbol);
		a.elements.get(j).index = parseIndex(s);
	}
	
	s = a.formula.substring(a.elements.get(a.elements.size() - 1).end_symbol + 1);
	a.elements.get(a.elements.size() - 1).index = parseIndex(s);
}

private void searchBorderElement(reagent a)
{
	int start_searching = 0;
	for (int j = 0;j < a.elements.size();j++)
	{
		// if a.elements.get(j).symbol.length() == 1 => +0 else +1
		a.elements.get(j).start_symbol = a.formula.indexOf(a.elements.get(j).symbol, start_searching);
		a.elements.get(j).end_symbol = a.elements.get(j).start_symbol + (a.elements.get(j).symbol.length() - 1);
		start_searching = a.elements.get(j).end_symbol + 1;

	}
}

private boolean searchGroup(String a, char bracket)
{
	for (int i = 0; i < a.length(); i++)
		if (a.charAt(i) == bracket) return true;
	return false;
}

private void countIndexGroup(reagent a, char open_bracket, char close_bracket)
{
	int index_Q = 1;

	for (int q = 0; q < a.formula.length(); q++)
		if (a.formula.charAt(q) == open_bracket)
		{
			int j;
			for (j = q; j < a.formula.length(); j++)
			{
				if (a.formula.charAt(j) == close_bracket)
				{
					index_Q = parseGroupIndex(a, j);
					break;
				}
			}
			for (int m = searchBeginGroup(a, q, open_bracket);m <= searchEndGroup(a, q, close_bracket);m++)
				a.elements.get(m).index *= index_Q;

			q = j;
		}
}
/**Считывание индекса группы.Впоследствии индекс каждого элемента,входящего в группу, умножается на эту величину*/
private int parseGroupIndex(reagent a, int start)
{
	String m = a.formula.substring(start + 1, (start + 2) + manySymbolsIsNumber(a.formula, start + 1));
	//(mn)k - k - индекс,состоящий минимум из одной цифры.Важно найти количество цифр в индексе.Это делает метод manySymbolsIsNumber
	try
	{
		return parseIndex(m);
	}
	catch (NumberFormatException e)
	{
		return 1;
	}

}
private int searchBeginGroup(reagent a, int start, char bracket)
{
	for (int i = start; i < a.formula.length(); i++)
		if (a.formula.charAt(i) == bracket) for (int j = 0;j < a.elements.size();j++)
			if (a.elements.get(j).start_symbol > i) return j;

	return -1;
}

private int searchEndGroup(reagent a, int start, char bracket)
{
	for (int i = start; i < a.formula.length(); i++)
		if (a.formula.charAt(i) == bracket)
			for (int j = 0;j < a.elements.size();j++)
				if (a.elements.get(j).start_symbol > i) return j - 1;

	return a.elements.size() - 1;
}

private int manySymbolsIsNumber(String formula, int start)
{
	int count = 0;
	try
	{
		for (int i = start + 1; i < formula.length(); i++)
		{
			Integer.parseInt(Character.toString(formula.charAt(i)));
			count++;
		}
	}
	catch (Exception e)
	{
		return 0;
	}
	return count;
}

private int parseIndex(String s)
{
	ArrayList<Character> q = searchDigit(s);
	int index = 0;

	if (q.size() == 0)
		return 1;

	else
		for (int m = q.size() - 1, r = 0; r < q.size(); m--, r++)
		{
			String z = Character.toString(q.get(r));
			index += (Integer.parseInt(z) * Math.pow(10, m));
		}

	return index;
}

private static ArrayList<Character> searchDigit(String s)
{
	ArrayList<Character> q = new ArrayList<Character>();

	for (int k = 0; k < s.length(); k++)
	{
		if (Character.isDigit(s.charAt(k))) q.add(s.charAt(k));

		if ((s.charAt(k) == '[') | (s.charAt(k) == ']') | (s.charAt(k) == '(') | (s.charAt(k) == ')')) break;
	}

	return q;
}

/**
 * Если в описании реагента встретились одинаковые элементы,то необходимо их объединить,т.е сделать истинную формулу
 * @param a - описание реагента {@link reagent#elements}
 * */
private void combineDuplicates(ArrayList<Element> a)
{
	for (int i = 0; i < a.size(); i++)
		for (int j = i + 1; j < a.size(); j++)
			if (a.get(i).symbol.equals(a.get(j).symbol))
			{
				a.get(i).index += a.get(j).index;
				a.remove(j);
				j--;
			}
}
}
