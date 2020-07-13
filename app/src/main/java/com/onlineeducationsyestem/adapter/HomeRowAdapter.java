package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class HomeRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    private static int TYPE_COURSES = 1;
    private static int TYPE_CATEGORY = 2;
    private static int TYPE_INSRUCTOR =3;


    public HomeRowAdapter(Context context, ArrayList<String> listProduct,
                          OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_CATEGORY) { // for call layout
            view = LayoutInflater.from(context).inflate(R.layout.row_home_category, viewGroup, false);
            return new CatViewHolder(view);
        }

        else if (viewType == TYPE_COURSES) { // for call layout
            view = LayoutInflater.from(context).inflate(R.layout.row_home_course, viewGroup, false);
            return new CoursesViewHolder(view);
        }
        else { // for email layout
            view = LayoutInflater.from(context).inflate(R.layout.row_home_insructor, viewGroup, false);
            return new InsructorViewHolder(view);
        }
    }

    class CoursesViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvSubName,tvInstructorname;

        CoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSubName = itemView.findViewById(R.id.tvSubName);
            tvInstructorname = itemView.findViewById(R.id.tvInstructorname);
        }
    }

    class CatViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvNoOfCourses;

        CatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNoOfCourses = itemView.findViewById(R.id.tvNoOfCourses);
        }
    }

    class InsructorViewHolder extends RecyclerView.ViewHolder {

        private TextView tvInstructorname;
        private TextView tvNoOfCourses;

        InsructorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstructorname = itemView.findViewById(R.id.tvInstructorname);
            tvNoOfCourses = itemView.findViewById(R.id.tvNoOfCourses);
        }
    }

    @Override
    public int getItemViewType(int position) {
         if(position ==2) {
            return TYPE_CATEGORY;
        }else if(position == 3)
        {
            return  TYPE_INSRUCTOR;
        }else
        {
             return TYPE_COURSES;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final  String data = listProduct.get(position);
        if (getItemViewType(position) == TYPE_CATEGORY) {

            //set value here
            //((CallViewHolder) viewHolder).setCallDetails(employees.get(position));
        } else if(getItemViewType(position) == TYPE_INSRUCTOR){
           // ((EmailViewHolder) viewHolder).setEmailDetails(employees.get(position));
        }
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


