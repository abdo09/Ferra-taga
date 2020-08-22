package com.ferra.taga.Base;

import android.annotation.SuppressLint;
import android.view.View;


import androidx.appcompat.widget.Toolbar;

import com.ferra.taga.R;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseBackFragment extends SupportFragment {

    @SuppressLint("NewApi")
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}