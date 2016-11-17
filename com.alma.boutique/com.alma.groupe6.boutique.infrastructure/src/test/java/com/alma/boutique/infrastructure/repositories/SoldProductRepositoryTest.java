package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.infrastructure.database.Database;
import com.alma.boutique.infrastructure.factories.SoldProductFactory;
import com.alma.boutique.infrastructure.util.DatabaseMock;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class SoldProductRepositoryTest {
    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new DatabaseMock();
    }

    @Test
    public void testOperations() throws Exception {
        IRepository<SoldProduct> repository = new SoldProductRepository(db);
        IFactory<SoldProduct> factory = new SoldProductFactory("dab", 5, "EUR", "some dab", "fish");
        SoldProduct firstElement = factory.create();

        factory = new SoldProductFactory("common dab", 10, "EUR", "some common dab", "fish");
        SoldProduct secondElement = factory.create();

        // insert an element into the repository
        repository.add(firstElement.getID(), firstElement);

        // check if the element has been inserted in the base
        SoldProduct retrieved = repository.read(firstElement.getID());
        assertThat(retrieved).as("the element should be have been added to the repository").isEqualTo(firstElement);

        // test the browse method
        repository.add(secondElement.getID(), secondElement);
        List<SoldProduct> allElements = repository.browse();
        assertThat(allElements).as("all elements should be browsable").contains(firstElement, secondElement);

        // test the edit method
        firstElement.setName("other dab");
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