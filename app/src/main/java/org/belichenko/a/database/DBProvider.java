package org.belichenko.a.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Content provider for database
 */
public class DBProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBHandler dbHelper;
    private static final int BANKS = 1;
    private static final int BANKS_ID = 2;

    static {
        sUriMatcher.addURI(DBContract.AUTHORITY, DBContract.Banks.TABLE_NAME, BANKS);
        sUriMatcher.addURI(DBContract.AUTHORITY, DBContract.Banks.TABLE_NAME + "/#", BANKS_ID);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DBHandler(getContext(), 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case BANKS:
                return DBContract.Banks.CONTENT_TYPE;
            case BANKS_ID:
                return DBContract.Banks.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues initialValues) {

        if (sUriMatcher.match(uri) != BANKS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        long rowId = -1;
        Uri rowUri = Uri.EMPTY;
        switch (sUriMatcher.match(uri)) {
            case BANKS:
//                if (values.containsKey(ContractClass.Students.COLUMN_NAME_FIRST_NAME) == false) {
//                    values.put(ContractClass.Students.COLUMN_NAME_FIRST_NAME, "");
//                }
//                if (values.containsKey(ContractClass.Students.COLUMN_NAME_SECOND_NAME) == false) {
//                    values.put(ContractClass.Students.COLUMN_NAME_SECOND_NAME, "");
//                }
//                if (values.containsKey(ContractClass.Students.COLUMN_NAME_AVERAGE_SCORE) == false) {
//                    values.put(ContractClass.Students.COLUMN_NAME_AVERAGE_SCORE, 0.0);
//                }
//                if (values.containsKey(ContractClass.Students.COLUMN_NAME_FK_CLASS_ID) == false) {
//                    values.put(ContractClass.Students.COLUMN_NAME_FK_CLASS_ID, -1);
//                }
//                rowId = db.insert(DBContract.Banks.TABLE_NAME,
//                        ContractClass.Students.COLUMN_NAME_FIRST_NAME,
//                        values);
//                if (rowId > 0) {
//                    rowUri = ContentUris.withAppendedId(DBContract.Banks.CONTENT_ID_URI_BASE, rowId);
//                    getContext().getContentResolver().notifyChange(rowUri, null);
//                }
                break;
        }
        return rowUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
