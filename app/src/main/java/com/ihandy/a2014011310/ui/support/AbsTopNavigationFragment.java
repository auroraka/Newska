

package com.ihandy.a2014011310.ui.support;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.support.adapter.PagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;


public abstract class AbsTopNavigationFragment extends Fragment{
    protected View parentView;
    protected ViewPager viewPager;
    protected   PagerAdapter pagerAdapter;
    private SmartTabLayout smartTabLayout;
    protected abstract  PagerAdapter initPagerAdapter();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_top_navigation,null);
        viewPager = (ViewPager) parentView.findViewById(R.id.inner_viewpager);
        smartTabLayout = (SmartTabLayout) getActivity().findViewById(R.id.tab_layout);
        smartTabLayout.setVisibility(View.VISIBLE);
        pagerAdapter = initPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        smartTabLayout.setViewPager(viewPager);
        return parentView;
    }

}
