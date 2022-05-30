package com.vending.machine.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.vending.machine.Main;
import com.vending.machine.api.SqlUtil;
import com.vending.machine.model.VendingMachine;
import com.vending.machine.model.VendingMachineItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdminDashboardController implements Initializable {
	SqlUtil sqlUtil;
	UiFunctions uiFunctions;
	
	File file;
	
	@FXML
	Label labelJsonFile;
	
	@FXML
	TableColumn<VendingMachineItem, String> tbColProductName;
	
	@FXML
	TableColumn<VendingMachineItem, String> tbColQuantity;
	
	@FXML
	TableColumn<VendingMachineItem, String> tbColPrice;
	
	@FXML 
	TableView tableViewProducts;
	
	@FXML
	Label lblTotalSales;
	
	@FXML
	Button btnUpdateConfig;
	
	@FXML
	public void btnExitPaneClicked() throws IOException {
		Parent mainWindow = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
		Stage mainStage = Main.parentWindow;
		Main.scene.getStylesheets().add("css/grid-style.css");
		mainStage.getScene().setRoot(mainWindow);
		
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sqlUtil = new SqlUtil();
		uiFunctions = new UiFunctions();
		
		tableViewProducts.setEditable(true);
		tbColProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbColQuantity.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tbColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		Main.scene.getStylesheets().remove("css/grid-style.css");
		tableViewProducts.setStyle("-fx-selection-bar: red; -fx-selection-bar-non-focused: salmon;");
		uiFunctions.reloadTable(tableViewProducts);
		try {
			uiFunctions.refreshTotalSales(lblTotalSales);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tbColQuantity;
		
		//tbColPrice;
		
	}	
	
	@FXML
	public void btnBrowseJson() {
		  FileChooser fileChooser = new FileChooser();  
		  fileChooser.setTitle("Open JSON Configuration File");  		  
	      file = fileChooser.showOpenDialog(Main.parentWindow);    
	      
	      if(file.getAbsolutePath()!=null) {
	    	  labelJsonFile.setText("File: "+file.getAbsolutePath());
	    	  btnUpdateConfig.setVisible(true);
	      }
	}
	
	@FXML
	public void btnUpdateConfig() throws Exception {
		String strConfig="";		
        BufferedReader in;
        in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        while (line != null) {
        	strConfig = strConfig+line;
            line = in.readLine();
        }
        //System.out.print(strConfig);
        Gson gson = new Gson();
        VendingMachine vendingMachine = gson.fromJson(strConfig, VendingMachine.class);
        System.out.print(vendingMachine.toString());
        
        sqlUtil.updateVendingMachineConfig(vendingMachine.getConfig());
        sqlUtil.updateVendingMachineItems(vendingMachine.getItems());
        uiFunctions.reloadTable(tableViewProducts);
        uiFunctions.infoBox("Successfully updated Vending Machine",null, null);
        
	}
	

}
