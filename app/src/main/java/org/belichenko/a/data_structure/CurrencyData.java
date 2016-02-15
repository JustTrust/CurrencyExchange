package org.belichenko.a.data_structure;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Structure of data from http://resources.finance.ua/ru/public/currency-cash.json
 */
public class CurrencyData {
    public String sourceId;
    public String date;
    public ArrayList<Organizations> organizations;
    public HashMap<String, String > orgTypes;
    public HashMap<String, String > currencies;
    public HashMap<String, String > regions;
    public HashMap<String, String > cities;

    private static CurrencyData ourInstance = new CurrencyData();

    public static CurrencyData getInstance() {
        return ourInstance;
    }

    private CurrencyData() {
    }
}