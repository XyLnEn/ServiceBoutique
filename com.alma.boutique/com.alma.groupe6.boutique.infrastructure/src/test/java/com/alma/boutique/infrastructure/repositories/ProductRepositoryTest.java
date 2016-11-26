package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.infrastructure.database.Database;
import com.alma.boutique.infrastructure.factories.ProductFactory;
import com.alma.boutique.infrastructure.util.DatabaseMock;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class ProductRepositoryTest {
    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new DatabaseMock();
    }

    @Test
    public void testOperations() throws Exception {
        IRepository<Product> repository = new ProductRepository(db);
        // some data needed for the tests
        IFactory<Product> factory = new ProductFactory("name A", 10, "EUR", "description A", "category A");
        Product firstElement = factory.create();

        factory = new ProductFactory("name B", 20, "CZK", "description B", "category B");
        Product secondElement = factory.create();

        // insert an element into the repository
        repository.add(firstElement.getID(), firstElement);

        // check if the element has been inserted in the base
        Product retrieved = repository.read(firstElement.getID());
        assertThat(retrieved).as("the element should be have been added to the repository").isEqualTo(firstElement);

        // test the browse method
        repository.add(secondElement.getID(), secondElement);
        List<Product> allElements = repository.browse();
        assertThat(allElements).as("all elements should be browsable").contains(firstElement, secondElement);

        // test the edit method
        firstElement.setName("other name");
        repository.edit(firstElement.getID(), firstElement);
        retrieved = repository.read(firstElement.getID());
        assertThat(retrieved).as("the element should have been updated after the edition in the repository").isEqualTo(firstElement);

        // test the delete method
        repository.delete(firstElement.getID());
        repository.delete(secondElement.getID());
        allElements = repository.browse();
        assertThat(allElements).as("the repository should be empty after the deletion").isEmpty();
    }
}
