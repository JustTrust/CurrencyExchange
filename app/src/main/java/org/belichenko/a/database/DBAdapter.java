package org.belichenko.a.database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Database adepter
 */
public class DBAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public DBAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DBAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root = mInflater.inflate(android.R.layout.simple_expandable_list_item_2, parent, false);
        ViewHolder holder = new ViewHolder();
        TextView tvUp = (TextView)root.findViewById(android.R.id.text1);
        TextView tvLov = (TextView)root.findViewById(android.R.id.text2);
        holder.textViewUpper = tvUp;
        holder.textViewLower = tvLov;
        root.setTag(holder);
        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(DBContract.Banks._ID));
        String textUpper = cursor.getString(cursor.getColumnIndex(DBContract.Banks.COLUMN_TITLE));
        String textLover = cursor.getString(cursor.getColumnIndex(DBContract.Banks.COLUMN_PHONE));
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder != null) {
            holder.textViewUpper.setText(textUpper);
            holder.textViewLower.setText(textLover);
            holder.classID = id;
        }
    }

    public static class ViewHolder{
        public TextView textViewUpper;
        public TextView textViewLower;
        public long classID;
    }
}
