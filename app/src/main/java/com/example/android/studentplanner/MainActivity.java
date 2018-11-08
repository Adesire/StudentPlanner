package com.example.android.studentplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText addNewCourse;
    Button addNewCourseButton;
    Button saveButton;
    Button clearButton;
    ListView courseList;
    static ArrayList<String> arrayList = new ArrayList<String>();;
    AlertDialog.Builder mBuilder;
    ArrayAdapter<String> adapter;

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                openSecondAct();
            }
        },SPLASH_TIME_OUT);
    }

    public void openSecondAct() {

        mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.course_dialog,null);

        addNewCourse = (EditText) mView.findViewById(R.id.new_course);
        addNewCourseButton = (Button) mView.findViewById(R.id.add_course);
        saveButton = (Button) mView.findViewById(R.id.save);
        clearButton = (Button) mView.findViewById(R.id.clear);
        courseList = (ListView) mView.findViewById(R.id.course_list);


        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);

        courseList.setAdapter(adapter);

        if(loadArrayList(getApplicationContext()).isEmpty()){
        //courseDialog();
        mBuilder.setView(mView);
        mBuilder.create().show();

        courseDialog();

        //loadArrayListHere(getApplicationContext());



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                    Intent mine = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(mine);
                }
            }
        });



        }else{
            Intent mine = new Intent(this,SecondActivity.class);
            startActivity(mine);
        }
    }

    public void courseDialog(){
        addNewCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = addNewCourse.getText().toString();
                if(!addNewCourse.getText().toString().isEmpty()){
                    arrayList.add(course);
                    saveArrayList();
                }else{
                    //nothing
                }

                adapter.notifyDataSetChanged();
                addNewCourse.setText("");
            }
        });


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void saveArrayList(){
        //USING SHARED PREFERENCES
        SharedPreferences savedContent = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = savedContent.edit();
        editor.putInt("course_size",arrayList.size());

        for(int i=0;i<arrayList.size();i++){
            editor.remove("course_size" + i);
            editor.putString("course_size"+i,arrayList.get(i));
        }
        editor.apply();

    }

    public ArrayList<String> loadArrayList(Context context){
        SharedPreferences savedContentLoad = PreferenceManager.getDefaultSharedPreferences(context);
        arrayList.clear();
        int size = savedContentLoad.getInt("course_size",0);

        for(int i=0;i<size;i++){
            arrayList.add(savedContentLoad.getString("course_size" +i,null));
        }
        return arrayList;
    }

    public void loadArrayListHere(Context context){
        SharedPreferences savedContentLoad = PreferenceManager.getDefaultSharedPreferences(context);
        arrayList.clear();
        int size = savedContentLoad.getInt("course_size",0);

        for(int i=0;i<size;i++){
            arrayList.add(savedContentLoad.getString("course_size" +i,null));
            Log.d("Sharing",savedContentLoad.getString("course_size" +i,null));
        }
    }


    public void openIt(View view) {
        openSecondAct();
    }
}
