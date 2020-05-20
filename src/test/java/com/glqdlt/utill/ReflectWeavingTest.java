package com.glqdlt.utill;

import org.junit.Assert;
import org.junit.Test;

public class ReflectWeavingTest {

    @Test
    public void simpleSuccess() throws IllegalAccessException {
        ReflectWeaving reflectWeaving = new ReflectWeaving();
        SimpleTestModel aa = reflectWeaving.makeInstance(SimpleTestModel.class);
    }

    @Test
    public void maybeFail() throws IllegalAccessException {
        ReflectWeaving reflectWeaving = new ReflectWeaving();
        try {
            NoConstructModel zz = reflectWeaving.makeInstance(NoConstructModel.class);
            Assert.fail("I expected ReflectError!");
        } catch (ReflectError e) {

        }
    }
}