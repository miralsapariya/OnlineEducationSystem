package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.Exam;

import java.util.ArrayList;

public class EdittextAdapter extends RecyclerView.Adapter<EdittextAdapter.ViewHolder> {

    private ArrayList<Exam.Option> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public EdittextAdapter(Context context,
                           ArrayList<Exam.Option> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public EdittextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_edittext, parent, false);
        EdittextAdapter.ViewHolder viewHolder = new EdittextAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final EdittextAdapter.ViewHolder holder, final int position) {
        final Exam.Option data = listProduct.get(position);


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Exam.Option getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView edittext;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            edittext=itemView.findViewById(R.id.edittext);
        }
    }
}




