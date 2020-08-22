package com.ferra.taga.Fragments.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ferra.taga.Adapters.MyCategoryAdapter;
import com.ferra.taga.Base.BaseMainFragment;;
import com.ferra.taga.Commn.Common;
import com.ferra.taga.Commn.SpaceItemDecoration;
import com.ferra.taga.EventBus.CategoryClick;
import com.ferra.taga.EventBus.FoodItemClick;
import com.ferra.taga.Fragments.FoodList.FoodDetails;
import com.ferra.taga.Fragments.FoodList.FoodList;
import com.ferra.taga.Global;
import com.ferra.taga.R;
import com.ferra.taga.ViewModel.MenuViewModel;
import com.ferra.taga.databinding.FragmentMainMenuBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentMainMenu extends BaseMainFragment {

    private FragmentMainMenuBinding binding;
    private MenuViewModel menuViewModel;
    private LayoutAnimationController animationController ;
    private MyCategoryAdapter adapter;
    private AlertDialog dialog ;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false);

        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        initViews();
        menuViewModel.getMessageError().observe(FragmentMainMenu.this, s -> {
            Global.errorMessage(_mActivity, Integer.valueOf(s));
        });
        menuViewModel.getCategoryList().observe(FragmentMainMenu.this, categoryModels -> {
            adapter = new MyCategoryAdapter(getContext(), categoryModels);
            binding.recMenu.setAdapter(adapter);
            binding.recMenu.setLayoutAnimation(animationController);
        });

        return binding.getRoot();
    }

    private void initViews() {
        animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_from_left);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2 );
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null){
                    switch (adapter.getItemViewType(position)){
                        case Common.DEFAULT_COLUMN_COUNT: return 1;
                        case Common.FULL_WIDTH_COLUMN: return 2;
                        default:return -1;
                    }
                }
                return -1;
            }
        });
        binding.recMenu.setLayoutManager(layoutManager);
        binding.recMenu.addItemDecoration(new SpaceItemDecoration(8));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCategorySelected(CategoryClick event) {
        if (event.isSuccess()) {
            FoodList foodLists = new FoodList();
            Bundle b = new Bundle();
            b.putString("menu id", event.getCategoryModel().getMenu_id());
            b.putString("title", event.getCategoryModel().getName());
            foodLists.setArguments(b);
            start(foodLists);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFoodSelected(FoodItemClick event) {
        if (event.isSuccess()) {
            start(new FoodDetails());
        }
    }
}
