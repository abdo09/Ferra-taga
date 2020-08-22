package com.ferra.taga.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ferra.taga.HomeActivity;
import com.ferra.taga.R;
import com.ferra.taga.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.realm.Realm;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setSupportActionBar(binding.toolbarLayout.toolbar);

        mAuth = FirebaseAuth.getInstance();

        binding.toolbarLayout.title.setOnClickListener(this);
        binding.toolbarLayout.title.setText(R.string.signOut);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.toolbarLayout.title.getId()){

            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(Profile.this, EnterMobileActivity.class));
            finish();
        }
    }
}