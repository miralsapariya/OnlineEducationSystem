package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.model.Exam;

import java.util.ArrayList;

public class QuestionTypeRadioAdapter extends RecyclerView.Adapter<QuestionTypeRadioAdapter.ViewHolder> {

    private ArrayList<Exam.Option> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionTypeRadioAdapter(Context context,
                                    ArrayList<Exam.Option> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionTypeRadioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_question_type_radio, parent, false);
        QuestionTypeRadioAdapter.ViewHolder viewHolder = new QuestionTypeRadioAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeRadioAdapter.ViewHolder holder, final int position) {
        final Exam.Option data = listProduct.get(position);
        holder.radioOption.setText(data.getOption());
        holder.radioOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.radioOption.isChecked()) {
                    data.setSelected(true);
                } else {
                    data.setSelected(false);
                }
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Exam.Option getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioOption;
        public ViewHolder(View itemView) {
            super(itemView);
            radioOption = itemView.findViewById(R.id.radioOption);
        }
    }
}





