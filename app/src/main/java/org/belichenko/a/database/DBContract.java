package org.belichenko.a.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Described data base tables
 */
public final class DBContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an private constructor.
    private DBContract() {
    }

    public static final String AUTHORITY = "org.belichenko.a.database.DBContract";

    /* Inner class that defines the table Banks */
    public static abstract class Banks implements BaseColumns {

        private static final String SCHEME = "content://";
        private static final String PATH_BANKS = "/organizations";
        private static final String PATH_BANKS_ID = "/organizations/";
        public static final int BANKS_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_BANKS);
        public static final Uri CONTENT_ID_URI = Uri.parse(SCHEME + AUTHORITY + PATH_BANKS_ID);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.organizations";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.organizations";
        public static final String DEFAULT_SORT_ORDER = "title ASC";
        // fields
        public static final String TABLE_NAME = "organizations";

        public static final String COLUMN_ORG_ID = "id";
        public static final String COLUMN_OLD_ID = "oldID";
        public static final String COLUMN_ORG_TYPE = "orgType";
        public static final String COLUMN_BRANCH = "branch";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MFO = "mfo";
        public static final String COLUMN_REGION = "region";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";

        public static final String[] DEFAULT_PROJECTION = new String[]{
                DBContract.Banks._ID,
                DBContract.Banks.COLUMN_ORG_ID,
                DBContract.Banks.COLUMN_OLD_ID,
                DBContract.Banks.COLUMN_ORG_TYPE,
                DBContract.Banks.COLUMN_BRANCH,
                DBContract.Banks.COLUMN_TITLE,
                DBContract.Banks.COLUMN_MFO,
                DBContract.Banks.COLUMN_REGION,
                DBContract.Banks.COLUMN_CITY,
                DBContract.Banks.COLUMN_LINK,
                DBContract.Banks.COLUMN_ADDRESS,
                DBContract.Banks.COLUMN_PHONE
        };
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
        public static final String COLUMN_ENTRY_ID = "entryID";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_BANK_ID = "organizationsID";
        public static final String COLUMN_CURRENCY_ID = "currencyID";
        public static final String COLUMN_SELL = "sell";
        public static final String COLUMN_BUY = "buy";
    }
}
