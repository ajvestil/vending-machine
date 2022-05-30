package com.vending.machine.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.vending.machine.api.SqlUtil;
import com.vending.machine.model.VendingMachineItem;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.scene.control.TableColumn;

public class UiFunctions {	
	
	 public static void infoBox(String infoMessage, String headerText, String title) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setContentText(infoMessage);
	        alert.setTitle(title);
	        alert.setHeaderText(headerText);
	        alert.showAndWait();
	    }

	 public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.initOwner(owner);
	        alert.show();
	    }
	 
	 public void reloadTable(TableView tableViewProducts) {
		 
		 SqlUtil sqlUtil = new SqlUtil();
		 
		 tableViewProducts.getItems().clear();
			ArrayList<VendingMachineItem> items = new ArrayList<VendingMachineItem>();
			try {
				items = sqlUtil.getProducts();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			for(VendingMachineItem item: items) {
				tableViewProducts.getItems().add(item);		
			}
			tableViewProducts.refresh();
	 }
	 
	 public void refreshTotalSales(Label lblTotalSales) throws SQLException {
		 
		SqlUtil sqlUtil = new SqlUtil();
		lblTotalSales.setText("$"+String.format("%.2f", sqlUtil.getTotalSales())); 
		
	 }
	 
	 
		
}
