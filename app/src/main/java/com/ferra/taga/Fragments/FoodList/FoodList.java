package com.ferra.taga.Fragments.FoodList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ferra.taga.Adapters.MyFoodListAdapter;
import com.ferra.taga.Base.BaseBackFragment;
import com.ferra.taga.EventBus.FoodItemClick;
import com.ferra.taga.Model.FoodListsModel;
import com.ferra.taga.R;
import com.ferra.taga.databinding.FragmentFoodListsMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class FoodList extends BaseBackFragment{
    private FragmentFoodListsMainBinding binding;
    private MyFoodListAdapter adapter;
    private LayoutAnimationController animationController;
    private String menu_id, title;
    private DatabaseReference foodListRef;
    private FoodListsModel model;
    private ArrayList<FoodListsModel> foodLists;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_lists_main, container, false);
        recyclerInit();
        return binding.getRoot();
    }

    private void recyclerInit() {
        model = new FoodListsModel();
        foodLists = new ArrayList<>();
        binding.recFoodLists.setHasFixedSize(true);
        binding.recFoodLists.setLayoutManager(new LinearLayoutManager(getContext()));
        animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_from_left);
        binding.recFoodLists.setLayoutAnimation(animationController);
    }

    private void initViews() {
        assert getArguments() != null;
        menu_id = getArguments().getString("menu id");
        title = getArguments().getString("title");
        foodListRef = FirebaseDatabase.getInstance().getReference().child("Category");
        foodListRef.child(menu_id).child("foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dSp : dataSnapshot.getChildren()) {
                        model = dSp.getValue(FoodListsModel.class);
                        assert model != null;
                        foodLists.add(model);
                    }
                }
                adapter = new MyFoodListAdapter(foodLists, getContext());
                binding.recFoodLists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
