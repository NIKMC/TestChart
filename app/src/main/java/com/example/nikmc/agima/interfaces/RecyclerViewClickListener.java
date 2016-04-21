package com.example.nikmc.agima.interfaces;

import android.view.View;

import com.example.nikmc.agima.model.ModelChart;

/**
 * Created by NIKMC on 21.04.2016.
 */
public interface RecyclerViewClickListener {

    public void ItemViewClick(View v, int position, ModelChart modelChart);
}
