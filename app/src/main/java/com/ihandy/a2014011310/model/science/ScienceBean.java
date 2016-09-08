
package com.ihandy.a2014011310.model.science;

import java.io.Serializable;


public class ScienceBean implements Serializable {
    private EngBean[] result;


    public EngBean[] getResult() {
        return result;
    }

    public void setResult(EngBean[] result) {
        this.result = result;
    }
}
