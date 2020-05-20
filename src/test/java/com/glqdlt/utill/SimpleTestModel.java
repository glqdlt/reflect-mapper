package com.glqdlt.utill;

import java.util.List;

public class SimpleTestModel {
    private String stringField;
    private Long longField;
    private List listField;
    private String haveNotGetter;

    public void setHaveNotGetter(String haveNotGetter) {
        this.haveNotGetter = haveNotGetter;
    }

    public String getHaveNotField() {
        return "test";
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public List getListField() {
        return listField;
    }

    public void setListField(List listField) {
        this.listField = listField;
    }
}
