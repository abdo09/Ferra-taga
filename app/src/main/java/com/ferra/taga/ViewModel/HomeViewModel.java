package com.ferra.taga.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ferra.taga.Callback.IBestDealsCallbackListener;
import com.ferra.taga.Callback.IPopularCategoryCallbackListener;
import com.ferra.taga.Commn.Common;
import com.ferra.taga.Model.BestDealsModel;
import com.ferra.taga.Model.PopularCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel implements IPopularCategoryCallbackListener, IBestDealsCallbackListener {

    private MutableLiveData<String> messageError;
    private IPopularCategoryCallbackListener popularCategoryCallbackListener;
    private MutableLiveData<ArrayList<PopularCategoryModel>> popularCategoryList;
    private IBestDealsCallbackListener bestDealsCallbackListener;
    private MutableLiveData<ArrayList<BestDealsModel>> bestDealsList;

    public MutableLiveData<ArrayList<PopularCategoryModel>> getPopularCategoryList() {
        if (popularCategoryList == null) {
            popularCategoryList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadPopularCategoryList();
        }
        return popularCategoryList;
    }

    public MutableLiveData<ArrayList<BestDealsModel>> getBestDealsList() {
        if (bestDealsList == null){
            bestDealsList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadBestDealList();
        }
        return bestDealsList;
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    public HomeViewModel() {
        popularCategoryCallbackListener = this;
        bestDealsCallbackListener = this;
    }

    @Override
    public void onPopularCategoryCallbackSuccess(ArrayList<PopularCategoryModel> popularCategoryModels) {
        popularCategoryList.setValue(popularCategoryModels);
    }

    @Override
    public void onPopularCategoryCallbackFailed(String message) {
        messageError.setValue(message);
    }

    @Override
    public void onBestDealsCallbackSuccess(ArrayList<BestDealsModel> bestDealsModels) {
        bestDealsList.setValue(bestDealsModels);
    }

    @Override
    public void onBestDealsCallbackFailed(String message) {
        messageError.setValue(message);
    }

    private void loadBestDealList() {
        ArrayList<BestDealsModel> tempList = new ArrayList<>();
        DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference(Common.BEST_DEALS_REFERENCE);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                    BestDealsModel model = itemSnapShot.getValue(BestDealsModel.class);
                    tempList.add(model);
                }
                bestDealsCallbackListener.onBestDealsCallbackSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                bestDealsCallbackListener.onBestDealsCallbackFailed(databaseError.getMessage());
            }
        });
    }

    private void loadPopularCategoryList() {
        ArrayList<PopularCategoryModel> tempList = new ArrayList<>();
        DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference(Common.POPULAR_CATEGORY_REFERENCE);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                    PopularCategoryModel model = itemSnapShot.getValue(PopularCategoryModel.class);
                    tempList.add(model);
                }
                popularCategoryCallbackListener.onPopularCategoryCallbackSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularCategoryCallbackListener.onPopularCategoryCallbackFailed(databaseError.getMessage());
            }
        });
    }

}