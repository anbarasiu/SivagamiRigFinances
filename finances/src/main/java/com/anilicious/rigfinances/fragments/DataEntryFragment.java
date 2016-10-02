package com.anilicious.rigfinances.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.activities.AddItemListAdapter;
import com.anilicious.rigfinances.activities.DataEntryActivity;
import com.anilicious.rigfinances.beans.Item;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class DataEntryFragment extends Fragment {

    private List<Item> items;
    private AddItemListAdapter list_items_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_entry_rows, null);
        setupItemList(view);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.item_submit_details);
        final Spinner spDate = (Spinner)view.findViewById(R.id.date);

        // On Click of Submit
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar inserted_date_c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(inserted_date_c.getTime());
                int inserted_date = Integer.parseInt(formattedDate);
                if(((DataEntryActivity)getActivity()).validForm()){ // TODO: Test validation
                    ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_item);
                    for(Item item : items){
                        String date = spDate.getSelectedItem().toString();
                        Integer Item_date = Integer.parseInt(CommonUtils.formatDateEntry(date));
                        item.setDate(Item_date);

                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                        dbAdapter.insertItem(item);
                    }

                    // Clear the Form
                    ((DataEntryActivity)getActivity()).clearForm();
                    items.clear();
                    list_items_adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    // Loop through and add the list of Item Items
    public List<Item> addItems(){
        List<Item> items = new ArrayList<Item>();

        ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_item);
        for(int i = 0; i < group.getChildCount(); i++){
            View form_field = group.getChildAt(i);
            TextView tvId = (TextView)form_field.findViewById(R.id.addItem_id);
            Spinner spCategory = (Spinner)form_field.findViewById(R.id.addItem_category);
            Spinner spSubCategory = (Spinner)form_field.findViewById(R.id.addItem_subcategory);
            EditText etAmount = (EditText)form_field.findViewById(R.id.addItem_amount);
            EditText etRemarks = (EditText)form_field.findViewById(R.id.addItem_remarks);

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
        }

        return items;
    }

    public void setupItemList(View view){
        items = new ArrayList<Item>();

        ListView list_item = (ListView)view.findViewById(R.id.list_item);
        list_items_adapter = new AddItemListAdapter(getActivity(), items);
        list_item.setAdapter(list_items_adapter);

        Button btn_addItem = (Button)view.findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_data_entry_rows);
                dialog.setTitle("Iteming Expense Details");

                final TextView tvId = (TextView)dialog.findViewById(R.id.addItem_id);
                final Spinner spCategory = (Spinner)dialog.findViewById(R.id.addItem_category);
                final Spinner spSubCategory = (Spinner)dialog.findViewById(R.id.addItem_subcategory);
                final EditText etAmount = (EditText)dialog.findViewById(R.id.addItem_amount);
                final EditText etRemarks = (EditText)dialog.findViewById(R.id.addItem_remarks);

                Button btn_addDetails = (Button)dialog.findViewById(R.id.item_submit_details);
                btn_addDetails.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewGroup group = (ViewGroup)dialog.findViewById(R.id.data_entry_fragment);
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
}
