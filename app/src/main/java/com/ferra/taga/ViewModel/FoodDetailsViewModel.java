package com.ferra.taga.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ferra.taga.Commn.Common;
import com.ferra.taga.Model.CommentModel;
import com.ferra.taga.Model.FoodListsModel;

public class FoodDetailsViewModel extends ViewModel {

    private MutableLiveData<FoodListsModel> modelMutableLiveDataFood;
    private MutableLiveData<CommentModel> mutableLiveDataComment;

    public void setCommentModel(CommentModel commentModel) {
        if (mutableLiveDataComment != null) {
            mutableLiveDataComment.setValue(commentModel);
        }
    }

    public MutableLiveData<CommentModel> getMutableLiveDataComment() {
        return mutableLiveDataComment;
    }

    public FoodDetailsViewModel() {
    }

    public MutableLiveData<FoodListsModel> getModelMutableLiveData() {
        if (modelMutableLiveDataFood == null) {
            modelMutableLiveDataFood = new MutableLiveData<>();
            modelMutableLiveDataFood.setValue(Common.selectedFood);
        }
        return modelMutableLiveDataFood;
    }

    public void setFoodModel(FoodListsModel model) {
        if (modelMutableLiveDataFood != null){
            modelMutableLiveDataFood.setValue(model);
        }
    }

}
