package com.anilicious.rigfinances.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;

import com.anilicious.rigfinances.beans.Item;
import com.anilicious.rigfinances.finances.R;

import java.util.List;

/**
 * Created by ANBARASI on 19/10/14.
 * Adapter to generate dynamic rows for multiple items
 */
public class AddItemListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List items;
    private String voucherType;
    private boolean notified;

    private List<String> items_dummy;

    private List<String> items_item;
    private List<String> items_quantity;
    private List<String> items_price;

    public AddItemListAdapter(Activity activity, List items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public String getItem(int i) {
        return null;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void onCallbackNotified(){
        //return true;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder = new ViewHolder();
        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button btn_delete = null;
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.item, null);
                    convertView.setTag(holder);
                } else{
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.id = (TextView)convertView.findViewById(R.id.id);
                holder.category = (TextView)convertView.findViewById(R.id.category);
                holder.subCategory = (TextView)convertView.findViewById(R.id.subcategory);
                holder.amount = (TextView)convertView.findViewById(R.id.amount);
                holder.remarks = (TextView)convertView.findViewById(R.id.remarks);
                holder.delete = (Button)convertView.findViewById(R.id.addItem_delete);

                holder.id.setText(((Item)items.get(position)).getId());
                holder.category.setText(((Item)items.get(position)).getCategory());
                holder.subCategory.setText(((Item)items.get(position)).getSubCategory());
                holder.amount.setText(String.valueOf(((Item)items.get(position)).getAmount()));
                holder.remarks.setText((((Item)items.get(position)).getRemarks()));

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        items.remove(position);
                        notifyDataSetChanged();
                    }
                });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView id;
        TextView category;
        TextView subCategory;
        TextView amount;
        TextView remarks;
        Button delete;
    }
}