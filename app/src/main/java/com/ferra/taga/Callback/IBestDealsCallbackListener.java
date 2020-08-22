package com.ferra.taga.Callback;

import com.ferra.taga.Model.BestDealsModel;

import java.util.ArrayList;

public interface IBestDealsCallbackListener {
    void onBestDealsCallbackSuccess(ArrayList<BestDealsModel> bestDealsModels);
    void onBestDealsCallbackFailed(String message);
}
