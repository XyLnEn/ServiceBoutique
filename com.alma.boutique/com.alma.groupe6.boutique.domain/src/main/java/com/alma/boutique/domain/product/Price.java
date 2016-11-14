package com.alma.boutique.domain.product;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Thomas Minier
 */
public class Price {
    private float value;
    private String currency;
    
    public Price() {
      this.value = 0;
      this.currency = "";
		}

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Price rhs = (Price) obj;
        return new EqualsBuilder()
                .append(this.value, rhs.value)
                .append(this.currency, rhs.currency)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(value)
                .append(currency)
                .toHashCode();
    }
}
