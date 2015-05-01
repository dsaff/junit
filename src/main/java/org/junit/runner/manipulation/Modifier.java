package org.junit.runner.manipulation;

import org.junit.runner.Runner;

public interface Modifier {
    Runner modify(Runner delegate);
}
