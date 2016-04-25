package com.example.nikmc.agima.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.nikmc.agima.R;
import com.example.nikmc.agima.model.ItemChart;
import com.example.nikmc.agima.model.ModelChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIKMC on 24.04.2016.
 */
public class GridColumnAdapter extends ArrayAdapter<ItemChart> {
    //Плохая идея
    private List<ItemChart> columns;
    private final static int sMAX = 1000;
    private int saveLastPosition = -1;
    private Context mContext;

    public GridColumnAdapter(Context context, List<ItemChart> columns) {
        super(context, R.layout.item_column, columns);
        mContext = context;
        this.columns = columns;

    }

    @Override
    public ItemChart getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemChart column = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_column, null);
        }
        View mView = (View)convertView.findViewById(R.id.viewLayoutColumn);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelected(position, column);

            }
        });

        return convertView;
    }

    private void saveSelected(int position, ItemChart itemChart){
        if(!itemChart.ismSelected()){
            if (saveLastPosition != -1){
                columns.get(saveLastPosition).setmSelected(false);
            }
            itemChart.setmSelected(true);
            saveLastPosition = position;
            notifyDataSetChanged();

        }
    }

}
