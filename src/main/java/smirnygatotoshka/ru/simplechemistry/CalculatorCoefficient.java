
package smirnygatotoshka.ru.simplechemistry;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author SmirnygaTotoshka
 * @date 25.08.2014-09.04.2016(12.03.2017)
 * @version 1.5
 * Класс,вычисляющий коэффициенты хим.уравнения
 */
public class CalculatorCoefficient
{

private ArrayList<Element> all_elements_in_reaction = new ArrayList<Element>();
private ArrayList<reagent> reagents;

public CalculatorCoefficient(ArrayList<reagent> reagents)
{
	this.reagents = reagents;
}

/**Метод,решающий уравнение.
 * 1- Составляет перечень ХЭ,участвующих в реакции
 * 2- Строит квадратную матрицу(строки - элементы,столбцы вещества)
 * 3- Решает её методом Гаусса
 * 4- Проверяет на точность балансировки
 * */
public void equalate() throws ChemistryException
{
	if (isEqualListElement())
	{
		initElements();
		deleteDuplicates(all_elements_in_reaction);
					
		int ROWS = all_elements_in_reaction.size() + 1;
		int COLUMN = reagents.size() + 1;

		Matrix matrix = new Matrix(ROWS, COLUMN);
		buildMatrix(matrix);
		solve(matrix);
			
		Float[] coefs = extractCoefficients(matrix);
		for (int i = 0;i < coefs.length;i++)
			reagents.get(i).ko = coefs[i].intValue();
			
		if (!isCorrectBalance()) throw new ChemistryException(R.string.incorrect_balance);
	}
	else throw new ChemistryException(R.string.not_equal_list_element);
}

private void initElements()
{
	
	for (int i = 0;i < reagents.size();i++)
		for (int j = 0;j < reagents.get(i).elements.size();j++)
			all_elements_in_reaction.add(reagents.get(i).elements.get(j));
					
}

public static void deleteDuplicates(ArrayList<Element> a)
{
	
	for (int i = 0; i < a.size(); i++)
		for (int j = i + 1; j < a.size(); j++)
			if (a.get(i).symbol.equals(a.get(j).symbol))
			{
				a.remove(j);
				j--;
			}
}

private Matrix buildMatrix(Matrix matrix)
{
	
	for (int i = 0;i < reagents.size();i++)
		for (int j = 0; j < all_elements_in_reaction.size(); j++)
			for (int k = 0;k < reagents.get(i).elements.size();k++)
				if (all_elements_in_reaction.get(j).symbol.equals(reagents.get(i).elements.get(k).symbol))
					matrix.setValue(j, i,reagents.get(i).elements.get(k).index);
							
	for (int i = ParserEqualation.borderParts;i < reagents.size();i++)
		for (int j = 0; j < all_elements_in_reaction.size(); j++)
			matrix.setValue(j, i, matrix.getValue(j, i) * -1);
			
	return matrix;
}

private void solve(Matrix matrix)
{
	matrix.gaussJordanEliminate();
	
	// Find row with more than one non-zero coefficient;
	int i;
	for (i = 0; i < matrix.getRows() - 1; i++)
	{
		if (countNonzeroCoeffs(matrix, i) > 1) break;
	}
	if (i == matrix.getRows() - 1)
		return;
	Log.d(SimpleChemistry.TAG, matrix.write_matrix());
	
	// Add an inhomogeneous equation
	matrix.setValue(matrix.getRows() - 1, i, 1);
	matrix.setValue(matrix.getRows() - 1, matrix.getColumn() - 1, 1);
	
	matrix.gaussJordanEliminate();
}

private int countNonzeroCoeffs(Matrix matrix, int line)
{
	int count = 0;
	for (int i = 0; i < matrix.getColumn(); i++)
	{
		if (matrix.getValue(line, i) != 0) count++;
	}
	return count;
}

private Float[] extractCoefficients(Matrix matrix)
{
	//lcm - наименьшее общее кратное
	//gcd - наибольший общий делитель
	int Column = matrix.getColumn();
	float lcm = 1;
	for (int i = 0; i < Column - 1; i++)
		lcm = (lcm / matrix.gcd(lcm, matrix.getValue(i, i))) * matrix.getValue(i, i);
		
	ArrayList<Float> coefs = new ArrayList<Float>();
	for (int i = 0; i < Column - 1; i++)
	{
		float coef = (lcm / (matrix.getValue(i, i)) * matrix.getValue(i, Column - 1));
		coefs.add(Math.abs(coef));
	}
	Float[] a = coefs.toArray(new Float[coefs.size()]);
	return a;
}

/**Проверка на соответсвие качественного состава реагентов и продуков.
 * Перечень элементов слева должен совпадать с таким же списком справа
 * */
private boolean isEqualListElement() {
	ArrayList<Element> left = formListElements(0,
											   ParserEqualation.borderParts);
	ArrayList<Element> right = formListElements(
			ParserEqualation.borderParts,
			reagents.size());

	deleteDuplicates(left);
	deleteDuplicates(right);

	for (int i = 0; i < left.size(); i++) {
		boolean contain = false;
		for (int j = 0; j < right.size(); j++)
			if (left.get(i).symbol.equals(right.get(j).symbol))
				contain = true;
		if (!contain)
			return false;
	}
	return true;
}

private ArrayList<Element> formListElements(int start, int end) {
	ArrayList<Element> a = new ArrayList<Element>();
	for (int i = start; i < end; i++)
		a.addAll(reagents.get(i).elements);
	return a;
}

public boolean isCorrectBalance()
{
	int summa_elements_left = countSummaElements(0, ParserEqualation.borderParts);
	int summa_elements_right = countSummaElements(ParserEqualation.borderParts, reagents.size());
	
	return summa_elements_left == summa_elements_right;
}

private int countSummaElements(int start, int end)
{
	int summa = 0;
	for (int i = start; i < end; i++)
		for (int j = 0;j < reagents.get(i).elements.size();j++)
			summa = summa + (reagents.get(i).elements.get(j).index
					* reagents.get(i).ko);
					
	return summa;
}
}