package org.junit.runners.model;

import org.junit.runner.manipulation.InvalidOrderingException;
import org.junit.runner.manipulation.Modifier;

public class Modifiers {
    public static Modifier definedBy(Class<? extends Modifier> value) {
        try {
            return value.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not create ordering", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not create ordering", e);
        }
    }
}
