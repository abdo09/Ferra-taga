package com.ferra.taga.Callback;

import com.ferra.taga.Model.PopularCategoryModel;

import java.util.ArrayList;

public interface IPopularCategoryCallbackListener {
    void onPopularCategoryCallbackSuccess(ArrayList<PopularCategoryModel> popularCategoryModels);
    void onPopularCategoryCallbackFailed(String message);
}
