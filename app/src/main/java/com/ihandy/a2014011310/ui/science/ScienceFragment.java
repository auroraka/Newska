
package com.ihandy.a2014011310.ui.science;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.api.ScienceApi;
import com.ihandy.a2014011310.database.cache.cache.ScienceCache;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.support.adapter.ScienceAdapter;
import com.ihandy.a2014011310.ui.support.BaseListFragment;




public class ScienceFragment extends BaseListFragment{

    private String mCategory;
    private String mUrl;

    @Override
    protected void onCreateCache() {
        cache = new ScienceCache(handler,mCategory,mUrl);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new ScienceAdapter(getContext(),cache);
    }

    @Override
    protected void loadFromNet() {
        cache.load();
    }

    @Override
    protected void loadFromCache() {
        cache.loadFromCache();
    }

    @Override
    protected boolean hasData() {
        return cache.hasData();
    }

    @Override
    protected void getArgs() {
        mUrl = ScienceApi.science_channel_url+ScienceApi.channel_tag[getArguments().getInt(getString(R.string.id_pos))];
        mCategory = getArguments().getString(getString(R.string.id_category));
    }
}
