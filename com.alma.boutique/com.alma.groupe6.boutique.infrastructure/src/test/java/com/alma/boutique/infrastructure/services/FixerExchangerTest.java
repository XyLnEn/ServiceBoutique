package com.alma.boutique.infrastructure.services;

import com.alma.boutique.infrastructure.webservice.JSONWebservice;
import com.alma.boutique.infrastructure.webservice.WebService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class FixerExchangerTest {

    private FixerExchanger fixerExchanger;

    @Before
    public void setUp() throws Exception {
        WebService<FixerExchangeRates> webService = new JSONWebservice<>("http://api.fixer.io", FixerExchangeRates.class);
        fixerExchanger = new FixerExchanger("/2016-11-16", webService);
    }

    @Test
    public void exchange() throws Exception {
        String currency = "AUD";
        float currentRate = (float) 1.4333;
        float value = 500;
        float expected = value * currentRate;
        // Note : this tests depends on the current rate of the AUD :/
        assertThat(fixerExchanger.exchange(value, currency)).as(("conversion should work for AUD to EUR")).isEqualTo(expected);
        assertThat(fixerExchanger.exchange(value, "EUR")).as(("no conversion for EUR to EUR")).isEqualTo(value);
    }
    
    @Test
    public void testExchangeBack() {
    	String currency = "USD";
      float currentRate = (float) 1.0702;
      float value = 500;
      float expected = value / currentRate;
      // Note : this tests depends on the current rate of the AUD :/
      assertThat(fixerExchanger.exchangeBack(value, currency)).as(("conversion should work for EUR to USD")).isEqualTo(expected);
      assertThat(fixerExchanger.exchangeBack(value, "EUR")).as(("no conversion for EUR to EUR")).isEqualTo(value);
    }
}