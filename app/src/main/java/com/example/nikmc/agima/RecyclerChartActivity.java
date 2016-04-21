package com.example.nikmc.agima;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nikmc.agima.adapters.ColumnsAdapterRecycler;
import com.example.nikmc.agima.interfaces.RecyclerViewClickListener;
import com.example.nikmc.agima.logic.LogicClass;
import com.example.nikmc.agima.model.ModelChart;

import java.util.ArrayList;
import java.util.List;

public class RecyclerChartActivity extends AppCompatActivity implements RecyclerViewClickListener{

    private final static int sCOUNT_TABLE = 20000;
    private final static int sMAX = 1000;
    private final static int sMIN = 0;
    private LinearLayout InfoView;
    private TextView name;
    private TextView count;
    private View selector;
    private RelativeLayout.LayoutParams selectorparams;
    private List<Integer> mTable;
    private List<String> mTitleTable;
    private List<ModelChart> mModelColumns = new ArrayList<>();
    private RecyclerView recyclerView;
    private ColumnsAdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_chart);
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
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        InfoView = (LinearLayout)findViewById(R.id.InfoView);
        count = (TextView)findViewById(R.id.InfoCountColumn);
        name = (TextView)findViewById(R.id.TitleColumn);
        selector = (View)findViewById(R.id.selector);
        selectorparams = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen.selector_width), (int)getResources().getDimension(R.dimen.selector_height));
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(llm);
        CreateData data = new CreateData(this);
        data.execute();

    }


    @Override
    public void ItemViewClick(View v, int position, ModelChart modelChart) {
        if(InfoView.getVisibility() == View.GONE){
            InfoView.setVisibility(View.VISIBLE);
        }
        count.setText(String.valueOf(mModelColumns.get(position).getCount()));
        name.setText(String.valueOf(mModelColumns.get(position).getTitle()));
        selectorparams.setMargins(v.getLeft() + v.getLayoutParams().width / 2, 0, 0, 0);
        selector.setLayoutParams(selectorparams);

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
            adapterRecycler = new ColumnsAdapterRecycler(mContext, values[0], RecyclerChartActivity.this);
            recyclerView.setAdapter(adapterRecycler);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Добавление шкалы величин с высотой равной scrollView
            createValueOfScale();
            if(dialog != null)
                dialog.dismiss();

        }

        private void createValueOfScale() {
            RelativeLayout text = (RelativeLayout)findViewById(R.id.Count);
            text.setVisibility(View.VISIBLE);
            text.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, recyclerView.getLayoutParams().height));
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



}
