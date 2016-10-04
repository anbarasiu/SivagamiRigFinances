package com.anilicious.rigfinances.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.beans.Item;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataEntryActivity extends Activity {

    private List<Item> items;
    private AddItemListAdapter list_items_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        // UI Object References
        Button btnSubmit = (Button)findViewById(R.id.item_submit_details);
        final Spinner spDate = (Spinner)findViewById(R.id.date);

        populateDates();
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
                    ViewGroup group = (ViewGroup)findViewById(R.id.list_item);
                    for(Item item : items){
                        String date = spDate.getSelectedItem().toString();
                        Integer Item_date = Integer.parseInt(CommonUtils.formatDateEntry(date));
                        item.setDate(Item_date);

                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getApplicationContext());
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

    private void populateDates(){

    }

    public void setupItemList(){
        items = new ArrayList<Item>();

        ListView list_item = (ListView)findViewById(R.id.list_item);
        list_items_adapter = new AddItemListAdapter(this, items);
        list_item.setAdapter(list_items_adapter);

        Button btn_addItem = (Button)findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Dialog dialog = new Dialog(DataEntryActivity.this);
                dialog.setContentView(R.layout.fragment_data_entry_rows);
                dialog.setTitle("Item Expense Details");

                final TextView tvId = (TextView)dialog.findViewById(R.id.dialog_id);
                final Spinner spCategory = (Spinner)dialog.findViewById(R.id.dialog_category);
                final Spinner spSubCategory = (Spinner)dialog.findViewById(R.id.dialog_subcategory);
                final EditText etAmount = (EditText)dialog.findViewById(R.id.dialog_amount);
                final EditText etRemarks = (EditText)dialog.findViewById(R.id.dialog_remarks);

                Button btn_addDetails = (Button)dialog.findViewById(R.id.dialog_submit_details);
                btn_addDetails.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewGroup group = (ViewGroup)dialog.findViewById(R.id.data_entry_dialog_parent);
                        if(CommonUtils.validForm(group)){
                            int id = Integer.parseInt(tvId.getText().toString());
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
