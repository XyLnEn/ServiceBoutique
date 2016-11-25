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
    private int ord;
    private int from;
    private int to;

    public TransactionMockFactory(int ord, int from, int to) {
        this.ord = ord;
        this.from = from;
        this.to = to;
    }

    @Override
    public Transaction create() throws IOException {
        return new Transaction(ord, from, to);
    }
}
