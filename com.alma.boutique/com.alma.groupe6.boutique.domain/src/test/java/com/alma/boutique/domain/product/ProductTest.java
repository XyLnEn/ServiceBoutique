package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * @author Thomas Minier
 */
public class ProductTest {

    private class StubProduct extends Product {
        public StubProduct(String name, Price price, String description, Category category) {
            super(name, price, description, category);
        }

    }

    @Test
    public void addDiscount() throws Exception {
        Category category = new Category("stub products");
        Price price = new Price(10, "EUR");
        Product product = new StubProduct("stub", price, "description", category);
        product.addDiscount(5);
        assertThat(product.getDiscount()).as("add a first discount").isEqualTo(5);
        product.addDiscount(2);
        assertThat(product.getDiscount()).as("add another discount").isEqualTo(7);
    }

    @Test
    public void calculatePrice() throws Exception {
        Category category = new Category("stub products");
        Price price = new Price(10, "EUR");
        Product product = new StubProduct("stub", price, "description", category);
        assertThat(product.calculatePrice()).as("calculate price without any discount").isEqualTo(10);
        product.addDiscount(5);
        assertThat(product.calculatePrice()).as("calculate price with a discount").isEqualTo(5);

        // check for exception when the discount > price
        product.addDiscount(50);
        assertThatExceptionOfType(IllegalDiscountException.class).isThrownBy(product::calculatePrice)
                .as("cannot have sum of discounts > price");
    }

    @Test
    public void testPojoStandard() {
        final Class<?> referenceClass = StubProduct.class;
        assertPojoMethodsFor(referenceClass).testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void sameCategoryAs() throws Exception {
        Category category = new Category("stub products");
        Price price = new Price(10, "EUR");
        Product product = new StubProduct("stub", price, "description", category);
        Product anotherProduct = new StubProduct("another stub", price, "another description", category);
        assertThat(product.sameCategoryAs(anotherProduct)).as("sameCategoryAs should work for two products with the same category").isTrue();
    }

}