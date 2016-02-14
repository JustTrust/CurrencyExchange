package org.belichenko.a.currencyexchange;

import java.util.ArrayList;

/**
 * Created by Админ on 16.12.2015.
 */
class Bank {
    private String name;
    private String mfo;
    private String phone;
    private String site;
    private String address;
    private String id;

    private ArrayList<MyCurrency> listOfCurrency;

    public String getName() {
        return name;
    }

    public ArrayList<MyCurrency> getListOfCurrency() {
        return listOfCurrency;
    }


    public void setListOfCurrency(ArrayList<MyCurrency> listOfCurrency) {
        this.listOfCurrency = listOfCurrency;
    }

    public Bank(String name, String mfo) {
        this.name = name;
        this.mfo = mfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    @Override
    public String toString() {
        return getName();
    }
}
