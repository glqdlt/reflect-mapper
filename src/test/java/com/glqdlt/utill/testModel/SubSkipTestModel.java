package com.glqdlt.utill.testModel;

import com.glqdlt.utill.WeavingIgnoreField;

public class SubSkipTestModel extends SuperSkipTestModel {

    @WeavingIgnoreField
    @Override
    public String getSkip() {
        return super.getSkip();
    }
}
