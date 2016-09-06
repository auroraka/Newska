/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Created by mummyding on 15-11-17.
 */
public class BaseScienceFragment extends AbsTopNavigationFragment {
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), ScienceApi.channel_title) {
            @Override
            public Fragment getItem(int position) {
                ScienceFragment fragment = new ScienceFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                bundle.putSerializable(getString(R.string.id_category),ScienceApi.channel_tag[position]);
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
