package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage)   {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Form1.fxml"));
			Scene scene = new Scene(root,850,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Investigation Raising & Sample Collection");
			primaryStage.setResizable(false);
		
			primaryStage.setScene(scene);
			Image I = new Image(getClass().getResourceAsStream("laboratory-test.png"));
			primaryStage.getIcons().add(I);
			primaryStage.show();
			
			
		}catch(Exception e) {
		e.printStackTrace();
		
		
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
