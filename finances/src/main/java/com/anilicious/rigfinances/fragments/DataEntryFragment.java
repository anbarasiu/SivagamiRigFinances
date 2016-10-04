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

    // Loop through and add the list of Items
    public List<Item> addItems(){
        List<Item> items = new ArrayList<Item>();

        ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_item);
        for(int i = 0; i < group.getChildCount(); i++){
            View form_field = group.getChildAt(i);
            TextView tvId = (TextView)form_field.findViewById(R.id.id);
            TextView spCategory = (TextView)form_field.findViewById(R.id.category);
            TextView spSubCategory = (TextView)form_field.findViewById(R.id.subcategory);
            TextView etAmount = (TextView)form_field.findViewById(R.id.amount);
            TextView etRemarks = (TextView)form_field.findViewById(R.id.remarks);

            int id = Integer.parseInt(tvId.getText().toString());
            String category = spCategory.getText().toString();
            String subCategory = spSubCategory.getText().toString();
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


}
