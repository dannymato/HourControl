package com.dannymato.hourcontrol;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

class MyListAdapter implements ListAdapter {

    private final Context mContext;
    private final String[] hoursEntries;
    private final String[] datesEntries;
    private final String[] employersEntries;
    private DatabaseHelper dbHelper;

    public MyListAdapter(Context context) {
        mContext = context;
        dbHelper = new DatabaseHelper(mContext);

        hoursEntries = getHours();
        datesEntries = getDates();
        employersEntries = getEmployers();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return hoursEntries.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listView;

        listView = inflator.inflate(R.layout.list_layout, null);

        TextView textHours = (TextView)listView.findViewById(R.id.list_item_hours);
        TextView textDate = (TextView)listView.findViewById(R.id.list_item_date);
        TextView textEmployer = (TextView)listView.findViewById(R.id.list_item_employer);

        Log.d("List Test", String.valueOf(MainActivity.countViews++));

        textDate.setText(datesEntries[position]);
        textHours.setText(hoursEntries[position] + " hours");
        textEmployer.setText(employersEntries[position]);

        return listView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private String[] getHours(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cur  = db.rawQuery("SELECT * FROM " + HourControlContract.HoursTable.TABLE_NAME, null);

        String[] temp = new String[cur.getCount()];

        int i = 0;

        while (cur.moveToNext())
            temp[i++] = cur.getString(cur.getColumnIndex(HourControlContract.HoursTable.COLUMN_NAME_COL2));

        cur.close();

        return temp;

    }

    private String[] getDates(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cur  = db.rawQuery("SELECT * FROM " + HourControlContract.HoursTable.TABLE_NAME, null);

        String[] temp = new String[cur.getCount()];

        int i = 0;

        while (cur.moveToNext())
            temp[i++] = cur.getString(cur.getColumnIndex(HourControlContract.HoursTable.COLUMN_NAME_COL1));

        cur.close();

        return temp;

    }

    private String[] getEmployers(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cur  = db.rawQuery("SELECT * FROM " + HourControlContract.HoursTable.TABLE_NAME, null);

        String[] temp = new String[cur.getCount()];

        int i = 0;

        while (cur.moveToNext())
            temp[i++] = cur.getString(cur.getColumnIndex(HourControlContract.HoursTable.COLUMN_NAME_COL3));

        cur.close();

        return temp;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
