package com.example.nikmc.agima;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikmc.agima.adapters.ColumnsAdapterRecycler;
import com.example.nikmc.agima.adapters.GridColumnAdapter;
import com.example.nikmc.agima.adapters.GridViewColumnAdapter;
import com.example.nikmc.agima.adapters.SimpleColumnsAdapter;
import com.example.nikmc.agima.logic.LogicClass;
import com.example.nikmc.agima.model.ItemChart;
import com.example.nikmc.agima.model.ModelChart;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final static int sCOUNT_TABLE = 20000;
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
    private HorizontalScrollView horizontalScrollViewColumn;
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
        horizontalScrollViewColumn = (HorizontalScrollView)findViewById(R.id.horizontalScrollViewColumn);
        Button delete = (Button)findViewById(R.id.btn);
        Button add = (Button)findViewById(R.id.add);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(countViewFirst < sCOUNT_TABLE) {
                    countViewFirst++;

//                Log.d("AGIMA", "getChildCount()" + linearLayout.getChildCount());
                    linearLayout.getChildCount();
//                Log.d("AGIMA", "getChildAt(0)" + linearLayout.getChildAt(0));
                    linearLayout.getChildAt(0);
                    Log.d("AGIMA", "delete first" + mModelColumns.get(countViewLast).getTitle());

                    linearLayout.removeViewAt(0);
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(countViewLast).getCount());
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    Log.d("AGIMA", "getTitle()" + mModelColumns.get(countViewLast).getTitle());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width), mModelColumns.get(countViewLast).getCount());
                    view.setLayoutParams(params);// set item content in view
                    linearLayout.addView(view);
                    countViewLast++;
                } else {
                    Toast.makeText(MainActivity.this,"Nope",Toast.LENGTH_SHORT).show();
                }

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countViewFirst > -1) {
                    linearLayout.getChildCount();
                    Log.d("AGIMA", "getChildAt(0)" + linearLayout.getChildAt(0));
                    final View view = inflater.inflate(R.layout.item_column, linearLayout, false);
                    Log.d("AGIMA", "getTitle() delete last" + mModelColumns.get(countViewLast).getTitle());
                    linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
                    Log.d("AGIMA", "mModelColumns.get(countView).getCount()" + mModelColumns.get(countViewFirst).getCount());
                    countViewLast--;

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width), mModelColumns.get(countViewFirst).getCount());
                    view.setLayoutParams(params);// set item content in view
                    linearLayout.addView(view, 0);
                    Log.d("AGIMA", "getTitle() add first" + mModelColumns.get(countViewFirst).getTitle());
                    countViewFirst--;
                } else {
                    Toast.makeText(MainActivity.this,"Nope",Toast.LENGTH_SHORT).show();
                }

            }
        });
/*
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height=sMAX;
        recyclerView.setLayoutParams(params);recyclerView.setLayoutManager(llm);
*/
        CreateData data = new CreateData(this);
        data.execute();

        horizontalScrollViewColumn.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
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
                        Log.d("AGIMA", "getChildCount" +
                                ( (LinearLayout)horizontalScrollViewColumn.getChildAt(0)).getChildAt(linearLayout.getChildCount()-1).getScrollBarFadeDuration() + ", " +
                                ( (LinearLayout)horizontalScrollViewColumn.getChildAt(0)).getChildAt(linearLayout.getChildCount()-1).getScrollBarSize() + ", " +
                                ( (LinearLayout)horizontalScrollViewColumn.getChildAt(0)).getChildAt(linearLayout.getChildCount()-1).getLeft());



            }
        });

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

            LogicClass init = new LogicClass();
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
            /*SimpleColumnsAdapter adapter = new SimpleColumnsAdapter(mContext, values[0]);
//            RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.listview_rotate);

            RotateAnimation rotate = new RotateAnimation(0, -90,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);

            rotate.setDuration(0);
            rotate.setRepeatCount(Animation.ABSOLUTE);
            //yourView.setAnimation(rotate);
            listViewColumn.setAnimation(rotate);
            listViewColumn.setAdapter(adapter);*/
            for (int i=0; i<END; i++){
                countViewLast += i;
                final View view  = inflater.inflate(R.layout.item_column, linearLayout, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.column_width), values[0].get(i).getCount());
                view.setLayoutParams(params);// set item content in view
                linearLayout.addView(view);
            }


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
