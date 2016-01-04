package org.belichenko.a.currencyexchange;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.belichenko.a.login.LogRegActivity;
import org.belichenko.a.utils.App;
import org.belichenko.a.utils.MyConstants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyConstants{

    private ArrayList<Bank> listOfBanks = new ArrayList<>();
    private MyCurrency currentCurrency;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.p
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check in preferences a user is login
        SharedPreferences mPrefs = this.getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);
        String user = mPrefs.getString(USER_IS_LOGIN, null);
        if (user == null){
            // user not login go to login activity
            Intent intent = new Intent(this, LogRegActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else{
            String title = getTitle().toString();
            setTitle(title +": "+user);
        }

        // fill the banks list
        for (DefaultBanks defBank : DefaultBanks.values()) {
            Bank newBank = new Bank(defBank.getName(), defBank.getMfo());

            // fill the currency list
            ArrayList<MyCurrency> listOfCurrency = new ArrayList<>();
            for (DefaultCurrency defCurrency : DefaultCurrency.values()) {
                // UAH don't add in list, its course always is 1
                if (defCurrency != DefaultCurrency.UAH) {
                    MyCurrency newCurrency = new MyCurrency(
                            defCurrency.name(),
                            defCurrency.getCode(),
                            defCurrency.getMiddleCourse()
                    );
                    listOfCurrency.add(newCurrency);
                }
            }
            newBank.setListOfCurrency(listOfCurrency);
            listOfBanks.add(newBank);
        }

        // set banks spinner
        Spinner bankSpinner = (Spinner) findViewById(R.id.bank_spinner);
        ArrayAdapter<Bank> adapterBank = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listOfBanks);
        bankSpinner.setAdapter(adapterBank);

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bank selectedBank = (Bank) parent.getItemAtPosition(position);
                if (selectedBank != null) {
                    updateCurrencyList(selectedBank.getListOfCurrency());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set buy-sell spinner
        ArrayList<String> listOfBuySell = new ArrayList<>();
        listOfBuySell.add(getResources().getString(R.string.sell));
        listOfBuySell.add(getResources().getString(R.string.buy));

        Spinner buysellSpinner = (Spinner) findViewById(R.id.buysell_spinner);
        ArrayAdapter<String> adapterBuySell = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, listOfBuySell);
        buysellSpinner.setAdapter(adapterBuySell);
        buysellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String buysell = (String) parent.getItemAtPosition(position);
                if (buysell != null) {
                    buySellCurrency();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set listener on money value field
        EditText editMoney = (EditText) findViewById(R.id.money_value);
        editMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                buySellCurrency();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        // set on listOfCurrency on item click listener
        ListView listOfCurrency = (ListView) findViewById(R.id.listOfCurrency);
        listOfCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentCurrency = (MyCurrency) parent.getItemAtPosition(position);
                buySellCurrency();
            }
        });
        // set on listOfCurrency on item click listener and change course
        listOfCurrency.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentCurrency = (MyCurrency) parent.getItemAtPosition(position);
                buySellCurrency();
                setNewCourse(currentCurrency);
                buySellCurrency();
                return true;
            }
        });
        // set listener on ListOfCurrency on radioButton

    }

    private void setNewCourse(final MyCurrency currency) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View changeCourseView = layoutInflater.inflate(R.layout.courses_change, null);

        // fill two fields, sell and buy with data
        final EditText inputSell = (EditText) changeCourseView.findViewById(R.id.editNumSell);
        inputSell.setText(String.format("%.3f", currency.getSellCource()));
        final EditText inputBuy = (EditText) changeCourseView.findViewById(R.id.editNumBuy);
        inputBuy.setText(String.format("%.3f", currency.getBuyCource()));

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setTitle(R.string.enterCurrency)
                .setView(changeCourseView)
                .setPositiveButton(R.string.okBt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //set up new courses
                        float moneyValue = 0f;
                        try {
                            moneyValue = Float.valueOf(inputBuy.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.alert), Toast.LENGTH_LONG).show();
                        }
                        if (moneyValue > 0f) {
                            currency.setBuyCource(moneyValue);
                        }
                        moneyValue = 0f;
                        try {
                            moneyValue = Float.valueOf(inputSell.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.alert), Toast.LENGTH_LONG).show();
                        }
                        if (moneyValue > 0f) {
                            currency.setSellCource(moneyValue);
                        }
                    }
                })
                .setNegativeButton(R.string.cancelBt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //nothing to do
                    }
                })
                .show();
    }

    /**
     * Fill List view of currency from array list
     *
     * @param currencyList
     */
    private void updateCurrencyList(ArrayList<MyCurrency> currencyList) {
        ArrayAdapter<MyCurrency> adapterCurrency = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_single_choice, currencyList);

        ListView listOfCurrency = (ListView) findViewById(R.id.listOfCurrency);
        listOfCurrency.setAdapter(adapterCurrency);

        // set first item selected by default
        listOfCurrency.setItemChecked(0, true);
        listOfCurrency.setSelection(0);
        listOfCurrency.performItemClick(listOfCurrency, 0, 0);
    }

    /**
     * Call when user select currency from list or operation type (buy-sell)
     */
    private void buySellCurrency() {
        if (currentCurrency == null) {
            return;
        }
        Spinner buysellSpinner = (Spinner) findViewById(R.id.buysell_spinner);
        String buySell = buysellSpinner.getSelectedItem().toString();
        TextView currency1 = (TextView) findViewById(R.id.currency_1);
        TextView currency2 = (TextView) findViewById(R.id.currency_2);
        String result = doCalculate();
        if (buySell.equals(getResources().getString(R.string.buy))) {
            currency1.setText(DefaultCurrency.UAH.name());
            currency2.setText(result + " " + currentCurrency.getName());
        } else if (buySell.equals(getResources().getString(R.string.sell))) {
            currency1.setText(currentCurrency.getName());
            currency2.setText(result + " " + DefaultCurrency.UAH.name());
        }
    }

    /**
     * Make calculate @return String with result value
     */
    private String doCalculate() {

        EditText editMoney = (EditText) findViewById(R.id.money_value);
        float moneyValue;
        try {
            moneyValue = Float.valueOf(editMoney.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.alert), Toast.LENGTH_LONG).show();
            return "0";
        }
        if (moneyValue <= 0f) {
            return "0";
        }
        if (currentCurrency == null) {
            return "0";
        }
        if ((currentCurrency.getSellCource() <= 0f) || (currentCurrency.getBuyCource() <= 0f)) {
            return "0";
        }
        Spinner buysellSpinner = (Spinner) findViewById(R.id.buysell_spinner);
        String buySell = buysellSpinner.getSelectedItem().toString();
        if (buySell.equals(getResources().getString(R.string.buy))) {
            return String.format("%.2f", moneyValue / currentCurrency.getSellCource());
        } else if (buySell.equals(getResources().getString(R.string.sell))) {
            return String.format("%.2f", moneyValue * currentCurrency.getBuyCource());
        }
        return "0";
    }

    public void logoutUser(View view){

        // logout user from preferences
        SharedPreferences mPrefs = this.getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(USER_IS_LOGIN, null);
        edit.apply();

        // user not login go to login activity
        Intent intent = new Intent(this, LogRegActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

}
