package com.alma.boutique.infrastructure.webservice;

import java.io.IOException;
import java.util.List;

/**
 * Interface repésentant un WebService quelconque.
 * @author Thomas Minier
 */
public interface WebService<T> {
    T read(String url) throws IOException;
    List<T> browse(String url) throws IOException;
}
