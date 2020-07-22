package com.onlineeducationsyestem;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.onlineeducationsyestem.adapter.ExpandedReportAdapter;
import com.onlineeducationsyestem.model.ContentItem;
import com.onlineeducationsyestem.model.Header;
import com.onlineeducationsyestem.widget.NonScrollExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportActivity extends BaseActivity {

    private NonScrollExpandableListView expandableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

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
        ArrayList<Header> listDataHeader = new ArrayList<Header>();
        HashMap<Header, ArrayList<ContentItem>> listDataChild = new HashMap<Header, ArrayList<ContentItem>>();

        Header h1 =new Header("Add-on",true);
        listDataHeader.add(h1);

        ArrayList<ContentItem> contentItems = new ArrayList<ContentItem>();
        ContentItem c1=new ContentItem();
        c1.setName("Minced pork");
        contentItems.add(c1);

        listDataChild.put(listDataHeader.get(0), contentItems);

        expandableView =findViewById(R.id.expandableView);
        ExpandedReportAdapter expandedCourseDetail =new ExpandedReportAdapter(ReportActivity.this, listDataHeader, listDataChild);
        expandableView.setAdapter(expandedCourseDetail);
        expandableView.expandGroup(0);
        expandedCourseDetail.notifyDataSetChanged();
    }

}
