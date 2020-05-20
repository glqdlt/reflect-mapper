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

    @Test
    public void checkSuperSubReleation() {
        ReflectWeaving reflectWeaving = new ReflectWeaving();
        Assert.assertTrue(reflectWeaving.isThisSuperType(SuperTypeModel.class, SubSubTypeModel.class));
        Assert.assertTrue(reflectWeaving.isThisSuperType(SuperTypeModel.class, SubTypeModel.class));
        Assert.assertFalse(reflectWeaving.isThisSuperType(SuperTypeModel.class, NoConstructModel.class));
    }

    @Test
    public void fromSuperDeepCopyToSub() {
        SuperTypeModel superTypeModel = new SuperTypeModel();
        superTypeModel.setFirst("firstValue");

        ReflectWeaving reflectWeaving = new ReflectWeaving();
        SubSubTypeModel zz = reflectWeaving.fromSuperDeepCopyToSub(superTypeModel, SubSubTypeModel.class);
        Assert.assertNotNull(zz);
        Assert.assertEquals(superTypeModel.getFirst(), zz.getFirst());

    }
}