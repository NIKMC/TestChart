package com.example.nikmc.agima.model;

/**
 * Created by NIKMC on 15.04.2016.
 */
public class ModelChart {
    private String Title;
    private int Count;

    public ModelChart(String title, int count) {
        Title = title;
        Count = count;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
