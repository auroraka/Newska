
package com.ihandy.a2014011310.database.cache.cache;

import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.ihandy.a2014011310.database.cache.BaseCache;
import com.ihandy.a2014011310.database.table.ScienceTable;
import com.ihandy.a2014011310.model.science.EngBean;
import com.ihandy.a2014011310.model.science.EngOrigin;
import com.ihandy.a2014011310.model.science.ScienceBean;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;

public class ScienceCache extends BaseCache<EngBean> {

    private ScienceTable table;
    boolean mLoadCacheResult;
    ArrayList<String> collectionTitles= new ArrayList<String>();

    public ScienceCache(){
        super();
    }

    public ScienceCache(Handler handler, String category, String url) {
        super(handler, category, url);
    }


    @Override
    protected void putData() {
        db.execSQL(mHelper.DELETE_TABLE_DATA +table.NAME+" where "+table.CATEGORY+"=\'"+mCategory+"\'");
       // db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<mList.size();i++){
            EngBean engBean = mList.get(i);
            values.put(ScienceTable.TITLE,engBean.getTitle().replace("\'","\'\'"));
            if (engBean.imgs==null){
                values.put(ScienceTable.IMAGE,"");
            }else {
                values.put(ScienceTable.IMAGE, engBean.getImage_url());
            }
            values.put(ScienceTable.COMMENT_COUNT,engBean.getOrigin());
            if (engBean.source==null){
                values.put(ScienceTable.URL,"");
            }else{
                values.put(ScienceTable.URL,engBean.getUrl());
            }
            values.put(ScienceTable.CATEGORY,mCategory);
            values.put(ScienceTable.IS_COLLECTED,engBean.getIs_collected());
            db.insert(ScienceTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(EngBean engBean) {
        values.put(ScienceTable.TITLE,engBean.getTitle().replace("\'","\'\'"));
        if (engBean.imgs==null){
            values.put(ScienceTable.IMAGE,"");
        }else {
            values.put(ScienceTable.IMAGE, engBean.getImage_url());
        }
        values.put(ScienceTable.COMMENT_COUNT,engBean.getOrigin());
        if (engBean.source==null){
            values.put(ScienceTable.URL,"");
        }else{
            values.put(ScienceTable.URL,engBean.getUrl());
        }
        db.insert(ScienceTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized void loadFromCache() {
        mList.clear();
        String sql = null;
        if(mCategory == null){
            sql = "select * from "+table.NAME;
        }else {
            sql = "select * from "+table.NAME +" where "+table.CATEGORY+"=\'"+mCategory+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            EngBean engBean = new EngBean();
            engBean.setTitle(cursor.getString(ScienceTable.ID_TITLE));

            String url=cursor.getString(ScienceTable.ID_IMAGE);
            if (!url.equals("") && engBean.imgs!=null){
                engBean.setImage_url(url);
            }
            engBean.setOrigin(cursor.getString(ScienceTable.ID_COMMENT_COUNT));
            engBean.setIs_collected(cursor.getInt(ScienceTable.ID_IS_COLLECTED));
            url=cursor.getString(ScienceTable.ID_URL);
            if (!url.equals("") && engBean.source!=null){
                engBean.setUrl(url);
            }
            mList.add(engBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
        cursor.close();
        mLoadCacheResult=(mList.size()!=0);
    }

    void getCollectionTitles() {
        collectionTitles.clear();
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            collectionTitles.add(cursor.getString(table.ID_TITLE));
        }
    }

    public void loadMore(String urlAppend){
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl+"&max_news_id="+urlAppend);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                loadFromCache();
                if (mLoadCacheResult==false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                }
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                if (response.isSuccessful() == false) {
                    loadFromCache();
                    if (mLoadCacheResult==false) {
                        mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    }
                    return;
                }

                Gson gson = new Gson();
                String str = response.body().string();
                EngOrigin engOrigin = gson.fromJson(str, EngOrigin.class);
                EngBean[] engBeans = engOrigin.data.news;

                for (EngBean engBean : engBeans) {
                    for (String title : collectionTitles) {
                        if (title.equals(engBean.getTitle())) {
                            engBean.setIs_collected(1);
                            Log.w("aaa","make collect "+engBean.getTitle());
                            break;
                        }
                    }
                    mList.add(engBean);
                }

                mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                Log.w("mmsize","mm"+mList.size());
                if (engOrigin.data.next_id!=0 && mList.size()<50){
                    loadMore(""+engOrigin.data.next_id);
                    Log.w("mmsize","mml"+mList.size()+"->"+engOrigin.data.next_id);
                }
                Log.w("mmsize","mme"+mList.size());
            }
        });
    }

    @Override
    public void load() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                loadFromCache();
                if (mLoadCacheResult==false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                }
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                if (response.isSuccessful() == false) {
                    loadFromCache();
                    if (mLoadCacheResult==false) {
                        mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    }
                    return;
                }


                getCollectionTitles();
//                collectionTitles.clear();
//                for (int i = 0; i < mList.size(); i++) {
//                    if (mList.get(i).getIs_collected() == 1) {
//                        collectionTitles.add(mList.get(i).getTitle());
//                    }
//                }
                Log.w("aaa","all collect "+collectionTitles.size());

                mList.clear();
                Gson gson = new Gson();
                String str = response.body().string();
                EngOrigin engOrigin = gson.fromJson(str, EngOrigin.class);
                EngBean[] engBeans = engOrigin.data.news;

                for (EngBean engBean : engBeans) {
                    for (String title : collectionTitles) {
                        if (title.equals(engBean.getTitle())) {
                            engBean.setIs_collected(1);
                            Log.w("aaa","make collect "+engBean.getTitle());
                            break;
                        }
                    }
                    mList.add(engBean);
                }

                mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                Log.w("mmsize", "kk" + mList.size());
                if (engOrigin.data.next_id != 0 && mList.size() < 150) {
                    loadMore("" + engOrigin.data.next_id);
                }
                Log.w("mmsize", "kke" + mList.size());
            }
        });
        Log.w("mmsize", "nn" + mList.size());
    }
}
