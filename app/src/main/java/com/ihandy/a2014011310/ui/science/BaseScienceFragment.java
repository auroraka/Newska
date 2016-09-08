
package com.ihandy.a2014011310.ui.science;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.api.ScienceApi;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.support.adapter.PagerAdapter;
import com.ihandy.a2014011310.ui.support.AbsTopNavigationFragment;

import java.lang.reflect.Field;


public class BaseScienceFragment extends AbsTopNavigationFragment {
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), ScienceApi.getChannel_title()) {
            @Override
            public Fragment getItem(int position) {
                ScienceFragment fragment = new ScienceFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                bundle.putSerializable(getString(R.string.id_category),ScienceApi.getChannel_tag()[position]);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getChildFragmentManager().getFragments()!=null){
            getChildFragmentManager().getFragments().clear();
        }
    }
}
