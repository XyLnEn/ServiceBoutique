package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.shared.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Thomas Minier
 */
public abstract class Product extends Entity {
    private String name;
    private Price price;
    private String description;
    private Category category;
    private float discount;

    public Product() {
    	super();
      this.name = "";
      this.price = new Price();
      this.description = "";
      this.category = new Category();
      discount = 0;
    }
    
    public Product(String name, Price price, String description, Category category) {
    	super();
      this.name = name;
      this.price = price;
      this.description = description;
      this.category = category;
      discount = 0;
    }

    public void addDiscount(float discount) {
        this.discount += discount;
    }

    public float calculatePrice() throws IllegalDiscountException {
        if(price.getValue() - discount < 0) {
            throw new IllegalDiscountException("Total discounts is superior to product's prize");
        }
        return price.getValue() - discount;
    }
    
    public void updateProduct(Product pro){
    	this.name = pro.getName();
      this.price = pro.getPrice();
      this.description = pro.getDescription();
      this.category = pro.getCategory();
      this.discount = pro.getDiscount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscount() {
        return discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public void setDiscount(float discount) {
			this.discount = discount;
		}

    public boolean sameCategoryAs(Product product) {
        return category.equals(product.getCategory());
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
        Product rhs = (Product) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.price, rhs.price)
                .append(this.description, rhs.description)
                .append(this.category, rhs.category)
                .append(this.discount, rhs.discount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(price)
                .append(description)
                .append(category)
                .append(discount)
                .toHashCode();
    }
}



