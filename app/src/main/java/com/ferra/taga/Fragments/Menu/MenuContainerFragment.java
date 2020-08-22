package com.ferra.taga.Fragments.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.ferra.taga.Base.BaseMainFragment;
import com.ferra.taga.R;

public class MenuContainerFragment extends BaseMainFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);

        return view;
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        if (findChildFragment(FragmentMainMenu.class) == null) {
            loadRootFragment(R.id.main_content_frame, new FragmentMainMenu());
        }
    }

    @Override
    public void onSupportVisible() {
        setActivityTitle(R.string.menu);
    }

    public void setActivityTitle(int title) {
        getActivity().setTitle(title);
    }
}
