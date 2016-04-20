package com.example.nikmc.agima.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nikmc.agima.R;
import com.example.nikmc.agima.model.ModelChart;

import java.util.List;

/**
 * Created by NIKMC on 17.04.2016.
 */
public class ColumnsAdapterRecycler extends RecyclerView.Adapter<ColumnsAdapterRecycler.ViewHolder>  {
    //Не используется(Была предпринята попытка использования)
    private List<ModelChart> columns;
    private final static int sMAX = 1000;
    private Context mContext;
    public ColumnsAdapterRecycler(Context context, List<ModelChart> columns) {
        mContext = context;
        this.columns = columns;
    }

    @Override
    public ColumnsAdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (columns == null) return 0;
        else return columns.size();
    }

    @Override
    public void onBindViewHolder(ColumnsAdapterRecycler.ViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, columns.get(position).getCount());
        params.setMargins(0,sMAX-columns.get(position).getCount(),0,0);
        holder.mView.setLayoutParams(params);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View mView;
        private LayoutInflater layoutInflater;
        public ViewHolder(View v) {
            super(v);
            mView = (View)v.findViewById(R.id.viewLayoutColumn);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clicked = getAdapterPosition();
            ModelChart modelChart = columns.get(clicked);
            layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View infoView = layoutInflater.inflate(R.layout.content_main, null);
            TextView count = (TextView)infoView.findViewById(R.id.InfoCountColumn);
            TextView name = (TextView)infoView.findViewById(R.id.TitleColumn);
            count.setText(modelChart.getCount());
            name.setText(modelChart.getTitle());
            if(infoView.getVisibility() == View.GONE){
                infoView.setVisibility(View.VISIBLE);
            }
        }
    }


}
