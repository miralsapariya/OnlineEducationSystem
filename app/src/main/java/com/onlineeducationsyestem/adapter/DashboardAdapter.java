package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.ApplyPromoCode;
import com.onlineeducationsyestem.interfaces.CheckOutInCart;
import com.onlineeducationsyestem.interfaces.DeleteItemInCart;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.Dashboard;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private ArrayList<Dashboard.Mycourselist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;
    private ApplyPromoCode applyPromoCode;
    private CheckOutInCart checkOutInCart;
    int amount=0;
    public DashboardAdapter(Context context,
                            ArrayList<Dashboard.Mycourselist> listProduct,
                            OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_dashboard, parent, false);
        DashboardAdapter.ViewHolder viewHolder = new DashboardAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DashboardAdapter.ViewHolder holder, final int position) {
        final Dashboard.Mycourselist data = listProduct.get(position);

        holder.tvNAme.setText(data.getCourseName());
        holder.tvSatus.setText(data.getStatus()+"");
        holder.btnStatus.setText(data.getButton());
        holder.btnStatus.setOnClickListener(new View.OnClickListener() {
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

    public Dashboard.Mycourselist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNAme, tvSatus;
        public Button btnStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNAme = itemView.findViewById(R.id.tvNAme);
            tvSatus = itemView.findViewById(R.id.tvSatus);
            btnStatus =itemView.findViewById(R.id.btnStatus);
        }
    }
}




