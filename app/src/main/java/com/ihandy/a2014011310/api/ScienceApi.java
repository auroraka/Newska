
package com.ihandy.a2014011310.api;

import android.util.Log;

import com.ihandy.a2014011310.support.Utils;

public class ScienceApi {
    static String [] channel_tag={"entertainment","health","national","sports","technology","top_stories","world"};
    static String [] channel_title={"Entertainment","Health","Nigeria","Sports","Technology","Top Stories","World"};
    public static String science_channel_url="http://assignment.crazz.cn/news/query?locale=en&category=";
    static int sz=channel_tag.length;
    public static int[] ruleOut=new int[sz];
    public static boolean updated=false;
    static String[] updated_channel_tag;
    static String [] updated_channel_title;
    static void update(){
        int cnt=0;
        for (int i=0;i<sz;i++){
            if (ruleOut[i]==0){
                cnt++;
            }
        }
        updated_channel_tag=new String[cnt];
        updated_channel_title=new String[cnt];
        cnt=0;
        for (int i=0;i<sz;i++){
            if (ruleOut[i]==0){
                updated_channel_tag[cnt]=channel_tag[i];
                updated_channel_title[cnt]=channel_title[i];
                cnt++;
            }
        }
        updated=true;
    }
    public static String[] getChannel_tag(){
        if (!updated){
            update();
        }
        return updated_channel_tag;
    }
    public static String[] getChannel_title(){
        if (!updated){
            update();
        }
        return updated_channel_title;
    }
}
