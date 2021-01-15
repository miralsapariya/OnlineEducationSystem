package com.onlineeducationsyestem.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.LessionSlideActivity;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.UserCourseDetailActivity;
import com.onlineeducationsyestem.adapter.MyCoursesAdapter;
import com.onlineeducationsyestem.interfaces.DownloadClick;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnCardViewClick;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.interfaces.OnResetCourse;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.MyCourseList;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;
import com.onlineeducationsyestem.util.DownloadTask;

import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;


public class MyCoursesFragment extends BaseFragment implements OnItemClick, NetworkListener, OnCardViewClick, DownloadClick, OnResetCourse {

    private static final int PERMISSION_PHOTO = 1;
    MyCourseList data;
    private View view;
    private RecyclerView rvMyCourses;
    private MyCoursesAdapter myCoursesAdapter;
    private TextView tvNoData;
    private String link = "";
    private int courseIDSelect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_courses, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) !=null &&AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ARABIC_LANG)) {
            String languageToLoad = "ar"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());

        }
    }

    private void initUI() {
        rvMyCourses = view.findViewById(R.id.rvMyCourses);
        tvNoData = view.findViewById(R.id.tvNoData);
        if (AppUtils.isInternetAvailable(activity)) {
            getMyCourseList();
        }else {
            AppUtils.showAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.alter_net));
        }

    }

    private void getMyCourseList() {
        String lang = "";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<MyCourseList> call = apiInterface.myCourseList(lang, AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            data = (MyCourseList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                myCoursesAdapter = new MyCoursesAdapter(activity
                        , data.getData().get(0).getCourseslist(), this, this, this, this);
                rvMyCourses.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                rvMyCourses.setLayoutManager(manager);
                rvMyCourses.setAdapter(myCoursesAdapter);
            }
        } else {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                Intent intent = new Intent(activity, UserCourseDetailActivity.class);
                intent.putExtra("course_id", courseIDSelect + "");
                startActivity(intent);
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            tvNoData.setVisibility(View.VISIBLE);
            rvMyCourses.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onCardClick(int pos) {
        Intent intent = new Intent(activity, UserCourseDetailActivity.class);
        intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId() + "");
        startActivity(intent);
    }

    @Override
    public void onGridClick(int pos) {
        if (AppConstant.COURSE_STATUS_START.equals(data.getData().get(0).getCourseslist().get(pos).getCourse_status())) {
            Intent intent = new Intent(activity, UserCourseDetailActivity.class);
            intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId() + "");
            startActivity(intent);
        } else if (AppConstant.COURSE_STATUS_RESUME.equals(data.getData().get(0).getCourseslist().get(pos).getCourse_status())) {
            Intent intent = new Intent(activity, LessionSlideActivity.class);
            intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId() + "");
            intent.putExtra("section_id", data.getData().get(0).getCourseslist().get(pos).getNext_section_id() + "");
            intent.putExtra("slide_id", data.getData().get(0).getCourseslist().get(pos).getNext_slide_id() + "");
            startActivity(intent);
        }
    }

    @Override
    public void onDownload(int pos) {
        link = data.getData().get(0).getCourseslist().get(pos).getCertificate_link();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
        } else {
            new DownloadTask(activity, data.getData().get(0).getCourseslist().get(pos).getCertificate_link());
        }
    }

    @Override
    public void onReset(int pos) {
        if (AppUtils.isInternetAvailable(activity)) {
            courseIDSelect = data.getData().get(0).getCourseslist().get(pos).getCourseId();
            resetCourse(data.getData().get(0).getCourseslist().get(pos).getCourseId());
        }
    }

    private void resetCourse(int Id) {

        String lang = "";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", Id + "");
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.resetCourse(lang, AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.RESET);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        new DownloadTask(activity, link);

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
                break;
        }
    }

}
