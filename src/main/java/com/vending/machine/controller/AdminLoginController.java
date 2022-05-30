package com.vending.machine.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.vending.machine.Main;
import com.vending.machine.api.SqlUtil;
import com.vending.machine.interfaces.Controller;
import com.vending.machine.model.Credential;
import com.vending.machine.model.VendingMachineConfig;
import com.vending.machine.model.VendingMachineItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AdminLoginController implements Initializable{
	
	SqlUtil sqlUtil;
	UiFunctions uiFunctions;
	
	@FXML
	TextField textFieldUserId;
	
	@FXML
	PasswordField passwordFieldUserPass;
	
	@FXML
	Button btnUserLogin;
		

	
	@FXML
	public void clearBtnClicked() {
		textFieldUserId.setText("");
		passwordFieldUserPass.setText("");
	}	
	
	
	@FXML
	public void exitBtnClicked() throws IOException {
		
		/*
		textFieldUserId.setText("");
		passwordFieldUserPass.setText("");
		*/
		Parent mainWindow = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
		Stage mainStage = Main.parentWindow;
		mainStage.getScene().setRoot(mainWindow);
		   
	}
	
	
	@FXML
	public void loginBtnClicked() throws IOException, SQLException {
		 
		Credential credential = sqlUtil.returnUser();
		
			if((textFieldUserId.getText().equals(credential.getUsername())) && (passwordFieldUserPass.getText().equals(credential.getPassword()))) {
	
			uiFunctions.infoBox("Successfuly login", null, null);
			Parent adminDashboard = FXMLLoader.load(getClass().getClassLoader().getResource("AdminDashboard.fxml"));
			Stage mainStage = Main.parentWindow;
			mainStage.getScene().setRoot(adminDashboard);
		}
		else {
			uiFunctions.infoBox("Incorrect Username/Password", null, null);
		}
	}
	
	/*
	@FXML
    public void login(ActionEvent event) throws SQLException {

        Window owner = btnUserLogin.getScene().getWindow();

        System.out.println(textFieldUserId.getText());
        System.out.println(passwordFieldUserPass.getText());

        if (textFieldUserId.getText().isEmpty()) {
        	uiFunctions.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your user id");
            return;
        }
        if (passwordFieldUserPass.getText().isEmpty()) {
        	uiFunctions.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter correct password");
            return;
        }

        String username = textFieldUserId.getText();
        String password = passwordFieldUserPass.getText();

        AdminDashboardController adminDashboard = new AdminDashboardController();
       
		boolean flag = adminDashboard.validate(username, password);

        if (!flag) {
        	uiFunctions.infoBox("Please enter correct Username and Password", null, "Failed");
        } else {
        	uiFunctions.infoBox("Login Successful!", null, "Failed");
        }
    }*/

   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sqlUtil = new SqlUtil();
		uiFunctions = new UiFunctions();
	}
	
}