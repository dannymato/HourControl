package com.dannymato.hourcontrol;

import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

public class NewJobActivity extends AppCompatActivity {

    private boolean isGoing = false;
    private boolean isFirst = true;
    private long lastPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job);

        FloatingActionButton fob = (FloatingActionButton) findViewById(R.id.startFOB);

        final Chronometer stopwatch = (Chronometer) findViewById(R.id.timeKeep);

        fob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGoing) {
                    lastPause = SystemClock.elapsedRealtime();
                    stopwatch.stop();
                } else {
                    if (isFirst) {
                        Log.d("Stopwatch", "First Start");
                        stopwatch.setBase(SystemClock.elapsedRealtime());
                        stopwatch.start();
                        isFirst = false;
                    } else {

                        stopwatch.setBase(stopwatch.getBase() + SystemClock.elapsedRealtime() - lastPause);
                        stopwatch.start();

                    }
                }
                isGoing = !isGoing;
            }
        });

    }


        private double timeToDouble(Chronometer stopwatch){

            String temp = stopwatch.getText().toString();

            int x = 0;

            for (int i = 0; i < temp.length(); i++){



            }

        }


    }
}
