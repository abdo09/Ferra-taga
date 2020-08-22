package com.ferra.taga.Base;

import android.content.Context;

import com.ferra.taga.Fragments.Home.FragmentHomeMain;
import com.ferra.taga.HomeActivity;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseMainFragment extends SupportFragment {
    private OnBackToFirstListener _mBackToFirstListener;

    public void setActivityTitle(int title) {
        if (getActivity() instanceof HomeActivity)
            getActivity().setTitle(title);
//        if (getActivity() instanceof NewRequestActivity)
//            getActivity().setTitle(title);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (getChildFragmentManager().getBackStackEntryCount() == 0)
            if (getActivity() instanceof HomeActivity)
                if (((HomeActivity) getActivity()).getSupportActionBar() != null)
                    ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (this instanceof FragmentHomeMain) {
                _mActivity.finish();
            } else {
                if (_mBackToFirstListener != null)
                    _mBackToFirstListener.onBackToFirstFragment();
                else {
                    _mActivity.finish();
                }
            }
        }
        return true;
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}
