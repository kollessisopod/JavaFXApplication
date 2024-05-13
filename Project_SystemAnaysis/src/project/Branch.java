package project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Branch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchID;
	private String branchCity;
	public Map<Product, Integer> productQuantities = new HashMap<>(); 
	public Vector<Transaction> transactionHistory = new Vector<>();
	
	public Branch(String branchID, String branchCity) {
		super();
		this.branchID = branchID;
		this.branchCity = branchCity;
	}

	public String getBranchID() {
		return branchID;
	}

	public Map<Product, Integer> getProductQuantities() {
		return productQuantities;
	}

	public String getBranchCity() {
		return branchCity;
	}
	
	public void setQuantity(Product product, Integer change) {
		productQuantities.put(product, change);
		if(change == 0) {
			productQuantities.remove(product, change);
		}
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
	
    public void recordTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        System.out.println("added to transaction log");
    }

	public Vector<Transaction> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(Vector<Transaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	public boolean hasProduct(Product product) {
		return productQuantities.containsKey(product);
	}
	
}
