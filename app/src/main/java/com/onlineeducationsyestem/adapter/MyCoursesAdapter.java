package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnCardViewClick;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.MyCourseList;
import com.onlineeducationsyestem.util.AppUtils;

import java.lang.reflect.Method;
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
        holder.circularProgressbar.setProgress(data.getPercentage());
        holder.tv.setText(data.getPercentage()+" %");

        holder.imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.imgSetting);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_my_couses);
                try {
                    Method method = popup.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                    method.setAccessible(true);
                    method.invoke(popup.getMenu(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                Log.d("=========== ", "menu1");
                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                Log.d("=========== ", "menu2");
                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
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
        public TextView tvName,tvProgress,tvEndDate,tvCurseStatus,tv;
        public CardView card_view;
        public ImageView img,imgSetting;
        private ProgressBar circularProgressbar;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            card_view =itemView.findViewById(R.id.card_view);
            img =itemView.findViewById(R.id.img);
            imgSetting =itemView.findViewById(R.id.imgSetting);
            tvProgress =itemView.findViewById(R.id.tvProgress);
          //  tvEndDate =itemView.findViewById(R.id.tvEndDate);
            tvCurseStatus =itemView.findViewById(R.id.tvCurseStatus);
            circularProgressbar =itemView.findViewById(R.id.circularProgressbar);
            tv =itemView.findViewById(R.id.tv);
        }
    }
}




