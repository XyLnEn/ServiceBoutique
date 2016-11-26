package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.database.Database;
import com.alma.boutique.infrastructure.factories.OrderFactory;
import com.alma.boutique.infrastructure.factories.ThirdPartyFactory;
import com.alma.boutique.infrastructure.factories.TransactionFactory;
import com.alma.boutique.infrastructure.util.DatabaseMock;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class TransactionRepositoryTest {
    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new DatabaseMock();
    }

    @Test
    public void testOperations() throws Exception {
        // some data needed for the tests
        IFactory<ThirdParty> clientFactory = new ThirdPartyFactory("michel", "6 avenue de l'huître", "0606060666", false);
        ThirdParty client = clientFactory.create();

        IFactory<ThirdParty> supplierFactory = new ThirdPartyFactory("Jotaro", "Japan", "0997979797", true);
        ThirdParty firstSupplier = supplierFactory.create();

        supplierFactory = new ThirdPartyFactory("dio", "Londres", "060606666", true);
        ThirdParty secondSupplier = supplierFactory.create();

        IFactory<Order> orderFactory = new OrderFactory("UPS");
        Order order = orderFactory.create();

        IRepository<Transaction> repository = new TransactionRepository(db);
        IFactory<Transaction> factory = new TransactionFactory(order.getId(), firstSupplier.getId(), client.getId());
        Transaction firstElement = factory.create();

        factory = new TransactionFactory(order.getId(), secondSupplier.getId(), client.getId());
        Transaction secondElement = factory.create();

        // insert an element into the repository
        repository.add(firstElement.getId(), firstElement);

        // check if the element has been inserted in the base
        Transaction retrieved = repository.read(firstElement.getId());
        assertThat(retrieved).as("the element should be have been added to the repository").isEqualTo(firstElement);

        // test the browse method
        repository.add(secondElement.getId(), secondElement);
        List<Transaction> allElements = repository.browse();
        assertThat(allElements).as("all elements should be browsable").contains(firstElement, secondElement);

        // test the edit method
        firstElement.setPartyId(12);
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
