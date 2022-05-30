package com.vending.machine.controller;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.vending.machine.Main;
import com.vending.machine.api.SqlUtil;
import com.vending.machine.model.VendingMachineConfig;
import com.vending.machine.model.VendingMachineItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {	
	SqlUtil sqlUtil;
	UiFunctions uiFunctions;
	
	@FXML
	Label testTxt;
	
	@FXML
	GridPane spanPane;
	
	@FXML
	TextField textFieldInsertMoney;
	
	@FXML
	TextField textFieldSelectProduct;
	
	@FXML
	Button btnInsertMoney;
	
	@FXML
	Button btnSelectProduct;
	
	@FXML
	Pane productPane;
	
	@FXML
	Label labelDispense;
	
	float insertedMoney = 0.00f;
	HashMap<String, VendingMachineItem> productGridPosition;
	
	
	
	public void showProducts() throws SQLException{
		
		
        GridPane gridPane = new GridPane();
		gridPane.setPrefWidth(470);
		gridPane.setPrefHeight(419);
		gridPane.getStyleClass().add("grid");
		
		spanPane.add(gridPane, 0, 0);
		
		
		Label l2 = new Label();
		spanPane.add(l2, 1, 0);
		
		
		
		
		ArrayList<VendingMachineItem> listOfProducts = sqlUtil.getProducts();
		
		
		
		VendingMachineConfig vendingMachineConfig = sqlUtil.getConfig();		
		
		for(int column=0;column<vendingMachineConfig.getColumns();column++) {
			ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100/vendingMachineConfig.getColumns());
            cc.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(cc);
		}
		
		for(int row=0; row < vendingMachineConfig.getRows();row++) {
			RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100/vendingMachineConfig.getRows());
            rc.setValignment(VPos.CENTER);            
            gridPane.getRowConstraints().add(rc);
		}
		
		String[] columnLabel = {"A", "B", "C", "D", "E", "F", "G","H", "I"};
		productGridPosition = new HashMap<String, VendingMachineItem>(); 

		for(int row = 0; row < vendingMachineConfig.getRows(); row++)
		{			
			
			int indexOfProduct = row*vendingMachineConfig.getColumns();
			boolean isOutOfBounds = false;
			
			for(int column = 0; column < vendingMachineConfig.getColumns(); column++) {
				
				if((column+indexOfProduct)<listOfProducts.size()) {
				
					Pane pane = new Pane();
					pane.getStyleClass().add("cell");
					
					Label l = new Label();					
					System.out.println(indexOfProduct);
					l.setText(listOfProducts.get(column+indexOfProduct).getName()+
							"\nQuantity: "+ listOfProducts.get(column+indexOfProduct).getAmount() +
							"\nPrice: $"+ listOfProducts.get(column+indexOfProduct).getPrice() +
							"\nCode:  "+ columnLabel[row]+column
							);
					l.setMaxWidth(pane.getMaxWidth());
					l.setWrapText(true);
				    pane.getChildren().add(l);			    
				    productGridPosition.put(""+columnLabel[row]+column, listOfProducts.get(column+indexOfProduct));
				    gridPane.add(pane, column, row); // column=0 row=0
				} else {
					break;
				}
			}
			if(isOutOfBounds) {break;}
		  
		}
		
		
	}
	
	@FXML
	public void insertMoneyOnButtonClicked() {
	
		if(!(textFieldInsertMoney.getText().equals(""))) {
			uiFunctions.infoBox("Successfuly Inserted Money", null, null);
			btnInsertMoney.setVisible(false);
			btnSelectProduct.setVisible(true);
			textFieldInsertMoney.setVisible(false);
			textFieldSelectProduct.setVisible(true);
		}else {
			uiFunctions.infoBox("Error: Insert your money",null, null);
		}
	}
	
	@FXML
	public void selectProductButtonClicked() throws SQLException {
		
		String producSelected;
		producSelected = textFieldSelectProduct.getText();

		if(!(producSelected.equals(""))) {
		
			
			insertedMoney = Float.parseFloat(textFieldInsertMoney.getText());
			System.out.println(insertedMoney);
			
			
			if(productGridPosition.get(producSelected)!=null) {			
				if(productGridPosition.get(producSelected).getAmount()>0) {	
					if(insertedMoney >= Float.parseFloat(productGridPosition.get(producSelected).getPrice().replace("$", ""))) {
						sqlUtil.dispenseProduct(productGridPosition.get(producSelected));
						try {
							showProducts();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Float change = Float.parseFloat(productGridPosition.get(producSelected).getPrice().replace("$", "")) - insertedMoney;
						change = Math.abs(change);
						labelDispense.setText("You have successfully dispensed " + productGridPosition.get(producSelected).getName()+".\nPlease get your change: "+change);
						
						textFieldSelectProduct.setText("");
						textFieldInsertMoney.setText("");
						insertedMoney=0f;
						
						btnInsertMoney.setVisible(true);
						btnSelectProduct.setVisible(false);
						textFieldInsertMoney.setVisible(true);
						textFieldSelectProduct.setVisible(false);
					} else {
						uiFunctions.infoBox("Error Despensing item, insuffecient credit",null, null);
						textFieldSelectProduct.setText("");
						textFieldInsertMoney.setText("");
						insertedMoney=0f;
						btnInsertMoney.setVisible(true);
						btnSelectProduct.setVisible(false);
						textFieldInsertMoney.setVisible(true);
						textFieldSelectProduct.setVisible(false);
					}
				} else {
					uiFunctions.infoBox("No more "+ productGridPosition.get(producSelected).getName()+" left.",null, null);
				}
			}else {
				uiFunctions.infoBox("Error: Please Input Correct Product Codey",null, null);
			}
		}else {
			uiFunctions.infoBox("Error: Please Input Correct Product Code",null, null);
		}
		
		
		
	}
	
	
	@FXML
	public void adminButtonClicked() throws Exception {
		
		Parent adminLogin = FXMLLoader.load(getClass().getClassLoader().getResource("AdminLogin.fxml"));
		Stage mainStage = Main.parentWindow;
	    mainStage.getScene().setRoot(adminLogin);
	   
		
	}
	
	
	
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sqlUtil = new SqlUtil();
		uiFunctions = new UiFunctions();
		try {
			showProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}