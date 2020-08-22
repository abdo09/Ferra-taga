package com.ferra.taga.Activity;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ferra.taga.R;
import com.ferra.taga.databinding.ActivityEnterPasswordBinding;


public class EnterPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEnterPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_password);
    }

    @Override
    public void onClick(View v) {
    }
}
