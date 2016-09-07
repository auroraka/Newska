
package com.ihandy.a2014011310.database.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.ihandy.a2014011310.NewskaApplication;
import com.ihandy.a2014011310.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCache<T> implements ICache<T> {
    protected Context mContext = NewskaApplication.AppContext;
    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;

    protected ContentValues values;
    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;
    protected String mCategory;

    protected String mUrl;
    protected String[] mUrls;

    public BaseCache() {
        mHelper = DatabaseHelper.instance(mContext);
    }

    protected BaseCache(Handler handler, String category){
        mHelper = DatabaseHelper.instance(mContext);
        mCategory = category;
        mHandler = handler;
    }
    protected BaseCache(Handler handler,String category,String[] urls){
        this(handler,category);
        mUrls = urls;
    }
    protected BaseCache(Handler handler,String category,String url){
        this(handler,category);
        mUrl = url;
    }
    protected BaseCache(Handler handler){
        this(handler,null);
    }

    protected abstract void putData();
    protected abstract void putData(T object);
    public synchronized void cache(){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData();
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public synchronized void addToCollection(T object){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData(object);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public synchronized void execSQL(String sql){
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
    }

    public  List<T> getmList(){
        return mList;
    }
    public boolean hasData(){
        return !mList.isEmpty();
    }

    public  abstract void loadFromCache();
    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }


}
