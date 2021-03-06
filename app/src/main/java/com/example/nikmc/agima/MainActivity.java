package com.example.nikmc.agima;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nikmc.agima.logic.LogicClass;
import com.example.nikmc.agima.model.ExtHScrollView;
import com.example.nikmc.agima.model.ItemChart;
import com.example.nikmc.agima.model.ModelChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int sCOUNT_TABLE = 20000;
    private final static int sMAX = 1000;
    private final static int sMIN = 0;
    private final static int END = 40;
    private final static int STEP = 20;
    private int countViewLast = -1;
    private int countViewFirst = -1;
    private int selectedView = -1;
    private List<Integer> mTable;
    private List<String> mTitleTable;
    private List<ModelChart> mModelColumns = new ArrayList<>();
    private List<ItemChart> mColumns = new ArrayList<>();
    private int mMAX = 0;

    private LinearLayout InfoView;
    private LinearLayout linearLayout;
    private LayoutInflater inflater;
    private TextView name;
    private TextView count;
    private View selector;
    private RelativeLayout.LayoutParams selectorparams;
    private LinearLayout main_layout;
    private ExtHScrollView horizontalScrollViewColumn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        InfoView = (LinearLayout)findViewById(R.id.InfoView);
        count = (TextView)findViewById(R.id.InfoCountColumn);
        name = (TextView)findViewById(R.id.TitleColumn);
        linearLayout = (LinearLayout) findViewById(R.id.LayoutColumn);
        main_layout  = (LinearLayout) findViewById(R.id.main_layout);
        inflater = LayoutInflater.from(this);
        selector = (View)findViewById(R.id.selector);
        selectorparams = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen.selector_width), (int)getResources().getDimension(R.dimen.selector_height));
        horizontalScrollViewColumn = (ExtHScrollView)findViewById(R.id.horizontalScrollViewColumn);

        CreateData data = new CreateData(this);
        data.execute();


        horizontalScrollViewColumn.setOnScrollChanged(new ExtHScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(final ExtHScrollView scrollView, final int l, int t, int oldl, int oldt) {
                Log.d("AGIMA", "l = " + l + " scrollView.getExtentHorizontal()="
                        + scrollView.getExtentHorizontal() + "l + scrollView.getExtentHorizontal()= " + scrollView.getChildWidth());
                if (l + scrollView.getExtentHorizontal() >= scrollView
                        .getChildWidth() - ((LinearLayout)scrollView.getChildAt(0)).getChildAt(0).getWidth()) {
                    horizontalScrollViewColumn.post(new Runnable() {
                        @Override
                        public void run() {
                            AddEnd(((LinearLayout) scrollView.getChildAt(0)).getChildAt(0).getWidth());

                        }
                    });
                }
                if (l + scrollView.getExtentHorizontal() <= scrollView.getExtentHorizontal()){
                    horizontalScrollViewColumn.post(new Runnable() {
                        @Override
                        public void run() {
                            AddFirst(scrollView.getChildWidth() - ((LinearLayout)scrollView.getChildAt(0)).getChildAt(0).getWidth());
                            Log.d("AGIMA", "l = " + l + " scrollView.getExtentHorizontal()="
                                    + scrollView.getExtentHorizontal() + "l + scrollView.getExtentHorizontal()= " + l + scrollView.getExtentHorizontal());
                        }
                    });
                }
            }
        });

    }

    private void AddEnd(int ScrollWidth) {
        if (countViewLast < sCOUNT_TABLE) {
            mMAX = 0;
            horizontalScrollViewColumn.scrollTo(ScrollWidth + 20,0);
            int step = 0;
            if (countViewLast + STEP <= sCOUNT_TABLE - 1) {
                for(int position = countViewLast; position < countViewLast + STEP; position++){
                    countViewFirst++;
                    linearLayout.removeViewAt(0);
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    setMax(mModelColumns.get(position).getCount());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    view.setTag(position);
                    if(mColumns.get((Integer)view.getTag()).ismSelected()){
                        view.setSelected(true);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkVisible();
                            //При выбора стобца меням его Background, и при выборе другого возвращаем обратно
                            //не лучшее решение
                            count.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getCount()));
                            name.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getTitle()));
                            view.setSelected(true);
                            //возникла проблема обновления старого view при нажатии на другой
                            //не имею представлений, как сделать
                            if (selectedView != -1) {
                                mColumns.get(selectedView).setmSelected(false);
                                selectedView = (Integer) view.getTag();
                            } else {
                                selectedView = (Integer) view.getTag();
                            }
                        }
                    });
                    linearLayout.addView(view);
                }
                countViewLast += STEP;
            } else {
                step = (sCOUNT_TABLE - 1) - countViewLast;
                for(int position = countViewLast; position < countViewLast + step; position++){
                    countViewFirst++;
                    linearLayout.removeViewAt(0);
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    setMax(mModelColumns.get(position).getCount());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    view.setTag(position);
                    if(mColumns.get((Integer)view.getTag()).ismSelected()){
                        view.setSelected(true);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkVisible();

                            //При выбора стобца меням его Background, и при выборе другого возвращаем обратно
                            //не лучшее решение
                            count.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getCount()));
                            name.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getTitle()));
                            view.setSelected(true);
                            //возникла проблема обновления старого view при нажатии на другой
                            //не имею представлений, как сделать
                            if(selectedView != -1){
                                mColumns.get(selectedView).setmSelected(false);
                                selectedView = (Integer)view.getTag();
                            } else {
                                selectedView = (Integer)view.getTag();
                            }
                        }
                    });
                    linearLayout.addView(view);
                }
                countViewLast += step;
            }
            createValueOfScale(getMax());
        }
    }

    private void AddFirst(int ScrollWidth) {
        if (countViewFirst > -1) {
            mMAX = 0;
            horizontalScrollViewColumn.scrollTo(ScrollWidth - 20, 0);
            int step = 0;
            if (countViewFirst - STEP >= 0) {
                for (int position = countViewFirst; position > countViewFirst - STEP; position--) {
                    linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    setMax(mModelColumns.get(position).getCount());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    view.setTag(position);
                    if(mColumns.get((Integer)view.getTag()).ismSelected()){
                        view.setSelected(true);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkVisible();
                            //При выбора стобца меням его Background, и при выборе другого возвращаем обратно
                            //не лучшее решение, на мой взгляд, но другого не придумал
                            count.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getCount()));
                            name.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getTitle()));
                            mColumns.get((Integer)view.getTag()).setmSelected(true);
                            view.setSelected(true);
                            //возникла проблема обновления старого view при нажатии на другой
                            //не имею представлений, как сделать
                            if(selectedView != -1){
                                mColumns.get(selectedView).setmSelected(false);
                                selectedView = (Integer)view.getTag();
                            } else {
                                selectedView = (Integer)view.getTag();
                            }

                        }
                    });
                    linearLayout.addView(view, 0);

                }
                countViewLast -= STEP;
                countViewFirst -= STEP;
            } else {
                step = countViewFirst + 1;
                for (int position = countViewFirst; position > -1; position--) {
                    linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    setMax(mModelColumns.get(position).getCount());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    view.setTag(position);
                    if(mColumns.get((Integer)view.getTag()).ismSelected()){
                        view.setSelected(true);
                    }
                    //Ужасное решение
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkVisible();
                            //При выбора стобца меням его Background, и при выборе другого возвращаем обратно
                            //не лучшее решение, на мой взгляд, но другого не придумал
                            count.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getCount()));
                            name.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getTitle()));
                            view.setSelected(true);
                            //возникла проблема обновления старого view при нажатии на другой
                            //не имею представлений, как сделать
                            if(selectedView != -1){
                                mColumns.get(selectedView).setmSelected(false);
                                selectedView = (Integer)view.getTag();
                            } else {
                                selectedView = (Integer)view.getTag();
                            }
                        }
                    });
                    linearLayout.addView(view, 0);

                }
                countViewLast -= step;
                countViewFirst -= step;
            }
            createValueOfScale(getMax());
        }
    }

    private void checkVisible(){
        if(InfoView.getVisibility() == View.GONE){
            InfoView.setVisibility(View.VISIBLE);
        }

    }

    public void setMax(int Max){
        if (Max > mMAX) mMAX = Max;
    }

    public int getMax(){
        return mMAX;
    }

    private void createValueOfScale(int MAX) {
        RelativeLayout text = (RelativeLayout)findViewById(R.id.Count);
        text.setVisibility(View.VISIBLE);
        text.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MAX));
        TextView topLabel = (TextView)findViewById(R.id.topLabel);
        TextView centerLabel = (TextView)findViewById(R.id.centerLabel);
        TextView bottomLabel = (TextView)findViewById(R.id.bottomLabel);
        topLabel.setText("--" + String.valueOf(MAX-50));
        centerLabel.setText("--" + String.valueOf(MAX / 2));
        bottomLabel.setText("--" + String.valueOf(sMIN + 50));
        RelativeLayout.LayoutParams labelTopparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        labelTopparams.setMargins(0, sMIN + 50, 0, 0);
        topLabel.setLayoutParams(labelTopparams);
        RelativeLayout.LayoutParams labelBottomparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        labelBottomparams.setMargins(0, MAX - 50, 0, 0);
        bottomLabel.setLayoutParams(labelBottomparams);

    }

        @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class CreateData extends AsyncTask<Void, List<ModelChart>, Void> {

        private Context mContext;
        private ProgressDialog dialog;
        public CreateData(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Загрузка...Пожалуйста подождите.");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            LogicClass init = new LogicClass(sCOUNT_TABLE);
            mTable = init.initRandom();         //создание рандомных чисел
            mTitleTable = init.initTitle();     //создание рандомных названий

            for(int position = 0; position< sCOUNT_TABLE; position++){
                mModelColumns.add(position, new ModelChart(mTitleTable.get(position), mTable.get(position)));
            }
            publishProgress(mModelColumns);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<ModelChart>... values) {
            super.onProgressUpdate(values);
            //В процессе(Как переиспользовать view для linearlayout без адаптера)
            mColumns.addAll(mModelColumns);

            for (int position=0; position<END; position++){
                countViewLast++;
                final View view  = inflater.inflate(R.layout.item_column, linearLayout, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width), values[0].get(position).getCount());
                view.setLayoutParams(params);// set item content in view
                view.setTag(position);
                if(mColumns.get((Integer)view.getTag()).ismSelected()){
                    view.setSelected(true);
                }
                setMax(values[0].get(position).getCount());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkVisible();
                        //При выбора стобца меням его Background, и при выборе другого возвращаем обратно
                        //не лучшее решение, на мой взгляд, но другого не придумал
                        count.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getCount()));
                        name.setText(String.valueOf(mModelColumns.get((Integer) view.getTag()).getTitle()));
                        view.setSelected(true);
                        if(selectedView != -1){
                            mColumns.get(selectedView).setmSelected(false);
                            selectedView = (Integer)view.getTag();
                        } else {
                            selectedView = (Integer)view.getTag();
                        }
                    }
                });
                linearLayout.addView(view);
            }
            createValueOfScale(getMax());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Добавление шкалы величин с высотой равной scrollView
            //createValueOfScale();
            if(dialog != null)
                dialog.dismiss();

        }
    }



