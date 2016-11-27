package com.alma.boutique.api;

import java.io.IOException;

/**
 * Interface representing a generic Factory, accoridng to the Factory Method pattern.
 * @author Lenny Lucas
 * @author Thomas Minier
 */
@FunctionalInterface
public interface IFactory<T> {
    T create() throws IOException;
}
