package com.anilicious.rigfinances.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.ViewGroup;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

public class DataEntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        populateDates();
    }

    private void populateDates(){

    }

    /*
     *  Reset form once Submitted button is clicked
     */
    public void clearForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.data_entry_fragment_parent);
        CommonUtils.clearForm(group);
    }

    public boolean validForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.data_entry_fragment_parent);
        return CommonUtils.validForm(group);
    }

}
