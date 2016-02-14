package org.belichenko.a.database;

import android.provider.BaseColumns;

/**
 * Described data base tables
 */
public final class DBContract {

        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public DBContract() {}

    /* Inner class that defines the table Banks */
    public static abstract class Banks implements BaseColumns {
        public static final String TABLE_NAME = "banks";
        public static final String COLUMN_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MFO = "mfo";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
    }

    /* Inner class that defines the table Currency */
    public static abstract class Currency implements BaseColumns {
        public static final String TABLE_NAME = "currency";
        public static final String COLUMN_ENTRY_ID = "id";
        public static final String COLUMN_NAME = "name";
    }

    /* Inner class that defines the table Courses */
    public static abstract class Courses implements BaseColumns {
        public static final String TABLE_NAME = "cources";
        public static final String COLUMN_ENTRY_ID = "entryid";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_BANK_ID = "bank";
        public static final String COLUMN_CURRENCY_ID = "currency";
        public static final String COLUMN_SELL = "sell";
        public static final String COLUMN_BUY = "buy";
    }
}
