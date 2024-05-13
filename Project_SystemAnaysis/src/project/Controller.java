package project;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Objects;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Controller implements Initializable{

	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    public void setStage(Stage stage) {
        this.stage = stage;
    }
	
	@FXML
	private TextField logintextField1;
	@FXML
	private TextField logintextField2;
	@FXML
	private TextField logintextField3;
	@FXML
	private TextField textField_BranchID;
	@FXML
	private TextField textField_BranchCity;
	@FXML
	private TextField textField_BrandID;
	@FXML
	private TextField textField_BrandName;
	@FXML
	private TextField textField_ProductID;
	@FXML
	private TextField textField_ProductName;
	@FXML
	private TextArea textArea_ProductDescription;
	@FXML
	private TextField textField_Name;
	@FXML
	private TextField textField_Surname;
	@FXML
	private TextField textField_Username;
	@FXML
	private TextField textField_OldPassword;
	@FXML
	private TextField textField_NewPassword;
	@FXML
	private PasswordField loginpasswordField1;
	@FXML
	private PasswordField loginpasswordField2;
	@FXML
	private PasswordField loginpasswordField3;
	@FXML
	private Spinner<Integer> setQuantitySpinner = new Spinner<Integer>();
	@FXML
	private Spinner<Integer> transferQuantitySpinner = new Spinner<Integer>();
	@FXML
	private ListView<String> listViewBranch = new ListView<String>();
	@FXML
	private ListView<String> listViewBrand= new ListView<String>();
	@FXML
	private ListView<String> listViewProduct = new ListView<String>();
	@FXML
	private ListView<String> listViewProductByBranch = new ListView<String>();
	@FXML
	private ListView<String> listViewProductsOfWarehouse = new ListView<String>();
	@FXML
	private ListView<String> listViewProductsOfWarehouseDetailed = new ListView<String>();
	@FXML
	private ListView<String> listViewTransaction = new ListView<String>();
	@FXML
	private Label labelName;
	@FXML
	private Label labelSurname;
	@FXML
	private Label labelUsername;
	@FXML
	private Label labelBrandName;
	@FXML
	private Label labelBranchID = new Label();
	@FXML
	private Label labelProductName;
	@FXML
	private Label labelProductBrand;
	@FXML
	private Label labelWarehouseQuantity;
	@FXML
	private Label labelBranchQuantity;
	
	
	DatabaseController databaseController;
	public List<String> listMaker;

	static Personnel activePersonnel;
	static BranchExecutive activeBranchExecutive;
	static ChiefExecutive activeChiefExecutive;
	static Branch activeBranch;
	static Product activeProduct;
	static Transaction activeTransaction;
	static Integer activeStage = 0;
	int activeSetInteger;
	int activeTransferInteger;

	
	//------------------ INITIALIZE -----------------// ------// ------// -----//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseController = new DatabaseController();
		refreshAllLists();
		labelUpdater();
		SpinnerValueFactory<Integer> setValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);
		SpinnerValueFactory<Integer> transferValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);

		setValueFactory.setValue(0);
		setQuantitySpinner.setValueFactory(setValueFactory);
		transferQuantitySpinner.setValueFactory(transferValueFactory);
		
		activeSetInteger = setValueFactory.getValue();
		activeTransferInteger = transferValueFactory.getValue();
		
		setQuantitySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				activeSetInteger = setValueFactory.getValue();
			}
		});
		transferQuantitySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				activeTransferInteger = transferValueFactory.getValue();
			}
		});
		
		listViewBranch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        String selectedBranch = listViewBranch.getSelectionModel().getSelectedItem();
		        if (selectedBranch != null) {
		            activeBranch = databaseController.getBranch(selectedBranch);
		            if (activeProduct != null) {
		                labelBranchQuantity.setText(activeBranch.getQuantity(activeProduct).toString());
		            }
		        } else {
		            labelBranchQuantity.setText("Sube Seciniz");
		        }
		    }
		});

		listViewProductsOfWarehouse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        String selectedProduct = listViewProductsOfWarehouse.getSelectionModel().getSelectedItem();
		        if (selectedProduct != null) {
		            activeProduct = databaseController.getProduct(selectedProduct);
		            labelWarehouseQuantity.setText(databaseController.getWarehouseQuantity(activeProduct).toString());}
		    }
		});
		
		listViewProductByBranch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        String selectedProduct = listViewProductByBranch.getSelectionModel().getSelectedItem();
		        if (selectedProduct != null) {
		            activeProduct = databaseController.getProduct(selectedProduct);
		            labelProductName.setText(selectedProduct);
		            labelBranchQuantity.setText(databaseController.getBranchQuantity(activeProduct, activeBranch).toString());}
		    }
		});
		
		listViewProductsOfWarehouseDetailed.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        String selectedProduct = listViewProductsOfWarehouseDetailed.getSelectionModel().getSelectedItem();
		        if (selectedProduct != null) {
		            activeProduct = databaseController.getProduct(selectedProduct);
		            labelWarehouseQuantity.setText(databaseController.getWarehouseQuantity(activeProduct).toString());
		            labelProductName.setText(activeProduct.getProductName());
		            labelProductBrand.setText(activeProduct.getBelongingBrand().getBrandName());
		        } else {
		            labelWarehouseQuantity.setText("Urun Sayisi");
		            labelProductName.setText("Urun Ismi");
		            labelProductBrand.setText("Urun Markasi");
		        }
		    }
		});
		
		if (activeTransaction != null && activeTransaction.getTransactionElements() != null) {
		    activeTransaction.getTransactionElements();
		}
		
	}
	
	
	public void labelUpdater() {
		
		switch(activeStage) {
		case 1:
			System.out.println("personnel active");
	        break;
		case 2:
			System.out.println("branch ex active");
	        break;
		case 3:
			System.out.println("chief ex active");
			break;
		default:
			break;
		case 0:
			break;
		}

		if(activeBranch!= null) {
			System.out.println(" branch not null");
			labelBranchID.setText(activeBranch.getBranchID());
		}

	}
		
	
	//------------------------LOGIN METHODS------------------------//
	public void loginPersonnel(ActionEvent event) throws IOException {
		
		try {

		String username;
		String password;
		
		username = logintextField1.getText();
		password = loginpasswordField1.getText();
		
		
		Personnel personnel = databaseController.PersonnelLogin(username, password);
		if(personnel != null)
        switchToPersonnelScene(event, personnel);

		
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void loginBranchEx(ActionEvent event) throws IOException {
		
		
		try {
		String username;
		String password;
		
		username = logintextField2.getText();
		password = loginpasswordField2.getText();
						
		BranchExecutive branchEx = databaseController.BranchExecutiveLogin(username, password);
        
		if(branchEx != null)
			switchToBranchExecutiveScene(event, branchEx);
		
		
        } catch (Exception e) {
			System.out.println(e);
		}
        
	}
	
	public void loginChiefEx(ActionEvent event) throws IOException {
		
		
		try {
			
		System.out.println("meow");
		String username;
		String password;
		
		username = logintextField3.getText();
		password = loginpasswordField3.getText();
		
		ChiefExecutive chiefEx = databaseController.ChiefExecutiveLogin(username, password);
        
        databaseController.printerOfAll();
        
        if (chiefEx != null) {
        		if(password.equals(chiefEx.getPassword())) {
                    System.out.println("Password for " + username + ": " + password);
                    switchToChiefExecutiveScene(event, chiefEx);
        		} else {
        			System.out.println("Wrong Password. Entered =" + password + "// Should've been =" + chiefEx.getPassword());
        		}
        } else {
            System.out.println("Username not found: " + username);
        }
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	//----------REFRESH ALL LISTS ------------//
	
    private void refreshAllLists() {
        // Clear the existing items in the ListView
        listViewBranch.getItems().clear();
        listViewBrand.getItems().clear();
        listViewProduct.getItems().clear();
        listViewProductsOfWarehouse.getItems().clear();
        listViewProductsOfWarehouseDetailed.getItems().clear();
        listViewProductByBranch.getItems().clear();
        listViewTransaction.getItems().clear();
        
        // Fetch the list of branches from the database
        listMaker = databaseController.getAllBranchNames();
        listViewBranch.getItems().addAll(listMaker);
        
        listMaker = databaseController.getAllBrandNames();
        listViewBrand.getItems().addAll(listMaker);

        listMaker = databaseController.getAllProductNames();
        listViewProduct.getItems().addAll(listMaker);
        
        
        if(activeBranch != null) {
        listMaker = databaseController.getAllProductNames();
        listViewProductByBranch.getItems().addAll(listMaker);       	
        }

        
        listMaker = databaseController.getAllProductsOfWarehouse();
        listViewProductsOfWarehouse.getItems().addAll(listMaker);
        
        listMaker = databaseController.getAllProductsOfWarehouse();
        listViewProductsOfWarehouseDetailed.getItems().addAll(listMaker);
        
        if(activeTransaction != null) {
        listMaker = databaseController.getAllProductOfCurrentTransaction(activeTransaction);
        listViewTransaction.getItems().addAll(listMaker);  	
        }
        
        
        
    }
    
	//----------- ------------- STARTER ------------ ------------------//
    
	public void startProgram(ActionEvent event) throws IOException {
        
		//databaseController.addChiefExecutive("Kemal Ata", "Turkoglu", "123456", "abcdef");
		databaseController.loadState("database.bat");
		
		switchToLogin(event);
		
	}
	
	//------------------SCENE CONTROLLERS----------------------//
	public void switchToTargetScene(ActionEvent event, String fxmlSelector, String titleSelector)throws IOException{
		
		root = FXMLLoader.load(getClass().getResource(fxmlSelector));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);		
		stage.setScene(scene);
		stage.setTitle(titleSelector);
		stage.show();
				
	}
	
	public void switchToLogin(ActionEvent event) throws IOException {
	
		switchToTargetScene(event, "loginScene.fxml", "Giris Ekrani");
		activePersonnel = null;
		activeBranchExecutive = null;
		activeChiefExecutive = null;
		activeBranch = null;
		activeProduct = null;	

		
	}
	
	public void switchToPersonnelScene(ActionEvent event, Personnel personnel) throws IOException {
		activePersonnel = personnel;
		activeStage = 1;		
		switchToTargetScene(event, "PersonnelScene.fxml", "Sube Calisani Ekrani");


		
	}
	
	public void switchToBranchExecutiveScene(ActionEvent event, BranchExecutive branchEx) throws IOException {
		activeBranchExecutive = branchEx;
		activeStage = 2;		
		switchToTargetScene(event, "BranchExecutiveScene.fxml","Sube Muduru Ekrani");
		

		
	}
	
	public void switchToChiefExecutiveScene(ActionEvent event, ChiefExecutive chiefEx) throws IOException {
		activeChiefExecutive = chiefEx;
		activeStage = 3;		
		switchToTargetScene(event, "ChiefExecutiveScene.fxml", "Yonetici Ekrani");


	}
	
	 
	
	//------------  PopUp BINDINGS -----------------------/// -------------///
    public void addNewBranchPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BranchAddGrid.fxml", "Sube Ekle");
    }
    
    public void addNewBrandPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BrandAddGrid.fxml", "Marka Ekle");
    }
    
    public void addNewProductPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "ProductAddGrid.fxml", "Urun Ekle");
    }
    
    public void deleteBrandPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BrandDeleteGrid.fxml","Marka Sil");
    }
    
    public void deleteBranchPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BranchDeleteGrid.fxml","Sube Sil");
    }
    
    public void deleteProductPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "ProductDeleteGrid.fxml","Urun Sil");
    }

    public void changePasswordBEPopUp(ActionEvent event) throws IOException {
    	PopUpGenerator(event, "ChangePasswordGridBE.fxml", "Sifre Degistir");
    }  
    
    public void changePasswordCEPopUp(ActionEvent event) throws IOException {
    	PopUpGenerator(event, "ChangePasswordGridCE.fxml", "Sifre Degistir");
    }  
    
    public void changePasswordAPPopUp(ActionEvent event) throws IOException {
    	PopUpGenerator(event, "ChangePasswordGridAP.fxml", "Sifre Degistir");
    }  
    
    public void assignBranchExecutivePopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "BranchExecutiveAssignGrid.fxml", "Subeye Mudur Ata");
    }
    
    public void employPersonnelPopUp(ActionEvent event) throws IOException{
		activeBranch = activeBranchExecutive.getBelongingBranch();	
		System.out.println("active branch id:" + activeBranch.getBranchID());
    	PopUpGenerator(event, "EmployPersonnelGrid.fxml", "Personel Ise Al");

    }
  
    public void setWarehouseProductPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "SetWarehouseProductGrid.fxml", "Depo Urunu Ayarla");
    }
    
    public void seeWarehouseProductsPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "SeeWarehouseProductsGrid.fxml", "Depoyu Gor");
    }
    
    public void transferProductToBranchPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "TransferWarehouseProductGrid.fxml", "Depodan Subeye Yolla");

    }
    
    public void transactionPopUp(ActionEvent event) throws IOException{
    	activeTransaction = new Transaction();
    	activeBranch = activePersonnel.getBelongingBranch();
    	PopUpGenerator(event, "TransactionGrid.fxml", "Islem Yap");

    }
    
    public void stockInquiryPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "StockInquiryGrid.fxml", "Stok Sorgula");

    }
    
    //----------------------- LOGIC METHODS ------------------------//
	public void addNewBranch(ActionEvent event) {
		String branchID = textField_BranchID.getText();
		String branchCity = textField_BranchCity.getText();
		
		databaseController.addBranch(branchID, branchCity);
		PopUpCloser(event);
		
		}
	
	public void addNewBrand(ActionEvent event) {
		String brandID = textField_BrandID.getText();
		String brandName = textField_BrandName.getText();
		
		databaseController.addBrand(brandID, brandName);
		PopUpCloser(event);
		
		}
	
	public void addNewProduct(ActionEvent event) {
		String productID = textField_ProductID.getText();
		String productName = textField_ProductName.getText();
		String productDescription = textArea_ProductDescription.getText();
        String selectedBrand = listViewBrand.getSelectionModel().getSelectedItem();
        Brand brand = databaseController.getBrand(selectedBrand);		
		
		databaseController.addProduct(productID, productName, productDescription, brand);
		PopUpCloser(event);
		}
	
    public void deleteSelectedBranch(ActionEvent event) {
        String selectedBranch = listViewBranch.getSelectionModel().getSelectedItem();
        if (selectedBranch != null) {
            databaseController.deleteBranch(selectedBranch);
            
            refreshAllLists();
            
        } else {
            System.out.println("Silinecek subeyi seciniz.");
        }
    }
	
    public void deleteSelectedBrand(ActionEvent event) {
        String selectedBrand = listViewBrand.getSelectionModel().getSelectedItem();
        if (selectedBrand != null) {
            databaseController.deleteBrand(selectedBrand);
            
            refreshAllLists();
            
        } else {
            System.out.println("Silinecek markayi seciniz.");
        }
    }
    
    public void deleteSelectedProduct(ActionEvent event) {
        String selectedProduct = listViewProduct.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            databaseController.deleteProduct(selectedProduct);
            
            refreshAllLists();
            
        } else {
            System.out.println("Silinecek urunu seciniz.");
        }
    }
    
    public void assignBranchExecutive(ActionEvent event) {
    	String name = textField_Name.getText();
    	String surname = textField_Surname.getText();
    	String username = textField_Username.getText();
        String selectedBranch = listViewBranch.getSelectionModel().getSelectedItem();
        Branch branch = databaseController.getBranch(selectedBranch);
        
        databaseController.addBranchExecutive(name, surname, username, branch);
        
        PopUpCloser(event);
        
    }
    
    public void employPersonnel(ActionEvent event) {
    	String name = textField_Name.getText();
    	String surname = textField_Surname.getText();
    	String username = textField_Username.getText();
        Branch branch = activeBranch;
        
        databaseController.addPersonnel(name, surname, username, branch);
        
        PopUpCloser(event);
        
    }
    
    public void setWarehouseProduct(ActionEvent event) {
        Integer quantity = activeSetInteger;
        String productID = listViewProduct.getSelectionModel().getSelectedItem();
        Product product = databaseController.getProduct(productID);
        
        databaseController.setWarehouseQuantity(product, quantity);

        
    }
    
    public void transferProductToBranch(ActionEvent event) {
       Integer quantity = activeSetInteger;

       databaseController.transferProductsToBranch(activeProduct, quantity, activeBranch);
    }

    public void addToTransaction(ActionEvent event) {
        Integer quantity = activeSetInteger;
    	databaseController.addToTransaction(activeProduct, quantity, activeTransaction);
    	refreshAllLists();
    }
    
    public void confirmTransaction(ActionEvent event) {
    	
    	if(databaseController.confirmTransaction(activeTransaction, activeBranch)){
    	databaseController.proceedTransaction(activeTransaction, activeBranch);
     	activeBranch.recordTransaction(activeTransaction);
        activeTransaction = new Transaction();
    	refreshAllLists();   		
    	}
    }
    
    public void printTransactionLog(ActionEvent event) {
    	databaseController.createTransactionHistoryFile(activeBranchExecutive.getBelongingBranch());
    }
    
    
    public void changePasswordBE(ActionEvent event) {
    	String oldPassword = textField_OldPassword.getText();
    	String newPassword = textField_NewPassword.getText();
    	
    	
    	
    	if(Objects.equals(activeBranchExecutive.getPassword(), oldPassword)) {
    		databaseController.changePasswordOfBranchExecutive(newPassword, activeBranchExecutive);
    		PopUpCloser(event);
    	} else {
    		System.out.println("Wrong password");
    	}
    	
    }
    
    public void changePasswordCE(ActionEvent event) {
    	String oldPassword = textField_OldPassword.getText();
    	String newPassword = textField_NewPassword.getText();
    	
    	
    	
    	if(Objects.equals(activeChiefExecutive.getPassword(), oldPassword)) {
    		databaseController.changePasswordOfChiefExecutive(newPassword, activeChiefExecutive);
    		PopUpCloser(event);
    	} else {
    		System.out.println("Wrong password");
    	}
    	
    }

    public void changePasswordAP(ActionEvent event) {
    		String newPassword = textField_NewPassword.getText();
    	
    		databaseController.changePasswordOfAllPersonnel(newPassword);
    		PopUpCloser(event);
    		
    }
    
    //-------------- POP UP CONTROLLERS -----------------//
    public void PopUpGenerator(ActionEvent event, String fxmlSelector, String titleSelector) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlSelector));
            Parent root = loader.load();
            
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle(titleSelector);
            popupStage.setScene(new Scene(root));
            
            popupStage.show();
            popupStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                	switch(activeStage) {
                	case 1:
                		activeProduct = null;
                		activeTransaction = null;
                		activeBranch = null;
                		break;
                	case 2:
                        activeProduct = null;
                        activeBranch = null;
                		break;
                	case 3:
                		activeProduct = null;
                		activeBranch = null;
                		activeBranchExecutive = null;
                		
                		break;
                		
                	}
                    System.out.println("Window is closing");
                }
            });
                                    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void PopUpCloser(ActionEvent event) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
    	switch(activeStage) {
    	case 1:
    		activeProduct = null;
    		activeTransaction = null;
    		activeBranch = null;
    		break;
    	case 2:
            activeProduct = null;
            activeBranch = null;
    		break;
    	case 3:
    		activeProduct = null;
    		activeBranch = null;
    		activeBranchExecutive = null;
    		
    		break;
    		
    	}
    }
}