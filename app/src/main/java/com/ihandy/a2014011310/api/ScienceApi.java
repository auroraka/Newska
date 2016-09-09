
package com.ihandy.a2014011310.api;

import android.util.Log;

import com.ihandy.a2014011310.support.Utils;

public class ScienceApi {
    static String [] channel_tag={"entertainment","health","national","sports","technology","top_stories","world"};
    public static String [] channel_title={"Entertainment","Health","Nigeria","Sports","Technology","Top Stories","World"};
    public static String science_channel_url="http://assignment.crazz.cn/news/query?locale=en&category=";
    public static int sz=channel_tag.length;
    public static boolean[] rule_out=new boolean[sz];
    public static boolean needUpdate=true;
    static String[] updated_channel_tag;
    static String [] updated_channel_title;
    public static boolean needUpdateCollect=true;
    static void update(){
        int cnt=0;
        for (int i=0;i<sz;i++){
            if (rule_out[i]==false){
                cnt++;
            }
        }
        updated_channel_tag=new String[cnt];
        updated_channel_title=new String[cnt];
        cnt=0;
        for (int i=0;i<sz;i++){
            if (rule_out[i]==false){
                updated_channel_tag[cnt]=channel_tag[i];
                updated_channel_title[cnt]=channel_title[i];
                cnt++;
            }
        }
        needUpdate=false;
    }
    public static String[] getChannel_tag(){
        if (needUpdate){
            update();
        }
        return updated_channel_tag;
    }
    public static String[] getChannel_title(){
        if (needUpdate){
            update();
        }
        return updated_channel_title;
    }
    public static void print(){
        for (int i=0;i<sz;i++){
            Log.w("rule_out "+i,""+rule_out[i]);
        }
    }
}
