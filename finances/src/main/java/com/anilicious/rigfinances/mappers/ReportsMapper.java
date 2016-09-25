package com.anilicious.rigfinances.mappers;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ANBARASI on 24/1/15.
 * Mapper Class to fetch details from the DB and return to Fragment to render Charts
 */
public class ReportsMapper {

    Context context;
    DBAdapter dbAdapter;

    public ReportsMapper(Context context){
        this.context = context.getApplicationContext();
        dbAdapter = DBAdapter.getInstance(context);
    }

    public HashMap<String, Double> mapExpenses(int dateFrom, int dateTo, List<String> selectedExpenses){
        HashMap<String, Double> chartMap = new HashMap<String, Double>();
        chartMap = dbAdapter.retrieveExpenseAmount(dateFrom, dateTo, selectedExpenses);
        return chartMap;
    }

    public HashMap<String, String> mapSalary(int employeeNumber){
        HashMap<String, String> salaryMap = new HashMap<String, String>();
        salaryMap = dbAdapter.retrieveSalaryReportsDetails(employeeNumber);
        return salaryMap;
    }

    public HashMap<String, Double> mapBoreDetails(Integer dateFrom, Integer dateTo){
        HashMap<String, Double> boreDetailsMap = new HashMap<String, Double>();
        boreDetailsMap = dbAdapter.retrieveBoreReportsDetails(dateFrom, dateTo);

        try{
            double mileage = 100.0; // TODO : Figure out where Mileage comes from
            double diesel_in_hand = (boreDetailsMap.get("total_liters") - boreDetailsMap.get("diesel_used"));
            double casing_pipe_in_hand = boreDetailsMap.get("total_pipe_length") - boreDetailsMap.get("casting_depth");
            boreDetailsMap.put("diesel_in_hand", diesel_in_hand);
            boreDetailsMap.put("casing_pipe_in_hand", casing_pipe_in_hand);
        }
        catch(Exception e){
            Log.e("Error : ", "Records not available");
        }
        return boreDetailsMap;
    }

    public Cursor mapExpenseDetails(int date_from, int date_to, String selectedExpense){
        // TODO: Figure out population of table
        String dateFrom = String.valueOf(date_from);
        String dateTo = String.valueOf(date_to);
        switch(selectedExpense){
            case CommonUtils.CONSTANTS.DIESEL:
                return dbAdapter.retrieveDieselDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.COOK:
                return dbAdapter.retrieveCookDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.MAINTENANCE:
                return dbAdapter.retrieveMaintenanceDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.SITE:
                return dbAdapter.retrieveSiteDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.PIPE:
                return dbAdapter.retrievePipeDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.ROAD:
                return dbAdapter.retrieveRoadDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.SALARY:
                return dbAdapter.retrieveSalaryDetails(dateFrom, dateTo);
            case CommonUtils.CONSTANTS.TOOLS:
                return dbAdapter.retrieveToolDetails(dateFrom, dateTo);
            default:
                return null;
        }
    }

    public void closeDBConn(){
        dbAdapter.close();
    }
}