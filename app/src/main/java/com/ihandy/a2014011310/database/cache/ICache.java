
package com.ihandy.a2014011310.database.cache;

import java.util.List;


public interface ICache<T>{
    void addToCollection(T object);
    void execSQL(String sql);
    List<T> getmList();
    boolean hasData();
    void load();
    void loadFromCache();
    void cache();
}
