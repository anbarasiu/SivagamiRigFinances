package com.anilicious.rigfinances.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.anilicious.rigfinances.fragments.DebitFragment;

import java.util.Calendar;

/**
 * Created by ANBARASI on 9/12/14.
 */
public class PickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private String pickerType;

    public PickerFragment(){

    }

    public PickerFragment(String pickerType){
        this.pickerType = pickerType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(pickerType == CommonUtils.DIALOG_DATE){
            //Current Date as Default Date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int date = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, date);
        }
        /*else if(pickerType == CommonUtils.DIALOG_TIME){
            return new TimePickerDialog(getActivity(), this, );
        }*/
        return null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String entryDate = new StringBuilder().append(day).append("/")
                .append(month + 1).append("/").append(year).toString();

        // Return the Date to the calling fragment
        Intent i = getActivity().getIntent();
        i.putExtra("entryDate", entryDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), 101, i);
    }

}
