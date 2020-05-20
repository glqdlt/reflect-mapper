package com.glqdlt.utill;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class ReflectWeaving {

    public <T> T makeInstance(Class<T> type) throws IllegalAccessException {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new ReflectError(String.format("'%s' is not have default constructor. check please ", type.getName()), e);
        }
    }

    public <Sub, Super> Boolean isThisSuperType(Class<Super> superType, Class<Sub> subType) {
        try {
            Class<? extends Super> _super = subType.asSubclass(superType);
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }


    public Method findMethod(String methodName, List<Method> methods) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        throw new ReflectError(String.format("Not founded method name '%s", methodName));
    }

    private List<Method> filterMethod(String methodPrefix, Method[] target) {
        List<Method> result = new LinkedList<Method>();
        for (Method m : target) {
            if (m.getName().startsWith(methodPrefix)) {
                result.add(m);
            }
        }
        return result;
    }

    private class InvokeJob {

        private Method setter;
        private Method getter;

        public InvokeJob(Method setter, Method getter) {
            this.setter = setter;
            this.getter = getter;
        }

        public Method getSetter() {
            return setter;
        }

        public void setSetter(Method setter) {
            this.setter = setter;
        }

        public Method getGetter() {
            return getter;
        }

        public void setGetter(Method getter) {
            this.getter = getter;
        }
    }

    public List<InvokeJob> filtedSubMethod(List<Method> superMethods, Method[] subMethods) {

        List<InvokeJob> result = new LinkedList<InvokeJob>();

        for (Method sub_ : subMethods) {
            for (Method super_ : superMethods) {

                String setter = replaceSetterToGetter("set", super_.getName());

                if (sub_.getName().equals(setter)) {
                    result.add(new InvokeJob(sub_, super_));
                }
            }
        }
        return result;
    }

    public <Sub, Super> Sub fromSuperDeepCopyToSub(Super targetSuperObject, Class<Sub> subType) {
        try {

            if (!isThisSuperType(targetSuperObject.getClass(), subType)) {
                throw new ReflectError(String.format("'%s' is not super type.", targetSuperObject.getClass().getName()));
            }

            Method[] superMethods = targetSuperObject.getClass().getMethods();
            final List<Method> invokeSuperMethods = this.filterMethod("get", superMethods);
            Sub _sub = makeInstance(subType);

            Method[] subMethods = subType.getMethods();
            final List<InvokeJob> invokeSubMethods = this.filtedSubMethod(invokeSuperMethods, subMethods);

            for (InvokeJob job : invokeSubMethods) {
                Method invokeSetter = job.getSetter();
                invokeSetter.invoke(_sub, job.getGetter().invoke(targetSuperObject));
            }

            return _sub;
        } catch (Exception e) {
            throw new ReflectError(e);
        }
    }

    private String replaceSetterToGetter(String appendPrefixString, String replaceTargetMethod) {
        return appendPrefixString + replaceTargetMethod.substring(3);
    }
}
