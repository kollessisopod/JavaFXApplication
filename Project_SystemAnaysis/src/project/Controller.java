package project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField logintextField1;
	@FXML
	private TextField logintextField2;
	@FXML
	private TextField logintextField3;
	@FXML
	private PasswordField loginpasswordField1;
	@FXML
	private PasswordField loginpasswordField2;
	@FXML
	private PasswordField loginpasswordField3;
	@FXML
	private Button loginbutton1;
	@FXML
	private Button loginbutton2;
	@FXML
	private Button loginbutton3;
	
	String username;
	String password;
	
	DatabaseController databaseController = new DatabaseController();

	
	
	public void loginSubeCalisani(ActionEvent event) throws IOException {
		
		
		try {
			
		System.out.println("meow");

		username = logintextField1.getText();
		password = loginpasswordField1.getText();
		
		System.out.println(username + password);
		
		databaseController.branchPersonnel.put(username,password);
		databaseController.saveState("database.bat");
		
		
		
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	
	public void switchToLogin(ActionEvent event) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
		
	}
	

}
