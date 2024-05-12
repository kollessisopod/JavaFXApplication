package project;

import java.io.Serializable;

public class TransactionElement implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Product product;
    private int quantity;

    public TransactionElement(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

}
