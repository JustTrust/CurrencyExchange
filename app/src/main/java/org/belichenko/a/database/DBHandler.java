package org.belichenko.a.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.belichenko.a.database.DBContract.*;

/**
 * Data base handler class
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_BANK_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Banks.TABLE_NAME + " (" +
                    Banks._ID + " INTEGER PRIMARY KEY," +
                    Banks.COLUMN_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    Banks.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    Banks.COLUMN_MFO + TEXT_TYPE + COMMA_SEP +
                    Banks.COLUMN_SITE + TEXT_TYPE + COMMA_SEP +
                    Banks.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    Banks.COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
                    " )";
    private static final String CREATE_CURRENCY_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Currency.TABLE_NAME + " (" +
                    Currency._ID + " INTEGER PRIMARY KEY," +
                    Currency.COLUMN_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    Currency.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    " )";
    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Courses.TABLE_NAME + " (" +
                    Courses._ID + " INTEGER PRIMARY KEY," +
                    Courses.COLUMN_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    Courses.COLUMN_BANK_ID + TEXT_TYPE + COMMA_SEP +
                    Courses.COLUMN_CURRENCY_ID + TEXT_TYPE + COMMA_SEP +
                    Courses.COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                    Courses.COLUMN_BUY + TEXT_TYPE + COMMA_SEP +
                    Courses.COLUMN_SELL + TEXT_TYPE + COMMA_SEP +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Banks.TABLE_NAME + COMMA_SEP +
            Currency.TABLE_NAME+ COMMA_SEP +
            Courses.TABLE_NAME;

    public DBHandler(Context context, int version) {
        super(context, "bd", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BANK_TABLE);
        db.execSQL(CREATE_CURRENCY_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
