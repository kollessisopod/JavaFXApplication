package project;

import java.io.Serializable;

public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  productID;
	private String productName;
	private String productDescription;
	private Brand belongingBrand;
	
	public Product(String productID, String productName, String productDescription, Brand belongingBrand) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productDescription = productDescription;
		this.belongingBrand = belongingBrand;
	}

	public String getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public Brand getBelongingBrand() {
		return belongingBrand;
	}
	
	
}
