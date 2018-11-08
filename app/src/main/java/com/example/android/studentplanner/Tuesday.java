package com.example.android.studentplanner;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bluzwong.swipeback.SwipeBackActivityHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class Tuesday extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimeTable {

    private static final String CHECK_BOX = "check_box" ;
    Spinner spin;
    ArrayAdapter<String> spinAdapter,table;
    ListView timeTable;

    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Long> list2 = new ArrayList<Long>();
    ArrayList<Long> list3 = new ArrayList<Long>();

    String spinItem,courseFromTime,courseToTime,totalCourseInfo,noteTime;
    Button save;
    TextView from,to;
    NotificationManager notifyMe;
    int youMin,youHr,NOTE_HR,NOTE_MIN;
    boolean isChecked = true;

    EditText editNote;
    TextView mTextView;
    Button saveNote,clearNote,timeNote;

    private static final String ACTION_NOTIFY = "com.example.android.studentplanner.ACTION_NOTIFY";

    private static final int NOTIFICATION_ID = 0;
    private static final int NOTIFICATION_ID2 =1231;

    SwipeBackActivityHelper help = new SwipeBackActivityHelper();

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        help.setEdgeMode(true)
                .setParallaxMode(true)
                .setParallaxRatio(3)
                .setNeedBackgroundShadow(true)
                .init(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                AlertDialog.Builder buildIt = new AlertDialog.Builder(Tuesday.this);
                View mView = getLayoutInflater().inflate(R.layout.notes_dialog,null);

                editNote = (EditText)mView.findViewById(R.id.notes);
                mTextView = (TextView)mView.findViewById(R.id.textView);
                saveNote = (Button)mView.findViewById(R.id.saveNotes);
                clearNote = (Button)mView.findViewById(R.id.clearNotes);
                timeNote = (Button)mView.findViewById(R.id.timeNotes);
                buildIt.setView(mView);
                buildIt.setPositiveButton(android.R.string.ok,null)
                        .create().show();

                loadNotes(timeNote);

                saveNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savingNotes();
                    }
                });
                clearNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editNote.setText("");
                        timeNote.setText(R.string.pick_a_time);
                        alarmNoteStuff(getNotification("You have a Note to remember."),0);
                        savingNotes();
                    }
                });
                timeNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNoteTimePickerDialog(v);
                        alarmNoteStuff(getNotification("You have a Note to remember."),1);
                    }
                });

            }
        });

        loadingTimeTableHere(getApplicationContext());

        notifyMe = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        table = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        from = (TextView)findViewById(R.id.fromTxt);
        to = (TextView)findViewById(R.id.toTxt);

        timeTable = (ListView)findViewById(R.id.timeTableList);
        timeTable.setAdapter(table);



        save = (Button)findViewById(R.id.saveTableBtn);

        spin = (Spinner) findViewById(R.id.course_spinner);

        spinAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new MainActivity().loadArrayList(getApplicationContext()));

        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setOnItemSelectedListener(this);

        if(spin != null){

            spin.setAdapter(spinAdapter);
        }
        //REMOVE ITEM FROM TABLE
        tableItemRemover();

        //CHECKBOX VALUE
        isChecked = loadCheckboxValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.turnOff);
        checkable.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.editCourses:
                //CourseList.next = Tuesday.class;
                Intent mine = new Intent(this,CourseList.class);
                startActivity(mine);
                if(!(new MainActivity().loadArrayList(getApplicationContext()).equals(new CourseList().loadArrayList(getApplicationContext())))){

                    spinAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new CourseList().loadArrayList(getApplicationContext()));
                    spin.setAdapter(spinAdapter);
                }
                return true;
            case R.id.reset:
                if(list.isEmpty()){
                    Toast.makeText(this,"EMPTY TABLE",Toast.LENGTH_LONG).show();
                    return true;
                }else{
                    AlertDialog.Builder surity = new AlertDialog.Builder(this);
                    surity.setTitle("Alert")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you sure you want to reset the table?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    list.clear();
                                    list2.clear();
                                    list3.clear();
                                    savingTimeTable();
                                    table.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel,null)
                            .show();
                    return true;
                }
            case R.id.turnOff:
                isChecked = !item.isChecked();
                userCheckPreference(isChecked);
                item.setChecked(isChecked);
                if(item.isChecked()){
                    if(list.isEmpty()){
                        list2.clear();
                        list3.clear();
                    }else {
                        scheduleReminder(getNotification("You have a class in 15 minutes"),1);
                        scheduleReminder2(getNotification("You have a class in 10 minutes"),1);
                    }
                    Toast.makeText(this,"Alarm On",Toast.LENGTH_SHORT).show();
                    item.setTitle(R.string.turn_off_alarm);
                }else{

                    scheduleReminder(getNotification("You have a class in 15 minutes"),0);
                    scheduleReminder2(getNotification("You have a class in 10 minutes"),0);

                    Toast.makeText(this,"Alarm Off",Toast.LENGTH_SHORT).show();
                    item.setTitle(R.string.turn_on_alarm);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFromTimePickerDialog(View view) {
        TimePickerFragment time = new TimePickerFragment();
        time.x=1;
        time.show(getSupportFragmentManager(),getString(R.string.time_picker));
    }

    public void showToTimePickerDialog(View view) {
        TimeToFragment time2 = new TimeToFragment();
        time2.x=1;
        time2.show(getSupportFragmentManager(),getString(R.string.time_picker));
    }

    public void showNoteTimePickerDialog(View view) {
        TimeNoteFragment time3 = new TimeNoteFragment();
        time3.x=1;
        time3.show(getSupportFragmentManager(),getString(R.string.time_picker));
    }

    public void processTimePickerResult(int hourOfDay,int minute){

        String hour = Integer.toString(hourOfDay);
        String min = Integer.toString(minute);
        youHr = hourOfDay;
        youMin = minute;
        if(minute<10){
            //hour = "0"+hour;
            min = "0"+min;
            String time = hour+":"+min;
            Toast.makeText(this,time,Toast.LENGTH_LONG).show();

            from.setText(time);
            courseFromTime = time;
        }else{
            String time = hour+":"+min;
            Toast.makeText(this,time,Toast.LENGTH_LONG).show();

            from.setText(time);
            courseFromTime = time;
        }
    }

    public void processTimeToPickerResult(int hourOfDay,int minute){

        String hour = Integer.toString(hourOfDay);
        String min = Integer.toString(minute);
        if(minute<10){
            //hour = "0"+hour;
            min = "0"+min;
            String time = hour+":"+min;
            Toast.makeText(this,time,Toast.LENGTH_LONG).show();
            to.setText(time);
            courseToTime = time;
        }else{
            String time = hour+":"+min;
            Toast.makeText(this,time,Toast.LENGTH_LONG).show();
            to.setText(time);
            courseToTime = time;
        }
    }

    public void processTimeNotePickerResult(int hourOfDay,int minute){
        String hour = Integer.toString(hourOfDay);
        String min = Integer.toString(minute);
        NOTE_HR = hourOfDay;
        NOTE_MIN = minute;
        if(minute<10){
            //hour = "0"+hour;
            min = "0"+min;
            String time = hour+":"+min;
            Toast.makeText(this,time,Toast.LENGTH_LONG).show();

            noteTime = time;
        }else{
            String time = hour+":"+min;
            Toast.makeText(this,time,Toast.LENGTH_LONG).show();

            noteTime = time;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        spinItem = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this,spinItem+"Chai",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void saveToTable(View view) {
        if(courseFromTime==null || courseToTime==null){
            AlertDialog.Builder timeChange = new AlertDialog.Builder(this);
            timeChange.setTitle("ERROR")
                    .setMessage("PLEASE INPUT TIME")
                    .setPositiveButton(android.R.string.ok,null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            String total =courseFromTime+" - "+courseToTime;
            totalCourseInfo =spinItem+"\t\t\t\t"+total;

            list.add(totalCourseInfo);
            savingTimeTable();
            table.notifyDataSetChanged();

            scheduleReminder(getNotification("You have a class in 15 minutes"),1);
            scheduleReminder2(getNotification("You have a class in 10 minutes"),1);

            from.setText(R.string.from_time);
            to.setText(R.string.to_time);
        }

    }



    public void tableItemRemover(){

        timeTable.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Object tableItem = parent.getItemAtPosition(position);
                //Toast.makeText(Tuesday.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                AlertDialog.Builder build = new AlertDialog.Builder(Tuesday.this);
                build.setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Remove from List?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(tableItem);
                                savingTimeTable();

                                scheduleReminder(getNotification("You have a class in 15 minutes"),0);
                                scheduleReminder2(getNotification("You have a class in 10 minutes"),0);

                                table.notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public void savingTimeTable(){
        //USING SHARED PREFERENCES
        SharedPreferences savedContentTimeTable = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = savedContentTimeTable.edit();
        editor.putInt("course_time_table_tue",list.size());

        for(int i=0;i<list.size();i++){
            editor.remove("course_time_table_tue" + i);
            editor.putString("course_time_table_tue"+i,list.get(i));
        }
        editor.apply();
    }

    public void loadingTimeTableHere(Context context){
        SharedPreferences savedContentLoadTimeTable = PreferenceManager.getDefaultSharedPreferences(context);
        list.clear();
        int size = savedContentLoadTimeTable.getInt("course_time_table_tue",0);

        for(int i=0;i<size;i++){
            list.add(savedContentLoadTimeTable.getString("course_time_table_tue" +i,null));
            Log.d("Sharing",savedContentLoadTimeTable.getString("course_time_table_tue" +i,null));
        }
    }

    //@Override
    public void scheduleReminder(Notification notification, int a) {
        Intent notifyIntent = new Intent(ACTION_NOTIFY);
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION_ID,1);
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION,notification);

        int yourHour = youHr;
        int yourMins = youMin-15;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY,yourHour);
        cal.set(Calendar.MINUTE,yourMins);
        cal.set(Calendar.SECOND,00);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        long triggerTime = cal.getTimeInMillis();
        list2.add(triggerTime);

        for(int i=0;i<list2.size();i++){
            if(Calendar.TUESDAY == dayOfWeek){
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(a==1) {
                Log.e("TAG",list2.get(i).toString());
                alarmManager.setRepeating(AlarmManager.RTC, list2.get(i), TimeTable.repeatInterval, pendingIntent);
            }else if(a==0){
                alarmManager.cancel(pendingIntent);
            }
            }

        }
    }

    @Override
    public void scheduleReminder2(Notification notification, int a) {
        Intent notifyIntent = new Intent(ACTION_NOTIFY);
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION_ID,2);
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION,notification);

        int yourHour = youHr;
        int yourMins = youMin-10;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY,yourHour);
        cal.set(Calendar.MINUTE,yourMins);
        cal.set(Calendar.SECOND,00);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        long triggerTime = cal.getTimeInMillis();
        list3.add(triggerTime);

        for(int i=0;i<list3.size();i++){
            if(Calendar.TUESDAY == dayOfWeek){
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100+i, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(a==1) {
                Log.e("TAG2",list3.get(i).toString());
                alarmManager.setRepeating(AlarmManager.RTC, list3.get(i), TimeTable.repeatInterval, pendingIntent);
            }else if(a==0){
                alarmManager.cancel(pendingIntent);
            }
            }

        }
    }

    @Override
    public void alarmNoteStuff(Notification notification, int a) {
        Intent notifyIntent = new Intent(ACTION_NOTIFY);
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION_ID,3);
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION,notification);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this,3,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        int yourHour = NOTE_HR;
        int yourMins = NOTE_MIN;

        Calendar calN = Calendar.getInstance();
        calN.setTimeInMillis(System.currentTimeMillis());
        calN.set(Calendar.HOUR_OF_DAY,yourHour);
        calN.set(Calendar.MINUTE,yourMins);

        int dayOfWeek = calN.get(Calendar.DAY_OF_WEEK);

        long triggerTime = calN.getTimeInMillis();

        if(dayOfWeek == Calendar.TUESDAY) {
        if (a == 1) {
            Toast.makeText(this, "Alarm SET", Toast.LENGTH_SHORT).show();
            alarmManager.set(AlarmManager.RTC, triggerTime, notifyPendingIntent);
        } else if (a == 0) {
            alarmManager.cancel(notifyPendingIntent);
        }
        }
    }

    @Override
    public Notification getNotification(String content) {
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,0,new Intent(this,Tuesday.class),PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("The Student Planner");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_notification_2);
        builder.setSound(alarmSound);
        builder.setContentIntent(contentPendingIntent);
        return builder.build();
    }

    public void savingNotes(){
        //USING SHARED PREFERENCES
        String note = editNote.getText().toString();
        SharedPreferences savedNote = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = savedNote.edit();

        editor.putString("save_note_tue",note);
        editor.putString("note_time_tue",noteTime);
        editor.apply();
        Toast.makeText(this,"SUCCESSFUL",Toast.LENGTH_SHORT).show();
    }

    public void loadNotes(Button button){
        SharedPreferences savedNote = PreferenceManager.getDefaultSharedPreferences(this);
        String note =savedNote.getString("save_note_tue",null);
        editNote.setText(note);
        if(editNote.getText().toString().isEmpty()){
            button.setText(R.string.pick_a_time);
        }else{
            button.setText(savedNote.getString("note_time_tue",null));
        }
    }

    @Override
    public void onBackPressed() {
        help.finish();
    }

    public void userCheckPreference(boolean v){
        SharedPreferences checkbox = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = checkbox.edit();
        editor.putBoolean(CHECK_BOX,v);
        editor.apply();
    }

    public boolean loadCheckboxValue(){
        SharedPreferences checkbox = PreferenceManager.getDefaultSharedPreferences(this);
        return checkbox.getBoolean(CHECK_BOX,true);
    }

}
