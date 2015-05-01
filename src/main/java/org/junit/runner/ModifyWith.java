package org.junit.runner;

import org.junit.runner.manipulation.Modifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When a test class is annotated with <code>&#064;ModifyWith</code> or extends a class annotated
 * with <code>&#064;ModifyWith</code>, JUnit will expect {@link #value()} to return a
 * subclass of {@link Modifier}; This class will be instantiated using a no-arg
 * constructor, and its {@link Modifier#modify(Runner)} method will be called in order
 * to modify the runner to be run.
 *
 * @since 4.13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ModifyWith {
    /**
     * @return a class that extends {@link Modifier}
     */
    Class<? extends Modifier> value();
}
