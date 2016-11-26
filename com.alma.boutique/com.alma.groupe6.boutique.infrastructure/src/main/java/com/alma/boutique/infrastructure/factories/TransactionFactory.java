package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.history.Transaction;

import java.io.IOException;

/**
 *
 * @author Thomas Minier
 */
public class TransactionFactory implements IFactory<Transaction> {

	private int ord;
    private int from;
    private int to;

    public TransactionFactory(int ord, int from, int to) {
        this.ord = ord;
        this.from = from;
        this.to = to;
    }

    @Override
	public Transaction create() throws IOException {
        return new Transaction(ord, from, to);
	}
}
