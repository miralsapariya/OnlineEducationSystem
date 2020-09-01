package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnCardViewClick;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.MyCourseList;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.ViewHolder> {

    private ArrayList<MyCourseList.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private OnCardViewClick onCardViewClick;

    public MyCoursesAdapter(Context context,
                            ArrayList<MyCourseList.Courseslist> listProduct,
                            OnItemClick onItemClick,OnCardViewClick onCardViewClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.onCardViewClick =onCardViewClick;
    }

    @Override
    public MyCoursesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_my_courses, parent, false);
        MyCoursesAdapter.ViewHolder viewHolder = new MyCoursesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyCoursesAdapter.ViewHolder holder, final int position) {
        final MyCourseList.Courseslist data = listProduct.get(position);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCardViewClick.onCardClick(position);
            }
        });
        AppUtils.loadImageWithPicasso(data.getImage() , holder.img, context, 0, 0);
        holder.tvName.setText(data.getCourseName());
        holder.tvProgress.setText(data.getProgress());
        holder.tvCurseStatus.setText(data.getButtonLabel());

        holder.tvCurseStatus.setOnClickListener(new View.OnClickListener() {
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

    public MyCourseList.Courseslist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvProgress,tvEndDate,tvCurseStatus;
        public CardView card_view;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            card_view =itemView.findViewById(R.id.card_view);
            img =itemView.findViewById(R.id.img);
            tvProgress =itemView.findViewById(R.id.tvProgress);
            tvEndDate =itemView.findViewById(R.id.tvEndDate);
            tvCurseStatus =itemView.findViewById(R.id.tvCurseStatus);
        }
    }
}




