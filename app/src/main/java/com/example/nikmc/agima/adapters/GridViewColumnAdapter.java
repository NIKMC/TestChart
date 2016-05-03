package com.example.nikmc.agima.adapters;

import android.content.Context;
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
 * Created by NIKMC on 26.04.2016.
 */
public class GridViewColumnAdapter extends BaseAdapter {

    private List<ModelChart> columns = new ArrayList<>();
    private Context mContext;
    private final static int sMAX = 1000;
    private LayoutInflater layoutInflater;
    public GridViewColumnAdapter(Context context, List<ModelChart> array) {
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
    public Object getItem(int position) {
        return columns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_column, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        final ModelChart column = columns.get(position);

//        LinearLayout viewColumn = (LinearLayout)viewHolder.mView.findViewById(R.id.viewLayoutColumn);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)mContext.getResources().getDimension(R.dimen.column_width), column.getCount());
        params.setMargins(0,sMAX-column.getCount(),0,0);

//        params.gravity = Gravity.BOTTOM;
        //viewHolder.mView.setLayoutParams(params);

        return view;
    }

    class ViewHolder {
        public LinearLayout mView;
        public ViewHolder(View v) {
            mView = (LinearLayout) v.findViewById(R.id.viewLayoutColumn);
//            mView.setOnClickListener(this);
        }
    }

}


