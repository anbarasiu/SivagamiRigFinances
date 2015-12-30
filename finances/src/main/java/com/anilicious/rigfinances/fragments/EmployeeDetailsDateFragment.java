package com.anilicious.rigfinances.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.Switch;

import com.anilicious.rigfinances.activities.EmployeeDetailsActivity;
import com.anilicious.rigfinances.activities.MainActivity;
import com.anilicious.rigfinances.activities.SettingsActivity;
import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Employee;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.Calendar;

/**
 * Created by ANBARASI on 12/29/15.
 */
public class EmployeeDetailsDateFragment extends Fragment {

    EditText etDate;
    EditText etDoj;
    EditText etDol;

    static final int DATE_DIALOG_ID = 1;
    private int pHour;
    private int pMinute;
    private int currentYear;
    private int currentMonth;
    private int currentDate;

    SharedPreferences sharedPrefs;

    private static final int DATE_FIELD_ID = 1;
    private static final int DOJ_FIELD_ID = 2;
    private static final int DOL_FIELD_ID = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_details, null);

        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final EditText etEmployeeNumber = (EditText)view.findViewById(R.id.editText1);
        final Switch sEntryType = (Switch)view.findViewById(R.id.switch1);
        etDoj = (EditText)view.findViewById(R.id.editText4);
        etDol = (EditText)view.findViewById(R.id.editText5);
        etDate = (EditText)view.findViewById(R.id.editText6);

        // Setup Date Picker
        setupPickers();

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(((VouchersActivity)getActivity()).validForm()){
                    int employeeNumber = Integer.parseInt(etEmployeeNumber.getText().toString());
                    String lastDateOfJoiningOrLeaving = etDate.getText().toString();
                    String date2 = etDoj.getText().toString();
                    String date3 = etDol.getText().toString();
                    Integer date = Integer.parseInt(CommonUtils.formatDateEntry(lastDateOfJoiningOrLeaving));
                    Integer dateOfJoining = Integer.parseInt(CommonUtils.formatDateEntry(date2));
                    Integer dateOfLeaving=Integer.parseInt(CommonUtils.formatDateEntry(date3));

                    Employee employee = new Employee();
                    employee.setDate(date);
                    employee.setNumber(employeeNumber);
                    employee.setDateOfJoining(dateOfJoining);
                    employee.setDateOfLeaving(dateOfLeaving);

                    // Insert to DB
                    DBAdapter dbAdapter = DBAdapter.getInstance(getActivity().getApplicationContext());
                    dbAdapter.updateEmployee(employee);

                    // Clear the Form
                    ((EmployeeDetailsActivity)getActivity()).clearForm();
            }
            }
        });

        sharedPrefs = this.getActivity().getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        return view;
    }

    /*
    * Setup the Time Picker Dialog
    */
    private void setupPickers(){

        /* Get the current time */
        final Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDate = cal.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateSetListener, currentYear, currentMonth, currentDate).show();
                //showDialog(DATE_FIELD_ID);
            }
        });

        etDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateOfJoiningSetListener, currentYear, currentMonth, currentDate).show();
                //showDialog(DOJ_FIELD_ID);
            }
        });

        etDol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateOfLeavingSetListener, currentYear, currentMonth, currentDate).show();
                //showDialog(DOL_FIELD_ID);
            }
        });
    }

    /*@Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DATE_FIELD_ID:
                return new DatePickerDialog(getActivity().getApplicationContext(), mDateSetListener, currentYear, currentMonth, currentDate);
            case DOJ_FIELD_ID:
                return new DatePickerDialog(getActivity().getApplicationContext(), mDateOfJoiningSetListener, currentYear, currentMonth, currentDate);
            case DOL_FIELD_ID:
                return new DatePickerDialog(getActivity().getApplicationContext(), mDateOfLeavingSetListener, currentYear, currentMonth, currentDate);
        }
        return null;
    }*/

    // TODO: Is there a better way instead of these multiple callbacks for different datepicker fields?

   /* Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDate.setText(new StringBuilder().append(day).append("/")
                            .append(month).append("/").append(year).toString());
                }
            };

    private DatePickerDialog.OnDateSetListener mDateOfJoiningSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDoj.setText(new StringBuilder().append(day).append("/")
                            .append(month).append("/").append(year).toString());
                }
            };

    private DatePickerDialog.OnDateSetListener mDateOfLeavingSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDol.setText(new StringBuilder().append(day).append("/")
                            .append(month).append("/").append(year).toString());
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent homeIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        else{
            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
