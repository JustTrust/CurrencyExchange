package org.belichenko.a.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Content provider for database
 */
public class DBProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBHandler dbHelper;
    private static final int BANKS = 1;
    private static final int BANKS_ID = 2;
    private static HashMap<String, String> sBAnkProjectionMap = new HashMap<>();

    static {
        sUriMatcher.addURI(DBContract.AUTHORITY, DBContract.Banks.TABLE_NAME, BANKS);
        sUriMatcher.addURI(DBContract.AUTHORITY, DBContract.Banks.TABLE_NAME + "/#", BANKS_ID);
    }

    static {
        for(int i=0; i < DBContract.Banks.DEFAULT_PROJECTION.length; i++) {
            sBAnkProjectionMap.put(
                    DBContract.Banks.DEFAULT_PROJECTION[i],
                    DBContract.Banks.DEFAULT_PROJECTION[i]);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHandler(getContext(), 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = null;
        switch (sUriMatcher.match(uri)) {
            case BANKS:
                qb.setTables(DBContract.Banks.TABLE_NAME);
                qb.setProjectionMap(sBAnkProjectionMap);
                orderBy = DBContract.Banks.DEFAULT_SORT_ORDER;
                break;
            case BANKS_ID:
                qb.setTables(DBContract.Banks.TABLE_NAME);
                qb.setProjectionMap(sBAnkProjectionMap);
                qb.appendWhere(DBContract.Banks._ID + "=" + uri.getPathSegments().get(DBContract.Banks.BANKS_ID_PATH_POSITION));
                orderBy = DBContract.Banks.DEFAULT_SORT_ORDER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
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

                // TODO: 18.02.2016 clear if it don't need
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
                rowId = db.insert(DBContract.Banks.TABLE_NAME,
                        DBContract.Banks.COLUMN_TITLE,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(DBContract.Banks.CONTENT_ID_URI, rowId);
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
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
