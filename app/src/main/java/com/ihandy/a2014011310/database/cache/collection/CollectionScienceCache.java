
package com.ihandy.a2014011310.database.cache.collection;

import android.database.Cursor;
import android.os.Handler;

import com.ihandy.a2014011310.database.cache.BaseCollectionCache;
import com.ihandy.a2014011310.database.table.ScienceTable;
import com.ihandy.a2014011310.model.science.EngBean;
import com.ihandy.a2014011310.support.CONSTANT;

public class CollectionScienceCache extends BaseCollectionCache<EngBean> {


    private ScienceTable table;

    public CollectionScienceCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            EngBean engBean = new EngBean();
            engBean.setTitle(cursor.getString(table.ID_TITLE));
            String url=cursor.getString(table.ID_IMAGE);
            if (!url.equals("") && engBean.imgs!=null){
                engBean.setImage_url(url);
            }
            engBean.setUrl(cursor.getString(table.ID_URL));
            engBean.setReplies_count(cursor.getShort(table.ID_COMMENT_COUNT));
            mList.add(engBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
