package com.ferra.taga.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ferra.taga.Global;
import com.ferra.taga.HomeActivity;
import com.ferra.taga.R;
import com.ferra.taga.databinding.ActivityUserDetaillsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUserDetaillsBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String currentUserId, ed_name, ed_email, ed_phone, ed_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detaills);
        init();
    }

    private void init() {
        binding.ActivityRegistrationRegisterButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.ActivityRegistrationRegisterButton.getId()) {
            ed_name = binding.ActivityRegistrationEditTextEnterName.getText().toString();
            ed_email = binding.ActivityRegistrationEditTextEnterEmail.getText().toString();
            ed_phone = binding.ActivityRegistrationEditTextEnterPhone.getText().toString();
            ed_address = binding.ActivityRegistrationEditTextEnterAddress.getText().toString();
            if (!ed_name.isEmpty() && !ed_email.isEmpty() && !ed_phone.isEmpty() && !ed_address.isEmpty()) {
                Map<String, Object> userModel = new HashMap<>();
                userModel.put("Name", ed_name);
                userModel.put("Email", ed_email);
                userModel.put("Phone", ed_phone);
                userModel.put("Address", ed_address);
                usersRef.child(currentUserId).setValue(userModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(UserDetailsActivity.this, HomeActivity.class));
                    }
                });
            } else if (ed_name.isEmpty() && ed_email.isEmpty() && ed_phone.isEmpty() && ed_address.isEmpty()) {
                Global.errorMessage(this, "Please fill all the fields");
            } else if (!ed_name.isEmpty() && !ed_email.isEmpty() && !ed_phone.isEmpty()) {
                Global.errorMessage(this, "Please enter your address");
            }
        } else if (!ed_name.isEmpty() && !ed_email.isEmpty()) {
            Global.errorMessage(this, "Please enter your phone");
        } else if (!ed_name.isEmpty()) {
            Global.errorMessage(this, "Please enter your email");
        } else {
            Global.errorMessage(this, "Please enter your Name");
        }
    }
}
