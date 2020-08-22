package com.ferra.taga.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ferra.taga.Callback.IRecyclerClickListener;
import com.ferra.taga.Commn.Common;
import com.ferra.taga.EventBus.CategoryClick;
import com.ferra.taga.Model.CategoryModel;
import com.ferra.taga.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.CategoryAdapterHolder> {
    ArrayList<CategoryModel> list;
    Context context;

    public MyCategoryAdapter(Context context, ArrayList<CategoryModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false);
        return new CategoryAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.img_menu);
        holder.tv_menu.setText(list.get(position).getName());
        holder.setListener((view, pos) -> {
            Common.categorySelected = list.get(pos);
            EventBus.getDefault().postSticky(new CategoryClick(true, list.get(pos)));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;
        @BindView(R.id.tv_menu)
        TextView tv_menu;
        @BindView(R.id.img_menu)
        ImageView img_menu;
        IRecyclerClickListener listener ;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public CategoryAdapterHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClickListener(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 1) {
            return Common.DEFAULT_COLUMN_COUNT;
        } else {
            if (list.size() % 2 == 0) {
                return Common.DEFAULT_COLUMN_COUNT;
            } else {
                return (position > 1 && position == list.size() - 1) ? Common.FULL_WIDTH_COLUMN : Common.DEFAULT_COLUMN_COUNT;
            }
        }
    }
}