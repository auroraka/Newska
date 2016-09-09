

package com.ihandy.a2014011310.ui.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.adapter.PagerAdapter;
import com.ihandy.a2014011310.ui.support.AbsTopNavigationFragment;

public class BaseCollectionFragment extends AbsTopNavigationFragment{
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        String [] tabTitles ={getContext().getString(R.string.science)};
        pagerAdapter = new PagerAdapter(getChildFragmentManager(),tabTitles) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new CollectionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }


    /**
     * destroy child fragments
     */
    @Override
    public void onDetach() {
        Log.w("aaa","BaseCollectionFragment onDetach");

        super.onDetach();
        if(getChildFragmentManager().getFragments()!=null){
            getChildFragmentManager().getFragments().clear();
        }
    }

}
