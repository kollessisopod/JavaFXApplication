package project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Warehouse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map<Product, Integer> productQuantities = new HashMap<>(); 
	
	public void setQuantity(Product product, Integer change) {
		productQuantities.put(product, change);
		System.out.println("Product name:" + product.getProductName() + " quantity: " + change);
	}
	
	public Integer getQuantity(Product product) {
	    Integer quantity = productQuantities.get(product);
	    if (quantity == null) {
	        return 0; 
	    } else {
	        return quantity;
	    }
	}

	public Map<Product, Integer> getProductQuantities() {
		return productQuantities;
	}


	
	
	
}
