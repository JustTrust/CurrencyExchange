package org.belichenko.a.data_structure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

/**
 * Structure of data from http://resources.finance.ua/ru/public/currency-cash.json
 */
public class CurrencyData {
    public String sourceId;
    public String date;
    public ArrayList<Organizations> organizations;
    public HashMap<String, String > orgTypes;
    public HashMap<String, String > currencies;
    public SortedMap<String, String > regions;
    public SortedMap<String, String > cities;

    private static CurrencyData ourInstance = new CurrencyData();

    public static CurrencyData getInstance() {
        return ourInstance;
    }

    private CurrencyData() {
    }
}