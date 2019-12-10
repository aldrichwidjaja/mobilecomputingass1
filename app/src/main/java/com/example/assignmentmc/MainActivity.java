package com.example.assignmentmc;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

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
    TextView event_view;
    TextView monthview;
    private Switch nightswitch; //switch to change night mode
    CompactCalendarView newcalender;
    private SimpleDateFormat dateformatmonth = new SimpleDateFormat ("dd - MMMM - YYYY", Locale.getDefault());
    private SimpleDateFormat dateformatmonth2 = new SimpleDateFormat ("MMMM - YYYY", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RL = (RelativeLayout)findViewById(R.id.Layout);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        monthview = (TextView)findViewById(R.id.monthview);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("My Calender - Aldrich Widjaja");



        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        date_view = (TextView) findViewById(R.id.date_view);
        final TextView dateTime = (TextView) findViewById(R.id.current_date);
        event_view = (TextView) findViewById(R.id.event_view);

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


        //to update time every 0.5s in the textview
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
                                String formattedDate = df.format(c);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        newcalender = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        newcalender.setUseThreeLetterAbbreviation(true);
        //set event here
        Event ev1 = new Event(Color.RED, 1576630800000L, "My Good Day");
        Event ev4 = new Event(Color.RED, 1576630800000L, "test day");
        Event ev2 = new Event(Color.RED, 1577235600000L, "Christmas!");
        Event ev3 = new Event(Color.RED, 1577581200000L, "ASSIGNMENT SUBMITION");

        newcalender.addEvent(ev1);
        newcalender.addEvent(ev2);
        newcalender.addEvent(ev3);
        newcalender.addEvent(ev4);



        newcalender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                date_view.setText(dateformatmonth.format(dateClicked));
                System.out.println(dateClicked.toString());

                if (dateClicked.toString().compareTo("Wed Dec 18 00:00:00 GMT+08:00 2019") ==0) {
                    date_view.setText(dateformatmonth.format(dateClicked));
                    event_view.setText("My Good Day");
                }else if (dateClicked.toString().compareTo("Wed Dec 25 00:00:00 GMT+08:00 2019") ==0) {
                    date_view.setText(dateformatmonth.format(dateClicked));
                    event_view.setText("Christmas");
                } else if (dateClicked.toString().compareTo("Sun Dec 29 00:00:00 GMT+08:00 2019") ==0) {
                    date_view.setText(dateformatmonth.format(dateClicked));
                    event_view.setText("ASSIGNMENT SUBMITION");
                } else {
                    date_view.setText(dateformatmonth.format(dateClicked));
                    event_view.setText("No events for that day");
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthview.setText(dateformatmonth2.format(firstDayOfNewMonth));
            }
        });

        // Add Listener in calendar
    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();


    }
}
