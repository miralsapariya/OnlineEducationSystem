package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.onlineeducationsyestem.adapter.ExpandedUserCourseDetail;
import com.onlineeducationsyestem.interfaces.OnChildItemClick;
import com.onlineeducationsyestem.model.ContentItem;
import com.onlineeducationsyestem.model.Header;
import com.onlineeducationsyestem.widget.NonScrollExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserCourseDetailActivity extends BaseActivity implements OnChildItemClick {

    private NonScrollExpandableListView expandableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course_detail);

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
        expandableView =findViewById(R.id.expandableView);
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
        ExpandedUserCourseDetail expandedCourseDetail =new
                ExpandedUserCourseDetail
                (UserCourseDetailActivity.this, listDataHeader, listDataChild,this);
        expandableView.setAdapter(expandedCourseDetail);
        expandableView.expandGroup(0);
        expandedCourseDetail.notifyDataSetChanged();
    }

    @Override
    public void onChildClick(int pos, int childPos) {


        Intent intent = new Intent(UserCourseDetailActivity.this,ExamActivity.class);
        startActivity(intent);

    }
}
