package com.onlineeducationsyestem.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.MainActivity;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.DownloadClick;
import com.onlineeducationsyestem.interfaces.OnCardViewClick;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.interfaces.OnResetCourse;
import com.onlineeducationsyestem.model.MyCourseList;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.ViewHolder> {

    private ArrayList<MyCourseList.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private OnCardViewClick onCardViewClick;
    private DownloadClick downloadClick;
    private OnResetCourse onResetCourse;

    public MyCoursesAdapter(Context context,
                            ArrayList<MyCourseList.Courseslist> listProduct,
                            OnItemClick onItemClick, OnCardViewClick onCardViewClick,
                            DownloadClick downloadClick, OnResetCourse onResetCourse) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.onCardViewClick =onCardViewClick;
        this.downloadClick =downloadClick;
        this.onResetCourse =onResetCourse;
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
                int[] location = new int[2];
                int currentRowId = position;
                View currentRow = view;
                // Get the x, y location and store it in the location[] array
                // location[0] = x, location[1] = y.
                view.getLocationOnScreen(location);

                //Initialize the Point with x, and y positions
                Point point = new Point();
                point.x = location[0];
                point.y = location[1];
                showStatusPopup((MainActivity)context, point,data,position);

            /*    PopupMenu popup = new PopupMenu(context, holder.imgSetting);
                popup.inflate(R.menu.menu_my_couses);
                try {
                    Method method = popup.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                    method.setAccessible(true);
                    method.invoke(popup.getMenu(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                if(data.getCertificate_link().length() > 1)
                                downloadClick.onDownload(position);
                                else
                                    Toast.makeText(context, context.getString(R.string.no_download),Toast.LENGTH_SHORT ).show();
                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                if(data.getIs_coursereset() == 1)
                                onResetCourse.onReset(position);
                                else
                                    Toast.makeText(context, context.getString(R.string.reset_course_no_start),Toast.LENGTH_SHORT ).show();
                                break;

                        }
                        return false;
                    }
                });
                popup.show();*/
            }
        });

    }
    private void showStatusPopup(final Activity context, Point p, final MyCourseList.Courseslist data, final int position) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_menu, null);

        // Creating the PopupWindow
        PopupWindow changeStatusPopUp = new PopupWindow(context);
        changeStatusPopUp.setContentView(layout);
        changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);
        LinearLayout llDownload=layout.findViewById(R.id.llDownload);
        LinearLayout llReset=layout.findViewById(R.id.llReset);
        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatusPopUp.dismiss();
                if(data.getCertificate_link().length() > 1)
                    downloadClick.onDownload(position);
                else
                    Toast.makeText(context, context.getString(R.string.no_download),Toast.LENGTH_SHORT ).show();

            }
        });

        llReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatusPopUp.dismiss();
                if(data.getIs_coursereset() == 1)
                    onResetCourse.onReset(position);
                else
                    Toast.makeText(context, context.getString(R.string.reset_course_no_start),Toast.LENGTH_SHORT ).show();
            }
        });

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = -20;
        int OFFSET_Y = 50;

        //Clear the default translucent background
        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
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




