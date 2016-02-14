package org.belichenko.a.data_structure;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Get list of currency
 */
public class Retrofit {
    private static final String ENDPOINT = "http://resources.finance.ua/ru/public";
    private static ApiInterface apiInterface;

    static {
        initialize();
    }

    interface ApiInterface {
        @GET("/currency-cash.json")
        void getCurrencyData(Callback<CurrencyData> callback);
    }

    public static void initialize() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiInterface = restAdapter.create(ApiInterface.class);
    }

    public static void getCurrencyData(Callback<CurrencyData> callback) {
        apiInterface.getCurrencyData(callback);
    }

}