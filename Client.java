package application;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
	// IO streams  
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	@Override 
	// Override the start method in the Application class 
	public void start(Stage primaryStage) {
		// Panel p to hold the label and text field   
		BorderPane paneForTextField = new BorderPane();
		paneForTextField.setPadding(new Insets(5, 5, 5, 5));
		paneForTextField.setStyle("-fx-border-color: blue");
		paneForTextField.setLeft(new Label("Enter a number to check prime: "));  
		TextField tf = new TextField();
		tf.setAlignment(Pos.BOTTOM_RIGHT);
		paneForTextField.setCenter(tf); 
		BorderPane mainPane = new BorderPane();  
		// Text area to display contents 
		TextArea ta = new TextArea(); 
		mainPane.setCenter(new ScrollPane(ta));
		mainPane.setTop(paneForTextField); 
		// Create a scene and place it in the stage   
		Scene scene = new Scene(mainPane, 450, 200);
		primaryStage.setTitle("Client");
		// Set the stage title 
		primaryStage.setScene(scene); 
		// 	Place the scene in the stage 
		primaryStage.show();
		// Display the stage
		tf.setOnAction(e -> {  
			try {
				// Get the radius from the text field 
				double number = Double.parseDouble(tf.getText().trim());
				// Send the radius to the server  
				toServer.writeDouble(number); 
				toServer.flush(); 
				// Get area from the server
				int answer = fromServer.read(); 
				// Display to the text area
				ta.appendText("Number is " + number + "\n");
				if (answer == 0) {
					ta.appendText(number +" is not a prime number"+ '\n');
				} else {
					ta.appendText(number +" is a prime number"+ '\n');

				}
			}
			catch (IOException ex) {
				System.err.println(ex);
				} 
			});
		try {
			// Create a socket to connect to the server 
			@SuppressWarnings("resource")
			Socket socket = new Socket("localhost", 8080);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			}   
		catch (IOException ex) { 
			ta.appendText(ex.toString() + '\n');
			} 
		} 
	  public static void main(String[] args) { 
		  launch(args);  
		  }
}