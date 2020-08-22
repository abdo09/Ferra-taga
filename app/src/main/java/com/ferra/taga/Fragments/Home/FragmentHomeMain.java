package com.ferra.taga.Fragments.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ferra.taga.Adapters.MyBestDealsAdapter;
import com.ferra.taga.Adapters.MyPopularCategoryAdapter;
import com.ferra.taga.Base.BaseMainFragment;
import com.ferra.taga.Model.PopularCategoryModel;
import com.ferra.taga.R;
import com.ferra.taga.ViewModel.HomeViewModel;
import com.ferra.taga.databinding.FragmentHomeMainBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentHomeMain extends BaseMainFragment {

    private FragmentHomeMainBinding binding;
    private HomeViewModel homeViewModel;
    private Unbinder unbinder;
    private LayoutAnimationController animationController ;


    @BindView(R.id.rec_popular)
    RecyclerView recPopular;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_main, container, false);
        init();
        unbinder = ButterKnife.bind(this, binding.getRoot());
        homeViewModel.getPopularCategoryList().observe(FragmentHomeMain.this, (ArrayList<PopularCategoryModel> popularCategoryModels) -> {
            MyPopularCategoryAdapter adapter = new MyPopularCategoryAdapter( getContext(), popularCategoryModels);
            binding.recPopular.setAdapter(adapter);
            binding.recPopular.setLayoutAnimation(animationController);
        });

        homeViewModel.getBestDealsList().observe(FragmentHomeMain.this, bestDealsModels -> {
            MyBestDealsAdapter adapter = new MyBestDealsAdapter(getContext(), bestDealsModels,true);
            binding.viewPagerHomeFragment.setAdapter(adapter);
        });
        return binding.getRoot();
    }

    private void init() {
        animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_from_left);
        binding.recPopular.setHasFixedSize(true);
        binding.recPopular.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.viewPagerHomeFragment.resumeAutoScroll();
    }

    @Override
    public void onPause() {
        binding.viewPagerHomeFragment.pauseAutoScroll();
        super.onPause();
    }
}