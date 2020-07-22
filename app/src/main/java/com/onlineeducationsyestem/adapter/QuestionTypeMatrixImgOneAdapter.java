package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;

import java.util.ArrayList;

public class QuestionTypeMatrixImgOneAdapter extends RecyclerView.Adapter<QuestionTypeMatrixImgOneAdapter.ViewHolder> {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionTypeMatrixImgOneAdapter(Context context,
                                           ArrayList<String> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionTypeMatrixImgOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_quetion_type_matrix_question_img_sorting, parent, false);
        QuestionTypeMatrixImgOneAdapter.ViewHolder viewHolder = new QuestionTypeMatrixImgOneAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeMatrixImgOneAdapter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position).toString();

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
        }
    }
}





