package com.example.android.studentplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.Calendar;

public class AlarmSoundService extends Service {

    private MediaPlayer myMedia;
    public static final String ACTION_SNOOZE =AlarmSoundService.class.getName()+".ACTION_SNOOZE";

    public AlarmSoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //START MEDIA PLAYER
        myMedia = MediaPlayer.create(this,R.raw.app_alarm);
        myMedia.start();
        myMedia.setLooping(false);
        //snooze();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //STOP AND RELEASE MEDIA PLAYER
        if(myMedia != null && myMedia.isPlaying()){
            myMedia.stop();
            myMedia.reset();
            myMedia.release();
        }
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action  = intent.getAction();
        if(ACTION_SNOOZE.equals(action)){
            snooze();
        }
        return START_NOT_STICKY;
    }*/
    public void snooze(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar classTime = Calendar.getInstance();
        classTime.set(Calendar.HOUR_OF_DAY,Monday.youHr);
        classTime.set(Calendar.MINUTE,Monday.youMin);

        long trigger = classTime.getTimeInMillis();
        long repeatInterval = AlarmManager.INTERVAL_DAY;
        //Monday.repeat = repeatInterval;

        alarmManager.set(AlarmManager.RTC,trigger,pendingIntent);
    }
}
