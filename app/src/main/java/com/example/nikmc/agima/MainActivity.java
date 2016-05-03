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
import android.widget.Toast;

import com.example.nikmc.agima.logic.LogicClass;
import com.example.nikmc.agima.model.ExtHScrollView;
import com.example.nikmc.agima.model.ModelChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int sCOUNT_TABLE = 200;
    private final static int sMAX = 1000;
    private final static int sMIN = 0;
    private final static int END = 40;
    private final static int STEP = 20;
    private int countViewLast = -1;
    private int countViewFirst = -1;
    private List<Integer> mTable;
    private List<String> mTitleTable;
    private List<ModelChart> mModelColumns = new ArrayList<>();
    private LinearLayout InfoView;
    private LinearLayout linearLayout;
    private LayoutInflater inflater;
    private TextView name;
    private TextView count;
    private View selector;
    private RelativeLayout.LayoutParams selectorparams;
    private LinearLayout main_layout;
//    private ListView listViewColumn;
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
//        listViewColumn = (ListView) findViewById(R.id.listView);
        horizontalScrollViewColumn = (ExtHScrollView)findViewById(R.id.horizontalScrollViewColumn);


        CreateData data = new CreateData(this);
        data.execute();

        /*horizontalScrollViewColumn.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = horizontalScrollViewColumn.getScrollX(); //for horizontalScrollView
//                linearLayout.getC
               // Log.d("AGIMA", "getChildCount" + linearLayout.getChildCount() );
            
                        Log.d("AGIMA", "scrollView" +
                                horizontalScrollViewColumn.getX() + ", " +
                                horizontalScrollViewColumn.getY() + ", " +
                                horizontalScrollViewColumn.getScrollX() + ", " +
                                        horizontalScrollViewColumn.getWidth() + ", " +

                                        horizontalScrollViewColumn.getScaleX());



            }
        });*/
        horizontalScrollViewColumn.setOnScrollChanged(new ExtHScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ExtHScrollView scrollView, int l, int t, int oldl, int oldt) {
                if (l + scrollView.getExtentHorizontal() >= scrollView
                        .getChildWidth() - ((LinearLayout)scrollView.getChildAt(0)).getChildAt(0).getWidth()) {
                    horizontalScrollViewColumn.post(new Runnable() {
                        @Override
                        public void run() {
                            AddEnd();
                        }
                    });
                } else if (l + scrollView.getExtentHorizontal() <= scrollView.getExtentHorizontal()){
                    horizontalScrollViewColumn.post(new Runnable() {
                        @Override
                        public void run() {
                            AddFirst();
                        }
                    });
                }
            }
        });

    }

    private void AddEnd() {
        if (countViewLast < sCOUNT_TABLE) {
            int step = 0;
            if (countViewLast + STEP <= sCOUNT_TABLE - 1) {
                for(int position = countViewLast; position < countViewLast + STEP; position++){
                    countViewFirst++;
                    linearLayout.removeViewAt(0);
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(position).getCount());
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    Log.d("AGIMA", "getTitle()" + mModelColumns.get(position).getTitle());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(position).getCount());
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    Log.d("AGIMA", "getTitle()" + mModelColumns.get(position).getTitle());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    linearLayout.addView(view);
                }
                countViewLast += step;
            }
        } else {
            Toast.makeText(MainActivity.this,"Nope",Toast.LENGTH_SHORT).show();
        }
    }

    private void AddFirst() {
        if (countViewFirst > -1) {
            int step = 0;
            if (countViewFirst - STEP >= -1) {
                for (int position = countViewFirst; position > countViewFirst - STEP; position--) {
                    linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(position).getCount());
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(position).getCount());
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    Log.d("AGIMA", "getTitle()" + mModelColumns.get(position).getTitle());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    linearLayout.addView(view, 0);
                    Log.d("AGIMA", "getTitle() add first" + mModelColumns.get(countViewFirst).getTitle());
                }
                countViewLast -= STEP;
                countViewFirst -= STEP;
            } else {
                step = countViewFirst + 1;
                for (int position = countViewFirst; position > -1; position--) {
                    linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(position).getCount());
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(position).getCount());
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    Log.d("AGIMA", "getTitle()" + mModelColumns.get(position).getTitle());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width),
                            mModelColumns.get(position).getCount());
                    view.setLayoutParams(params);// set item content in view
                    linearLayout.addView(view, 0);
                    Log.d("AGIMA", "getTitle() add first" + mModelColumns.get(countViewFirst).getTitle());
                }
                countViewLast -= step;
                countViewLast -= step;
            }
        } else {
            Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
        }

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

            for (int i=0; i<END; i++){
                countViewLast++;
                final View view  = inflater.inflate(R.layout.item_column, linearLayout, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width), values[0].get(i).getCount());
                view.setLayoutParams(params);// set item content in view
                linearLayout.addView(view);
            }
            createValueOfScale();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Добавление шкалы величин с высотой равной scrollView
            //createValueOfScale();
            if(dialog != null)
                dialog.dismiss();

        }

        private void createValueOfScale() {
            RelativeLayout text = (RelativeLayout)findViewById(R.id.Count);
            text.setVisibility(View.VISIBLE);
            text.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, horizontalScrollViewColumn.getLayoutParams().height));
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
