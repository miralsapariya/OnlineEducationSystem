package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.model.ContentItem;
import com.onlineeducationsyestem.model.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandedReportAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Header> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Header , ArrayList<ContentItem>> _listDataChild;

    public ExpandedReportAdapter(Context context, List<Header> listDataHeader,
                                 HashMap<Header, ArrayList<ContentItem>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ContentItem childText = (ContentItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_child_reoprt, null);
        }

        TextView tvAns =  convertView
                .findViewById(R.id.tvAns);

        // radio.setText(childText.getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Header headerTitle = (Header) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_parent_report, null);
        }

        TextView tvQue = (TextView) convertView
                .findViewById(R.id.tvQue);
        ImageView imgIndicator = convertView.findViewById(R.id.imgIndicator);

        if(isExpanded)
        {
            imgIndicator.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_up_arrow));
        }else
        {
            imgIndicator.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_down_arrow));

        }
        // tvchoice.setText(headerTitle.getHeader());

        // ExpandableListView mExpandableListView = (ExpandableListView) parent;
        // mExpandableListView.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}