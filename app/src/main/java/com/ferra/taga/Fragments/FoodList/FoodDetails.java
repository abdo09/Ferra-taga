package com.ferra.taga.Fragments.FoodList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.ferra.taga.Base.BaseBackFragment;
import com.ferra.taga.Commn.Common;
import com.ferra.taga.Global;
import com.ferra.taga.Model.CommentModel;
import com.ferra.taga.Model.FoodListsModel;
import com.ferra.taga.R;
import com.ferra.taga.ViewModel.FoodDetailsViewModel;
import com.ferra.taga.databinding.FoodDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class FoodDetails extends BaseBackFragment implements View.OnClickListener {

    private FoodDetailsBinding binding;
    private FoodDetailsViewModel foodDetailsViewModel;
    private android.app.AlertDialog waitingDialog;
    private FirebaseAuth mAuth;
    private String uID;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        foodDetailsViewModel = ViewModelProviders.of(this).get(FoodDetailsViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.food_details, container, false);
        init();

        foodDetailsViewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), this::displayInfo);
        /*foodDetailsViewModel.getMutableLiveDataComment().observe(this, this::submitToFirebase);*/

        return binding.getRoot();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        uID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        binding.fabRating.setOnClickListener(this);
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
    }

    private void submitToFirebase(CommentModel commentModel) {
        waitingDialog.show();
        FirebaseDatabase.getInstance()
                .getReference(Common.commentRef)
                .child(Common.selectedFood.getId())
                .push()
                .setValue(commentModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        addRatingToFood(commentModel.getRatingBarValue());
                    }
                    waitingDialog.dismiss();
                });

    }

    private void addRatingToFood(Float ratingValue) {
        FirebaseDatabase.getInstance()
                .getReference(Common.categoryRef)
                .child(Common.categorySelected.getMenu_id())
                .child("foods")
                .child(Common.selectedFood.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            FoodListsModel model = dataSnapshot.getValue(FoodListsModel.class);
                            assert model != null;
                            model.setKey(Common.selectedFood.getKey());

                            if (model.getRatingValue() == null)
                                model.setRatingValue(0d);
                            if (model.getRatingCount() == null)
                                model.setRatingCount(0L);

                            double sumRating = model.getRatingValue() + ratingValue;
                            long ratingCount = model.getRatingCount() + 1;
                            double result = sumRating / ratingCount;

                            HashMap<String, Object> updateRating = new HashMap<>();
                            updateRating.put("RatingValue", result);
                            updateRating.put("RatingCount", ratingCount);

                            model.setRatingCount(ratingCount);
                            model.setRatingValue(result);

                            dataSnapshot.getRef()
                                    .updateChildren(updateRating)
                                    .addOnCompleteListener(task -> {
                                        waitingDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Global.successMessage(_mActivity, "Thank you!");
                                            Common.selectedFood = model;
                                            foodDetailsViewModel.setFoodModel(model);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        waitingDialog.dismiss();
                        Global.errorMessage(_mActivity, databaseError.getMessage());
                    }
                });

    }

    private void displayInfo(FoodListsModel foodListsModel) {
        Picasso.get().load(foodListsModel.getImage()).into(binding.detailsImgFood);
        binding.tvDetailsFoodName.setText(new StringBuilder(foodListsModel.getName()));
        binding.foodDescription.setText(new StringBuilder(foodListsModel.getDescription()));
        binding.tvPrice.setText(new StringBuilder(String.valueOf(foodListsModel.getPrice())));

        if (foodListsModel.getRatingValue() != null)
            binding.ratingBar.setRating(foodListsModel.getRatingValue().floatValue());
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == binding.fabRating.getId()) {
            showRatingDialog();
        }
    }
    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Food Rating");
        builder.setMessage("Please fill information");

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_rating, null);
        RatingBar ratingBar = view.findViewById(R.id.rating_bar);
        EditText editText = view.findViewById(R.id.ed_comment);

        builder.setView(view);

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setPositiveButton("Submit", (dialog, which) -> {
            CommentModel model = new CommentModel();
            model.setUid(uID);
            model.setName(Common.currentUser.getName());
            model.setComment(editText.getText().toString());
            model.setRatingBarValue(ratingBar.getRating());
            Map<String, Object> serverTimeStamp = new HashMap<>();
            serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
            model.setCommentTimeStamp(serverTimeStamp);

            foodDetailsViewModel.setCommentModel(model);

        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}