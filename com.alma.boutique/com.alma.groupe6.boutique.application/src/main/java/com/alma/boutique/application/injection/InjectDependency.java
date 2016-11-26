package com.alma.boutique.application.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that signal that an attribute of a class must have it's dependencies injected
 * @author Lenny Lucas
 * @author Thomas Minier
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectDependency {
    // The name corresponding to the attribute in the container
    String name();
    // The class of the container to use in order to perform the Injection
    Class containerClass();
}
