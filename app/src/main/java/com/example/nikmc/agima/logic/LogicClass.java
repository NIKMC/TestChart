package com.example.nikmc.agima.logic;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by NIKMC on 16.04.2016.
 */
public class LogicClass {

    private static int sCOUNT_TABLE = -1;
    private final static int sMAX = 1000;
    private final static int sMIN = 0;
    public LogicClass(int count) {
        sCOUNT_TABLE = count;
    }

    private static DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };


    public List<String> initTitle(){
        List<String> title = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM", dateFormatSymbols);



        for (int i = 0; i < sCOUNT_TABLE; i++) {
            c.add(Calendar.DATE, 1);
            title.add(format.format(c.getTime()));
        }
        return title;
    }

    public List<Integer> initRandom(){
        Random random = new Random();
        List<Integer> randomValue = new ArrayList<>();
        for (int i=0; i < sCOUNT_TABLE; i++)
            randomValue.add(i, random.nextInt((sMAX - sMIN) + sMIN));
        return randomValue;
    }


}
