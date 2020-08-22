package com.ferra.taga.EventBus;

import com.ferra.taga.Model.FoodListsModel;

public class FoodItemClick {
    private Boolean success ;
    private FoodListsModel foodListsModel ;

    public FoodItemClick(Boolean success, FoodListsModel foodListsModel) {
        this.success = success;
        this.foodListsModel = foodListsModel;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public FoodListsModel getFoodListsModel() {
        return foodListsModel;
    }

    public void setFoodListsModel(FoodListsModel foodListsModel) {
        this.foodListsModel = foodListsModel;
    }
}
