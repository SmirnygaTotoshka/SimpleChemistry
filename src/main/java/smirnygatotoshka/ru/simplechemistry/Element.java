package smirnygatotoshka.ru.simplechemistry;

/**
 * Класс описывающий химический элемент
 * Created by SmirnygaTotoshka on 25.01.2017.
 */

public class Element {

    public Element(int serial_number, String symbol, String name, int group, int under_group, int period,
                   String class_element, byte[] elctro_config, String phasa, int maxstepen, int minstepen,
                   int temprature_plav, int temprature_kip, double atomic_weight)
    {
        this.serial_number = serial_number;
        this.symbol = symbol;
        this.name = name;
        this.group = group;
        this.under_group = under_group;
        this.period = period;
        this.class_element = class_element;
        this.elctro_config = elctro_config;
        this.phasa = phasa;
        this.maxstepen = maxstepen;
        this.minstepen = minstepen;
        this.temprature_plav = temprature_plav;
        this.temprature_kip = temprature_kip;
        this.atomic_weight = atomic_weight;
    }

    public Element()
    {

    }

    public Element(String symbol, double atomic_weight)
    {
        this.symbol = symbol;
        this.atomic_weight = atomic_weight;
    }



    public int serial_number;
    public String symbol;
    public String name;
    public int group;
    /** Если 1,то элемент в главной подгруппе. 0 - побочная*/
    public int under_group;
    public int period;
    public String class_element;
    public int start_symbol, end_symbol;
    public byte[] elctro_config;
    public String phasa;
    public int maxstepen;
    public int minstepen;
    public int temprature_plav;
    public int temprature_kip;
    public double atomic_weight;
    public int index = 0;


}
