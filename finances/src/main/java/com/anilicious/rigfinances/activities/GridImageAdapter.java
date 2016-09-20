package com.anilicious.rigfinances.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 26/12/14.
 */
public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] gridValues;

    public GridImageAdapter(Context context, String[] gridValues){
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.main_grid_element, null);

            // set value into textview
            LinearLayout llGrid = (LinearLayout) gridView.findViewById(R.id.main_grid_element);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            layoutParams.height = (int)(calculateScreenHeight() * 0.45);
            llGrid.setLayoutParams(layoutParams);
            TextView tvDescription = (TextView) gridView.findViewById(R.id.main_grid_text);
            TextView tvDetDescription = (TextView) gridView.findViewById(R.id.sub_main_grid_text);
            ImageView ivGrid = (ImageView) gridView.findViewById(R.id.grid_imageView);
            tvDescription.setText(gridValues[position]);

            Resources resources = context.getResources();

            String gridValue = gridValues[position];

            // TODO: Refactor

            if (gridValue.equals("Vouchers")) {
                ivGrid.setImageDrawable(resources.getDrawable(R.drawable.vouchers));
                llGrid.setBackgroundColor(Color.rgb(22, 46, 62));
                tvDetDescription.setText("Enter the Expense details");
            } else if (gridValue.equals("Bore Entry")) {
                ivGrid.setImageDrawable(resources.getDrawable(R.drawable.bore_entry));
                llGrid.setBackgroundColor(Color.rgb(39, 86, 164));
                tvDetDescription.setText("Enter the Bore Details");
            } else if (gridValue.equals("Reports")) {
                ivGrid.setImageDrawable(resources.getDrawable(R.drawable.reports));
                llGrid.setBackgroundColor(Color.rgb(39, 86, 164));
                tvDetDescription.setText("View Expense and other reports");
            } else if (gridValue.equals("Employee Details")){
                ivGrid.setImageDrawable(resources.getDrawable(R.drawable.employee_details));
                llGrid.setBackgroundColor(Color.rgb(22, 46, 62));
                tvDetDescription.setText("Add/Edit the Employee Joining and Relieving dates");
            }
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    private int calculateScreenHeight(){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public int getCount() {
        return gridValues.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
