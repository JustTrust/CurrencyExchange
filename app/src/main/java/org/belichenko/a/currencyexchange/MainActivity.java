package org.belichenko.a.currencyexchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.belichenko.a.data_structure.Courses;
import org.belichenko.a.data_structure.CurrencyData;
import org.belichenko.a.data_structure.Organizations;
import org.belichenko.a.data_structure.Retrofit;
import org.belichenko.a.login.LogRegActivity;
import org.belichenko.a.utils.MyConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnTextChanged;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MyConstants {

    //Bind the Views of the activity_main.xml file
    @Bind(R.id.money_value)
    EditText money_value;
    @Bind(R.id.currency_1)
    TextView currency_1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.currency_2)
    TextView currency_2;
    @Bind(R.id.bank_spinner)
    Spinner bank_spinner;
    @Bind(R.id.buysell_spinner)
    Spinner buysell_spinner;
    @Bind(R.id.listOfCurrency)
    ListView listOfCurrency;

    private ArrayList<Organizations> listOfBanks = new ArrayList<>();
    private MyCurrency currentCurrency;
    private ArrayAdapter<Organizations> adapterBank;
    private int adapterBankPosition;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putInt(BANKS_LIST_INDEX, adapterBankPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        if (savedState != null) {
            adapterBankPosition = savedState.getInt(BANKS_LIST_INDEX, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.container_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (listOfBanks.isEmpty()) {
            updateDataFromSite();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // use Butter knife
        ButterKnife.bind(this);
        // check in preferences a user is login
        SharedPreferences mPrefs = this.getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);
        String user = mPrefs.getString(USER_IS_LOGIN, null);
        if (user == null) {
            // user not login go to login activity
            Intent intent = new Intent(this, LogRegActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            String title = getTitle().toString();
            setTitle(title + ": " + user);
        }

        if (savedState != null) {
            adapterBankPosition = savedState.getInt(BANKS_LIST_INDEX, 0);
        }

        // set banks spinner
        adapterBank = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listOfBanks);
        bank_spinner.setAdapter(adapterBank);

        bank_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Organizations selectedBank = (Organizations) parent.getItemAtPosition(position);
                if (selectedBank != null) {
                    updateCurrencyList(selectedBank.currencies);
                    adapterBankPosition = position;
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

        ArrayAdapter<String> adapterBuySell = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, listOfBuySell);
        buysell_spinner.setAdapter(adapterBuySell);
        buysell_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_update:
                updateDataFromSite();
                return true;
            case R.id.action_logout:
                logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDataFromSite() {
        Retrofit.getCurrencyData(new Callback<CurrencyData>() {
            @Override
            public void success(CurrencyData currencyData, Response response) {
                if (currencyData != null) {
                    listOfBanks.clear();
                    listOfBanks.addAll(currencyData.organizations);
                    adapterBank.notifyDataSetChanged();
                    if (adapterBankPosition > 0){
                        if (adapterBank.getCount()>= adapterBankPosition){
                            bank_spinner.setSelection(adapterBankPosition);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure() called with: " + "error = [" + error + "]");
            }
        });
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
     * @param currency array list
     */
    private void updateCurrencyList(HashMap<String, Courses> currency) {
        Log.d(TAG, "updateCurrencyList() called with: " + "currency = [" + currency + "]");
        ArrayList<MyCurrency> currencyList = new ArrayList<>();

        for (Map.Entry<String, Courses> entry : currency.entrySet()) {
            currencyList.add(new MyCurrency(entry.getKey(),
                    stringToFloat(entry.getValue().bid),
                    stringToFloat(entry.getValue().ask)));
        }
        ArrayAdapter<MyCurrency> adapterCurrency = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_single_choice, currencyList);

        listOfCurrency.setAdapter(adapterCurrency);

        // set first item selected by default
        listOfCurrency.setItemChecked(0, true);
        listOfCurrency.setSelection(0);
        listOfCurrency.performItemClick(listOfCurrency, 0, 0);
    }

    private float stringToFloat(String st) {
        float result = 0f;
        try {
            result = Float.parseFloat(st);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return result;
    }

    /**
     * Call when user select currency from list or operation type (buy-sell)
     */
    private void buySellCurrency() {
        if (currentCurrency == null) {
            return;
        }
        String buySell = buysell_spinner.getSelectedItem().toString();
        String result = doCalculate();
        if (buySell.equals(getResources().getString(R.string.buy))) {
            currency_1.setText(DefaultCurrency.UAH.name());
            currency_2.setText(result + " " + currentCurrency.getName());
        } else if (buySell.equals(getResources().getString(R.string.sell))) {
            currency_1.setText(currentCurrency.getName());
            currency_2.setText(result + " " + DefaultCurrency.UAH.name());
        }
    }

    /**
     * Make calculate @return String with result value
     */
    private String doCalculate() {

        float moneyValueF;
        try {
            moneyValueF = Float.valueOf(money_value.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.alert), Toast.LENGTH_LONG).show();
            return "0";
        }
        if (moneyValueF <= 0f) {
            return "0";
        }
        if (currentCurrency == null) {
            return "0";
        }
        if ((currentCurrency.getSellCource() <= 0f) || (currentCurrency.getBuyCource() <= 0f)) {
            return "0";
        }

        String buySell = buysell_spinner.getSelectedItem().toString();
        if (buySell.equals(getResources().getString(R.string.buy))) {
            return String.format("%.2f", moneyValueF / currentCurrency.getSellCource());
        } else if (buySell.equals(getResources().getString(R.string.sell))) {
            return String.format("%.2f", moneyValueF * currentCurrency.getBuyCource());
        }
        return "0";
    }

    protected void logoutUser() {

        // logout user from preferences
        SharedPreferences mPrefs = this.getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(USER_IS_LOGIN, null);
        edit.apply();

        // user not login go to login activity
        Intent intent = new Intent(this, LogRegActivity.class);
        startActivity(intent);
        finish();
    }

    @OnItemClick(R.id.listOfCurrency)
    protected void onItemCurrencyClick(AdapterView<?> parent, View view, int position, long id) {
        currentCurrency = (MyCurrency) parent.getItemAtPosition(position);
        buySellCurrency();
    }

    @OnItemLongClick(R.id.listOfCurrency)
    protected boolean onItemLongCurrencyClick(AdapterView<?> parent, View view, int position, long id) {
        currentCurrency = (MyCurrency) parent.getItemAtPosition(position);
        buySellCurrency();
        setNewCourse(currentCurrency);
        buySellCurrency();
        return true;
    }

    @OnTextChanged(R.id.money_value)
    protected void onMoneyValueChanged(CharSequence text) {
        buySellCurrency();
    }

}
