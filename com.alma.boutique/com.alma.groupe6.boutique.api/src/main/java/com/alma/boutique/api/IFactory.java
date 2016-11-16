package com.alma.boutique.api;

import java.io.IOException;

/**
 * Interface représentant une factory générique.
 * @author Thomas Minier
 */
@FunctionalInterface
public interface IFactory<T> {
    T create() throws IOException;
}