/*    public class CreateData extends AsyncTask<Void, ModelChart, Void>{

        private Context mContext;
        private ProgressDialog dialog;
        public CreateData(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Загрузка...Пожалуйста подождите.");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            LogicClass init = new LogicClass();
            mTable = init.initRandom();         //создание рандомных чисел
            mTitleTable = init.initTitle();     //создание рандомных названий

            for(int position = 0; position< sCOUNT_TABLE; position++){
                mModelColumns.add(position, new ModelChart(mTitleTable.get(position), mTable.get(position)));
                publishProgress(new ModelChart(mTitleTable.get(position), mTable.get(position)));
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final ModelChart... values) {
            super.onProgressUpdate(values);
                //Добавление в LinearLayout View как столбец
                final View view  = inflater.inflate(R.layout.item_column, linearLayout, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width), values[0].getCount());
                view.setLayoutParams(params);// set item content in view
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Отображение информации о стобце при нажатии
                        if(InfoView.getVisibility() == View.GONE){
                            InfoView.setVisibility(View.VISIBLE);
                        }
                        //При выбора стобца меням его Background, и при выборе другого возвращаем обратно
                        //не лучшее решение
                        for (int i=0; i < linearLayout.getChildCount(); i++){
                            View child = linearLayout.getChildAt(i);
                            child.setSelected(false);
                        }
                        view.setSelected(true);
                        //Пытался получить координаты видимого столбца в scrollview, но решение не верно
                        final ViewTreeObserver viewTreeObserver = main_layout.getViewTreeObserver();
                        if (viewTreeObserver.isAlive()) {
                            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    //View and it's hierarchy laid out, perform your operations
                                    Log.d("AGIMA", "left = " + view.getLeft());
                                    selectorparams.setMargins(view.getLeft() + view.getLayoutParams().width / 2, 0, 0, 0);
                                    selector.setLayoutParams(selectorparams);
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                                    } else {
                                        viewTreeObserver.removeGlobalOnLayoutListener(this);
                                    }
                                }
                            });
                        }
                        //информация о столюце(название и количество просмотров)
                        count.setText(String.valueOf(values[0].getCount()));
                        name.setText(String.valueOf(values[0].getTitle()));
                    }
                });
                linearLayout.addView(view);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(dialog != null)
                dialog.dismiss();
            //Добавление шкалы величин с высотой равной scrollView
            RelativeLayout text = (RelativeLayout)findViewById(R.id.Count);
            text.setVisibility(View.VISIBLE);
            text.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, linearLayout.getLayoutParams().height));
            TextView topLabel = (TextView)findViewById(R.id.topLabel);
            TextView centerLabel = (TextView)findViewById(R.id.centerLabel);
            TextView bottomLabel = (TextView)findViewById(R.id.bottomLabel);
            topLabel.setText("--" + String.valueOf(sMAX-50));
            centerLabel.setText("--" + String.valueOf(sMAX / 2));
            bottomLabel.setText("--" + String.valueOf(sMIN + 50));
            RelativeLayout.LayoutParams labelTopparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            labelTopparams.setMargins(0, sMIN + 50, 0, 0);
            topLabel.setLayoutParams(labelTopparams);
            RelativeLayout.LayoutParams labelBottomparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            labelBottomparams.setMargins(0, sMAX - 50, 0, 0);
            bottomLabel.setLayoutParams(labelBottomparams);
        }


    }*/



}
