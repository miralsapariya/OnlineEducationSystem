package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class GridTopCategoryAdapter extends RecyclerView.Adapter<GridTopCategoryAdapter.ViewHolder>
       {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public GridTopCategoryAdapter(Context context,
                                 ArrayList<String> listProduct,OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public GridTopCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_grid_top_cat, parent, false);
        GridTopCategoryAdapter.ViewHolder viewHolder = new GridTopCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GridTopCategoryAdapter.ViewHolder holder, final int position) {
        final  String data = listProduct.get(position);
        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClick.onGridClick(position);
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
        private RelativeLayout rlMain;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNoOfCourses =itemView.findViewById(R.id.tvNoOfCourses);
            tvInstructorname =itemView.findViewById(R.id.tvInstructorname);
            rlMain =itemView.findViewById(R.id.rlMain);

        }
    }
}



