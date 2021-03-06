package com.anilicious.rigfinances.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anilicious.rigfinances.beans.Item;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;
import com.anilicious.rigfinances.utils.PickerFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataEntryActivity extends Activity implements IDataEntryActivity, AdapterView.OnItemSelectedListener {

    private List<Item> items;
    private AddItemListAdapter list_items_adapter;

    Spinner spCategory;
    Spinner spSubCategory;
    EditText etDate;

    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        dbAdapter = DBAdapter.getInstance(getApplicationContext());

        // UI Object References
        Button btnSubmit = (Button)findViewById(R.id.item_submit_details);
        etDate = (EditText)findViewById(R.id.date);

        // Setup Date Picker
        setupDatePicker();
        setupItemList();

        // On Click of Submit
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar inserted_date_c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(inserted_date_c.getTime());
                int inserted_date = Integer.parseInt(formattedDate);
                if(validForm()){ // TODO: Test validation
                    String date = etDate.getText().toString();
                    Integer Item_date = Integer.parseInt(CommonUtils.formatDateEntry(date));

                    dbAdapter.deleteItems(Item_date.toString());

                    ViewGroup group = (ViewGroup)findViewById(R.id.list_item);
                    for(Item item : items){
                        item.setDate(Item_date);

                        // Insert to DB
                        dbAdapter.insertItem(item);
                    }

                    // Clear the Form
                    clearForm();
                    items.clear();
                    list_items_adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void setupItemList(){
        items = new ArrayList<Item>();

        ListView list_item = (ListView)findViewById(R.id.list_item);
        list_item.setEmptyView(findViewById(R.id.empty));
        list_items_adapter = new AddItemListAdapter(this, items);
        list_item.setAdapter(list_items_adapter);

        Button btn_addItem = (Button)findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Dialog dialog = new Dialog(DataEntryActivity.this);
                dialog.setContentView(R.layout.fragment_data_entry_rows);
                dialog.setTitle("Item Expense Details");

                final TextView tvId = (TextView)dialog.findViewById(R.id.dialog_id);
                spCategory = (Spinner)dialog.findViewById(R.id.dialog_category);
                spSubCategory = (Spinner)dialog.findViewById(R.id.dialog_subcategory);
                final EditText etAmount = (EditText)dialog.findViewById(R.id.dialog_amount);
                final EditText etRemarks = (EditText)dialog.findViewById(R.id.dialog_remarks);

                setupSpinners(dialog);

                Button btn_addDetails = (Button)dialog.findViewById(R.id.dialog_submit_details);
                btn_addDetails.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewGroup group = (ViewGroup)dialog.findViewById(R.id.data_entry_dialog_parent);
                        if(CommonUtils.validForm(group)){
                            int id = list_items_adapter.getCount() + 1;
                            String category = spCategory.getSelectedItem().toString();
                            String subCategory = spSubCategory.getSelectedItem().toString();
                            float amount = Float.parseFloat(etAmount.getText().toString());
                            String remarks = etRemarks.getText().toString();

                            Item item = new Item();
                            item.setAmount(amount);
                            item.setCategory(category);
                            item.setId(id);
                            item.setRemarks(remarks);
                            item.setSubCategory(subCategory);
                            items.add(item);
                            list_items_adapter.notifyDataSetChanged();

                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void setupSpinners(Dialog view){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DataEntryActivity.this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spCategory.setAdapter(adapter);
        spCategory.setOnItemSelectedListener(this);
    }

    // Begin - DatePicker Methods

    private void setupDatePicker(){
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        etDate.setText(dt.format(new Date()).toString());
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = PickerFragment.newInstance(CommonUtils.DIALOG_DATE);
                picker.show(getFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 101){
            String entryDate = data.getStringExtra("entryDate");
            etDate.setText(entryDate);
        }
    }

    // End - DatePicker Methods

    private void retrieveDetails(String date){
        Cursor c = dbAdapter.retrieveItemsByDate(date);
        while(c.moveToNext()){
            Item i = new Item();
            i.setId(c.getInt(0));
            i.setCategory(c.getString(1));
            i.setSubCategory(c.getString(2));
            i.setAmount(c.getFloat(3));
            i.setRemarks(c.getString(4));

            items.add(i);
            list_items_adapter.notifyDataSetChanged();
        }
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

    @Override
    public void onDialogDataSet(String string) {
        if(string != null) {
            etDate.setText(string);
            items.clear();
            list_items_adapter.notifyDataSetChanged();

            retrieveDetails(CommonUtils.formatDateEntry(string));
        }
    }

    // Action bar menu items - Begin

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_download){
            ExportDatabaseCSVTask exportDatabaseCSVTask = new ExportDatabaseCSVTask();
            exportDatabaseCSVTask.execute();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int arr;
        switch(position){
            case 1:
                arr = R.array.subCategories_diesel;
                break;
            case 2:
                arr = R.array.subCategories_maintenance;
                break;
            case 3:
                arr = R.array.subCategories_pipe;
                break;
            case 4:
                arr = R.array.subCategories_hammer;
                break;
            case 5:
                arr = R.array.subCategories_bit;
                break;
            case 6:
                arr = R.array.subCategories_catering;
                break;
            case 7:
                arr = R.array.subCategories_salary;
                break;
            case 8:
                arr = R.array.subCategories_tools;
                break;
            case 9:
                arr = R.array.subCategories_road;
                break;
            default:
                arr = R.array.subCategories_others;
                break;
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DataEntryActivity.this,
                arr, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spSubCategory.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Action bar menu items - End

    /*
     *  Export DB to a CSV - Background Task
     */
    private class ExportDatabaseCSVTask extends AsyncTask<String, String, Boolean> {
        private final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        boolean memoryErr = false;

        @Override
        protected void onPreExecute() {
            Toast.makeText(DataEntryActivity.this, "Downloading Reports into a CSV at " + Environment.getExternalStorageDirectory().getAbsolutePath() + "...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                int rowCount = 0;
                int colCount = 0;

                DBAdapter dbAdapter = DBAdapter.getInstance(getApplicationContext());
                Cursor c = dbAdapter.retrieveItemDetails();

                File sdCardDir = Environment.getExternalStorageDirectory();

                String fileName = "SivagamiBorewells-Report.csv";
                File saveFile = new File(sdCardDir, fileName);
                FileWriter fw = new FileWriter(saveFile);
                BufferedWriter bw = new BufferedWriter(fw);

                for(int i=0; i<c.getColumnCount(); i++){
                    // Table Heading
                    bw.write(c.getColumnName(i) + ',');
                }
                bw.newLine();

                while(c.moveToNext()){
                    // Table Data
                    String content = "";
                    content += Integer.toString(c.getInt(0)) + ',';
                    content += c.getString(1) + ',';
                    content += c.getString(2) + ',';
                    content += Float.toString(c.getFloat(3)) + ',';
                    content += c.getString(4) + ',';
                    content += CommonUtils.reverseFormatDateEntry(Integer.toString(c.getInt(5)));
                    bw.write(content);
                    bw.newLine();
                    bw.flush();
                }
            } catch(Exception e){
                Log.e("EXPORT", "Export failed!");
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(DataEntryActivity.this, "Exported successfully!", Toast.LENGTH_LONG).show();
        }
    }
}
