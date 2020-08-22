package com.ferra.taga;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.ferra.taga.Activity.Profile;
import com.ferra.taga.Base.BaseMainFragment;
import com.ferra.taga.Fragments.Home.FragmentHomeMain;
import com.ferra.taga.Fragments.Menu.MenuContainerFragment;
import com.ferra.taga.Fragments.Tools.ToolsContainerFragment;
import com.ferra.taga.Fragments.Tools.FragmentMainTools;
import com.ferra.taga.Fragments.Home.HomeContainerFragment;
import com.ferra.taga.Fragments.Menu.FragmentMainMenu;

import com.ferra.taga.databinding.ActivityHomeBinding;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class HomeActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener, View.OnClickListener {

    private ActivityHomeBinding binding;

    final int FIRST = 0;
    final int SECOND = 1;
    final int THIRD = 2;
    final int FOURTH = 3;

    public SupportFragment[] mFragments = new SupportFragment[6];

    @Override
    public void setTitle(CharSequence title) {
        binding.toolbarLayout.title.setText(title);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"RestrictedApi", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.toolbarLayout.profileLayout.setOnClickListener(this);
        binding.toolbarLayout.adminFirstName.setText(R.string.profile);
        binding.toolbarLayout.adminImage.setImageResource(R.drawable.globe_icon);

        setSupportActionBar(binding.toolbarLayout.toolbar);
        Global.setBottomNavigationBarColorColor(HomeActivity.this, R.color.backgroundColor);

        SupportFragment firstFragment = findFragment(HomeContainerFragment.class);

        binding.bottomBar.setItems(R.xml.user_bottombar);
        binding.fab.setVisibility(View.GONE);


        mFragments[FIRST] = firstFragment;
        mFragments[SECOND] = new MenuContainerFragment();
        mFragments[THIRD] = new ToolsContainerFragment();


        mFragments[FIRST] = new HomeContainerFragment();

        loadMultipleRootFragment(R.id.main_content_frame, FIRST,
                mFragments[FIRST],
                mFragments[SECOND],
                mFragments[THIRD]);

        binding.bottomBar.selectTabAtPosition(FIRST);

        binding.bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.navigation_home:
                    showHideFragment(mFragments[FIRST], mFragments[binding.bottomBar.getCurrentTabPosition()]);
                    break;
                case R.id.navigation_menu:
                    showHideFragment(mFragments[SECOND], mFragments[binding.bottomBar.getCurrentTabPosition()]);
                    break;
                case R.id.navigation_tools:
                    showHideFragment(mFragments[THIRD], mFragments[binding.bottomBar.getCurrentTabPosition()]);
                    break;
            }
        });

        binding.bottomBar.setOnTabReselectListener(tabId -> {
            final SupportFragment currentFragment = mFragments[binding.bottomBar.getCurrentTabPosition()];
            if (currentFragment != null) {
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                if (count > 1) {
                    if (currentFragment instanceof HomeContainerFragment) {
                        currentFragment.popToChild(FragmentHomeMain.class, false);
                    } else if (currentFragment instanceof MenuContainerFragment) {
                        currentFragment.popToChild(FragmentMainMenu.class, false);
                    } else if (currentFragment instanceof ToolsContainerFragment) {
                        currentFragment.popToChild(FragmentMainTools.class, false);
                    }
                }
            }
        });
    }

    @Override
    public void onBackToFirstFragment() {
        binding.bottomBar.selectTabAtPosition(0);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.toolbarLayout.profileLayout.getId()) {
            startActivity(new Intent(HomeActivity.this, Profile.class));
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}