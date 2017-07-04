package com.dannymato.hourcontrol;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Choreographer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;

import java.util.Date;

public class NewJobActivity extends AppCompatActivity {

    private boolean isGoing = false;
    private boolean isFirst = true;
    private long lastPause;
    private Chronometer stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job);

        Toolbar toolbar = (Toolbar)findViewById(R.id.NewJobtoolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fob = (FloatingActionButton) findViewById(R.id.startFOB);

        final Chronometer stopwatch = (Chronometer) findViewById(R.id.timeKeep);
        stop = stopwatch;

        fob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGoing) {
                    pause(stopwatch);
                } else {
                    if (isFirst) {
                        Log.d("Stopwatch", "First Start");
                        stopwatch.setBase(SystemClock.elapsedRealtime());
                        stopwatch.start();
                        isFirst = false;
                    } else {
                        resume(stopwatch);
                    }
                }
                isGoing = !isGoing;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.new_job_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                save(stop);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String timeToHours(Chronometer stopwatch){

        String temp = stopwatch.getText().toString();

        String hours = "";

        int x = 0;
        if (temp.length() == 5){

            hours = String.format("%.1f", (Integer.getInteger(temp.substring(0,2))/60.0));

        }
        else if (temp.length() > 5){

            hours = temp.substring(0,2);
            hours += String.format("%.1f", (Integer.getInteger(temp.substring(3,5))/60.0));

        }

        return hours;
    }

    private void pause(Chronometer stopwatch){
        if(isGoing) {
            lastPause = SystemClock.elapsedRealtime();
            stopwatch.stop();
            Log.d("Seconds of Chrono", timeToHours(stopwatch));
        }
    }

    private void resume(Chronometer stopwatch){
        stopwatch.setBase(stopwatch.getBase() + SystemClock.elapsedRealtime() - lastPause);
        stopwatch.start();
    }

    private void save(Chronometer stopwatch){
        DialogFragment saveDialog = new SaveDialog();
        saveDialog.show(getSupportFragmentManager(),"SaveDialog");
    }

    private String secondsToHours(int seconds){
        return String.format("%.1f", seconds/60.0/60.0);
    }

    private String getDate(){
        return DateFormat.getDateTimeInstance().format(new Date());
    }

    public static class SaveDialog extends DialogFragment{

        private static String selectedEmployer = "";

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {



            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cur = db.rawQuery("SELECT * FROM " + HourControlContract.EmployerTable.TABLE_NAME, null);

            final String[] employers = new String[cur.getCount()];

            selectedEmployer = employers[0];

            int i = 0;
            while(cur.moveToNext())
                employers[i++] = cur.getString(cur.getColumnIndex(HourControlContract.EmployerTable.COLUMN_1));

            cur.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Would you like to save these hours?")
                    .setPositiveButton("SAVE",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
                    .setSingleChoiceItems(employers, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SaveDialog.setSelectedEmployer(employers[which]);
                }
            });

            return builder.create();
        }

        protected static String getSelectedEmployer(){return selectedEmployer;}
        protected static void setSelectedEmployer(String string){selectedEmployer = string;}
    }
}
