package com.onlineeducationsyestem.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.model.Exam;
import com.onlineeducationsyestem.widget.ItemMoveCallback2;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionTypeMatrixImgSecondAdapter extends
        RecyclerView.Adapter<QuestionTypeMatrixImgSecondAdapter.MyViewHolder> implements
        ItemMoveCallback2.ItemTouchHelperContract {

    private ArrayList<Exam.Option> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSort;
        private LinearLayout llMAin;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            tvSort = itemView.findViewById(R.id.tvSort);
        }
    }

    public QuestionTypeMatrixImgSecondAdapter(ArrayList<Exam.Option> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_quetion_type_matrix_sorting, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvSort.setText(data.get(position).getOptionMatrix());

      /*  holder.rowView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startDrag(viewHolder);
                }
                return false;
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }


}
