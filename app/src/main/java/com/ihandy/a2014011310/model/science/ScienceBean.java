
package com.ihandy.a2014011310.model.science;

import java.io.Serializable;


public class ScienceBean implements Serializable {
    private ArticleBean[] result;


    public ArticleBean[] getResult() {
        return result;
    }

    public void setResult(ArticleBean[] result) {
        this.result = result;
    }
}
