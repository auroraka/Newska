package com.ihandy.a2014011310.model.science;

/**
 * Created by ytl on 2016/9/7.
 */
import com.ihandy.a2014011310.model.science.EngBean;
public class EngOrigin {
   public Data data;
    public Meta meta;
    public class Data{
        public EngBean news[];
        public long next_id;
        public String toString(){
            return "EngBeancount: "+news.length+"\n"+
                     "next_id: "+next_id+"\n"
                    ;
        }
    }
    public class Meta{
        public int code;
        public String toString(){
            return ""+code+"\n";
        }
    }
    public String toString(){
        return  data.toString()
                +meta.toString();
    }
}
