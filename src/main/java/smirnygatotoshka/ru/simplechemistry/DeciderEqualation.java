package smirnygatotoshka.ru.simplechemistry;

import java.util.ArrayList;

/**
 * Created by SmirnygaTotoshka on 30.07.2017.
 */

public class DeciderEqualation implements Disposable
{
    private ParserEqualation parser_equalation;
    private ParserIndexReagent parser_index_reagent = new ParserIndexReagent();
    private CalculatorCoefficient calculator;
    private static ArrayList<reagent> reagents = new ArrayList<reagent>();

/**
 * Метод, запускающий все стадии решения уравнения, а именно:
 * 1.Считывание формул
 * 2.Считывание индексов элементов
 * 3.Уравнивание
 * 4.Вывод на экран
 *
 * @param equalation - Уравнение*/
public void equalize(String equalation)throws ChemistryException
{
        parser_equalation = new ParserEqualation(equalation);
        reagents = parser_equalation.formListReagents();
        for (int i = 0;i < reagents.size();i++)
            parser_index_reagent.countIndexReagent(reagents.get(i));
        calculator = new CalculatorCoefficient(reagents);
        calculator.equalate();
}

public ArrayList<reagent> getEqualation()
{
    return reagents;
}
public void dispose()
{
    reagents.removeAll(reagents);
}
}
