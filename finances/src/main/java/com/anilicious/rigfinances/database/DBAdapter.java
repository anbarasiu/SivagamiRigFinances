package com.anilicious.rigfinances.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ANBARASI on 14/11/14.
 */
public class DBAdapter extends SQLiteOpenHelper{

    private static DBAdapter dbInstance;

    private static final String DATABASE_NAME = "sivagamiborewells.db";
    private static final int DATABASE_VERSION = 1;

    /* BEGIN : Tables - Create Statements */

    private static final String DIESEL_DATABASE_NAME = "DIESEL";
    private static final String DIESEL_DATABASE_CREATE = "CREATE TABLE " + DIESEL_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'D' + number
            "Date" + " integer, " +
            "diesel_for" + " text, " +
            "litres" + " number, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "diesel_in_hand" + " number, " +
            "inserted_date" + " number);"; // DIH = DIH+litres - find out how to implement formulae

    private static final String COOK_DATABASE_NAME = "COOK";
    private static final String COOK_DATABASE_CREATE = "CREATE TABLE " + COOK_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'C' + number
            "Date" + " integer, " +
            "item" + " text, " +
            "quantity" + " number, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "Date_entered" + " number);";

    /*private static final String COOKITEM_DATABASE_NAME = "COOKITEM";
    private static final String COOKITEM_DATABASE_CREATE = "CREATE TABLE " + COOKITEM_DATABASE_NAME + "(" +
            "cook_key" + " INTEGER, " + // 'C' + number
            "item_key" + " number, " +
            "item" + " text, " +
            "quantity" + " number, " +
            "amount" + " number);";*/

    private static final String ROAD_DATABASE_NAME = "ROAD";
    private static final String ROAD_DATABASE_CREATE = "CREATE TABLE " + ROAD_DATABASE_NAME + "(" +
            "_id " + "INTEGER, " + // 'R' + number
            "Date" + " integer, " +
            "expense" + " text, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "Date_entered" + " number);";

    private static final String MAINTENANCE_DATABASE_NAME = "MAINTENANCE";
    private static final String MAINTENANCE_DATABASE_CREATE = "CREATE TABLE " + MAINTENANCE_DATABASE_NAME + "(" +
            "_id " + "INTEGER, " + // 'M' + number
            "Date" + " integer, " +
            "work_type" + " text, " +
            "service" + " boolean, " +
            "engine_hours" + " real, " +
            "amount" + " number, " +
            "reason" + " text, " +
            "spent_by" + " text, " +
            "Date_entered" + " number);";

    private static final String TOOL_DATABASE_NAME = "TOOL";
    private static final String TOOL_DATABASE_CREATE = "CREATE TABLE " + TOOL_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'T' + number
            "Date" + " Number, " +
            "item" + " text, " +
            "detail" + " text, " +
            "quantity" + " number, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "Date_entered" + " number);";

    /*private static final String TOOLITEM_DATABASE_NAME = "TOOLITEM";
    private static final String TOOLITEM_DATABASE_CREATE = "CREATE TABLE " + TOOLITEM_DATABASE_NAME + "(" +
            "tool_key" + "INTEGER, " + // 'C' + number
            "item_key" + " number, " +
            "item" + " text, " +
            "details" + " text, " +
            "company" + " text, " +
            "quantity" + " number, " +
            "amount" + " number);";*/

    private static final String PIPE_DATABASE_NAME = "PIPE";
    private static final String PIPE_DATABASE_CREATE = "CREATE TABLE " + PIPE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'P' + number
            "Date" + " integer, " +
            "work_type" + " text, " +
            "length" + " real, " +
            "type" + " text, " +
            "amount" + " real, " +
            "spent_by" + " text, " +
            "remarks" + " text, " +
            "Date_entered" + " number);";

    private static final String SALARY_DATABASE_NAME = "SALARY";
    private static final String SALARY_DATABASE_CREATE = "CREATE TABLE " + SALARY_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'S' + number
            "Date" + " integer, " +
            "employee_number" + " INTEGER, " +
            "employee_name" + " text, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "reason" + " text, " +
            "Date_entered" + " number);";

    private static final String SITE_DATABASE_NAME = "SITE_EXPENSES";
    private static final String SITE_DATABASE_CREATE = "CREATE TABLE " + SITE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'SE' + number
            "Date" + " Integer, " +
            "work_type" + " text, " +
            "amount" + " number, " +
            "remarks" + " text, " +
            "spent_by" + " text, " +
            "Date_entered" + " number);";

    private static final String CREDIT_DATABASE_NAME = "CREDIT";
    private static final String CREDIT_DATABASE_CREATE = "CREATE TABLE " + CREDIT_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'CR' + number
            "Date" + " integer, " +
            "amount_received" + " number, " +
            "received_from" + " text, " +
            "received_by" + " text, " +
            "remarks" + " text, " +
            "Date_entered" + " number);";
            /*"amount_in_credit" + " text)*/

    private static final String BORE_DATABASE_NAME = "BORE_DETAILS";
    private static final String BORE_DATABASE_CREATE = "CREATE TABLE " + BORE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'B' + number
            "Date" + " integer, " +
            "total_depth" + " number, " +
            "casting_depth" + " text, " +
            "engine_hrs_start" + " number, " +
            "engine_hrs_end" + " number, " +
            "customer_name" + " text, " +
            "place" + " text, " +
            "agent_name" + " text, " +
            "bore_type" + " text, " +
            "bill_amount" + " text, " +
            "commission" + " text, " +
            "engine_running_time" + " text, " +
            /*"total_amount" + " text, " +*/
            "diesel_used" + " number, " +
            "Date_entered" + " number);";
            /*"last_engine_hr_end" + " text);"*/


    private static final String EMPLOYEE_DATABASE_NAME = "EMPLOYEE_DETAILS";
    private static final String USER_DATABASE_NAME = "USER_DETAILS";

    private static final String EMPLOYEE_DATABASE_CREATE = "CREATE TABLE " + EMPLOYEE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'B' + number
            "Date" + " integer, " +
            "employee_name" + " text, " +
            "employee_number" + " INTEGER, " +
            "designation" + " text, " +
            "date_of_joining" + " integer, " +
            "date_of_leaving" + " integer, " +
            "current_balance" + " REAL, " +
            "remarks" + " text, " +
            "salary" + " REAL, " +
            "inserted_date" + " number);";
    private static final String USER_DATABASE_CREATE = "CREATE TABLE " + USER_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'U' + number
            "user_name" + " text, " +
            "password" + " text, " +
            "role" + " text);";

    /* END : Tables - Create Statements */

    private static final String[] tables = new String[] {USER_DATABASE_CREATE,
                                            DIESEL_DATABASE_CREATE,
                                            COOK_DATABASE_CREATE,
                                            /*COOKITEM_DATABASE_CREATE,*/
                                            ROAD_DATABASE_CREATE,
                                            MAINTENANCE_DATABASE_CREATE,
                                            TOOL_DATABASE_CREATE,
                                            /*TOOLITEM_DATABASE_CREATE,*/
                                            PIPE_DATABASE_CREATE,
                                            SALARY_DATABASE_CREATE,
                                            SITE_DATABASE_CREATE,
                                            CREDIT_DATABASE_CREATE,
                                            BORE_DATABASE_CREATE,
                                            EMPLOYEE_DATABASE_CREATE};

    private static final String[] tableNames = new String[] {
            DIESEL_DATABASE_NAME,
            COOK_DATABASE_NAME,
            ROAD_DATABASE_NAME,
            MAINTENANCE_DATABASE_NAME,
            TOOL_DATABASE_NAME,
            PIPE_DATABASE_NAME,
            SALARY_DATABASE_NAME,
            SITE_DATABASE_NAME,
            CREDIT_DATABASE_NAME,
            BORE_DATABASE_NAME,
            EMPLOYEE_DATABASE_NAME};

    public static DBAdapter getInstance(Context context){
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if(dbInstance == null){
            dbInstance = new DBAdapter(context.getApplicationContext());
        }
        return dbInstance;
    }

    private DBAdapter(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(String table : tables){
            sqLiteDatabase.execSQL(table);
            Log.d("Created! ", table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DBAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        for(String table : tables){
            //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL(table);
            Log.d("Created! ", table);
        }
    }

    /* BEGIN : Tables - Insert Methods */

    public void insertUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUsername());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        database.insert(USER_DATABASE_NAME, null, values);
        database.close();
    }

    public List<User> retrieveUsers(){
        List<User> usersList = new ArrayList<User>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(USER_DATABASE_NAME, new String[] {"user_name", "password", "role"}, null, null, null, null, null);

        while(cursor.moveToNext()){
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String role = cursor.getString(cursor.getColumnIndex("role"));

            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.setRole(role);
            usersList.add(user);
        }

        database.close();

        return usersList;
    }


    public void insertDiesel(Diesel diesel){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", diesel.getDate());
        values.put("diesel_for", diesel.getDieselFor());
        values.put("litres", diesel.getLitres());
        values.put("amount", diesel.getTotalAmount());
        values.put("spent_by", diesel.getSpentBy());
        values.put("inserted_date", diesel.getInsertedDate());

        database.insert(DIESEL_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertCook(Cook cook){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item", cook.getItem());
        values.put("Date", cook.getDate());
        values.put("quantity", cook.getQuantity());
        values.put("amount", cook.getPrice());
        values.put("spent_by", cook.getSpentBy());
        /*values.put("amount", cook.getAmount());*/

        database.insert(COOK_DATABASE_NAME, null, values);
        database.close();
    }

    /*public void insertCookItem(CookItem cookItem){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        database.insert(COOKITEM_DATABASE_NAME, null, values);
        database.close();
    }*/

    public void insertRoad(Road road){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", road.getDate());
        values.put("expense", road.getExpenseDetails());
        values.put("amount", road.getTotalAmount());
        values.put("spent_by", road.getSpentBy());

        database.insert(ROAD_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertMaintenance(Maintenance maintenance){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", maintenance.getDate());
        values.put("work_type", maintenance.getWorkType());
        values.put("service", maintenance.isService());
        values.put("engine_hours", maintenance.getEngineHrs());
        values.put("amount", maintenance.getAmount());
        values.put("reason", maintenance.getReason());
        values.put("spent_by", maintenance.getSpentBy());

        database.insert(MAINTENANCE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertTool(Tools tool){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", tool.getDate());
        values.put("item", tool.getItem());
        values.put("detail", tool.getDetails());
        values.put("quantity", tool.getQuantity());
        values.put("amount", tool.getPrice());
        values.put("spent_by", tool.getSpentBy());
        /*values.put("company", tool.getCompany());
        values.put("total_amount", tool.getTotalAmount());*/

        database.insert(TOOL_DATABASE_NAME, null, values);
        database.close();
    }

    /*public void insertToolItem(ToolItem toolItem){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item", toolItem.getItem());
        values.put("details", toolItem.getDetails());
        values.put("company", toolItem.getCompany());
        values.put("quantity", toolItem.getQuantity());
        values.put("amount", toolItem.getAmount());
        values.put("amount", toolItem.getTotalAmount());

        database.insert(TOOLITEM_DATABASE_NAME, null, values);
        database.close();
    }*/

    public void insertPipe(Pipe pipe){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", pipe.getDate());
        values.put("work_type", pipe.getWorkType());
        values.put("length", pipe.getLength());
        values.put("type", pipe.getType());
        values.put("amount", pipe.getAmount());
        values.put("spent_by", pipe.getSpentBy());
        values.put("remarks", pipe.getRemarks());

        database.insert(PIPE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertSalary(Salary salary){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", salary.getDate());
        values.put("employee_number", salary.getEmployeeNumber());
        values.put("employee_name", salary.getEmployeeName());
        values.put("amount", salary.getAmount());
        values.put("spent_by", salary.getSpentBy());
        values.put("reason", salary.getReason());

        database.insert(SALARY_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertCredit(Credit credit){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", credit.getDate());
        values.put("amount_received", credit.getAmountReceived());
        values.put("received_from", credit.getFrom());
        values.put("received_by", credit.getReceivedBy());
        values.put("remarks", credit.getRemarks());
        /*values.put("amount_in_credit", credit.getAmountInCredit());*/

        database.insert(CREDIT_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertSite(Site site){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", site.getDate());
        values.put("work_type", site.getWorkType());
        values.put("remarks", site.getRemarks());
        values.put("amount", site.getTotalAmount());
        values.put("spent_by", site.getSpentBy());

        database.insert(SITE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertBore(Bore bore){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", bore.getDate());
        values.put("total_depth", bore.getTotalDepth());
        values.put("casting_depth", bore.getCastingDepth());
        values.put("engine_hrs_start", bore.getEngineHrsStart());
        values.put("engine_hrs_end", bore.getEngineHrsEnd());
        values.put("customer_name", bore.getCustomerName());
        values.put("place", bore.getPlace());
        values.put("agent_name", bore.getAgentName());
        values.put("bore_type", bore.getBoreType());
        values.put("bill_amount", bore.getBillAmount());
        values.put("commission", bore.getCommission());
        values.put("diesel_used", bore.getDieselUsed());
        /*values.put("total_amount", bore.getTotalAmount());*/

        database.insert(BORE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertEmployee(Employee employee){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_name", employee.getName());
        values.put("employee_number", employee.getNumber());
        values.put("designation", employee.getDesignation());
        values.put("salary", employee.getSalary());
        values.put("remarks", "Registration");
        values.put("date_of_joining", employee.getDateOfJoining());
        values.put("date_of_leaving", employee.getDateOfLeaving());
        values.put("inserted_date", employee.getInserted_date());

        long row = database.insert(EMPLOYEE_DATABASE_NAME, null, values);
        database.close();
    }

    public String[] retrieveExistingEmployees(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT DISTINCT employee_name,employee_number from " + EMPLOYEE_DATABASE_NAME +";", null);
        HashMap<Integer, String> employeesMap = new HashMap<Integer, String>();
        while(cursor.moveToNext()){
            employeesMap.put(cursor.getInt(1), cursor.getInt(1) + " : " + cursor.getString(0));
        }

        Collection<String> vals = employeesMap.values();
        String[] employees = vals.toArray(new String[vals.size()]);

        database.close();
        return employees;
    }

    public void updateEmployee(Employee employee){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("Date", employee.getDate());
        values.put("date_of_joining", employee.getDateOfJoining());
        values.put("date_of_leaving", employee.getDateOfLeaving());
        values.put("inserted_date", employee.getInserted_date());

        String employeeNumber = Integer.toString(employee.getNumber());

        /*database.rawQuery("UPDATE " + EMPLOYEE_DATABASE_NAME + " SET date_of_joining = ?, date_of_leaving = ?, inserted_date = ? " +
                "WHERE employee_number = ? AND inserted_date = (SELECT MAX(inserted_date) from " + EMPLOYEE_DATABASE_NAME + " " +
                "WHERE employee_number = ?);", new String[]{Integer.toString(employee.getDateOfJoining()), Integer.toString(employee.getDateOfLeaving()), Integer.toString(employee.getInserted_date()), employeeNumber, employeeNumber});
*/
        Cursor c = database.rawQuery("UPDATE " + EMPLOYEE_DATABASE_NAME + " SET date_of_joining = "+employee.getDateOfJoining()+", date_of_leaving = "+employee.getDateOfLeaving()+", inserted_date = "+employee.getInserted_date()+" " +
                "WHERE employee_number = "+employeeNumber+" AND inserted_date = (SELECT MAX(inserted_date) from " + EMPLOYEE_DATABASE_NAME + " " +
                "WHERE employee_number = "+employeeNumber+");", null);

        c.moveToNext(); // Odd!! TODO
        database.close();
    }

    public Cursor retrieveDieselDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DIESEL_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrieveCookDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(COOK_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrieveMaintenanceDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(MAINTENANCE_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrievePipeDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(PIPE_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrieveRoadDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(ROAD_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrieveSalaryDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SALARY_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrieveSiteDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SITE_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public Cursor retrieveToolDetails(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TOOL_DATABASE_NAME,
                null,
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        return cursor;
    }

    public HashMap<String, Double> retrieveExpenseAmount(int dateFrom, int dateTo, List<String> selectedExpenses){
        HashMap<String, Double> expenseReport = new HashMap<String, Double>();
        List<String> columnRetriever = new ArrayList<String>();

        //String queryColumns = "SELECT";
        //String queryTables = "";
        //String columnName = "";

        String query = "";
        SQLiteDatabase database = this.getReadableDatabase();
        for(String type : selectedExpenses){
            query = "SELECT SUM(amount) from " + type + " where Date BETWEEN " + dateFrom + " AND " + dateTo;
            /* switch(type){
                case CommonUtils.CONSTANTS.DIESEL :
                    columnName = "d";
                    queryTables += " " + DIESEL_DATABASE_NAME + " d";
                    break;
                case CommonUtils.CONSTANTS.MAINTENANCE :
                    columnName = "m";
                    queryTables += " " + MAINTENANCE_DATABASE_NAME + " m";
                    break;
                case CommonUtils.CONSTANTS.COOK :
                    columnName = "c";
                    queryTables += " " + COOK_DATABASE_NAME + " c";
                    break;
                case CommonUtils.CONSTANTS.PIPE :
                    columnName = "p";
                    queryTables += " " + PIPE_DATABASE_NAME + " p";
                    break;
                case CommonUtils.CONSTANTS.ROAD :
                    columnName = "r";
                    queryTables += " " + ROAD_DATABASE_NAME + " r";
                    break;
                case CommonUtils.CONSTANTS.SITE :
                    columnName = "si";
                    queryTables += " " + SITE_DATABASE_NAME + " si";
                    break;
                case CommonUtils.CONSTANTS.SALARY :
                    columnName = "sa";
                    queryTables += " " + SALARY_DATABASE_NAME + " sa";
                    break;
                case CommonUtils.CONSTANTS.TOOLS :
                    columnName = "t";
                    queryTables += " " + TOOL_DATABASE_NAME + " t";
                    break;
            }
            queryColumns += " SUM(" + columnName + ".amount)";
            if(selectedExpenses.indexOf(type) != selectedExpenses.size() - 1){
                queryColumns += ",";
                queryTables += " OUTER JOIN";
            }
            columnRetriever.add(type); */

            Cursor cursor = database.rawQuery(query, null);

            while(cursor.moveToNext()){
                if(cursor.getDouble(0) != 0.0){
                    expenseReport.put(type, cursor.getDouble(0));
                }
            }
        }
        // TODO: Error handling : If no entry made, just return 0

        //String query = queryColumns + " FROM" + queryTables + ";";// " WHERE " + columnName + ".date BETWEEN '" + dateFrom + "' AND '" + dateTo +"';";

        database.close();
        return expenseReport;
    }

    public HashMap<String, String> retrieveEmployeeDetails(int employeeNumber){
        SQLiteDatabase database = this.getReadableDatabase();
        HashMap<String, String> employeeDetails = new HashMap<String, String>();

        String query = "SELECT employee_name,employee_number,date_of_joining,date_of_leaving,salary FROM "+ EMPLOYEE_DATABASE_NAME
                + " WHERE employee_number = " + employeeNumber + ";";

        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            employeeDetails.put("employee_name", cursor.getString(0));
            employeeDetails.put("joining_date", cursor.getString(2));
            employeeDetails.put("leaving_date", cursor.getString(3));
            employeeDetails.put("salary_given", cursor.getString(4));
        }

        database.close();
        return employeeDetails;
    }

    public HashMap<String, String> retrieveEmployeeLatestDetails(int employeeNumber){
        SQLiteDatabase database = this.getReadableDatabase();
        HashMap<String, String> employeeDetails = new HashMap<String, String>();

        String query = "SELECT employee_name,employee_number,date_of_joining,date_of_leaving,salary,designation FROM "+ EMPLOYEE_DATABASE_NAME
                + " WHERE employee_number =" + employeeNumber + " ORDER BY inserted_date DESC LIMIT 1;";

        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            employeeDetails.put("employee_name", cursor.getString(0));
            employeeDetails.put("joining_date", cursor.getString(2));
            employeeDetails.put("leaving_date", cursor.getString(3));
            employeeDetails.put("salary_given", cursor.getString(4));
            employeeDetails.put("designation", cursor.getString(5));
        }

        database.close();
        return employeeDetails;
    }

    public HashMap<String, String> retrieveSalaryReportsDetails(int employeeNumber){
        SQLiteDatabase database = this.getReadableDatabase();
        HashMap<String, String> salaryReport = new HashMap<String, String>();

        String query = /*"SELECT employee_name,employee_number,sum(date_of_joining),sum(date_of_leaving),sum(salary) FROM "+ EMPLOYEE_DATABASE_NAME
                + " WHERE employee_number = " + employeeNumber + ";";*/
                "SELECT E.employee_name, E.employee_number, E.date_of_joining, E.date_of_leaving, SUM(S.amount) " +
                "FROM "+ EMPLOYEE_DATABASE_NAME+" E INNER JOIN "+ SALARY_DATABASE_NAME +" S "+
                "ON E.employee_number = S.employee_number " +
                "WHERE E.employee_number = " + employeeNumber + " GROUP BY E.employee_number;";

        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            salaryReport.put("employee_name", cursor.getString(0));
            salaryReport.put("joining_date", cursor.getString(2));
            salaryReport.put("leaving_date", cursor.getString(3));
            salaryReport.put("salary_given", cursor.getString(4));
        }

        database.close();
        return salaryReport;
    }

    public HashMap<String, Double> retrieveBoreReportsDetails(Integer dateFrom, Integer dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        HashMap<java.lang.String, Double> boreDetailsReport = new HashMap<java.lang.String, Double>();

        String query1 = "SELECT SUM(total_depth), SUM(casting_depth), SUM(bill_amount), SUM(commission), SUM(bill_amount), SUM(diesel_used), MIN(engine_hrs_start), MAX(engine_hrs_end) " +
                "FROM "+ BORE_DATABASE_NAME +
                " WHERE Date BETWEEN " + dateFrom + " AND " + dateTo + ";";
        /*"SELECT group_concat(employee_name),employee_number,sum(date_of_joining),sum(date_of_leaving),sum(salary) FROM "+ EMPLOYEE_DATABASE_NAME
                + " WHERE employee_number = 1001;";*/
                /*"SELECT SUM(total_depth), SUM(casting_depth), SUM(bill_amount), SUM(commission),MIN(engine_hrs_start), MAX(engine_hrs_end) " +
                "FROM "+ BORE_DATABASE_NAME +
                " WHERE Bore_Date BETWEEN " + dateFrom + " AND " + dateTo + ";";*/

        Cursor cursor1 = database.rawQuery(query1, null);

        String query2 = "SELECT SUM(length) FROM " + PIPE_DATABASE_NAME + " WHERE Date BETWEEN " + dateFrom + " AND " + dateTo + ";";

        Cursor cursor2 = database.rawQuery(query2, null);

        String query3 = "SELECT SUM(litres) FROM " + DIESEL_DATABASE_NAME + " WHERE diesel_for = 'Compressor' AND Date BETWEEN " + dateFrom + " AND " + dateTo + ";";

        Cursor cursor3 = database.rawQuery(query3, null);

        while(cursor1.moveToNext()){
            boreDetailsReport.put("total_depth", cursor1.getDouble(0));
            boreDetailsReport.put("casting_depth", cursor1.getDouble(1));
            boreDetailsReport.put("bill_amount", cursor1.getDouble(2));
            boreDetailsReport.put("commission", cursor1.getDouble(3));
            boreDetailsReport.put("engine_hrs_start", cursor1.getDouble(6));
            boreDetailsReport.put("engine_hrs_end", cursor1.getDouble(7));
            boreDetailsReport.put("diesel_used", cursor1.getDouble(5));
        }

        while(cursor2.moveToNext()){
            boreDetailsReport.put("total_pipe_length", cursor2.getDouble(0));
        }

        while(cursor3.moveToNext()){
            boreDetailsReport.put("total_liters", cursor3.getDouble(0));
        }

        database.close();
        return boreDetailsReport;
    }

    /*
     * Retrieve all data from the DB to export to CSV
     */
    public Map<String, List<String[]>> retrieveAll(){
        Map<String, List<String[]>> csvData = new HashMap<String, List<String[]>>();
        SQLiteDatabase database = this.getReadableDatabase();

        for(String tableName : tableNames){
            Cursor cursor = database.rawQuery("Select * from " + tableName, null);
            List<String[]> innerCsvData = new ArrayList<String[]>();

            String[] columnNamesData = new String[cursor.getColumnCount()];
            for(int i=0; i<cursor.getColumnCount(); i++){
                columnNamesData[i] = cursor.getColumnName(i);
            }
            innerCsvData.add(columnNamesData);

            while(cursor.moveToNext()){
                String[] data = new String[cursor.getColumnCount()];
                for(int column=0; column<cursor.getColumnCount(); column++){
                    switch(cursor.getType(column)){
                        case Cursor.FIELD_TYPE_FLOAT:
                            data[column] = Float.toString(cursor.getFloat(column));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            data[column] = Integer.toString(cursor.getInt(column));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            data[column] = cursor.getString(column);
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            // Do we need to handle null values?
                            break;
                        default:
                    }
                }
                innerCsvData.add(data);
            }
            if(innerCsvData.size() != 0){
                csvData.put(tableName, innerCsvData);
            }
        }
        database.close();
        return csvData;
    }

    public Map<String, List<String>> retrieveAllColumnNames(){
        SQLiteDatabase database = this.getReadableDatabase();
        Map<String, List<String>> csvData = new HashMap<String, List<String>>();
        for(String tableName : tableNames){
            Cursor cursor = database.rawQuery("Select * from " + tableName, null);
            List<String> columnNames = new ArrayList<String>();
            for(String columnName : cursor.getColumnNames()){
                columnNames.add(columnName);
            }

            if(columnNames.size() != 0){
                csvData.put(tableName, columnNames);
            }
        }

        database.close();
        return csvData;
    }

    public void close(){
        SQLiteDatabase database = this.getReadableDatabase();
        database.close();
    }
}