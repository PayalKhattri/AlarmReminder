package com.example.alarmreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("we are in the reciever","Yes");
        String getString=intent.getExtras().getString("extra");


        Log.e("key",getString);
    Intent serviceIntent=new Intent(context,RingtonePlayingService.class);

    serviceIntent.putExtra("extra",getString);
    context.startService(serviceIntent);
    }
}
