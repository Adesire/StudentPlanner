package com.example.android.studentplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CourseList extends AppCompatActivity {

    EditText addNewCourse;
    Button addNewCourseButton;
    Button saveButton;
    Button clearButton;
    ListView courseList;
    ArrayAdapter<String> adapter;
    MainActivity activity = new MainActivity();
    ArrayList<String> list = MainActivity.arrayList;
    static Class next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_dialog);

       addNewCourse = (EditText)findViewById(R.id.new_course);
       addNewCourseButton = (Button)findViewById(R.id.add_course);
        saveButton = (Button)findViewById(R.id.save);
        clearButton = (Button)findViewById(R.id.clear);
        courseList = (ListView)findViewById(R.id.course_list);

        theDialog();

    }

    public void theDialog(){

        //list=activity.loadArrayList(getApplicationContext());
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        for(String x:list){
            Log.d("COURSE",x);
        }
        courseList.setAdapter(adapter);

        addNewCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = addNewCourse.getText().toString();
                if(!addNewCourse.getText().toString().isEmpty()){
                    list.add(course);
                    saveArrayList();
                }


                adapter.notifyDataSetChanged();
                addNewCourse.setText("");
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });

        saveIntent();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.isEmpty()){
                    Toast.makeText(CourseList.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(CourseList.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

    }

    public void saveIntent(){
       /* if(next.equals(Monday.class)){
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.isEmpty()){
                        Toast.makeText(CourseList.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(CourseList.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            });
        }else if(next.equals(Tuesday.class)){
                saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.isEmpty()){
                        Toast.makeText(CourseList.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(CourseList.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            });
        }else if(next.equals(Wednesday.class)){
                saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.isEmpty()){
                        Toast.makeText(CourseList.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(CourseList.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            });
        }else if(next.equals(Thursday.class)){
                saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.isEmpty()){
                        Toast.makeText(CourseList.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(CourseList.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            });
        }else if(next.equals(Friday.class)){
                saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.isEmpty()){
                        Toast.makeText(CourseList.this,"Please enter your courses",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(CourseList.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            });
        }*/

    }

    public void saveArrayList(){
        //USING SHARED PREFERENCES
        SharedPreferences savedContent = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = savedContent.edit();
        editor.putInt("course_size",list.size());

        for(int i=0;i<list.size();i++){
            editor.remove("course_size" + i);
            editor.putString("course_size"+i,list.get(i));
        }
        editor.apply();

    }

    public ArrayList<String> loadArrayList(Context context){
        SharedPreferences savedContentLoad = PreferenceManager.getDefaultSharedPreferences(context);
        list.clear();
        int size = savedContentLoad.getInt("course_size",0);

        for(int i=0;i<size;i++){
            list.add(savedContentLoad.getString("course_size" +i,null));
            Log.d("Sharing",savedContentLoad.getString("course_size" +i,null));
        }
        return list;
    }

}
