package com.vending.machine;
import java.sql.SQLException;

import com.vending.machine.api.SqlUtil;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	public static Stage parentWindow;
	static SqlUtil sqlUtil = new SqlUtil();
	public static Scene scene;
	
	public static void main(String[] args) throws SQLException {
		
        sqlUtil.startDbServer("localhost", "3306");        
        sqlUtil.connectDb();
		
        Application.launch();
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub
		parentWindow = primaryStage;
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
		scene = new Scene(root);
		scene.getStylesheets().add("css/grid-style.css");
		primaryStage.setScene(scene);
		
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            	try {
					sqlUtil.stopDbServer();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Platform.exit();
                System.exit(0);
            }
        });
        
	}
	
	
}
