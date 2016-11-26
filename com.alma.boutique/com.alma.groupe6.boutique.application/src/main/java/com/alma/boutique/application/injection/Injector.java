package com.alma.boutique.application.injection;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Static class that take care of the injection of dependencies in a class
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class Injector {
    private static final Logger logger = Logger.getLogger(Injector.class);

    private Injector() {
    }

    /**
     * Method that inject dependencies of attributes in a well annoted class
     * @param target the target of the dependencies injection
     */
    public static void injectAttributes(Object target) {
        Class<?> targetClass = target.getClass();
        for(Field field: targetClass.getDeclaredFields()) {
            // process only if the InjectDependency annotation on the field
            if(field.isAnnotationPresent(InjectDependency.class)) {
                // retrieve the metadata provided by the annotation
                InjectDependency annotation = field.getAnnotation(InjectDependency.class);
                String annotationName = annotation.name();
                Class<?> containerClass = field.getAnnotation(InjectDependency.class).containerClass();

                // calculate the name of the container's getter to use
                String getterName = "get" + annotationName.substring(0, 1).toUpperCase() + annotationName.substring(1);
                try {
                    // use the container to inject the depdency in the field
                    Object container = containerClass.newInstance();
                    Method getter = containerClass.getMethod(getterName);
                    Object[] invokeParams = {};
                    Object dependency = getter.invoke(container, invokeParams);
                    field.setAccessible(true);
                    field.set(target, dependency);
                    field.setAccessible(false);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    logger.error(e);
                }
            }
        }
    }
}
