package com.anilicious.rigfinances.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.anilicious.rigfinances.activities.IDataEntryActivity;

import java.util.Calendar;

/**
 * Created by ANBARASI on 9/12/14.
 */
public class PickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private String pickerType;
    private IDataEntryActivity mCallback;

    public static PickerFragment newInstance(String pickerType){
        Bundle bundle = new Bundle();
        bundle.putString("pickerType", pickerType);
        PickerFragment pickerFragment = new PickerFragment();
        pickerFragment.setArguments(bundle);
        return pickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getArguments() != null){
            pickerType = getArguments().getString("pickerType");
        }
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
        /*
        Intent i = getActivity().getIntent();
        i.putExtra("entryDate", entryDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), 101, i);
        */

        // Return the Date to the calling activity
        mCallback.onDialogDataSet(entryDate);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (IDataEntryActivity) activity;
        } catch(ClassCastException e){
            Log.d("PickerFragment", "Activity doesn't implement the IDataEntryActivity interface");
        }
    }
}