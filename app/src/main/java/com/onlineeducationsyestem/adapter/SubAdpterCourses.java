package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnNewCourseClick;

import java.util.ArrayList;

public class SubAdpterCourses extends RecyclerView.Adapter<SubAdpterCourses.ViewHolder>
         {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnNewCourseClick onItemClick;
    private Context context;

    public SubAdpterCourses(Context context,
                            ArrayList<String> listProduct, OnNewCourseClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public SubAdpterCourses.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_sub_category, parent, false);
        SubAdpterCourses.ViewHolder viewHolder = new SubAdpterCourses.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubAdpterCourses.ViewHolder holder, int position) {
        final  String data = listProduct.get(position);



    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvSubName,tvInstructorname;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSubName =itemView.findViewById(R.id.tvSubName);
            tvName =itemView.findViewById(R.id.tvName);
            tvInstructorname =itemView.findViewById(R.id.tvInstructorname);

        }
    }
}

