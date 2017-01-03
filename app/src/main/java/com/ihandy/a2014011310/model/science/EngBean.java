package com.ihandy.a2014011310.model.science;

import java.io.Serializable;
/**
 * Created by ytl on 2016/9/7.
 */
public class EngBean implements Serializable{
    public String category;
    public String country;
    public long fetched_time;
    public Img imgs[];
    public String locale_category;
    public long news_id;
    public String origin;
    public String relative_news;
    public Source source;
    public  String title;
    public String updated_time;
    public int is_collected=0;

    public class Img implements Serializable{
        public String url;
        public String toString(){
            return url+"\n";
        }
    }
    public class Source implements Serializable{
        public String name;
        public String url;
        public String toString(){
            return name+"\n"+url+"\n";
        }
    }


    public String getImage_url(){
        if (imgs!=null){
            return imgs[0].url;
        }else{
            return null;
        }

    }
    public void setImage_url(String url){
        if (imgs==null){
            imgs=new Img[1];
        }
        imgs[0].url=url;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String ori) {
        origin=ori;
    }

    public String getUrl() {
        if (source ==null){
            return null;
        }else{
            return source.url;
        }
    }
    public void setUrl(String url) {
        if (source==null) source=new Source();
        source.url=url;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_collected() {
        return is_collected;
    }
    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }


    public String toString(){
        String str="";
        str+="category: "+category+"\n";
        str+="country: "+country+"\n";
        str+="fetched_time: "+fetched_time+"\n";
        for (Img img:imgs) {
            str+=img.toString();
        }
        str+="locale_category: "+locale_category+"\n";
        str+="news_id: "+news_id+"\n";
        str+="origin: "+origin+"\n";
        str+="relative_news: "+relative_news+"\n";
        str+=source.toString();
        str+="title: "+title+"\n";
        str+="updated_time: "+updated_time+"\n";
        return str;
    }
}
