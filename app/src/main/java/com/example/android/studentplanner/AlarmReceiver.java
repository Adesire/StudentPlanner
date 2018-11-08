package com.example.android.studentplanner;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    //private static final int NOTIFICATION_ID = 0;
    static String message,message1;
    String m1,m2;
    static int num;
    Intent intent;
    //m1 =
    static ArrayList<String> msg;
    static String[] mesg = new String[num];
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String NOTIFICATION_MSG = "notification_msg";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        //context.startService(new Intent(context,AlarmSoundService.class));
        notificationManager.notify(id, notification);
    }

    public void sendNotification(Context context,String m, int id){

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notifyMe = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Create the content intent for the notification,which launches this activity,which launches this activity
        Intent contentIntent = new Intent(context,SecondActivity.class);
        //PendingIntent contentPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Create the actions
        /*Intent snooze = new Intent(context,AlarmSoundService.class);
        snooze.setAction(AlarmSoundService.ACTION_SNOOZE);
        PendingIntent pending = PendingIntent.getService(context,1,snooze,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action= new NotificationCompat.Action(0,"SNOOZE",pending);
*/
        // = new Monday()userOrder;



            //Build the notification
            //PendingIntent contentPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID+i,contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(context)
                    .setContentTitle("THE STUDENT PLANNER")
                    //.setContentText("You have " + msg.get(i) + message1)
                    .setContentText("You have " + m)
                    .setSmallIcon(R.drawable.ic_notification_2)
                    //.setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)//.addAction(action)
                    //.setTimeoutAfter(60*1000)
                    .setSound(alarmSound);//.setTimeoutAfter(60 * 1000);
        //Deliver the notification
        Notification mine = notifyBuild.build();
        notifyMe.notify(id, mine);Log.e("MXYZKL",m+"");

            //Log.d("MY LIST2",msg.get(i));
            //for(int i=0;i<msg.size();i++) {
            //Deliver the notification
                /*notifyBuild.setContentText("You have " +message);
                Log.e("MXYZKL",message);
            mine = notifyBuild.build();
            notifyMe.notify(NOTIFICATION_ID, mine);*/
        //}
        //START SOUND
        //context.startService(new Intent(context,AlarmSoundService.class));


        Calendar classTime = Calendar.getInstance();
        classTime.setTimeInMillis(System.currentTimeMillis());
        classTime.set(Calendar.HOUR_OF_DAY,Monday.youHr);
        classTime.set(Calendar.MINUTE,Monday.youMin);

        /*if(System.currentTimeMillis()>=classTime.getTimeInMillis()){
            notifyMe.cancelAll();
        }*/
        /*if(SystemClock.elapsedRealtime()==classTime.getTimeInMillis()){
            AlarmManager alarmManager2 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent in = new Intent(context,AlarmReceiver.class);
            PendingIntent notifyPending = PendingIntent.getBroadcast(context,123,in,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager2.set(AlarmManager.RTC,classTime.getTimeInMillis(),notifyPending);
        }*/
    }
}
