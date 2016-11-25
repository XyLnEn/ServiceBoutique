package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class ThirdPartyFactory implements IFactory<ThirdParty> {
    private String name;
    private String address;
    private String telNumber;
    private boolean isSupplier;

    public ThirdPartyFactory(String name, String address, String telNumber, boolean isSupplier) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.isSupplier = isSupplier;
    }

    @Override
    public ThirdParty create() throws IOException {
        Identity info = new Identity(address, telNumber);
        return new ThirdParty(name, info, isSupplier);
    }
}
