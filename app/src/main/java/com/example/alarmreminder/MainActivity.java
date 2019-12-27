package com.example.alarmreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
AlarmManager alarmManager;
TimePicker alarmTimePicker;
TextView updateText;
Context context;
PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmTimePicker=findViewById(R.id.timePicker);
        updateText=findViewById(R.id.updateText);
       final Calendar calendar=Calendar.getInstance();

        final Intent myIntent=new Intent(this.context,AlarmReciever.class);

        Button alarmOn=findViewById(R.id.alarmOn);


        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE,alarmTimePicker.getMinute());
               int hour=alarmTimePicker.getHour();
                int minute=alarmTimePicker.getMinute();
                String hourString=String.valueOf(hour);
                String minuteString=String.valueOf(minute);
                if(hour>12)
                {
                    hourString=String.valueOf(hour-12);
                }
                if(minute<10)
                {
                    minuteString="0"+String.valueOf(minute);
                }
                setAlarmText("Alarm set to: "+hourString+":"+minuteString);
myIntent.putExtra("extra","yes");


            pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        });
        Button alarmOff=findViewById(R.id.alarmOff);
alarmOff.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        setAlarmText("Alarm Off");
        alarmManager.cancel(pendingIntent);

        myIntent.putExtra("extra","no");

sendBroadcast(myIntent);


    }
});
    }

    private void setAlarmText(String output) {
        updateText.setText(output);
    }
}
