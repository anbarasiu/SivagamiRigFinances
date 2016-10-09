package com.anilicious.rigfinances.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ANBARASI on 15/11/14.
 */
public final class CommonUtils {

    public static final String VOUCHER_COOK = "cook";
    public static final String VOUCHER_TOOL = "tool";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";

    public static final String FINANCIAL_YEAR_START = "01/04";  // DD/MM
    public static final String FINANCIAL_YEAR_END = "01/03";    // DD/MM

    public class CONSTANTS{
        public static final String DIESEL = "Diesel";
        public static final String COOK = "Cook";
        public static final String CREDIT = "Credit";
        public static final String MAINTENANCE = "Maintenance";
        public static final String PIPE = "Pipe";
        public static final String ROAD = "Road";
        public static final String SALARY = "Salary";
        public static final String SITE = "Site";
        public static final String TOOLS = "Tool";
    }

    public static String formatDateEntry(String date){
        if(date.isEmpty()){
            return "0";
        }
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //return dateFormat.format(date);
        String[] temp = date.split("/");
        if(temp[1].length() <= 1)
        {
            temp[1] = "0" + temp[1];
        }
        if(temp[0].length() <= 1)
        {
            temp[0] = "0" + temp[0];
        }
        String formattedDate = temp[2] + temp[1] + temp[0];
        return formattedDate;
    }

    public static String reverseFormatDateEntry(String date){
        if(date == null || date.isEmpty() || date.equals("0")){
            return "";
        }

        return date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
    }

    /*
        Clear all input-fields in a form
     */
    public static void clearForm(ViewGroup viewGroup){
        for(int i = 0; i < viewGroup.getChildCount(); i++){
            View view = viewGroup.getChildAt(i);

            if(view instanceof EditText){
                    ((EditText)view).getText().clear();
            }

            if(view instanceof RadioGroup){
                ((RadioGroup)view).clearCheck();
            }

            if(view instanceof ViewGroup && ((ViewGroup)view).getChildCount() > 0){
                clearForm((ViewGroup)view);
            }
        }
    }

    public static boolean validForm(ViewGroup viewGroup){
        boolean isValid = true;

        for(int i = 0; i < viewGroup.getChildCount(); i++){
            View view = viewGroup.getChildAt(i);

            if(view instanceof EditText){
                if(TextUtils.isEmpty(((EditText)view).getText().toString())){
                    view.requestFocus();
                    ((EditText) view).setError("Required!");
                    isValid = false;
                }
            }

            if(view instanceof Spinner){
                if(((Spinner) view).getSelectedItemPosition() == 0) { // Default item selected in Spinner
                    view.requestFocus();
                    View childTextView = ((Spinner) view).getSelectedView();
                    if (childTextView instanceof TextView) {
                        ((TextView) childTextView).setError("Required!");
                    }
                    isValid = false;
                }
            }

            if(view instanceof RadioGroup){
                if(((RadioGroup) view).getCheckedRadioButtonId() == -1) // None selected in Radio Group
                    return false;
            }

            if(view instanceof ViewGroup && ((ViewGroup)view).getChildCount() > 0){ // Recursive call in case of View Group
                isValid = validForm((ViewGroup)view);
            }
        }
        return isValid;
    }
}