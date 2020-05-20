package com.glqdlt.utill;

import com.glqdlt.utill.testModel.*;
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

    @Test
    public void fromSuperDeepCopyToSubSkip() {
        SuperSkipTestModel superSkipTestModel = new SubSkipTestModel();
        superSkipTestModel.setFirst("first");
        superSkipTestModel.setSecond("second");
        superSkipTestModel.setSkip("skip");

        ReflectWeaving reflectWeaving = new ReflectWeaving();
        SubSkipTestModel sub = reflectWeaving.fromSuperDeepCopyToSub(superSkipTestModel, SubSkipTestModel.class);

        Assert.assertEquals(superSkipTestModel.getFirst(), sub.getFirst());
        Assert.assertEquals(superSkipTestModel.getSecond(), sub.getSecond());
        Assert.assertNull(sub.getSkip());
    }

    @Test
    public void notReleationClassDeepCopy() {
        NotRelationClassA notRelationClassA = new NotRelationClassA();
        notRelationClassA.setGetName("name");
        notRelationClassA.setGetField("field");

        ReflectWeaving reflectWeaving = new ReflectWeaving();
        NotReleationClassB zzz = reflectWeaving.deepCopy(notRelationClassA, NotReleationClassB.class);
        Assert.assertEquals(notRelationClassA.getGetName(), zzz.getGetName());
        Assert.assertEquals(notRelationClassA.getGetField(), zzz.getGetField());
    }

    @Test
    public void ignoreDeepCopyAtoB() {

    }
}