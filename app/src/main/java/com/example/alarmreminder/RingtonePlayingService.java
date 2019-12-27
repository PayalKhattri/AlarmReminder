package com.example.alarmreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer mediaSong;
int startId;
boolean isRunning;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Log.i("LocalService", "Received start id " + startId + ": " + intent);

            String state=intent.getExtras().getString("extra");

            Log.e("ringtone starts",state);





            assert state!=null;
            switch (state) {
                case "no":
                    startId = 0;
                    break;
                case "yes":
                    startId = 1;
                    break;
                default:
                    startId = 0;
                    break;
            }


if(!this.isRunning && startId==1){
    mediaSong=MediaPlayer.create(this,R.raw.alarm);

    NotificationManager notifyManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

    Intent intentMain=new Intent(this.getApplicationContext(),MainActivity.class);

    PendingIntent pendingIntentMain=PendingIntent.getActivity(this,0,intentMain,0);
    Notification notificationPopup=new Notification.Builder(this)
            .setContentTitle("An alarm is going off")
            .setContentText("Click me!")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntentMain)

            .setAutoCancel(true)
            .build();
    mediaSong.start();
    notifyManager.notify(0,notificationPopup);
    this.isRunning=true;
    this.startId=0;

}
else if(this.isRunning && startId==0){
mediaSong.stop();
mediaSong.reset();
this.isRunning=false;
this.startId=0;
}
else if(!this.isRunning && startId==0){
this.isRunning=false;
this.startId=0;
}
else if(this.isRunning && startId==1){
this.isRunning=true;
this.startId=1;
}




            return START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {

            // Tell the user we stopped.
            Toast.makeText(this, "On Destroy called", Toast.LENGTH_SHORT).show();

      super.onDestroy();
      this.isRunning=false;
        }




}
