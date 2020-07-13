package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class HomeSearchAdapter extends RecyclerView.Adapter<HomeSearchAdapter.ViewHolder>
        implements OnItemClick {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public HomeSearchAdapter(Context context,
                                 ArrayList<String> listProduct,OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home_search, parent, false);
        HomeSearchAdapter.ViewHolder viewHolder = new HomeSearchAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeSearchAdapter.ViewHolder holder, int position) {
        final String data = listProduct.get(position);


    }
    @Override
    public void onGridClick(int pos) {

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img,imgNext;

        public TextView tvCatName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCatName =itemView.findViewById(R.id.tvCatName);
            img =itemView.findViewById(R.id.img);
            imgNext =itemView.findViewById(R.id.imgNext);

        }
    }
}




