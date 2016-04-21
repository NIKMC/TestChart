package com.example.nikmc.agima.model;

/**
 * Created by NIKMC on 21.04.2016.
 */
public class ItemChart {

    protected boolean mSelected;

    public ItemChart() {
        this.mSelected = false;
    }

    public boolean ismSelected() {
        return mSelected;
    }

    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }
}
