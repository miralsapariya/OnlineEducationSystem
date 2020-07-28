package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.PopularInstructorActivity;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.SubCategoryActivity;
import com.onlineeducationsyestem.TrendingCourseActivity;
import com.onlineeducationsyestem.interfaces.OnInstructorsClick;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.interfaces.OnNewCourseClick;
import com.onlineeducationsyestem.model.Home;
import com.onlineeducationsyestem.network.ServerConstents;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
        implements OnItemClick {

    private ArrayList<Home.Datum> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public HomeAdapter(Context context,
                       ArrayList<Home.Datum> listProduct,OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home, parent, false);
        HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, final int position) {
        final  Home.Datum data = listProduct.get(position);

        holder.tvNameCategory.setText(data.getTitle());
        if(data.getType().equals(ServerConstents.HOME_TYPE_COURSE) )
        {
            HomeAdpterCourses homeAdapter =
                    new HomeAdpterCourses(context, data.getList(), new OnNewCourseClick() {
                        @Override
                        public void onNewCourseClick(int pos) {


                        }
                    });

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);
            holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, TrendingCourseActivity.class);
                    intent.putExtra("title", data.getTitle());
                    if(position == 0)
                    intent.putExtra("from", "new_courses");
                    else
                        intent.putExtra("from", "trending_courses");
                    context.startActivity(intent);
                }
            });
        }


        else if(data.getType().equals(ServerConstents.HOME_TYPE_CATEGORY))
        {
            HomeAdpterCategory homeAdapter =
                    new HomeAdpterCategory(context, data.getList(),this);

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);
        }else {
            HomeAdapterInstructor homeAdapter =
                    new HomeAdapterInstructor(context, data.getList(), new OnInstructorsClick() {
                        @Override
                        public void onInstructorClick(int pos) {

                        }
                    });

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);

            holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent =new Intent(context, PopularInstructorActivity.class);
                    context.startActivity(intent);

                }
            });
        }

         }

    @Override
    public void onGridClick(int pos) {

        Intent intent=new Intent(context, SubCategoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  Home.Datum getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCategory,tvViewAll;
        private RecyclerView rvHorizonatal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameCategory =itemView.findViewById(R.id.tvNameCategory);
            tvViewAll =itemView.findViewById(R.id.tvViewAll);

            rvHorizonatal =itemView.findViewById(R.id.rvHorizonatal);

        }
    }
}

