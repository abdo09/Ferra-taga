package com.ferra.taga.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryModel implements Serializable {
    private String menu_id, name, image;
    private ArrayList<FoodListsModel> foodList ;

    public CategoryModel() {}

    public CategoryModel(String menu_id, String name, String image, ArrayList<FoodListsModel> foodList) {
        this.menu_id = menu_id;
        this.name = name;
        this.image = image;
        this.foodList = foodList;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<FoodListsModel> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<FoodListsModel> foodList) {
        this.foodList = foodList;
    }

    private static final long serialVersionUID = 1L;
}
