package com.ferra.taga.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ferra.taga.Commn.Common;
import com.ferra.taga.Model.FoodListsModel;

import java.util.ArrayList;

public class FoodListsViewModel extends ViewModel {

    public FoodListsViewModel() {
    }


    private MutableLiveData<ArrayList<FoodListsModel>> mutableLiveDataFoodList;

    public MutableLiveData<ArrayList<FoodListsModel>> getMutableLiveDataFoodModelList() {
        if (mutableLiveDataFoodList == null ){
            mutableLiveDataFoodList = new MutableLiveData<>();
            mutableLiveDataFoodList.setValue(Common.categorySelected.getFoodList());
        }
        return mutableLiveDataFoodList;
    }
}
