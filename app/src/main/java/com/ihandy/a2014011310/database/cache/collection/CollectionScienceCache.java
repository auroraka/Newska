
package com.ihandy.a2014011310.database.cache.collection;

import android.database.Cursor;
import android.os.Handler;

import com.ihandy.a2014011310.database.cache.BaseCollectionCache;
import com.ihandy.a2014011310.database.table.ScienceTable;
import com.ihandy.a2014011310.model.science.ArticleBean;
import com.ihandy.a2014011310.support.CONSTANT;

public class CollectionScienceCache extends BaseCollectionCache<ArticleBean> {


    private ScienceTable table;

    public CollectionScienceCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(table.ID_TITLE));
            articleBean.setInfo(cursor.getString(table.ID_INFO));
            articleBean.getImage_info().setUrl(cursor.getString(table.ID_IMAGE));
            articleBean.setUrl(cursor.getString(table.ID_URL));
            articleBean.setReplies_count(cursor.getShort(table.ID_COMMENT_COUNT));
            articleBean.setSummary(cursor.getString(table.ID_DESCRIPTION));
            mList.add(articleBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
