package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.CourseDetailActivity;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class TrendingCourseAdapter extends RecyclerView.Adapter<TrendingCourseAdapter.ViewHolder> {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public TrendingCourseAdapter(Context context,
                                 ArrayList<String> listProduct,OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public TrendingCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_trending_course, parent, false);
        TrendingCourseAdapter.ViewHolder viewHolder = new TrendingCourseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TrendingCourseAdapter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourse, tvPrice;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}




