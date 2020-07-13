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

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
        implements OnItemClick {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public HomeAdapter(Context context,
                       ArrayList<String> listProduct,OnItemClick onItemClick) {
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
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, int position) {
        final  String data = listProduct.get(position);

        ArrayList<String> list =new ArrayList<>();
        list.add("adsdsd");
        list.add("adsdsd");
        list.add("adsdsd");
        list.add("adsdsd");

        if(position == 0)
        {
            HomeAdpterCourses homeAdapter =
                    new HomeAdpterCourses(context, list, new OnNewCourseClick() {
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
                    context.startActivity(intent);

                }
            });
        }else if(position == 1)
        {
            HomeAdpterCourses homeAdapter =
                    new HomeAdpterCourses(context, list,new OnNewCourseClick() {
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
                    context.startActivity(intent);

                }
            });

        } else if(position == 2)
        {
            HomeAdpterCategory homeAdapter =
                    new HomeAdpterCategory(context, list,this);

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);
        }else {
            HomeAdapterInstructor homeAdapter =
                    new HomeAdapterInstructor(context, list, new OnInstructorsClick() {
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

       /* HomeRowAdapter homeAdapter =
                new HomeRowAdapter(context, list,this);

        holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvHorizonatal.setHasFixedSize(true);
        holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
        holder.rvHorizonatal.setAdapter(homeAdapter);
*/
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

    public  String getItem(int id) {
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

