package com.ferra.taga.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ferra.taga.Callback.ICategoryCallbackListener;
import com.ferra.taga.Commn.Common;
import com.ferra.taga.Model.CategoryModel;
import com.ferra.taga.Model.FoodListsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuViewModel extends ViewModel implements ICategoryCallbackListener {
    public ArrayList<FoodListsModel> foodListsModel;
    private MutableLiveData<String> messageError = new MutableLiveData<>();
    private ICategoryCallbackListener categoryCallbackListener;
    private MutableLiveData<ArrayList<CategoryModel>> categoryList;

    public MenuViewModel() {
        categoryCallbackListener = this;
    }

    public MutableLiveData<ArrayList<CategoryModel>> getCategoryList() {
        if (categoryList == null){
            categoryList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadCategory();
        }
        return categoryList;
    }

    private void loadCategory() {
        ArrayList<CategoryModel> tempList = new ArrayList<>();
        DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference(Common.CATEGORY_REFERENCE);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                    CategoryModel model = itemSnapShot.getValue(CategoryModel.class);
                    assert model != null;
                    model.setMenu_id(itemSnapShot.getKey());
                    foodListsModel = model.getFoodList();
                    tempList.add(model);
                }
                categoryCallbackListener.onCategoryCallbackSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                categoryCallbackListener.onCategoryCallbackFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onCategoryCallbackSuccess(ArrayList<CategoryModel> categoryModelList) {
        categoryList.setValue(categoryModelList);
    }

    @Override
    public void onCategoryCallbackFailed(String message) {
        messageError.setValue(message);
    }
}
