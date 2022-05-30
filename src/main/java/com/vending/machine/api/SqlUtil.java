package com.vending.machine.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.h2.tools.Server;

import com.vending.machine.model.Credential;
import com.vending.machine.model.VendingMachineConfig;
import com.vending.machine.model.VendingMachineItem;

public class SqlUtil {
	static Server server;
	
	public static void startDbServer(String host, String port) throws SQLException{
		// start the TCP Server		
		try{
			
			Server.shutdownTcpServer("tcp://"+host+":"+port, "", true, true);
		}catch(Exception e){
		}
		server = Server.createTcpServer("-tcpPort", port, "-tcpAllowOthers").start();
		serverCheckDbStatus();

		System.out.println("Successfuly started db...");
	}
	
	public static void stopDbServer() throws SQLException {
		server.stop();	
	}
	
	public static void serverCheckDbStatus(){
		System.out.println("Server status: "+server.getStatus());
	}
	
	public void addItem(VendingMachineItem item) throws SQLException {
				
		//insert to DB
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try {
	      
				String insertQuery = "insert into VENDINGMACHINEDB.\"products\" values(null,'"+item.getName().replace("'", "''")+"', "+item.getAmount()+", "+Float.parseFloat(item.getPrice().replace("$", ""))+")";
				st.executeUpdate(insertQuery);			
	      
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
	}
	
	public void removeItems(int id) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try {
	      
				String deleteQuery = "DELETE FROM VENDINGMACHINEDB.\"products\" WHERE \"id\" = "+id;
				st.executeUpdate(deleteQuery);			
	      
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
	}
	
	public void updateVendingMachineConfig(VendingMachineConfig config) throws SQLException {
	
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try {
	      
				String updateQuery = "UPDATE VENDINGMACHINEDB.\"config\" SET \"Rows\" = "+config.getRows()+",\"Columns\" = "+config.getColumns()+" WHERE \"id\" = 1";
				st.executeUpdate(updateQuery);			
	      
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
		System.out.println("\n"+ config.toString());
		
		
	}
	
	public void updateVendingMachineItems(ArrayList<VendingMachineItem> items) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try {
	      
			String query = "DELETE FROM VENDINGMACHINEDB.\"products\"";
			st.executeUpdate(query);
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
		for(VendingMachineItem item: items) {
			addItem(item);
		}
		
		//System.out.println("\n"+ config.toString());
		
		
	}
	
	
	
	
	public void connectDb() throws SQLException {
		
		boolean configWasInitialized=false;
		boolean auditWasInitialized=false;
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;INIT=create schema if not exists vendingMachineDb\\;runscript from 'classpath:vendingMachineDb.sql';");
		Statement st = conn.createStatement();
		
		
		try{
		
			String query = "SELECT * FROM VENDINGMACHINEDB.\"config\"";
		 	      
	      
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
	      
			// iterate through the java resultset and check if username and password are set
			while (rs.next())
			{
	    	  
		    	/*
		        int id = rs.getInt("id");
		        String username = rs.getString("Username");
		        String password = rs.getString("Password");
		        int rows = rs.getInt("Rows");
		        int columns = rs.getInt("Columns");
		        
		        // print the results
		        System.out.format("%s, %s, %s, %s, %s\n", id, username, password, rows, columns);
		        */
				configWasInitialized = true;
				break;
			}
			
			query = "SELECT * FROM VENDINGMACHINEDB.\"audit\"";
			// execute the query, and get a java resultset
			rs = st.executeQuery(query);
	      
			// iterate through the java resultset and check if username and password are set
			while (rs.next())
			{
	    	  
		    	/*
		        int id = rs.getInt("id");
		        String username = rs.getString("Username");
		        String password = rs.getString("Password");
		        int rows = rs.getInt("Rows");
		        int columns = rs.getInt("Columns");
		        
		        // print the results
		        System.out.format("%s, %s, %s, %s, %s\n", id, username, password, rows, columns);
		        */
				auditWasInitialized = true;
				break;
			}
	      
	      
			if(!configWasInitialized){ //initialize config
	    	  
				String insertConfigQuery = "insert into VENDINGMACHINEDB.\"config\" values(1,'admin', '1234', 4, 8, '$')";
				st.executeUpdate(insertConfigQuery);
				System.out.print("Successfully initialized config...");				
			}else {
				System.out.print("Config is ready...");
			}
			
			if(!auditWasInitialized){ //initialize audit
		    	  
				String insertConfigQuery = "insert into VENDINGMACHINEDB.\"audit\" values(1, 0.00)";
				st.executeUpdate(insertConfigQuery);
				System.out.print("Successfully initialized audit...");				
			}else {
				System.out.print("Audit table is ready...");
			}
	      
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
		
		
	}

	public Credential returnUser() throws SQLException {
		Credential credential = new Credential();
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try{
		
			String query = "SELECT * FROM VENDINGMACHINEDB.\"config\"";
		 	      
	      
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
	  
			while (rs.next())
			{
	    	  
				credential.setUsername(rs.getString("Username"));
		        credential.setPassword(rs.getString("Password"));
		        break;
			}
	      
	        
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
		
		
		return credential;
		
		
	}
	
	public ArrayList<VendingMachineItem> getProducts() throws SQLException {
		ArrayList<VendingMachineItem> products = new ArrayList<VendingMachineItem> ();
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try{
		
			String query = "SELECT * FROM VENDINGMACHINEDB.\"products\"";
		 	      
	      
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
	  
			while (rs.next())
			{
				VendingMachineItem item = new VendingMachineItem();
				item.setName(rs.getString("ProductName"));
				item.setAmount(Integer.parseInt(rs.getString("Quantity")));
				item.setPrice(rs.getString("Price"));
				item.setId(rs.getString("id"));
				products.add(item);
			}
	      
	        
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
		
		
		return products;
		
		
	}
	
	public VendingMachineConfig getConfig() throws SQLException {
		VendingMachineConfig config = new VendingMachineConfig();
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try{
		
			String query = "SELECT \"Columns\", \"Rows\" FROM VENDINGMACHINEDB.\"config\"";
		 	      
	      
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next())
			{				
				config.setColumns(Integer.parseInt(rs.getString("Columns")));
				config.setRows(Integer.parseInt(rs.getString("Rows")));
				break;
			}
	      
	        
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }
		
		return config;
	}
	
	public void dispenseProduct(VendingMachineItem item) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		
		try {
	      
			int newQuantity = item.getAmount() - 1;				
			String updateQuery = "UPDATE VENDINGMACHINEDB.\"products\" SET \"Quantity\" = "+newQuantity+" WHERE \"id\" = "+item.getId();
			st.executeUpdate(updateQuery);
						
			String query = "SELECT \"TotalSales\" FROM VENDINGMACHINEDB.\"audit\"";    
		      
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			float newTotalSales = 0.00f;
			while (rs.next())
			{		
				newTotalSales = Float.parseFloat(rs.getString("TotalSales")) + Float.parseFloat(item.getPrice());
				break;
			} 	
			
			updateQuery = "UPDATE VENDINGMACHINEDB.\"audit\" SET \"TotalSales\" = "+newTotalSales+" WHERE \"id\" = 1";
			st.executeUpdate(updateQuery);
	      
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }	
		
	}
	
public float getTotalSales() throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:3306/~/vendingMachineDb;user=root;password=;");
		Statement st = conn.createStatement();
		
		float totalSales = 0.00f;
		try {
	      
			
			String query = "SELECT \"TotalSales\" FROM VENDINGMACHINEDB.\"audit\"";    
		      
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next())
			{		
				totalSales = Float.parseFloat(rs.getString("TotalSales"));
				break;
			} 	
	      
			st.close();
			conn.close();
    	}
	    catch (Exception e)
	    {
	    	st.close();
		    conn.close();
		    System.err.println("Got an exception! ");
		    System.err.println(e.getMessage());	      
	    }	
		return totalSales;
	}

}
