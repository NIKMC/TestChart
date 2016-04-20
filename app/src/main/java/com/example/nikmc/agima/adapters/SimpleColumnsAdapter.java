package com.example.nikmc.agima.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.nikmc.agima.R;
import com.example.nikmc.agima.model.ModelChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIKMC on 17.04.2016.
 */
public class SimpleColumnsAdapter extends BaseAdapter {
    //не используется, нет возможности использовать адаптер в linearlayout
    private List<ModelChart> columns = new ArrayList<>();
    private Context mContext;
    private final static int sMAX = 1000;
    private LayoutInflater layoutInflater;
    public SimpleColumnsAdapter(Context context, List<ModelChart> array) {
        columns.addAll(array);
        mContext = context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return columns.size();
    }

    @Override
    public ModelChart getItem(int position) {
        return columns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_column, parent, false);
        }
        final ModelChart column = columns.get(position);
        LinearLayout viewColumn = (LinearLayout)view.findViewById(R.id.viewLayoutColumn);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, column.getCount());
//        params.gravity = Gravity.BOTTOM;
        params.setMargins(0,sMAX-column.getCount(),0,0);
        viewColumn.setLayoutParams(params);

        return view;
    }
}
