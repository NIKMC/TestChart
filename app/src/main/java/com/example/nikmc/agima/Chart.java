package com.example.nikmc.agima;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Chart extends AppCompatActivity {

    private final static int sCOUNT_TABLE = 20000;
    private final static int sMAX = 1000;
    private final static int sMIN = 0;
    private List<Integer> mTable;
    private List<String> mTitleTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chart);

        mTable = initRandom();
        mTitleTable = initTitle();

        for(int i=0; i < sCOUNT_TABLE; i++){
            Log.d("AGIMA", mTable.get(i) + " - " + mTitleTable.get(i));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private static DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    private List<String> initTitle(){
        List<String> title = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM", dateFormatSymbols);



        for (int i = 0; i < sCOUNT_TABLE; i++) {
            c.add(Calendar.DATE, i);
//            System.out.println(c.get(c.DAY_OF_MONTH) + "." + (c.get(c.MONTH) + 1) + "." + c.get(c.YEAR));
            title.add(format.format(c.getTime()));
        }
        return title;
    }

    private List<Integer> initRandom(){
        Random random = new Random();
        List<Integer> randomValue = new ArrayList<>();
        for (int i=0; i < sCOUNT_TABLE; i++)
        randomValue.add(i, random.nextInt((sMAX - sMIN) + sMIN));
        return randomValue;
    }

}
