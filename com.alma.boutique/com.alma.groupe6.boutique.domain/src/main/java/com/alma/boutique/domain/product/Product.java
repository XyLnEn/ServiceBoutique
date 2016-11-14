package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.shared.Entity;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price.equals(product.getPrice()) && getName().equals(product.getName())
                && getDescription().equals(product.getDescription()) && getCategory().equals(product.getCategory());

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + (Float.compare(discount, +0.0f) != 0 ? Float.floatToIntBits(discount) : 0);
        return result;
    }
}



