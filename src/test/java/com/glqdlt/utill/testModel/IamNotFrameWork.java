package com.glqdlt.utill.testModel;

import com.glqdlt.utill.WeavingDescription;

@WeavingDescription(ignoreMethodName = {"getName()", "equals"})
public class IamNotFrameWork {

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
