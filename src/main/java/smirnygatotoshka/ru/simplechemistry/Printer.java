package smirnygatotoshka.ru.simplechemistry;

import android.content.res.Resources;

import java.util.ArrayList;


/**
 * Created by SmirnygaTotoshka on 30.07.2017.
 */

public abstract class Printer implements Disposable
{
public Printer()
{
    result = new ArrayList<String>();
}

public Printer(Resources res)
{
    this.res = res;
}

    protected ArrayList<String> result;
    protected Resources res;

    public abstract void formResult();

    public ArrayList<String> getResult()
    {
        return result;
    }

    public void dispose()
    {
        result.removeAll(result);
    }

}
