module Project_SystemAnaysis {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.fxml;
	requires javafx.base;
	
	opens project to javafx.graphics, javafx.fxml;
}
