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
import com.ferra.taga.EventBus.FoodItemClick;
import com.ferra.taga.Model.FoodListsModel;
import com.ferra.taga.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFoodListAdapter extends RecyclerView.Adapter<MyFoodListAdapter.FoodListHolder> {
    private ArrayList<FoodListsModel> list;
    private Context context;

    public MyFoodListAdapter(ArrayList<FoodListsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_food_lists, parent, false);
        return new FoodListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListHolder holder, int position) {
        Picasso.get().load(list.get(position).getImage()).into(holder.img_food);
        holder.tv_food_name.setText(new StringBuilder("").append(list.get(position).getName()));
        holder.tv_price.setText(new StringBuilder("$").append(list.get(position).getPrice()));
        holder.clickListener((view, pos) -> {
            Common.selectedFood = list.get(pos);
            Common.selectedFood.setKey(String.valueOf(pos));
            EventBus.getDefault().postSticky(new FoodItemClick(true, list.get(pos)));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FoodListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder ;
        @BindView(R.id.tv_food_name)
        TextView tv_food_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.img_food)
        ImageView img_food;
        @BindView(R.id.img_fav)
        ImageView img_fav;
        @BindView(R.id.img_add_to_cart)
        ImageView img_add_to_cart;
        IRecyclerClickListener listener;
        public void clickListener(IRecyclerClickListener listener){
            this.listener = listener ;
        }
        public FoodListHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v, getAdapterPosition());
        }
    }
}