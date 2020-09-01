package com.onlineeducationsyestem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.UserCourseDetailActivity;
import com.onlineeducationsyestem.adapter.MyCoursesAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnCardViewClick;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.MyCourseList;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;


public class MyCoursesFragment extends BaseFragment implements OnItemClick, NetworkListener, OnCardViewClick {

        private View view;
        private RecyclerView rvMyCourses;
        private MyCoursesAdapter myCoursesAdapter;
        private TextView tvNoData;
    MyCourseList data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_my_courses, container, false);

        initUI();
        return view;
    }

    private void initUI()
    {
        rvMyCourses =view.findViewById(R.id.rvMyCourses);
        tvNoData =view.findViewById(R.id.tvNoData);
        if (AppUtils.isInternetAvailable(activity)) {
            getMyCourseList();
        }
    }

    private void getMyCourseList()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<MyCourseList> call = apiInterface.myCourseList(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (MyCourseList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                myCoursesAdapter =new MyCoursesAdapter(activity,data.getData().get(0).getCourseslist(),this,this);
                rvMyCourses.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                rvMyCourses.setLayoutManager(manager);
                rvMyCourses.setAdapter(myCoursesAdapter);
            }
            }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            tvNoData.setVisibility(View.VISIBLE);
            rvMyCourses.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onCardClick(int pos) {
        Intent intent =new Intent(activity, UserCourseDetailActivity.class);
        intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId()+"");
        startActivity(intent);
    }

    @Override
    public void onGridClick(int pos) {
        if(AppConstant.COURSE_STATUS_START.equals(data.getData().get(0).getCourseslist().get(pos).getCourse_status()))
        {
            Intent intent =new Intent(activity, UserCourseDetailActivity.class);
            intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId()+"");
            startActivity(intent);
        }else if(AppConstant.COURSE_STATUS_RESUME.equals(data.getData().get(0).getCourseslist().get(pos).getCourse_status()))
        {
            Intent intent =new Intent(activity, UserCourseDetailActivity.class);
            intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId()+"");
            intent.putExtra("section_id", data.getData().get(0).getCourseslist().get(pos).getNext_section_id()+"");
            intent.putExtra("slide_id",data.getData().get(0).getCourseslist().get(pos).getNext_slide_id()+"");
                    startActivity(intent);
        }
    }
}
