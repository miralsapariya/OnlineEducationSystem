package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnInstructorsClick;

import java.util.ArrayList;

public class HomeAdapterInstructor extends RecyclerView.Adapter<HomeAdapterInstructor.ViewHolder>
       {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnInstructorsClick onItemClick;
    private Context context;

    public HomeAdapterInstructor(Context context,
                              ArrayList<String> listProduct,OnInstructorsClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeAdapterInstructor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home_insructor, parent, false);
        HomeAdapterInstructor.ViewHolder viewHolder = new HomeAdapterInstructor.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapterInstructor.ViewHolder holder, final int position) {
        final  String data = listProduct.get(position);

        holder.llInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onInstructorClick(position);
            }
        });


    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvInstructorname,tvNoOfCourses;
        private LinearLayout llInstructor;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNoOfCourses =itemView.findViewById(R.id.tvNoOfCourses);
            tvInstructorname =itemView.findViewById(R.id.tvInstructorname);
            llInstructor =itemView.findViewById(R.id.llInstructor);
        }
    }
}



