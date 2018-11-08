package com.example.android.studentplanner;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    int x;

    public TimePickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute,true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(x==0){
            Monday activity = (Monday)getActivity();
            activity.processTimePickerResult(hourOfDay,minute);
        }
        if(x==1){
            Tuesday activity = (Tuesday) getActivity();
            activity.processTimePickerResult(hourOfDay,minute);
        }
        if(x==2){
            Wednesday activity = (Wednesday) getActivity();
            activity.processTimePickerResult(hourOfDay,minute);
        }
        if(x==3){
            Thursday activity = (Thursday) getActivity();
            activity.processTimePickerResult(hourOfDay,minute);
        }
        if(x==4){
            Friday activity = (Friday) getActivity();
            activity.processTimePickerResult(hourOfDay,minute);
        }

    }
}
