package com.onlineeducationsyestem;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.CourseDetailCourseIncludesAdapter;
import com.onlineeducationsyestem.adapter.ExpandedCourseDetail;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.CourseDetail;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;
import com.onlineeducationsyestem.widget.NonScrollExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class CourseDetailActivity extends BaseActivity implements NetworkListener {

    private RecyclerView rvCourse;
    private CourseDetailCourseIncludesAdapter courseDetailCourseIncludesAdapter;
    private NonScrollExpandableListView expandableView;
    private LinearLayout llCreatedByInstructor,llCurriculum;
    private ImageView imgWhishList,imgBack;
    private String course_id;
    private ImageView imgCourse,imgUser;
    private TextView tvCourseTitle,tvNewPrice,
            tvOldPrice,tvSubscriber,tvTitle1,tvCourseIncludetitle,tvCurriculum,
            tvStudent,tvCourse,tvViewProfile,tvCreateBy,tvMoreSection,tvDuration;
    private Button buyNow;
    private WebView tvCourseDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initToolbar();
        initUI();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initUI() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            course_id = extras.getString("course_id");
        }
        rvCourse = findViewById(R.id.rvCourse);
        imgCourse=findViewById(R.id.imgCourse);
        imgUser=findViewById(R.id.imgUser);
        tvStudent=findViewById(R.id.tvStudent);
        tvDuration =findViewById(R.id.tvDuration);

        tvCourse =findViewById(R.id.tvCourse);
        tvCreateBy=findViewById(R.id.tvCreateBy);
        tvCourseTitle =findViewById(R.id.tvCourseTitle);
        tvCourseDetail =findViewById(R.id.tvCourseDetail);
        tvNewPrice =findViewById(R.id.tvNewPrice);
        tvOldPrice =findViewById(R.id.tvOldPrice);
        tvSubscriber=findViewById(R.id.tvSubscriber);
        tvTitle1 =findViewById(R.id.tvTitle1);
        tvCourseIncludetitle =findViewById(R.id.tvCourseIncludetitle);
        tvCurriculum=findViewById(R.id.tvCurriculum);
        tvViewProfile =findViewById(R.id.tvViewProfile);
        tvMoreSection =findViewById(R.id.tvMoreSection);
        buyNow =findViewById(R.id.buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID) == null) {
                    AppUtils.loginAlert(CourseDetailActivity.this);
                }else {
                    callCart();
                }
            }
        });

        llCreatedByInstructor = findViewById(R.id.llCreatedByInstructor);
        llCurriculum =findViewById(R.id.llCurriculum);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgWhishList = findViewById(R.id.imgWhishList);
        imgWhishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(CourseDetailActivity.this)) {

                    if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID) == null) {
                        Toast.makeText(CourseDetailActivity.this, getString(R.string.toast_login_first), Toast.LENGTH_SHORT).show();
                    } else {
                        hintWhishList();
                    }

                }
            }
        });


        if (AppUtils.isInternetAvailable(CourseDetailActivity.this)) {

            hintCourseDetail();
        }


    }

    private void callCart()
    {
        if (AppUtils.isInternetAvailable(CourseDetailActivity.this)) {
            String lang="";
            AppUtils.showDialog(CourseDetailActivity.this, getString(R.string.pls_wait));
            ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
            final HashMap params = new HashMap<>();

            params.put("course_id",course_id+"");
            if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                    AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                lang = AppConstant.ENG_LANG;
            }else
            {
                lang= AppConstant.ARABIC_LANG;
            }

            Call<BaseBean> call = apiInterface.addToCart(lang,AppSharedPreference.getInstance().
                    getString(CourseDetailActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
            ApiCall.getInstance().hitService(CourseDetailActivity.this, call, this, ServerConstents.CART);

        }
    }
    private void hintCourseDetail() {

        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("id", course_id);
        if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID) == null) {
            params.put("user_id","");
        } else {
            params.put("user_id", AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID));
        }

        if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG))
        {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<CourseDetail> call = apiInterface.getCourseDetail(lang,params);

        ApiCall.getInstance().hitService(CourseDetailActivity.this, call, this, ServerConstents.DETAIL);

    }

    private void hintWhishList() {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);

        if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.addWhishList(lang,
                AppSharedPreference.getInstance().
                        getString(CourseDetailActivity.this, AppSharedPreference.ACCESS_TOKEN),

                params);

        ApiCall.getInstance().hitService(CourseDetailActivity.this, call, this, ServerConstents.WHISH_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if(requestCode == ServerConstents.WHISH_LIST) {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                imgWhishList.setImageResource(R.drawable.ic_whishlisted);
                Toast.makeText(CourseDetailActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else  if(requestCode == ServerConstents.CART){

            BaseBean baseBean =(BaseBean) response;
            Toast.makeText(CourseDetailActivity.this, baseBean.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }else {

           final  CourseDetail data = (CourseDetail) response;

            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                AppUtils.loadImageWithPicasso
                        (data.getData().get(0).getImage() , imgCourse, CourseDetailActivity.this, 0, 0);
                tvCourseTitle.setText(data.getData().get(0).getCourseName());
                tvTitle1.setText(data.getData().get(0).getCourseName());

              /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvCourseDetail.setText(Html.fromHtml(data.getData().get(0).getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvCourseDetail.setText(Html.fromHtml(data.getData().get(0).getDescription()));
                }*/

                tvCourseDetail.getSettings().setJavaScriptEnabled(true);
                tvCourseDetail.loadDataWithBaseURL(null, data.getData().get(0).getDescription(), "text/html", "utf-8", null);
               // tvCourseDetail.loadData(data.getData().get(0).getDescription(), "text/html; charset=utf-8", "UTF-8");
                if(data.getData().get(0).getIs_free() ==0)
                {
                    buyNow.setText(getString(R.string.buy_now));
                }else
                {
                    buyNow.setText(getString(R.string.enroll_now));
                }

                tvNewPrice.setText(data.getData().get(0).getCoursePrice());
                tvOldPrice.setText(data.getData().get(0).getCourseOldPrice());
                tvOldPrice.setPaintFlags( tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvSubscriber.setText(data.getData().get(0).getTotalSubscriber()+"");
                tvCurriculum.setText(data.getData().get(0).getSectionTitle());
                if(data.getData().get(0).getIsWishlist() == 1)
                {
                    imgWhishList.setImageResource(R.drawable.ic_whishlisted);
                }else
                {
                    imgWhishList.setImageResource(R.drawable.ic_heart);
                }
                tvCourseIncludetitle.setText(data.getData().get(0).getCourseIncludeTitle());


                courseDetailCourseIncludesAdapter =
                        new CourseDetailCourseIncludesAdapter(CourseDetailActivity.this, data.getData().get(0).getCourseInclude());
                rvCourse.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(CourseDetailActivity.this);
                rvCourse.setLayoutManager(manager);
                rvCourse.setAdapter(courseDetailCourseIncludesAdapter);

                //cocurriculam

                if(data.getData().get(0).getSectionDetails().size() > 0) {
                    llCurriculum.setVisibility(View.VISIBLE);
                    ArrayList<CourseDetail.SectionDetail> listDataHeader = new ArrayList<CourseDetail.SectionDetail>();
                    HashMap<CourseDetail.SectionDetail, ArrayList<CourseDetail.SectionSlideDetail>> listDataChild = new HashMap<CourseDetail.SectionDetail, ArrayList<CourseDetail.SectionSlideDetail>>();

                    if (data.getData().get(0).getSectionDetails().size() > 3) {
                        tvMoreSection.setVisibility(View.VISIBLE);
                    } else {
                        tvMoreSection.setVisibility(View.GONE);
                    }
                    if (data.getData().get(0).getSectionDetails().size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            listDataHeader.add(data.getData().get(0).getSectionDetails().get(i));
                        }
                    } else {
                        listDataHeader.addAll(data.getData().get(0).getSectionDetails());
                    }


                    expandableView = findViewById(R.id.expandableView);
                    ExpandedCourseDetail expandedCourseDetail = new ExpandedCourseDetail(CourseDetailActivity.this, listDataHeader);
                    expandableView.setAdapter(expandedCourseDetail);
                    expandableView.expandGroup(0);
                    expandedCourseDetail.notifyDataSetChanged();

                    tvMoreSection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(CourseDetailActivity.this, MoreSectionActivity.class);
                            intent.putExtra("listData", data.getData().get(0).getSectionDetails());
                            startActivity(intent);

                        }
                    });

                }else
                {
                    llCurriculum.setVisibility(View.GONE);
                }
                //
                if(data.getData().get(0).getCreatedBy().equalsIgnoreCase("instructor")) {
                    llCreatedByInstructor.setVisibility(View.VISIBLE);
                    AppUtils.loadImageWithPicasso(data.getData().get(0).getInstructorDetails().getProfileImage() , imgUser, CourseDetailActivity.this, 0, 0);
                    tvCourse.setText(data.getData().get(0).getInstructorDetails().getTotalCourse()+"");
                    tvStudent.setText(data.getData().get(0).getInstructorDetails().getTotalStudents()+"");

                    tvCreateBy.setText(data.getData().get(0).getInstructorDetails().getInstructorName());
                }else {
                    llCreatedByInstructor.setVisibility(View.GONE);

                }
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.WHISH_LIST) {
            imgWhishList.setImageResource(R.drawable.ic_heart);
        }
        Toast.makeText(CourseDetailActivity.this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {

    }
}
