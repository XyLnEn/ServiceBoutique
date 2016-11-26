package com.alma.boutique.application.injection;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests pour la classe Injector
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class InjectorTest {
    private static final String InjectedValue = "injected string";
    private static class ExampleInjectionContainer {
        public ExampleInjectionContainer() {
        }

        public String getValue() {
            return InjectedValue;
        }
    }

    private static class ExampleClass {
        @InjectDependency(
                name = "value",
                containerClass = ExampleInjectionContainer.class)
        private String value;

        public ExampleClass() {
            Injector.injectAttributes(this);
        }

        public String getValue() {
            return value;
        }
    }

    @Test
    public void injectAttributes() throws Exception {
        // Create the class, then check if the injection has been done
        ExampleClass example = new ExampleClass();
        assertThat(example.getValue()).as("the injection should have been done").isNotNull();
        assertThat(example.getValue()).as("the injection should have inject the correct value").isEqualTo(InjectedValue);
    }

}