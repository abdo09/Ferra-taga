package com.ferra.taga.Fragments.Tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.ferra.taga.Base.BaseMainFragment;
import com.ferra.taga.R;
import com.ferra.taga.databinding.FragmentMainToolsBinding;

public class FragmentMainTools extends BaseMainFragment {

    private FragmentMainToolsBinding binding;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_tools, container, false);

        return binding.getRoot();
    }
}
