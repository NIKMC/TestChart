package com.example.nikmc.agima.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nikmc.agima.R;
import com.example.nikmc.agima.interfaces.RecyclerViewClickListener;
import com.example.nikmc.agima.model.ItemChart;
import com.example.nikmc.agima.model.ModelChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIKMC on 17.04.2016.
 */
public class ColumnsAdapterRecycler extends RecyclerView.Adapter<ColumnsAdapterRecycler.ViewHolder>  {
    //Не используется(Была предпринята попытка использования)
    private List<ItemChart> columns = new ArrayList<>();
    private final static int sMAX = 1000;
    private int saveLastPosition = -1;
    private Context mContext;
    private static RecyclerViewClickListener clickRecyclerListener;
    public ColumnsAdapterRecycler(Context context, List<ModelChart> columns, RecyclerViewClickListener clickRecyclerListener) {
        mContext = context;
        this.columns.addAll(columns);
        this.clickRecyclerListener = clickRecyclerListener;
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
        if(columns.get(position).ismSelected()) {
            holder.mView.setSelected(true);
        }
        else {
            holder.mView.setSelected(false);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)mContext.getResources().getDimension(R.dimen.column_width), ((ModelChart)columns.get(position)).getCount());
        params.setMargins(0,sMAX-((ModelChart)columns.get(position)).getCount(),0,0);
        holder.mView.setLayoutParams(params);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = (View)v.findViewById(R.id.viewLayoutColumn);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                int clicked = getAdapterPosition();
                clickRecyclerListener.ItemViewClick(v, clicked, (ModelChart) columns.get(clicked));
                saveSelected(clicked, columns.get(clicked));
            }
        }

        private void saveSelected(int position, ItemChart itemChart){
            if(!itemChart.ismSelected()){
                if (saveLastPosition != -1){
                    columns.get(saveLastPosition).setmSelected(false);
                    notifyItemChanged(saveLastPosition);
                }
                itemChart.setmSelected(true);
                saveLastPosition = position;
                notifyItemChanged(position);

            }
        }
    }


}
