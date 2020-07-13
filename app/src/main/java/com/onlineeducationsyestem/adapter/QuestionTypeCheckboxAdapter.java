package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;

import java.util.ArrayList;

public class QuestionTypeCheckboxAdapter extends RecyclerView.Adapter<QuestionTypeCheckboxAdapter.ViewHolder> {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionTypeCheckboxAdapter(Context context,
                                       ArrayList<String> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionTypeCheckboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_question_type_checkbox, parent, false);
        QuestionTypeCheckboxAdapter.ViewHolder viewHolder = new QuestionTypeCheckboxAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeCheckboxAdapter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);

            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}





