package com.example.assignmentmc;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    View view;
    RelativeLayout RL;
    // Define the variable of CalendarView type
    // and TextView type;
    CalendarView calender;
    TextView date_view;
    private Switch nightswitch; //switch to change night mode


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RL = (RelativeLayout)findViewById(R.id.Layout);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);


        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        calender = (CalendarView)findViewById(R.id.calender);
        date_view = (TextView) findViewById(R.id.date_view);
        final TextView dateTime = (TextView) findViewById(R.id.current_date);
        nightswitch = (Switch)findViewById(R.id.nightmode);

        nightswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RL.setBackgroundColor(Color.parseColor("#3C3B37"));
                }
                else {
                    RL.setBackgroundColor(Color.parseColor("#FF009688"));
                }
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("My Calender - Aldrich Widjaja");




        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date c = Calendar.getInstance().getTime();
                                dateTime.setText("Current | " + c);

                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String formattedDate = df.format(c);;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        // Add Listener in calendar
        calender.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {
                                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                                // set this date in TextView for Display
                                date_view.setText("Selected | "+ Date);
                            }
                        });
    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
