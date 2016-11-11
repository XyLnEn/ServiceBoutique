package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Thomas Minier
 */
public class ProductTest {

    private class StubProduct extends Product {
        public StubProduct(String name, float price, String description, Category category) {
            super(name, price, description, category);
        }

    }

    @Test
    public void addDiscount() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        product.addDiscount(5);
        assertThat(product.getDiscount()).as("add a first discount").isEqualTo(5);
        product.addDiscount(2);
        assertThat(product.getDiscount()).as("add another discount").isEqualTo(7);
    }

    @Test
    public void calculatePrice() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        assertThat(product.calculatePrice()).as("calculate price without any discount").isEqualTo(10);
        product.addDiscount(5);
        assertThat(product.calculatePrice()).as("calculate price with a discount").isEqualTo(5);

        // check for exception when the discount > price
        product.addDiscount(50);
        assertThatExceptionOfType(IllegalDiscountException.class).isThrownBy(product::calculatePrice)
                .as("cannot have sum of discounts > price");
    }

    @Test
    public void getName() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        assertThat(product.getName()).as("name getter should work").isEqualTo("stub");
    }

    @Test
    public void setName() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        String value = "another stub";
        product.setName(value);
        assertThat(product.getName()).as("name setter should work").isEqualTo(value);
    }

    @Test
    public void getPrice() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        assertThat(product.getPrice()).as("price getter should work").isEqualTo(10);
    }

    @Test
    public void setPrice() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        float value = 20;
        product.setPrice(value);
        assertThat(product.getPrice()).as("price setter should work").isEqualTo(value);
    }

    @Test
    public void getDescription() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        assertThat(product.getDescription()).as("description getter should work").isEqualTo("description");
    }

    @Test
    public void setDescription() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        String value = "another description";
        product.setDescription(value);
        assertThat(product.getDescription()).as("description setter should work").isEqualTo(value);
    }

    @Test
    public void getDiscount() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        assertThat(product.getDiscount()).as("discount getter should work").isEqualTo(0);
    }

    @Test
    public void getCategory() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        assertThat(product.getCategory()).as("category getter should work").isEqualTo(category);
    }

    @Test
    public void setCategory() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        Category value = new Category("another category");
        product.setCategory(value);
        assertThat(product.getCategory()).as("category setter should work").isEqualTo(value);
    }

    @Test
    public void sameCategoryAs() throws Exception {
        Category category = new Category("stub products");
        Product product = new StubProduct("stub", 10, "description", category);
        Product anotherProduct = new StubProduct("another stub", 20, "another description", category);
        assertThat(product.sameCategoryAs(anotherProduct)).as("sameCategoryAs should work for two products with the same category").isTrue();
    }

}