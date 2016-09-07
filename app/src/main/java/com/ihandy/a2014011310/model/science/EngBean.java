package com.ihandy.a2014011310.model.science;

/**
 * Created by ytl on 2016/9/7.
 */
public class EngBean {
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

    public class Img{
        public String url;
        public String toString(){
            return url+"\n";
        }
    }
    public class Source{
        public String name;
        public String url;
        public String toString(){
            return name+"\n"+url+"\n";
        }
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
