package com.ferra.taga.EventBus;

import com.ferra.taga.Model.CategoryModel;

public class CategoryClick {
    private Boolean success ;
    private CategoryModel categoryModel ;

    public CategoryClick(Boolean success, CategoryModel categoryModel) {
        this.success = success;
        this.categoryModel = categoryModel;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
