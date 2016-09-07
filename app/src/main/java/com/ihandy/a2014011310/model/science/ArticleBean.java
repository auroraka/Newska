

package com.ihandy.a2014011310.model.science;


import java.io.Serializable;

public class ArticleBean implements Serializable{

    private Author author;
    private String date_published;
    private int replies_count;
    private Image_info image_info = new Image_info();
    private String url;
    private String title;
    private String summary;
    private String Info;
    private int is_collected = 0;

    class Author implements Serializable{
        String nickname;
    }

    public Image_info getImage_info() {
        return image_info;
    }
    public void setImage_info(Image_info image_info) {
        this.image_info = image_info;
    }
    public class Image_info implements Serializable{
        String url;
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }

    public int getReplies_count() {
        return replies_count;
    }
    public void setReplies_count(int replies_count) {
        this.replies_count = replies_count;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
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


    @Override
    public String toString() {
        return getTitle()+"  "+getUrl();
    }
}
