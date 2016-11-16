package com.alma.boutique.infrastructure.webservice;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class URLFormatterTest {
    @Test
    public void appendParameters() throws Exception {
        String expected = "example.org/1/2";
        String[] parameters = {"1", "2" };

        // case with an url that doesn't end with '/'
        String url = "example.org";
        assertThat(URLFormatter.appendParameters(url, parameters)).as("append parameters should work with an url that doesn't ends with '/'").isEqualTo(expected);

        // case with an url ending with '/'
        url += "/";
        assertThat(URLFormatter.appendParameters(url, parameters)).as("append parameters should work with an url that ends with '/'").isEqualTo(expected);
    }

}