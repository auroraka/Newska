
package com.ihandy.a2014011310.support.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ihandy.a2014011310.database.cache.BaseCollectionCache;
import com.ihandy.a2014011310.database.cache.ICache;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;

import java.util.List;


public abstract class BaseListAdapter<M,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    protected List<M> mItems;
    protected Context mContext;
    protected ICache<M> mCache;

    protected boolean isCollection = false;


    public BaseListAdapter(Context context, ICache<M> cache) {
        mContext = context;
        mCache = cache;
        mItems = cache.getmList();

        if(cache instanceof BaseCollectionCache){
            isCollection = true;
        }

        HttpUtil.readNetworkState();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    protected M getItem(int position){
        return mItems.get(position);
    }


}
