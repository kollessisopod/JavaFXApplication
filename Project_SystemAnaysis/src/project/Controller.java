package project;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private ListView<String> listViewProductByBranchDetailed = new ListView<String>();
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
	private Label labelProductDescription;
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
		            labelProductDescription.setText(activeProduct.getProductDescription());
		            labelProductBrand.setText(activeProduct.getBelongingBrand().getBrandName());
		        } else {
		            labelWarehouseQuantity.setText("Urun Sayisi");
		            labelProductName.setText("Urun Ismi");
		            labelProductDescription.setText("Urun Aciklamasi");
		            labelProductBrand.setText("Urun Markasi");
		        }
		    }
		});
		
		listViewProductByBranchDetailed.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        String selectedProduct = listViewProductByBranchDetailed.getSelectionModel().getSelectedItem();
		        if (selectedProduct != null) {
		            activeProduct = databaseController.getProduct(selectedProduct);
		            labelBranchID.setText(activeBranch.getBranchID());
		            labelBranchQuantity.setText(activeBranch.getQuantity(activeProduct).toString());
		            labelProductName.setText(activeProduct.getProductName());
		            labelProductDescription.setText(activeProduct.getProductDescription());
		            labelProductBrand.setText(activeProduct.getBelongingBrand().getBrandName());
		        } else {
		            labelBranchID.setText("Sube ID");
		        	labelBranchQuantity.setText("Urun Sayisi");
		            labelProductName.setText("Urun Ismi");
		            labelProductDescription.setText("Urun Aciklamasi");
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
		AlertGenerator(event, "Hoşgeldiniz, " + personnel.getName() + " " + personnel.getSurname());
		return;
		
		} catch (Exception e) {	
			System.out.println(e);
            AlertGenerator(event, "Giriş yapılamadı.");
            return;
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
			AlertGenerator(event, "Hoşgeldiniz, " + branchEx.getName() + " " + branchEx.getSurname());
			return;
		
        } catch (Exception e) {
			System.out.println(e);
            AlertGenerator(event, "Giriş yapılamadı.");
            return;
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
                    switchToChiefExecutiveScene(event, chiefEx);
                    AlertGenerator(event, "Hoşgeldiniz, " + chiefEx.getName() + " " + chiefEx.getSurname());
                    return;
        		}
        	}
		} catch (Exception e) {
			System.out.println(e);
            AlertGenerator(event, "Giriş yapılamadı.");
            return;
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
        listMaker = databaseController.getAllProductNamesOfBranch(activeBranch);
        listViewProductByBranch.getItems().addAll(listMaker); 
        listViewProductByBranchDetailed.getItems().addAll(listMaker); 

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
        
		databaseController.loadState("database.bat");
		databaseController.addChiefExecutive("Kemal Ata", "Türkoğlu", "123456", "abcdef");
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
        PopUpGenerator(event, "BranchAddGrid.fxml", "Şube Ekle");
    }
    
    public void addNewBrandPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BrandAddGrid.fxml", "Marka Ekle");
    }
    
    public void addNewProductPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "ProductAddGrid.fxml", "Ürün Ekle");
    }
    
    public void deleteBrandPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BrandDeleteGrid.fxml","Marka Sil");
    }
    
    public void deleteBranchPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "BranchDeleteGrid.fxml","Şube Sil");
    }
    
    public void deleteProductPopUp(ActionEvent event) throws IOException {
        PopUpGenerator(event, "ProductDeleteGrid.fxml","Ürün Sil");
    }

    public void changePasswordBEPopUp(ActionEvent event) throws IOException {
    	PopUpGenerator(event, "ChangePasswordGridBE.fxml", "Şifre Değiştir");
    }  
    
    public void changePasswordCEPopUp(ActionEvent event) throws IOException {
    	PopUpGenerator(event, "ChangePasswordGridCE.fxml", "Şifre Değiştir");
    }  
    
    public void changePasswordAPPopUp(ActionEvent event) throws IOException {
    	PopUpGenerator(event, "ChangePasswordGridAP.fxml", "Şifre Değiştir");
    }  
    
    public void assignBranchExecutivePopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "BranchExecutiveAssignGrid.fxml", "Şubeye Müdür Ata");
    }
    
    public void employPersonnelPopUp(ActionEvent event) throws IOException{
		activeBranch = activeBranchExecutive.getBelongingBranch();	
		System.out.println("active branch id:" + activeBranch.getBranchID());
    	PopUpGenerator(event, "EmployPersonnelGrid.fxml", "Personel İşe Al");

    }
  
    public void setWarehouseProductPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "SetWarehouseProductGrid.fxml", "Depo Ürünü Ayarla");
    }
    
    public void seeWarehouseProductsPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "SeeWarehouseProductsGrid.fxml", "Depoyu Gör");
    }
    
    public void transferProductToBranchPopUp(ActionEvent event) throws IOException{
    	PopUpGenerator(event, "TransferWarehouseProductGrid.fxml", "Depodan Şubeye Yolla");

    }
    
    public void transactionPopUp(ActionEvent event) throws IOException{
    	activeTransaction = new Transaction();
    	activeBranch = activePersonnel.getBelongingBranch();
    	PopUpGenerator(event, "TransactionGrid.fxml", "İşlem Yap");

    }
    
    public void stockInquiryPopUp(ActionEvent event) throws IOException{
    	if(activePersonnel != null) {
        	activeBranch = activePersonnel.getBelongingBranch();
    	}
    	
    	if(activeBranchExecutive != null) {
        	activeBranch = activeBranchExecutive.getBelongingBranch();
    	}
    	
    	activeProduct = new Product(null, null, null, null);
    	PopUpGenerator(event, "StockInquiryGrid.fxml", "Stok Sorgula");
    }
    
    //----------------------- LOGIC METHODS ------------------------//
	public void addNewBranch(ActionEvent event) {
		String branchID = textField_BranchID.getText();
		String branchCity = textField_BranchCity.getText();
		
	    if (branchID.isEmpty() || branchCity.isEmpty()) {
	        AlertGenerator(event, "Şube ID ve Şehri girilmelidir.");
	        return;
	    }
	    
		databaseController.addBranch(branchID, branchCity);
		PopUpCloser(event);
		AlertGenerator(event, "Ekleme başarılı.");
        return;
		}
	
	public void addNewBrand(ActionEvent event) {
		String brandID = textField_BrandID.getText();
		String brandName = textField_BrandName.getText();
		
	    if (brandID.isEmpty() || brandName.isEmpty()) {
	        AlertGenerator(event, "Marka ID ve Marka ismi girilmelidir.");
	        return;
	    }
		databaseController.addBrand(brandID, brandName);
		PopUpCloser(event);
		AlertGenerator(event, "Ekleme başarılı.");
        return;
		
		}
	
	public void addNewProduct(ActionEvent event) {
		String productID = textField_ProductID.getText();
		String productName = textField_ProductName.getText();
		String productDescription = textArea_ProductDescription.getText();
        String selectedBrand = listViewBrand.getSelectionModel().getSelectedItem();

		try{
	    if (productID.isEmpty() || productName.isEmpty() || productDescription.isEmpty() || selectedBrand.isEmpty()) {
	        AlertGenerator(event, "Tüm boşluklar doldurulmalıdır.");
	        return;
	    }			
		} catch (NullPointerException e){
	        AlertGenerator(event, "Ürünlerin markası seçilmelidir..");
	        return;
		}		
        Brand brand = databaseController.getBrand(selectedBrand);				

		databaseController.addProduct(productID, productName, productDescription, brand);
		PopUpCloser(event);
		AlertGenerator(event, "Ekleme başarılı.");
        return;
		}
	
    public void deleteSelectedBranch(ActionEvent event) {
        String selectedBranch = listViewBranch.getSelectionModel().getSelectedItem();
        if (selectedBranch != null) {
            databaseController.deleteBranch(selectedBranch);
            
            refreshAllLists();
	        AlertGenerator(event, "Seçili şube, şube çalışanları, müdürü ve ürünleri temizlendi.");
            return;
        } else {
	        AlertGenerator(event, "Silinecek şubeyi seçiniz.");
        }
    }
	
    public void deleteSelectedBrand(ActionEvent event) {
        String selectedBrand = listViewBrand.getSelectionModel().getSelectedItem();
        if (selectedBrand != null) {
            databaseController.deleteBrand(selectedBrand);
            
            refreshAllLists();
	        AlertGenerator(event, "Seçili markaya ait olan bütün ürünlerin depodaki ve tüm şubelerdeki bilgileri temizlendi.");
            return;
        } else {
	        AlertGenerator(event, "Silinecek markayı seçiniz.");
        }
    }
    
    public void deleteSelectedProduct(ActionEvent event) {
        String selectedProduct = listViewProduct.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
        	
            databaseController.deleteProduct(selectedProduct);
            
            refreshAllLists();
	        AlertGenerator(event, "Seçili ürünün depodaki ve tüm şubelerdeki bilgileri temizlendi.");

            return;
        } else {
	        AlertGenerator(event, "Silinecek ürünü seçiniz.");
        }
    }
    
    public void assignBranchExecutive(ActionEvent event) {
    	String name = textField_Name.getText();
    	String surname = textField_Surname.getText();
    	String username = textField_Username.getText();
        String selectedBranch = listViewBranch.getSelectionModel().getSelectedItem();      
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || selectedBranch == null) {
            AlertGenerator(event, "Tüm bilgilerin eksiksiz doldurulması gerekmektedir.");
            return;
        }
        
        Branch branch = databaseController.getBranch(selectedBranch);
        databaseController.addBranchExecutive(name, surname, username, branch);
        
        PopUpCloser(event);
		AlertGenerator(event, "Şube müdürü ataması başarılı.");
        return;
    }
    
    public void employPersonnel(ActionEvent event) {
    	String name = textField_Name.getText();
    	String surname = textField_Surname.getText();
    	String username = textField_Username.getText();
        Branch branch = activeBranch;
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty()) {
            AlertGenerator(event, "Tüm bilgilerin eksiksiz doldurulması gerekmektedir.");
            return;
        }
        databaseController.addPersonnel(name, surname, username, branch);
        
        PopUpCloser(event);
		AlertGenerator(event, "Personel işe alımı başarılı.");
        return;
    }
    
    public void setWarehouseProduct(ActionEvent event) {
        Integer quantity = activeSetInteger;
        String productID = listViewProduct.getSelectionModel().getSelectedItem();
        Product product = databaseController.getProduct(productID);
        if (quantity == null || productID == null) {
            AlertGenerator(event, "Ürün ve miktar seçilmelidir.");
            return;
        }
        databaseController.setWarehouseQuantity(product, quantity);

    }
    
    public void transferProductToBranch(ActionEvent event) {
       Integer quantity = activeSetInteger;
       if (quantity == null || activeProduct == null || activeBranch == null) {
           AlertGenerator(event, "Ürün, miktar ve hedef şube seçilmelidir.");
           return;
       }
       if (databaseController.getWarehouseQuantity(activeProduct) < quantity) {
           AlertGenerator(event, "Depoda istenilen miktarda ürün yok. Transfer yapılamaz.");
           return;
       }
       
       
       databaseController.transferProductsToBranch(activeProduct, quantity, activeBranch);
   	labelUpdater();
   	refreshAllLists();
    }

    public void addToTransaction(ActionEvent event) {
        Integer quantity = activeSetInteger;
        if (quantity == null || activeProduct == null || activeTransaction == null) {
            AlertGenerator(event, "Ürün ve miktar seçilmelidir.");
            return;
        }
    	databaseController.addToTransaction(activeProduct, quantity, activeTransaction);
    	refreshAllLists();
    }
    
    public void confirmTransaction(ActionEvent event) {
        if (activeTransaction == null || activeBranch == null) {
            AlertGenerator(event, "Onaylamak için bir işlem ve şube seçilmelidir.");
            return;
        }
        
        if (activeTransaction.getTransactionElements().isEmpty()) {
            AlertGenerator(event, "Sepette en az bir ürün olmalıdır.");
            return;
        }
        
    	if(databaseController.confirmTransaction(activeTransaction, activeBranch)){
    	databaseController.proceedTransaction(activeTransaction, activeBranch);
     	activeBranch.recordTransaction(activeTransaction);
        activeTransaction = new Transaction();
    	refreshAllLists();  
    	labelUpdater();
    	} else {
            AlertGenerator(event, "Şube stoğundan fazla ürün istenmektedir. İşlem iptal ediliyor.");
            activeTransaction = new Transaction();
            return;
    	}
    }
    
    public void printTransactionLog(ActionEvent event) {
    	databaseController.createTransactionHistoryFile(activeBranchExecutive.getBelongingBranch());
        AlertGenerator(event, "Şube raporu çıktı alındı.");
        return;
    }
    
    public void printTransactionLogFull(ActionEvent event) {
    	databaseController.createChiefHistoryFile();
        AlertGenerator(event, "Genel rapor çıktı alındı.");
        return;
    }
    
    public void changePasswordBE(ActionEvent event) {
    	String oldPassword = textField_OldPassword.getText();
    	String newPassword = textField_NewPassword.getText();
    	
    	
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            AlertGenerator(event, "Eski ve yeni şifreleri doldurunuz.");
            return;
        }
    	if(Objects.equals(activeBranchExecutive.getPassword(), oldPassword)) {
    		databaseController.changePasswordOfBranchExecutive(newPassword, activeBranchExecutive);
    		PopUpCloser(event);            
    		AlertGenerator(event, "Şifre başarıyla değiştirildi.");
            return;
    	} else {
            AlertGenerator(event, "Hatalı eski şifre.");
    	}
    	
    }
    
    public void changePasswordCE(ActionEvent event) {
    	String oldPassword = textField_OldPassword.getText();
    	String newPassword = textField_NewPassword.getText();
    	
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            AlertGenerator(event, "Eski ve yeni şifreleri doldurunuz.");
            return;
        }
    	
    	if(Objects.equals(activeChiefExecutive.getPassword(), oldPassword)) {
    		databaseController.changePasswordOfChiefExecutive(newPassword, activeChiefExecutive);   		
    		PopUpCloser(event);
    		AlertGenerator(event, "Şifre başarıyla değiştirildi.");
            return;
    	} else {
            AlertGenerator(event, "Hatalı eski şifre.");
    	}
    	
    }

    public void changePasswordAP(ActionEvent event) {
    		String newPassword = textField_NewPassword.getText();
            if (newPassword.isEmpty()) {
                AlertGenerator(event, "Yeni şifreyi belirleyiniz.");
                return;
            }
    		databaseController.changePasswordOfAllPersonnel(newPassword);
    		PopUpCloser(event);
    		AlertGenerator(event, "Tüm personelin şifresi başarıyla değiştirildi.");
            return;
    }
    
    //-------------- POP UP CONTROLLERS -----------------//
    
    public void AlertGenerator(ActionEvent event, String context) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle(null);
            alert.setContentText(context);
            alert.showAndWait();

    }
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