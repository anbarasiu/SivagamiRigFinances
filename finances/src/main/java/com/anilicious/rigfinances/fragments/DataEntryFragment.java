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
        View view = inflater.inflate(R.layout.fragment_vouchers_cook, null);
        setupCookList(view);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        //final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText3);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText5);

        // On Click of Submit
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar inserted_date_c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(inserted_date_c.getTime());
                int inserted_date = Integer.parseInt(formattedDate);
                if(((DataEntryActivity)getActivity()).validForm()){ // TODO: Test validation
                    String spentBy = etSpentBy.getText().toString();

                    ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_cook);
                    for(Item item : items){
                        String item = item.getItem();
                        int quantity = item.getQuantity();
                        float price = item.getAmount();

                        DebitFragment parent = (DebitFragment)getParentFragment();

                        Cook cook = new Cook();
                        //TODO: Calculate Total Amount?
                        cook.setSpentBy(spentBy);
                        cook.setItem(item);
                        cook.setQuantity(quantity);
                        cook.setPrice(price);
                        cook.setInsertedDate(inserted_date);
                        String date = parent.getEntryDate().toString();
                        Integer Cook_date = Integer.parseInt(CommonUtils.formatDateEntry(date));  // TODO: Test
                        cook.setDate(Cook_date);

                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                        dbAdapter.insertCook(cook);
                    }

                    // Clear the Form
                    ((VouchersActivity)getActivity()).clearForm();
                    items.clear();
                    list_items_adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    // Loop through and add the list of Cook Items
    public List<Item> addItems(){
        List<Item> items = new ArrayList<Item>();

        ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_cook);
        for(int i = 0; i < group.getChildCount(); i++){
            View form_field = group.getChildAt(i);
            EditText etItem = (EditText)form_field.findViewById(R.id.addItem_item);
            EditText etQuantity = (EditText)form_field.findViewById(R.id.addItem_quantity);
            EditText etPrice = (EditText)form_field.findViewById(R.id.addItem_price);

            String item = etItem.getText().toString();
            int quantity = Integer.parseInt(etQuantity.getText().toString());
            float price = Float.parseFloat(etPrice.getText().toString());

            Item item = new Item(item, quantity, price);
            items.add(item);
        }

        return items;
    }

    public void setupCookList(View view){
        items = new ArrayList<Item>();

        ListView list_cook = (ListView)view.findViewById(R.id.list_cook);
        list_items_adapter = new AddItemListAdapter(getActivity(), items);
        list_cook.setAdapter(list_items_adapter);

        Button btn_addItem = (Button)view.findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_data_entry_rows);
                dialog.setTitle("Cooking Expense Details");

                final EditText etItem = (EditText)dialog.findViewById(R.id.addItem_item);
                final EditText etQuantity = (EditText)dialog.findViewById(R.id.addItem_quantity);
                final EditText etPrice = (EditText)dialog.findViewById(R.id.addItem_price);

                Button btn_addDetails = (Button)dialog.findViewById(R.id.cook_submit_details);
                btn_addDetails.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewGroup group = (ViewGroup)dialog.findViewById(R.id.addItem_parent);
                        if(CommonUtils.validForm(group)){
                            String item = etItem.getText().toString();
                            int quantity = Integer.parseInt(etQuantity.getText().toString());
                            float price = Float.parseFloat(etPrice.getText().toString());

                            Item item = new Item(item, quantity, price);
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
