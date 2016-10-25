package com.anilicious.rigfinances.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anilicious.rigfinances.beans.Bore;
import com.anilicious.rigfinances.beans.Cook;
import com.anilicious.rigfinances.beans.Credit;
import com.anilicious.rigfinances.beans.Diesel;
import com.anilicious.rigfinances.beans.Employee;
import com.anilicious.rigfinances.beans.Item;
import com.anilicious.rigfinances.beans.Maintenance;
import com.anilicious.rigfinances.beans.Pipe;
import com.anilicious.rigfinances.beans.Road;
import com.anilicious.rigfinances.beans.Salary;
import com.anilicious.rigfinances.beans.Site;
import com.anilicious.rigfinances.beans.Tools;
import com.anilicious.rigfinances.beans.User;

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
    private static final String ITEM_DATABASE_NAME = "ITEM";
    private static final String ITEM_DATABASE_CREATE = "CREATE TABLE " + ITEM_DATABASE_NAME + "(" +
            "id " + "INTEGER primary key, " +
            "category" + " text, " +
            "subCategory" + " text, " +
            "remarks" + " text, " +
            "amount" + " number);";

    /* END : Tables - Create Statements */

    private static final String[] tables = new String[] {ITEM_DATABASE_CREATE};

    private static final String[] tableNames = new String[] {
            ITEM_DATABASE_NAME};

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

    public void insertItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", item.getId());
        values.put("category", item.getCategory());
        values.put("subCategory", item.getSubCategory());
        values.put("amount", item.getAmount());
        values.put("remarks", item.getRemarks());
        database.insert(ITEM_DATABASE_NAME, null, values);
        database.close();
    }

    public void deleteItems(String date){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(ITEM_DATABASE_NAME, "date=?", new String[]{date});
        database.close();
    }

    public Cursor retrieveItemDetails(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(ITEM_DATABASE_NAME,
                null,
                null,
                null,
                null, null, null);
        return cursor;
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

    /*
    Retrieve items from the DB to export to CSV
    */
    public List<String[]> retrieveItems(){
        SQLiteDatabase database = this.getReadableDatabase();

            Cursor cursor = database.rawQuery("Select * from " + ITEM_DATABASE_NAME, null);
            List<String[]> csvData = new ArrayList<String[]>();

            String[] columnNamesData = new String[cursor.getColumnCount()];
            for(int i=0; i<cursor.getColumnCount(); i++){
                columnNamesData[i] = cursor.getColumnName(i);
            }
            csvData.add(columnNamesData);

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
                csvData.add(data);
            }
        database.close();
        return csvData;
    }

    public void close(){
        SQLiteDatabase database = this.getReadableDatabase();
        database.close();
    }
}