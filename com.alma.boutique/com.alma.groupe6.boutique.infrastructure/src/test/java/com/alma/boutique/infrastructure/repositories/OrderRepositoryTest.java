package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.infrastructure.database.Database;
import com.alma.boutique.infrastructure.factories.OrderFactory;
import com.alma.boutique.infrastructure.util.DatabaseMock;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class OrderRepositoryTest {
    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new DatabaseMock();
    }

    @Test
    public void testOperations() throws Exception {
        IRepository<Order> repository = new OrderRepository(db);
        // some data needed for the tests
        IFactory<Order> factory = new OrderFactory("UPS");
        Order firstElement = factory.create();

        factory = new OrderFactory("Da poste");
        Order secondElement = factory.create();

        // insert an element into the repository
        repository.add(firstElement.getId(), firstElement);

        // check if the element has been inserted in the base
        Order retrieved = repository.read(firstElement.getId());
        assertThat(retrieved).as("the element should be have been added to the repository").isEqualTo(firstElement);

        // test the browse method
        repository.add(secondElement.getId(), secondElement);
        List<Order> allElements = repository.browse();
        assertThat(allElements).as("all elements should be browsable").contains(firstElement, secondElement);

        // test the edit method
        firstElement.setDeliverer("PZK");
        repository.edit(firstElement.getId(), firstElement);
        retrieved = repository.read(firstElement.getId());
        assertThat(retrieved).as("the element should have been updated after the edition in the repository").isEqualTo(firstElement);

        // test the delete method
        repository.delete(firstElement.getId());
        repository.delete(secondElement.getId());
        allElements = repository.browse();
        assertThat(allElements).as("the repository should be empty after the deletion").isEmpty();
    }
}
