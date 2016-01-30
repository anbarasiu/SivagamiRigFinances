package com.anilicious.rigfinances.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
public class EmployeeDetailsRegistrationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_registration, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final EditText etEmployeeNumber = (EditText)view.findViewById(R.id.editText1);
        final EditText etEmployeeName = (EditText)view.findViewById(R.id.editText2);
        final EditText etDesignation = (EditText)view.findViewById(R.id.editText3);
        final EditText etSalary = (EditText)view.findViewById(R.id.editText4);

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(((EmployeeDetailsActivity)getActivity()).validForm()){
                    int employeeNumber = Integer.parseInt(etEmployeeNumber.getText().toString());
                    String employeeName = etEmployeeName.getText().toString();
                    String designation = etDesignation.getText().toString();
                    double salary = Double.parseDouble(etSalary.getText().toString());

                    Employee employee = new Employee();
                    employee.setNumber(employeeNumber);
                    employee.setName(employeeName);
                    employee.setDesignation(designation);
                    employee.setSalary(salary);

                    // Insert to DB
                    DBAdapter dbAdapter = DBAdapter.getInstance(getActivity().getApplicationContext());
                    dbAdapter.insertEmployee(employee);

                    // Clear the Form
                    ((EmployeeDetailsActivity)getActivity()).clearForm();
                }
            }
        });
        return view;
    }
}