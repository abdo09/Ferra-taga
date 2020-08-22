package com.ferra.taga.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.ferra.taga.R;
import com.ferra.taga.databinding.ActivityNewsFeedViewBinding;

public class NewsFeedView extends AppCompatActivity {
    ActivityNewsFeedViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_feed_view);
    }
}
