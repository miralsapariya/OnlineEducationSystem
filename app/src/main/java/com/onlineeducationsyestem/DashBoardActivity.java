package com.onlineeducationsyestem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.onlineeducationsyestem.adapter.DashboardAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.Dashboard;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;
import com.onlineeducationsyestem.util.DownloadTask;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;

public class DashBoardActivity extends BaseActivity implements NetworkListener, OnItemClick
{
    private PieChart chart;
    private ImageView imgBack;
    private Dashboard data;
    private RecyclerView recyclerView;
    private TextView tvTotCourse,tvComplateCourse;
    private static final int PERMISSION_PHOTO = 1;
    private String link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI()
    {
        recyclerView =findViewById(R.id.recyclerView);
        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDrawCenterText(false);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(false);
        chart.animateY(1400, Easing.EaseInOutQuad);
        chart.setDrawHoleEnabled(false);
        chart.getLegend().setEnabled(true);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);
        chart.setDrawEntryLabels(false);

        tvTotCourse=findViewById(R.id.tvTotCourse);
        tvComplateCourse=findViewById(R.id.tvComplateCourse);

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(DashBoardActivity.this)) {
            getDashBorad();
        }
    }

    @Override
    public void onGridClick(int pos) {
        Log.d("-------", "=============");

        link=data.getData().get(0).getMycourselist().get(pos).getDownloadLink();
        if(!data.getData().get(0).getMycourselist().get(pos).getDownloadLink().equals(""))
        {
            if (ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
            } else {
                new DownloadTask(DashBoardActivity.this, data.getData().get(0).getMycourselist().get(pos).getDownloadLink());
            }
        }else
        {
            if(data.getData().get(0).getMycourselist().get(pos).getCourseStatus().equals("2")){
                Intent intent =new Intent(DashBoardActivity.this,LessionSlideActivity.class);
                intent.putExtra("course_id", data.getData().get(0).getMycourselist().get(pos).getCourseId()+"");
                intent.putExtra("section_id", data.getData().get(0).getMycourselist().get(pos).getSectionId()+"");
                intent.putExtra("slide_id", data.getData().get(0).getMycourselist().get(pos).getSlideId()+"");
                startActivity(intent);
            }else
            {
                    Intent intent = new Intent(DashBoardActivity.this, CourseDetailActivity.class);
                    intent.putExtra("course_id", data.getData().get(0).getMycourselist().get(pos).getCourseId() + "");
                    startActivity(intent);
            }
        }

    }

    private void getDashBorad()
    {
        String lang="";
        AppUtils.showDialog(DashBoardActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(DashBoardActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(DashBoardActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<Dashboard> call = apiInterface.getDashBord(lang, AppSharedPreference.getInstance().
                getString(DashBoardActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(DashBoardActivity.this, call, this, ServerConstents.CATEGORY);

    }
    public static String nFormate(float d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(10);
        String st= nf.format(d);
        return st;
    }
    private void setData(PieChart chart, float count1,int conunt11,
                         float count2,int conunt22, float count3,int conunt33) {
        ArrayList<PieEntry> entries=new ArrayList<>();

        ArrayList<Integer> colors=new ArrayList<>();
        for (int i=0; i < 3; i++) {
            if (i == 0) {
                //if (count1 > 0) {

                    colors.add(getResources().getColor(R.color.colorPrimary));
                    entries.add(new PieEntry(count1,getString(R.string.not_started)+"("+conunt11+")"));
               // }
            }

             else if(i==1){
                //if (count2 > 0) {
                    colors.add(getResources().getColor(R.color.chart2));
                    entries.add(new PieEntry(count2,getString(R.string.completed)+"("+conunt22+")"));
               // }
            }else
            {
                //if (count3 > 0) {
                colors.add(getResources().getColor(R.color.chart1));
                entries.add(new PieEntry(count3,getString(R.string.in_progress)+"("+conunt33+")"));
            //}
            }
        }

        PieDataSet dataSet=new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data=new PieData(dataSet);
        data.setValueFormatter(new NonZeroChartValueFormatter(0));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //text on pie
        data.setDrawValues(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }
    class NonZeroChartValueFormatter extends DefaultValueFormatter {

        NonZeroChartValueFormatter(int digits) {
            super(digits);
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                        ViewPortHandler viewPortHandler) {
            if (value > 0) {



                return mFormat.format(value);
            } else {
                return "";
            }
        }
    }
    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CATEGORY) {
            data= (Dashboard) response;

            Log.d("=========sussessss ", "==============");
            DashboardAdapter dashboardAdapter =
                    new DashboardAdapter(DashBoardActivity.this, data.getData().get(0).getMycourselist(),this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(DashBoardActivity.this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(dashboardAdapter);

            tvTotCourse.setText(data.getData().get(0).getTotalCourse()+"");
            tvComplateCourse.setText(data.getData().get(0).getCompletedCourse()+"");

            setData(chart,data.getData().get(0).getChartDataPer().getNotStartedPer(),
                    data.getData().get(0).getChartData().getNotStarted(),
                    data.getData().get(0).getChartDataPer().getCompletedPer(),
                    data.getData().get(0).getChartData().getCompleted(),
                    data.getData().get(0).getChartDataPer().getInProgressPer(),
                    data.getData().get(0).getChartData().getInProgress()
                    );
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Log.d("=========errr ", "==============");
    }

    @Override
    public void onFailure() {
        Log.d("=========fail ", "==============");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        new DownloadTask(DashBoardActivity.this, link);

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
                break;
        }
    }
}