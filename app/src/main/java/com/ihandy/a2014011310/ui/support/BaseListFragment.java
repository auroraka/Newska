

package com.ihandy.a2014011310.ui.support;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ihandy.a2014011310.NewskaApplication;
import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.database.cache.ICache;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;
import com.yalantis.phoenix.PullToRefreshView;


public abstract class BaseListFragment extends Fragment{

    protected View parentView;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PullToRefreshView refreshView;

    protected ImageView placeHolder;
    protected ProgressBar progressBar;

    protected RecyclerView.Adapter adapter;
    protected ICache cache;

    protected int mLayout = 0;


    protected boolean withHeaderTab = true;
    protected boolean withRefreshView = true;
    protected boolean needCache = true;


    protected abstract void onCreateCache();

    protected abstract RecyclerView.Adapter bindAdapter();

    protected abstract void loadFromNet();
    protected abstract void loadFromCache();
    protected abstract boolean hasData();
    protected abstract void getArgs();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayout();
        needCache = setCache();
        getArgs();
        parentView = inflater.inflate(mLayout, container, false);
        withHeaderTab = setHeaderTab();
        withRefreshView = setRefreshView();

        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        placeHolder = (ImageView) parentView.findViewById(R.id.placeholder);

        onCreateCache();

        adapter = bindAdapter();


        mLayoutManager = new LinearLayoutManager(NewskaApplication.AppContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);


        View view = getActivity().findViewById(R.id.tab_layout);
        if(withHeaderTab){
             view.setVisibility(View.VISIBLE);
        }else{
            if(view !=null) {
                view.setVisibility(View.GONE);
            }
        }

        if(withRefreshView){
            refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadFromNet();
                }
            });

            placeHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeHolder.setVisibility(View.GONE);
                    loadFromNet();
                }
            });
        }else{
            refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                        refreshView.setRefreshing(false);
                }
            });
        }

        HttpUtil.readNetworkState();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadFromCache();
            }
        }).start();

        return parentView;
    }



    protected boolean setHeaderTab(){
        return true;
    }
    protected boolean setRefreshView(){
        return true;
    }
    protected boolean setCache(){
        return true;
    }
    protected void setLayout(){
        mLayout = R.layout.layout_common_list;
    }

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    if(isAdded()) {
                        Utils.DLog(getString(R.string.Text_Net_Exception));
                    }
                    break;
                case CONSTANT.ID_SUCCESS:
                    if(isAdded()){
                        Utils.DLog(getString(R.string.text_refresh_success));
                    }
                    if(needCache){
                        cache.cache();
                    }
                    break;
                case CONSTANT.ID_FROM_CACHE:
                    if(withRefreshView && hasData() == false){
                        loadFromNet();
                        return false;
                    }else if(Settings.isAutoRefresh && HttpUtil.isWIFI){
                       // progressBar.setVisibility(View.GONE);
                        loadFromNet();
                        return false;
                    }
                    break;
            }
            if(withRefreshView){
                refreshView.setRefreshing(false);
            }

            if(hasData()){
                placeHolder.setVisibility(View.GONE);
            }else{
                placeHolder.setVisibility(View.VISIBLE);
            }

            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            return false;
        }
    });




}
