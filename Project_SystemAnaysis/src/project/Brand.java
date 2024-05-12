package project;

import java.io.Serializable;

public class Brand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String brandID;
	private String brandName;
	public String getBrandID() {
		return brandID;
	}
	public String getBrandName() {
		return brandName;
	}
	
	public Brand(String brandID, String brandName) {
		super();
		this.brandID = brandID;
		this.brandName = brandName;
	}
	
	
}
