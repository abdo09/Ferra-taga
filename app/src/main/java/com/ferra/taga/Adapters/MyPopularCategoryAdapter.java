package com.ferra.taga.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ferra.taga.Model.PopularCategoryModel;
import com.ferra.taga.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPopularCategoryAdapter extends RecyclerView.Adapter<MyPopularCategoryAdapter.MyPopularCategoryHolder> {
    ArrayList<PopularCategoryModel> list;
    Context context;

    public MyPopularCategoryAdapter(Context context, ArrayList<PopularCategoryModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPopularCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popular_categroies, parent, false);
        return new MyPopularCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPopularCategoryHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.img_category);
        holder.tv_category_name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyPopularCategoryHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder ;

        @BindView(R.id.tv_popular)
        TextView  tv_category_name ;
        @BindView(R.id.img_pop_category)
        CircleImageView img_category ;
        public MyPopularCategoryHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}