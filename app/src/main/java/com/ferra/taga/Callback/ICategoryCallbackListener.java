package com.ferra.taga.Callback;

import com.ferra.taga.Model.BestDealsModel;
import com.ferra.taga.Model.CategoryModel;

import java.util.ArrayList;

public interface ICategoryCallbackListener {
    void onCategoryCallbackSuccess(ArrayList<CategoryModel> categoryModelList);
    void onCategoryCallbackFailed(String message);
}
