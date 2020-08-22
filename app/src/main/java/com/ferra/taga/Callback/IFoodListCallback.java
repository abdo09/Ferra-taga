package com.ferra.taga.Callback;

import com.ferra.taga.Model.FoodListsModel;

import java.util.ArrayList;

public interface IFoodListCallback {
    void onFoodListCallbackSuccess(ArrayList<FoodListsModel> bestDealsModels);
    void onFoodListCallbackFailed(String message);
}
