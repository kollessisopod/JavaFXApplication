package project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DatabaseController implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	static public Map<String, Personnel> personnelMap = new HashMap<>();
	static public Map<String, BranchExecutive> branchExecutiveMap = new HashMap<>();
    static public Map<String, ChiefExecutive> chiefExecutiveMap = new HashMap<>();
    static public Map<String, Branch> branchMap = new HashMap<>();
    static public Map<String, Brand> brandMap = new HashMap<>();
    static public Map<String, Product> productMap = new HashMap<>();
    static public Warehouse warehouse = new Warehouse();
    static public String defaultPassword = "Star2024!";
    
    //--------------------------- ADDERS | DELETERS | SETTERS | LOGIC OPERATIONS ---------------------------//
    public void addPersonnel(String name, String surname, String username, Branch branch) {
        personnelMap.put(username, new Personnel(name, surname, username, branch, defaultPassword));
        saveState("database.bat");
    }

    public void addBranchExecutive(String name, String surname, String username, Branch branch) {
        branchExecutiveMap.put(username, new BranchExecutive(name, surname, username, branch, defaultPassword));
        saveState("database.bat");
    }

    public void addChiefExecutive(String name, String surname, String username, String password) {
        chiefExecutiveMap.put(username, new ChiefExecutive(name, surname, username, password));
        saveState("database.bat");
    }	

    public void addBranch(String id, String city) {
        branchMap.put(id, new Branch(id, city));
        saveState("database.bat");
    }
    
    public void addBrand(String id, String brandName) {
        brandMap.put(id, new Brand(id, brandName));
        saveState("database.bat");
    }
    
    public void addProduct(String id, String productName, String productDesc, Brand brand) {
        productMap.put(id, new Product(id, productName, productDesc, brand));
        saveState("database.bat");
    }

    
    public void deleteBranch(String selectedBranch) {
        // Check if the selected branch exists in the branchMap
        if (branchMap.containsKey(selectedBranch)) {
            // Remove the branch from the map
            branchMap.remove(selectedBranch);
            System.out.println("Branch '" + selectedBranch + "' deleted successfully.");
            saveState("database.bat");
        } else {
            System.out.println("Branch '" + selectedBranch + "' not found.");
        }

    }
    
    public void deleteBrand(String selectedBrand) {
        if (brandMap.containsKey(selectedBrand)) {
            brandMap.remove(selectedBrand);
            System.out.println("Brand '" + selectedBrand + "' deleted successfully.");
            saveState("database.bat");
        } else {
            System.out.println("Brand '" + selectedBrand + "' not found.");
        }

    }
    
    public void deleteProduct(String selectedProduct) {
        if (productMap.containsKey(selectedProduct)) {
        	productMap.remove(selectedProduct);
            System.out.println("Product '" + selectedProduct + "' deleted successfully.");
            saveState("database.bat");
        } else {
            System.out.println("Product '" + selectedProduct + "' not found.");
        }

    }
 
    
    public void setWarehouseQuantity(Product product, Integer quantity) {
    	
    	warehouse.setQuantity(product, quantity);
    	saveState("database.bat");
    }
    
    public void setBranchQuantity(Product product, Integer quantity, Branch branch) {
    	
    	branch.setQuantity(product, quantity);
    	saveState("database.bat");

    }
    
    
    public void transferProductsToBranch(Product product, Integer quantity, Branch branch) {
    	
    	Integer warehouseNew = getWarehouseQuantity(product) - quantity;
    	setWarehouseQuantity(product, warehouseNew);
    	setBranchQuantity(product, quantity, branch);
    	saveState("database.bat");

    }

    public Boolean confirmTransaction(Transaction transaction, Branch branch) {
    	
        Map<Product, Integer> branchQuantities = branch.getProductQuantities();

    	
        for (TransactionElement element : transaction.getTransactionElements()) {
            Product product = element.getProduct();
            int transactionQuantity = element.getQuantity();

            // Check if the product is in stock in the branch
            if (branchQuantities.containsKey(product)) {
                int branchQuantity = branchQuantities.get(product);

                // If transaction quantity exceeds branch quantity, return false
                if (transactionQuantity > branchQuantity) {
                	System.out.println("Stock overload");
                    return false;
                }
            } else {
                // If the product is not in stock in the branch, return false
                return false;
            }
        }
        // If all elements are in stock, return true
        return true;
    	
    }

    
    public void proceedTransaction(Transaction transaction, Branch branch) {
        Map<Product, Integer> branchQuantities = branch.getProductQuantities();

        
    	
        for (TransactionElement element : transaction.getTransactionElements()) {
            Product product = element.getProduct();
            int transactionQuantity = element.getQuantity();

            // Check if the product is in stock in the branch
            if (branchQuantities.containsKey(product)) {
                int branchQuantity = branchQuantities.get(product);
                branch.setQuantity(product, branchQuantity - transactionQuantity);
            }
        }
        
        saveState("database.bat");
    }
    
    public void addToTransaction(Product product, Integer quantity, Transaction transaction ) {
    	transaction.addTransactionElement(new TransactionElement(product, quantity));
    }
    
    //-----------------LOGIN METHODS----------------------//
    
	public Personnel PersonnelLogin(String username, String password){
		
        Personnel personnel = personnelMap.get(username);
        
        printerOfAll();
        
        if (personnel != null) {
        		if(password.equals(personnel.getPassword())) {
                    System.out.println("Password for " + username + ": " + password);
                    return personnel;
        		} else {
        			System.out.println("Wrong Password. Entered =" + password + "// Should've been =" + personnel.getPassword());
        			return null;
        		}
        } else {
            System.out.println("Username not found: " + username);
            return null;
        }
		
	}
    
	public BranchExecutive BranchExecutiveLogin(String username, String password){
		
        BranchExecutive branchExecutive = branchExecutiveMap.get(username);
        
        printerOfAll();
        
        if (branchExecutive != null) {
        		if(password.equals(branchExecutive.getPassword())) {
                    System.out.println("Password for " + username + ": " + password);
                    return branchExecutive;
        		} else {
        			if(branchExecutive.getPassword().equals(null)) {
        				System.out.println("New password needed.");
        			return branchExecutive;
        			} else {
        			System.out.println("Wrong Password:" + password + "Should've been =" + branchExecutive.getPassword());
        			return null;
        			}
        		}
        } else {
            System.out.println("Username not found: " + username);
            return null;
        }
		
	}
	
	public ChiefExecutive ChiefExecutiveLogin(String username, String password){
		
        ChiefExecutive chiefExecutive = chiefExecutiveMap.get(username);
        
        printerOfAll();
        
        if (chiefExecutive != null) {
        		if(password.equals(chiefExecutive.getPassword())) {
                    System.out.println("Password for " + username + ": " + password);
                    return chiefExecutive;
        		} else {
        			System.out.println("Wrong Password. Entered =" + password + "// Should've been =" + chiefExecutive.getPassword());
        			return null;
        		}
        } else {
            System.out.println("Username not found: " + username);
            return null;
        }
		
	}
	
	public void changePasswordOfBranchExecutive(String password, BranchExecutive branchEx) {
		branchEx.setPassword(password);
        saveState("database.bat");

	}
	
	public void changePasswordOfChiefExecutive(String password, ChiefExecutive chiefEx) {
		chiefEx.setPassword(password);
        saveState("database.bat");

	}
	
	public void changePasswordOfAllPersonnel(String password) {
		
	       for (HashMap.Entry<String, Personnel> entry : personnelMap.entrySet()) {
	            Personnel personnel = entry.getValue();
	            personnel.setPassword(password);
	            defaultPassword = password;
	        }
           System.out.println("All personnel password has been changed to:" + password);
           saveState("database.bat");

	}
	
	
	//---------------PRINTEROFALL--------//
    public void printerOfAll() {
        System.out.println("Branch Personnel:");
        for (HashMap.Entry<String, Personnel> entry : personnelMap.entrySet()) {
            Personnel personnel = entry.getValue();
            System.out.println("Username: " + entry.getKey() + ", Name: " + personnel.getName() + ", Surname: " + personnel.getSurname());
        }

        System.out.println("Branch Executive:");
        for (HashMap.Entry<String, BranchExecutive> entry : branchExecutiveMap.entrySet()) {
            BranchExecutive executive = entry.getValue();
            System.out.println("Username: " + entry.getKey() + ", Name: " + executive.getName() + ", Surname: " + executive.getSurname());
        }

        System.out.println("Chief Executive:");
        for (HashMap.Entry<String, ChiefExecutive> entry : chiefExecutiveMap.entrySet()) {
            ChiefExecutive chief = entry.getValue();
            System.out.println("Username: " + entry.getKey() + ", Name: " + chief.getName() + ", Surname: " + chief.getSurname());
        }
        
        System.out.println("Branches:");
        for (HashMap.Entry<String, Branch> entry : branchMap.entrySet()) {
            Branch branch = entry.getValue();
            System.out.println("Branch ID:" + entry.getKey() + "Branch Name:" + branch.getBranchCity());
        }       
        
        System.out.println("Brands:");
        for (HashMap.Entry<String, Brand> entry : brandMap.entrySet()) {
            Brand brand = entry.getValue();
            System.out.println("Brand ID:" + entry.getKey() + "Brand Name:" + brand.getBrandName());
        }        

        System.out.println("Products:");
        for (HashMap.Entry<String, Product> entry : productMap.entrySet()) {
            Product product = entry.getValue();
            System.out.println("Product ID:" + entry.getKey() + "Product Name:" + product.getProductName());
        } 
        
        
    }
	
    //---------------GETTERS AND SETTERS--------------//
    
    public Branch getBranch(String branchID) {
        return branchMap.get(branchID);
    }
	
    public Brand getBrand(String brandID) {
        return brandMap.get(brandID);
    }
    
    public Product getProduct(String productID) {
    	return productMap.get(productID);
    }
    
	public List<String> getAllBranchNames() {
	    List<String> list = new ArrayList<>();
	    for (HashMap.Entry<String, Branch> entry : branchMap.entrySet()) {
	        Branch branch = entry.getValue();
	        list.add(branch.getBranchID());
	    }
	    return list;
	}

	public List<String> getAllBrandNames() {
	    List<String> list = new ArrayList<>();
	    for (HashMap.Entry<String, Brand> entry : brandMap.entrySet()) {
	        Brand brand = entry.getValue();
	        list.add(brand.getBrandID());
	    }
	    return list;
	}

	public List<String> getAllProductNames() {
	    List<String> list = new ArrayList<>();
	    for (HashMap.Entry<String, Product> entry : productMap.entrySet()) {
	        Product product = entry.getValue();
	        list.add(product.getProductID());
	    }
	    return list;
	}
	
	public List<String> getAllProductNamesOfBranch(Branch branch) {
	    List<String> list = new ArrayList<>();
	    for (HashMap.Entry<Product, Integer> entry : branch.productQuantities.entrySet()) {
	        Product product = entry.getKey();
	        list.add(product.getProductID());
	    }
	    return list;
	}
	
	
	
	public List<String> getAllProductsOfWarehouse() {
	    List<String> list = new ArrayList<>();
	    for (HashMap.Entry<Product, Integer> entry : warehouse.productQuantities.entrySet()) {
	        Product product = entry.getKey();
	        if(product != null)
	        list.add(product.getProductID());
	    }
	    return list;
	}
	  
	public List<String> getAllProductOfCurrentTransaction(Transaction transaction) {
	    List<String> list = new ArrayList<>();
	    Vector<TransactionElement> elements = transaction.getTransactionElements();
        for (TransactionElement element : elements) {
            Product product = element.getProduct();
            int quantity = element.getQuantity();
            list.add(product.getProductID() + ", Adet: " + quantity);
            System.out.println("added to list>" + product.getProductID() + ", Adet: " + quantity);
        }
	    return list;
	}
	

	public Integer getWarehouseQuantity(Product product) {
    	
    	return warehouse.getQuantity(product);
    }
    
    
    public Integer getBranchQuantity(Product product, Branch branch) {
    	
    	return branch.getQuantity(product);
    }
    
    
    
    
	
    //------------- FILE OPERATIONS ----------------//
    
    public void createTransactionHistoryFile(Branch branch) {
        // Name the file with branch ID and transaction history
        String fileName = branch.getBranchID() + "_transaction_history.txt";

        try {
            // Create a FileWriter to write to the file
            FileWriter writer = new FileWriter(fileName);

            // Get the transaction history of the branch
            List<Transaction> transactionHistory = branch.getTransactionHistory();

            // Iterate through each transaction in the history
            for (Transaction transaction : transactionHistory) {
                writer.write("Transaction Date: " + transaction.getDateTime() + "\n");

                // Iterate through each transaction element
                for (TransactionElement element : transaction.getTransactionElements()) {
                    Product product = element.getProduct();
                    int quantity = element.getQuantity();

                    // Write product information to the file
                    writer.write("Product ID: " + product.getProductID() + "\n");
                    writer.write("Product Name: " + product.getProductName() + "\n");
                    writer.write("Quantity: " + quantity + "\n");
                    writer.write("\n");
                }

                writer.write("--------------------\n");
            }

            // Close the FileWriter
            writer.close();
            System.out.println("Transaction history file created successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while creating the transaction history file.");
            e.printStackTrace();
        }
    }
    
    public void saveState(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(personnelMap);
            oos.writeObject(branchExecutiveMap);
            oos.writeObject(chiefExecutiveMap);
            oos.writeObject(branchMap);
            oos.writeObject(brandMap);
            oos.writeObject(productMap);
            oos.writeObject(defaultPassword);
            oos.writeObject(warehouse);
            for (Branch branch : branchMap.values()) {
                oos.writeObject(branch.getTransactionHistory());
            }
            
            System.out.println("Save Successful.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	@SuppressWarnings("unchecked")
	public void loadState(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      
        	personnelMap = (HashMap<String, Personnel>) ois.readObject();
        	branchExecutiveMap = (HashMap<String, BranchExecutive>) ois.readObject();
        	chiefExecutiveMap = (HashMap<String, ChiefExecutive>) ois.readObject();
        	branchMap = (HashMap<String, Branch>) ois.readObject();
        	brandMap = (HashMap<String, Brand>) ois. readObject();
        	productMap = (HashMap<String, Product>) ois.readObject();
        	defaultPassword = (String) ois.readObject();
        	warehouse = (Warehouse) ois.readObject();

            for (Branch branch : branchMap.values()) {
                branch.setTransactionHistory((Vector<Transaction>) ois.readObject());
            }
            System.out.println("Load Successful.");
            printerOfAll();



        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load Failure.");
            e.printStackTrace();
        }
    }

}