package com.alma.boutique.domain.product;

/**
 * @author Thomas Minier
 */
public class Price {
    private float value;
    private String currency;

    public Price(float value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return Float.compare(price.value, value) == 0 && currency.equals(price.currency);

    }

    @Override
    public int hashCode() {
        int result = Float.compare(value, 0.0f) == 0 ? Float.floatToIntBits(value) : 0;
        result = 31 * result + currency.hashCode();
        return result;
    }
}
