package com.onlineeducationsyestem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.tommykw.tagview.DataTransform;
import com.github.tommykw.tagview.TagView;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.interfaces.OnSubItemClick;
import com.onlineeducationsyestem.model.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
        implements OnSubItemClick {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnSubItemClick onItemClick;
    private Context context;

    public CategoryAdapter(Context context,
                       ArrayList<String> listProduct, OnSubItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_category_tag, parent, false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, int position) {
        final String data = listProduct.get(position);

       /* ArrayList<Tag> list = new ArrayList<>();
        Tag t1 =new Tag("It certification");
        list.add(t1);

        Tag t2 =new Tag("Netword and security");
        list.add(t2);

        Tag t3 =new Tag("Hardware");
        list.add(t3);



        holder.tagCategory.addTags(list);*/

        List<Item> list = new ArrayList<>();
        Item i= new Item(1, "It certification");
        list.add(i);
        Item i1= new Item(2, "Netword and security");
        list.add(i1);
        Item i11= new Item(3, "Hardware");
        list.add(i11);
        Item i111= new Item(4, "Operating system");
        list.add(i111);
        Item i1111= new Item(5, "Other");
        list.add(i1111);

        holder.tagCategory.setTags(list, new DataTransform<Item>() {
            @NotNull
            @Override
            public String transfer(Item item) {
                return item.getName();
            }
        });

        holder.tagCategory.setClickListener(new TagView.TagClickListener<Item>() {
            @Override
            public void onTagClick(Item item) {
                item.getId();
            }
        });

    }

    @Override
    public void onSubGridClick(int pos) {

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCategory,tvViewAll;
        TagView<Item> tagCategory ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tvNameCategory);
            tvViewAll = itemView.findViewById(R.id.tvViewAll);
            tagCategory =itemView.findViewById(R.id.tagCategory);

        }
    }
}

