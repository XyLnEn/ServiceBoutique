package com.alma.boutique.domain.mocks.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class TransactionMockFactory implements IFactory<Transaction> {
    private Order ord;
    private ThirdParty from;
    private ThirdParty to;

    public TransactionMockFactory(Order ord, ThirdParty from, ThirdParty to) {
        this.ord = ord;
        this.from = from;
        this.to = to;
    }

    @Override
    public Transaction create() throws IOException {
        return new Transaction(ord, from, to);
    }
}
