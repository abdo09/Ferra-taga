package com.ferra.taga.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodListsModel  {
    public String key;
    private String name, image, id, description ;
    private Long price ;
    private ArrayList<AddonModel> addonModelList ;
    private ArrayList<ModelSize> modelSizeList ;
    private Double ratingValue ;
    private Long ratingCount ;

    public FoodListsModel() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ArrayList<AddonModel> getAddonModelList() {
        return addonModelList;
    }

    public void setAddonModelList(ArrayList<AddonModel> addonModelList) {
        this.addonModelList = addonModelList;
    }

    public ArrayList<ModelSize> getModelSizeList() {
        return modelSizeList;
    }

    public void setModelSizeList(ArrayList<ModelSize> modelSizeList) {
        this.modelSizeList = modelSizeList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }
}
