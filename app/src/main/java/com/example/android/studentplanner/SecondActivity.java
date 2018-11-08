package com.example.android.studentplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;

import com.github.bluzwong.swipeback.SwipeBackActivityHelper;

public class SecondActivity extends AppCompatActivity {

    Button monday,tuesday,wednesday,thursday,friday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        monday = (Button) findViewById(R.id.mon);
        tuesday = (Button) findViewById(R.id.tue);
        wednesday = (Button) findViewById(R.id.wed);
        thursday = (Button) findViewById(R.id.thur);
        friday = (Button) findViewById(R.id.fri);

    }

    public void monWork(View view) {
        Intent intent = new Intent(this,Monday.class);
        swipeAnimation(intent);
       // startActivity(intent);
    }

    public void tueWork(View view) {
        Intent intent = new Intent(this,Tuesday.class);
        swipeAnimation(intent);
        //startActivity(intent);
    }

    public void wedWork(View view) {
        Intent intent = new Intent(this,Wednesday.class);
        swipeAnimation(intent);
        //startActivity(intent);
    }

    public void thurWork(View view) {
        Intent intent = new Intent(this,Thursday.class);
        swipeAnimation(intent);
        //startActivity(intent);
    }

    public void friWork(View view) {
        Intent intent = new Intent(this,Friday.class);
        swipeAnimation(intent);
        //startActivity(intent);
    }

    public void swipeAnimation(Intent intent){
        SwipeBackActivityHelper.activityBuilder(SecondActivity.this)
                .intent(intent)
                .needParallax(true)
                .needBackgroundShadow(true)
                .startActivity();
    }
}
