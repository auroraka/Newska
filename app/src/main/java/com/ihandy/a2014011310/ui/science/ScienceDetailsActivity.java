package com.ihandy.a2014011310.ui.science;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.database.cache.cache.ScienceCache;
import com.ihandy.a2014011310.database.table.ScienceTable;
import com.ihandy.a2014011310.model.science.EngBean;
import com.ihandy.a2014011310.model.science.ScienceBean;
import com.ihandy.a2014011310.support.DisplayUtil;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.ui.support.BaseDetailsActivity;

public class ScienceDetailsActivity extends BaseDetailsActivity {
    private ScienceCache mCache;
    private EngBean engBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCache = new ScienceCache();
        engBean = (EngBean) getIntent().getSerializableExtra(getString(R.string.id_science));
        isCollected = (engBean.getIs_collected()==1? true:false);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        onDataRefresh();
    }

    @Override
    protected void onDataRefresh() {
        scrollView.setVisibility(View.VISIBLE);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
            }
        });
        contentView.loadUrl(engBean.getUrl());

        if(HttpUtil.isWIFI == true || Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false) == false) {
            String url=engBean.getImage_url();
            if (url!=null) {
                setMainContentBg(url);
            }
        }

        hideLoading();
    }

    @Override
    protected void removeFromCollection() {
        mCache.execSQL(ScienceTable.updateCollectionFlag(engBean.getTitle(), 0));
        mCache.execSQL(ScienceTable.deleteCollectionFlag(engBean.getTitle()));
    }

    @Override
    protected void addToCollection() {
        mCache.execSQL(ScienceTable.updateCollectionFlag(engBean.getTitle(),1));
        mCache.addToCollection(engBean);
    }

    @Override
    protected String getShareInfo() {
        return "["+engBean.getTitle()+"]:"+engBean.getUrl()+" ( "+getString(R.string.text_share_from)+getString(R.string.app_name)+")";
    }
}
