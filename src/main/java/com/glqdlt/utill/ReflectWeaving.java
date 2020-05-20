package com.glqdlt.utill;

import java.lang.annotation.Annotation;
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

    public List<InvokeJob> filtedMethod(List<Method> resourceMethod, Method[] targetMethods) {

        List<InvokeJob> result = new LinkedList<InvokeJob>();

        for (Method sub_ : targetMethods) {

            boolean isSkip = isSkip(sub_.getAnnotations());

            if (isSkip) {
                continue;
            }

            for (Method super_ : resourceMethod) {

                String setter = replaceSetterToGetter("set", super_.getName());

                if (sub_.getName().equals(setter)) {
                    result.add(new InvokeJob(sub_, super_));
                }
            }
        }
        return result;
    }

    private boolean isSkip(Annotation[] _anno) {
        boolean isSkip = false;
        for (Annotation a : _anno) {
            if (a instanceof WeavingIgnoreField) {
                isSkip = true;
            }
        }
        return isSkip;
    }

    public <Sub, Super> Sub fromSuperDeepCopyToSub(Super targetSuperObject, Class<Sub> subType) {

        if (!isThisSuperType(targetSuperObject.getClass(), subType)) {
            throw new ReflectError(String.format("'%s' is not super type.", targetSuperObject.getClass().getName()));
        }
        return deepCopy(targetSuperObject, subType);

    }

    public <T, R> T deepCopy(R resource, Class<T> targetType) {
        try {

            Method[] superMethods = resource.getClass().getMethods();

            List<Method> findGetMethod = WeavingFilter.filter(superMethods, new AddOnFunction<Method>() {
                public Boolean addOnCondition(Method method) {
                    return method.getName().startsWith("get");
                }
            });

            final List<Method> invokeGetMethods = WeavingFilter.filter(findGetMethod, new AddOnFunction<Method>() {
                public Boolean addOnCondition(Method method) {
                    return !isSkip(method.getAnnotations());
                }
            });

            T _t = makeInstance(targetType);

            Method[] targetMethods = targetType.getMethods();
            final List<InvokeJob> filtedTargetMethod = this.filtedMethod(invokeGetMethods, targetMethods);

            for (InvokeJob job : filtedTargetMethod) {
                Method invokeSetter = job.getSetter();
                invokeSetter.invoke(_t, job.getGetter().invoke(resource));
            }

            return _t;
        } catch (Exception e) {
            throw new ReflectError(e);
        }
    }

    private String replaceSetterToGetter(String appendPrefixString, String replaceTargetMethod) {
        return appendPrefixString + replaceTargetMethod.substring(3);
    }
}
