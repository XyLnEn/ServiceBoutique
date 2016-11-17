package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;
import com.alma.boutique.domain.thirdperson.Supplier;
import com.alma.boutique.infrastructure.database.Database;
import com.alma.boutique.infrastructure.factories.ClientFactory;
import com.alma.boutique.infrastructure.factories.OrderSuppliedProductFactory;
import com.alma.boutique.infrastructure.factories.SupplierFactory;
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
        IFactory<Client> clientFactory = new ClientFactory("michel", "drucker", "6 avenue de l'huître", "0606060666");
        Client client = clientFactory.create();

        IFactory<Supplier> supplierFactory = new SupplierFactory("Jotaro", "Japan", "0997979797");
        Supplier firstSupplier = supplierFactory.create();

        supplierFactory = new SupplierFactory("dio", "Londres", "060606666");
        Supplier secondSupplier = supplierFactory.create();

        IFactory<OrderSuppliedProduct> orderFactory = new OrderSuppliedProductFactory("UPS");
        Order order = orderFactory.create();

        IRepository<Transaction> repository = new TransactionRepository(db);
        IFactory<Transaction> factory = new TransactionFactory(order, firstSupplier, client);
        Transaction firstElement = factory.create();

        factory = new TransactionFactory(order, secondSupplier, client);
        Transaction secondElement = factory.create();

        // insert an element into the repository
        repository.add(firstElement.getID(), firstElement);

        // check if the element has been inserted in the base
        Transaction retrieved = repository.read(firstElement.getID());
        assertThat(retrieved).as("the element should be have been added to the repository").isEqualTo(firstElement);

        // test the browse method
        repository.add(secondElement.getID(), secondElement);
        List<Transaction> allElements = repository.browse();
        assertThat(allElements).as("all elements should be browsable").contains(firstElement, secondElement);

        // test the edit method
        firstElement.setFrom(secondSupplier);
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