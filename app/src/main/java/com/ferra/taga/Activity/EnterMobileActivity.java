package com.ferra.taga.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ferra.taga.Global;
import com.ferra.taga.HomeActivity;
import com.ferra.taga.R;
import com.ferra.taga.databinding.ActivityEnterMobileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ferra.taga.Commn.Common.currentUser;

public class EnterMobileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEnterMobileBinding binding;

    private String verificationsID;

    private FirebaseAuth auth, mAuth;
    private DatabaseReference usersRef, userCheckRef;
    private FirebaseUser currentUser;
    private String currentUserId, checkCurrentUserId;
    private String phone = "+";

    @Override
    protected void onStart() {
        super.onStart();
        userCheckRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        /*currentUser = auth.getCurrentUser();
        checkCurrentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        if (currentUser != null){
            checkUserExistence(checkCurrentUserId, userCheckRef);
        }*/
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_mobile);

        binding.btnVerify.setVisibility(View.INVISIBLE);
        binding.btnSend.setOnClickListener(this);
        binding.btnVerify.setOnClickListener(this);

        Global.setBottomNavigationBarColorColor(EnterMobileActivity.this, R.color.colorAccent);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationsID = s;
            binding.progress.setVisibility(View.INVISIBLE);
            Global.warningMessage(EnterMobileActivity.this, R.string.code_sent);
            binding.btnVerify.setVisibility(View.VISIBLE);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                binding.progress.setVisibility(View.INVISIBLE);
            }
            verifyCode(code);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            binding.progress.setVisibility(View.INVISIBLE);
            Global.errorMessage(EnterMobileActivity.this, Integer.valueOf(e.getMessage()));
        }
    };

    private void sendVerificationCode(String phoneNumber) {
        binding.progress.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,
                60, TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD, callbacks);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationsID, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                init();
                checkUserExistence();
            } else {
                Global.errorMessage(EnterMobileActivity.this, Integer.valueOf(task.getException().getMessage()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnSend.getId()) {
            if (!binding.edEnterYourPhone.getText().toString().isEmpty() || binding.edEnterYourPhone.getText().toString().length() > 11) {
                phone += binding.edEnterYourPhone.getText().toString();
                Log.i("TAG", "onClick: " + phone);
                sendVerificationCode(phone);
                Global.toggleKeyboard(this);
            } else {
                Global.errorMessage(EnterMobileActivity.this, R.string.number_empty_or_not_correct);
            }
        }
        if (v.getId() == binding.btnVerify.getId()) {
            String code = binding.edEnterVerificationCode.getText().toString().trim();
            if (code.isEmpty() || code.length() < 6) {
                Global.errorMessage(EnterMobileActivity.this, R.string.code_empty_or_not_correct);
            }
            verifyCode(code);
        }
    }

    private void checkUserExistence() {
        usersRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    startActivity(new Intent(EnterMobileActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(EnterMobileActivity.this, UserDetailsActivity.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkUserExistence(String currentUser, DatabaseReference userCheckRef) {
        userCheckRef.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    startActivity(new Intent(EnterMobileActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(EnterMobileActivity.this, UserDetailsActivity.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
            mAuth = FirebaseAuth.getInstance();
            currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            usersRef = FirebaseDatabase.getInstance().getReference("Users");
    }
}