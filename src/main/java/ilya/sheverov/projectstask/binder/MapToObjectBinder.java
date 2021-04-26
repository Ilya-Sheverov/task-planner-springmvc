package ilya.sheverov.projectstask.binder;

import ilya.sheverov.projectstask.exception.EntityHandlerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MapToObjectBinder {

    public Map<Class<?>, InfoAboutThePropertiesOfTheClass> classesPropertiesInfo;

    public MapToObjectBinder(Class<?>... classes) {
        try {
            setClassesPropertiesInfo(classes);
        } catch (NoSuchMethodException e) {
            //logger
        }
    }

    public static void main(String[] args) throws Exception {
    }

    public <T> T buildObject(Class<T> aClass, Map<String, String[]> properties) {
        InfoAboutThePropertiesOfTheClass classInfo = classesPropertiesInfo.get(aClass);

        try {
            Object object = classInfo.getConstructor().newInstance();
            Map<String, InfoAboutMethod> propertiesWithSetMethods = classInfo.getPropertiesWithSetMethods();
            propertiesWithSetMethods.keySet()
                .forEach(fieldName -> {
                    if (properties.containsKey(fieldName)) {
                        InfoAboutMethod infoAboutMethod = propertiesWithSetMethods.get(fieldName);
                        Class<?> typeMethodParam = infoAboutMethod.getMethodParameterType();
                        Method method = infoAboutMethod.getMethod();
                        Object methodArgValue;
                        if (typeMethodParam.equals(String.class)) {
                            methodArgValue = properties.get(fieldName)[0];
                        } else {
                            methodArgValue = properties.get(fieldName);
                        }
                        try {
                            method.invoke(object, methodArgValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            return (T) object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EntityHandlerException();
        }
    }

    private InfoAboutThePropertiesOfTheClass getClassInfo(Class<?> aClass) throws NoSuchMethodException {
        InfoAboutThePropertiesOfTheClass classInfo = new InfoAboutThePropertiesOfTheClass();
        Constructor defaultConstructor = aClass.getConstructor();
        classInfo.setConstructor(defaultConstructor);
        Field[] fields = aClass.getDeclaredFields();
        Method[] methods = aClass.getDeclaredMethods();
        Arrays
            .stream(fields)
            .filter(field -> {
                Class<?> fieldType = field.getType();
              return fieldType.isAssignableFrom(String.class) ||
                  fieldType.isAssignableFrom(String[].class);
            })
            .forEach(field -> {
                String setterMethodName = setterMethodName(field.getName());
                Arrays.stream(methods).forEach(method -> {
                    if (method.getName().equals(setterMethodName)) {
                        if (method.getParameterCount() == 1) {
                            Class<?> methodParameterType = method.getParameterTypes()[0];
                            if (methodParameterType.equals(field.getType())) {
                                if (method.trySetAccessible()) {
                                    InfoAboutMethod infoAboutMethod = new InfoAboutMethod();
                                    infoAboutMethod
                                        .setMethod(method)
                                        .setMethodParameterType(methodParameterType);
                                    classInfo.setPropertyNameWithSetMethod(field.getName(), infoAboutMethod);
                                }
                            }
                        }
                    }
                });
            });
        return classInfo;
    }

    /**
     * Возвращает название set метода для поля. Например, если название поля "name", то
     * ввернет название метода "setName".
     *
     * @param fieldName название поля, для которого генерируется название set метода.
     * @return название set метода.
     */
    private String setterMethodName(String fieldName) {
        StringBuilder sb = new StringBuilder("set")
            .append(fieldName.substring(0, 1).toUpperCase())
            .append(fieldName.substring(1));
        return sb.toString();
    }

    private void setClassesPropertiesInfo(Class<?>... classes) throws NoSuchMethodException {
        classesPropertiesInfo = new HashMap<>();
        for (Class<?> aClass : classes) {
            InfoAboutThePropertiesOfTheClass classInfo = getClassInfo(aClass);
            classesPropertiesInfo.put(aClass, getClassInfo(aClass));
        }
    }

    private class InfoAboutThePropertiesOfTheClass {

        private Constructor constructor;

        private Map<String, InfoAboutMethod> propertiesWithSetMethods = new HashMap<>();

        public Constructor getConstructor() {
            return constructor;
        }

        public void setConstructor(Constructor constructor) {
            this.constructor = constructor;
        }

        public Map<String, InfoAboutMethod> getPropertiesWithSetMethods() {
            return propertiesWithSetMethods;
        }

        public void setPropertyNameWithSetMethod(String propertyName, InfoAboutMethod setMethodInfo) {
            propertiesWithSetMethods.put(propertyName, setMethodInfo);
        }
    }

    private class InfoAboutMethod {
        private Method method;
        private Class<?> methodParameterType;

        public Method getMethod() {
            return method;
        }

        public InfoAboutMethod setMethod(Method method) {
            this.method = method;
            return this;
        }

        public Class<?> getMethodParameterType() {
            return methodParameterType;
        }

        public InfoAboutMethod setMethodParameterType(Class<?> methodParameterType) {
            this.methodParameterType = methodParameterType;
            return this;
        }
    }
}
