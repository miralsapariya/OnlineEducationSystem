package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.ApplyPromoCode;
import com.onlineeducationsyestem.interfaces.CheckOutInCart;
import com.onlineeducationsyestem.interfaces.DeleteItemInCart;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.CartList;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<CartList.ListData> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;
    private ApplyPromoCode applyPromoCode;
    private CheckOutInCart checkOutInCart;
    //float amount=0;
    public CartListAdapter(Context context,
                           ArrayList<CartList.ListData> listProduct,
                           OnItemClick onItemClick, DeleteItemInCart deleteItemInCart, ApplyPromoCode applyPromoCode, CheckOutInCart checkOutInCart) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.deleteItemInCart = deleteItemInCart;
        this.applyPromoCode = applyPromoCode;
        this.checkOutInCart = checkOutInCart;
    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_cart, parent, false);
        CartListAdapter.ViewHolder viewHolder = new CartListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartListAdapter.ViewHolder holder, final int position) {
        final CartList.ListData data = listProduct.get(position);

        AppUtils.loadImageWithPicasso(data.getImage(), holder.img, context, 0, 0);

        holder.tvCourseName.setText(data.getCourseName());
        holder.tvPrice.setText(data.getCoursePrice() + "");
        holder.tvDiscountPrice.setText(data.getCourseDiscount() + "");
        holder.tvInstructor.setText(data.getInstructorName());
        holder.tvOldPrice.setText(data.getCourseOldPrice() + "");
        holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemInCart.deleteCart(position);
            }
        });


        //free courses
        if(!data.getCoursePrice().contains("$")){
         data.amount=0.0f;
        }else {
            data.amount = Float.parseFloat(data.getCoursePrice().substring(1)) - data.getCourseDiscount();
            Log.d("========> ", data.amount+"");
        }
        if (data.getCourseDiscount() == 0) {
            holder.llPromo.setVisibility(View.VISIBLE);
        } else {
            holder.llPromo.setVisibility(View.INVISIBLE);
        }
        if (AppSharedPreference.getInstance().getString(context, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(context, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {

            holder.tvApply.setBackground(context.getDrawable(R.drawable.round_rect_right));
        } else {
            holder.tvApply.setBackground(context.getDrawable(R.drawable.round_rect_left));
        }

        holder.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(holder.tvPromoCode.getText().toString()))
                    applyPromoCode.promoApply(position, holder.tvPromoCode.getText().toString());

            }
        });


        holder.buyWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("amount : ", data.amount+" course:: "+data.getCourseName());
                checkOutInCart.doCheckout(data.getCartid() + "", data.amount + "",data.getCourseName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public CartList.ListData getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourseName, tvInstructor, tvPrice, tvOldPrice,
                tvDiscountPrice, buyWith, tvApply;
        private LinearLayout llMain, llPromo;
        private ImageView img, imgDelete;
        private EditText tvPromoCode;


        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvInstructor = itemView.findViewById(R.id.tvInstructor);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            buyWith = itemView.findViewById(R.id.buyWith);
            tvPromoCode = itemView.findViewById(R.id.tvPromoCode);
            tvApply = itemView.findViewById(R.id.tvApply);
            img = itemView.findViewById(R.id.img);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            llPromo = itemView.findViewById(R.id.llPromo);

        }
    }
}




