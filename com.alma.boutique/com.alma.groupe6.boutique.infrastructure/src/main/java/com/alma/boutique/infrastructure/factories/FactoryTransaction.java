package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import java.io.IOException;

/**
 *
 * @author Thomas Minier
 */
public class FactoryTransaction implements IFactory<Transaction> {

	private Order ord;
    private ThirdParty from;
    private ThirdParty to;

    public FactoryTransaction(Order ord, ThirdParty from, ThirdParty to) {
        this.ord = ord;
        this.from = from;
        this.to = to;
    }

    @Override
	public Transaction create() throws IOException {
        return new Transaction(ord, from, to);
	}
}
