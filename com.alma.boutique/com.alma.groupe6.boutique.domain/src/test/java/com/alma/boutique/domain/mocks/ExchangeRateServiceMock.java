package com.alma.boutique.domain.mocks;

import com.alma.boutique.api.services.ExchangeRateService;

public class ExchangeRateServiceMock implements ExchangeRateService {

	@Override
	public float exchange(float value, String currency) {
		return value;
	}

}
