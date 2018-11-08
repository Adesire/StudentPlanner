package com.example.android.studentplanner;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TimePicker;

public class TimeToFragment extends TimePickerFragment {

    int x;

    public TimeToFragment(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(x==0){
            Monday activity = (Monday)getActivity();
            activity.processTimeToPickerResult(hourOfDay,minute);
        }
        if(x==1){
            Tuesday activity = (Tuesday) getActivity();
            activity.processTimeToPickerResult(hourOfDay,minute);
        }
        if(x==2){
            Wednesday activity = (Wednesday) getActivity();
            activity.processTimeToPickerResult(hourOfDay,minute);
        }
        if(x==3){
            Thursday activity = (Thursday) getActivity();
            activity.processTimeToPickerResult(hourOfDay,minute);
        }
        if(x==4){
            Friday activity = (Friday) getActivity();
            activity.processTimeToPickerResult(hourOfDay,minute);
        }
    }
}
