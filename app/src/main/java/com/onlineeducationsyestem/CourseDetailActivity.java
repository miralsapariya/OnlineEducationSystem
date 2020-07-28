package com.onlineeducationsyestem;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.CourseDetailCourseIncludesAdapter;
import com.onlineeducationsyestem.adapter.ExpandedCourseDetail;
import com.onlineeducationsyestem.model.ContentItem;
import com.onlineeducationsyestem.model.Header;
import com.onlineeducationsyestem.widget.NonScrollExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseDetailActivity extends BaseActivity {

    private RecyclerView rvCourse;
    private CourseDetailCourseIncludesAdapter courseDetailCourseIncludesAdapter;
    private NonScrollExpandableListView expandableView;
    private LinearLayout llCreatedByInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initToolbar();
        initUI();

    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initUI()
    {

        rvCourse =findViewById(R.id.rvCourse);
        llCreatedByInstructor =findViewById(R.id.llCreatedByInstructor);


        ArrayList<String> list =new ArrayList<>();
        list.add("djkjf");
        list.add("hfjd");
        list.add("dkjfkjd");
        list.add("dkjd");

        courseDetailCourseIncludesAdapter =
                new CourseDetailCourseIncludesAdapter(CourseDetailActivity.this, list);
        rvCourse.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(CourseDetailActivity.this);
        rvCourse.setLayoutManager(manager);
        rvCourse.setAdapter(courseDetailCourseIncludesAdapter);

        ArrayList<Header> listDataHeader = new ArrayList<Header>();
        HashMap<Header, ArrayList<ContentItem>> listDataChild = new HashMap<Header, ArrayList<ContentItem>>();

        Header h1 =new Header("Add-on",true);
        listDataHeader.add(h1);

        ArrayList<ContentItem> contentItems = new ArrayList<ContentItem>();
        ContentItem c1=new ContentItem();
        c1.setName("Minced pork");
        contentItems.add(c1);
        ContentItem c11=new ContentItem();
        c11.setName("Minced pork");
        contentItems.add(c11);

        listDataChild.put(listDataHeader.get(0), contentItems);

        expandableView =findViewById(R.id.expandableView);
        ExpandedCourseDetail expandedCourseDetail =new ExpandedCourseDetail(CourseDetailActivity.this, listDataHeader, listDataChild);
        expandableView.setAdapter(expandedCourseDetail);
        expandableView.expandGroup(0);
        expandedCourseDetail.notifyDataSetChanged();

       /* expandableView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                parent.smoothScrollToPosition(groupPosition);

                if (parent.isGroupExpanded(groupPosition)) {
                    ImageView imageView = v.findViewById(R.id.imgIndicator);
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.minus));
                } else {
                    ImageView imageView = v.findViewById(R.id.imgIndicator);
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.plus));
                }
                return false    ;
            }
        });*/

    }
}
