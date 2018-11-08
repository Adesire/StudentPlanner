package com.example.android.studentplanner;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.Context;

public interface TimeTable {
    long repeatInterval = 8*AlarmManager.INTERVAL_DAY;
    //TimePickers
    void processTimePickerResult(int hourOfDay,int minute);
    void processTimeToPickerResult(int hourOfDay,int minute);
    void processTimeNotePickerResult(int hourOfDay,int minute);

    //TimeTable
    void savingTimeTable();
    void loadingTimeTableHere(Context context);

    //Notification
    void scheduleReminder(Notification notification, int a);
    void scheduleReminder2(Notification notification,int a);
    void alarmNoteStuff(Notification notification,int a);
    Notification getNotification(String content);

    //CHECKBOX STUFF
    void userCheckPreference(boolean v);
    boolean loadCheckboxValue();

}
