package smirnygatotoshka.ru.simplechemistry;

import android.util.Log;

import java.util.ArrayList;

/**
 * Считывает по формуле информацию о содержащихся в ней элементах
 * Created by SmirnygaTotoshka on 14.07.2017.
 */
public class ParserEqualation
{
/**Граница между формулами в уравнении*/
private final String formul_separator = "+";
/**Граница между частями уравнения*/
private final String part_separator = "=";
/**Номер реагента в списке,с которого начинается правая часть уравнения*/
static int borderParts;

private reagent reagent;
private String equalation;
private ArrayList<reagent> listReagents;

public ParserEqualation()
{
}

public ParserEqualation(String equalation)
{
    String a = equalation.replace(" ", "");
    this.equalation = a;
    this.reagent = new reagent();
    this.listReagents = new ArrayList<reagent>();
    Log.d(SimpleChemistry.TAG,equalation);
}

public ArrayList<reagent> formListReagents()throws ChemistryException
{
    String[] parts = equalation.split(part_separator);
    printResultInLog(parts);
    if (parts.length != 2)
        throw new ChemistryException(R.string.not_found_parts);

    borderParts = parts[0].split("\\"+formul_separator).length;

    if(borderParts == 0)
        borderParts = 1;
    Log.d(SimpleChemistry.TAG,"borderParts = " + borderParts);
    String[] formuls = equalation.split("\\"+formul_separator+"|"+part_separator);
    printResultInLog(formuls);

    for(int i = 0;i < formuls.length;i++)
    {
        reagent.formula = formuls[i] + " ";
        reagent.elements = parseElements(reagent.formula);
        listReagents.add(reagent);
        reagent = new reagent();
    }

    return listReagents;
}

public reagent parseReagent(String formula)throws  ChemistryException
{
    reagent = new reagent();
    reagent.formula = formula;
    reagent.elements = parseElements(formula);
    return reagent;
}

private ArrayList<Element> parseElements(String formula) throws ChemistryException
{
    ArrayList<Element> elements = new ArrayList<Element>();

    for (int j = 0; j < formula.length(); j++)
    {
        char x = formula.charAt(j);
        char y = ' ';
        if(j + 1 < formula.length())
            y = formula.charAt(j + 1);

        //Символы элементов представлены либо одной заглавной либо одной заглавной и одной строчной буквами
        if ((Character.isUpperCase(x)) & (Character.isLowerCase(y)))
        {
            String b = Character.toString(x) + Character.toString(y);
            Element element = addElement(b);
            elements.add(element);
        }

        else if ((Character.isLetter(x)) & (Character.isUpperCase(x)))
        {
            Element element = addElement(Character.toString(x));
            elements.add(element);
        }
    }
    if (elements.size() == 0) throw new ChemistryException(R.string.incorrect_formula);

    return elements;
}

private Element addElement(String sym)throws ChemistryException
{
    return DatabaseElements.getFromData(sym,SimpleChemistry.dataElements);
}

private void printResultInLog(String[] result)
{
    for(int i = 0;i < result.length;i++)
        Log.d(SimpleChemistry.TAG,result[i]+"\n");
}


}
