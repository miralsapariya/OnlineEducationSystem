package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.interfaces.OnNewCourseClick;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>
       {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public SubCategoryAdapter(Context context,
                              ArrayList<String> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_subcat, parent, false);
        SubCategoryAdapter.ViewHolder viewHolder = new SubCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.ViewHolder holder, int position) {
        final  String data = listProduct.get(position);

        ArrayList<String> list =new ArrayList<>();
        list.add("adsdsd");
        list.add("adsdsd");
        list.add("adsdsd");
        list.add("adsdsd");


            SubAdpterCourses homeAdapter =
                    new SubAdpterCourses(context, list, new OnNewCourseClick() {
                        @Override
                        public void onNewCourseClick(int pos) {


                        }
                    });

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);

    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCategory;
        private RecyclerView rvHorizonatal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameCategory =itemView.findViewById(R.id.tvNameCategory);
            rvHorizonatal =itemView.findViewById(R.id.rvHorizonatal);

        }
    }
}

