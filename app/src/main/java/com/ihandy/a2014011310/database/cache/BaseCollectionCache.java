
package com.ihandy.a2014011310.database.cache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.ihandy.a2014011310.NewskaApplication;
import com.ihandy.a2014011310.database.DatabaseHelper;
import com.ihandy.a2014011310.database.cache.ICache;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseCollectionCache<T> implements ICache<T> {

    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;


    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;

    public BaseCollectionCache(Handler mHandler) {
        this.mHandler = mHandler;
        mHelper = DatabaseHelper.instance(NewskaApplication.AppContext);
    }

    @Override
    public void addToCollection(T object) {

    }

    @Override
    public void execSQL(String sql) {
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
    }

    @Override
    public List<T> getmList() {
        return mList;
    }

    @Override
    public boolean hasData() {
        return !mList.isEmpty();
    }

    @Override
    public void load() {

    }


    @Override
    public void cache() {

    }

    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }

}
