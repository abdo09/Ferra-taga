package com.ferra.taga.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.bumptech.glide.Glide;
import com.ferra.taga.Model.BestDealsModel;
import com.ferra.taga.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBestDealsAdapter extends LoopingPagerAdapter<BestDealsModel> {
    @BindView(R.id.tv_best_deal)
    TextView tv_best_deal_name ;
    @BindView(R.id.img_best_deal)
    ImageView img_best_deal_image ;
    Unbinder unbinder ;
    public MyBestDealsAdapter(Context context, ArrayList<BestDealsModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.layout_best_deals, container, false);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        unbinder = ButterKnife.bind(this, convertView);
        Glide.with(convertView).load(itemList.get(listPosition).getImage()).into(img_best_deal_image);
        tv_best_deal_name.setText(itemList.get(listPosition).getName());
    }
}
