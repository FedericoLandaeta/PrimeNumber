package application;






import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class Server extends Application {
	
	public static int isPrime(double number) {
	       if(number<2) {
	           return 0;
	       }
	       int i=2;
	       while(i<number) {
	           if(number%i==0) {
	               return 0;
	           }
	           i++;
	       }
	       return 1;      
	   }
	
	
	@Override 
	// Override the start method in the Application class  
	public void start(Stage primaryStage) {
		// Text area for displaying contents
		TextArea ta = new TextArea();   
		// Create a scene and place it in the stage
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Server");
		// Set the stage title
		primaryStage.setScene(scene);
		// Place the scene in the stage 
		primaryStage.show(); 
		// Display the stage
		new Thread( () -> {  
			try { 
				// Create a server socket
				ServerSocket serverSocket = new ServerSocket(8080);
				Platform.runLater(() -> 
				ta.appendText("Server started at " + new Date() + '\n'));    
				// Listen for a connection request   
				Socket socket = serverSocket.accept(); 
				// Create data input and output streams   
				DataInputStream inputFromClient = new DataInputStream(    
						socket.getInputStream()); 
				DataOutputStream outputToClient = new DataOutputStream( 
						socket.getOutputStream());
				while (true) { 
					// Receive radius from the client 
					double number = inputFromClient.readDouble();  
					// Compute answer
					int answer = Server.isPrime(number);
					// Send area back to the client    
					outputToClient.write(answer); 
					Platform.runLater(() -> {    
						ta.appendText("Number received from client: "   
					+ number + '\n');  
						if (answer == 0) {
						ta.appendText(number + " is not a prime number"+ '\n');
						} else {
							ta.appendText(number + " is a prime number"+ '\n');
						}
						});   
					}
				}
			catch(IOException ex) {   
				ex.printStackTrace();
				
				}   
		}).start();
	}
	 public static void main(String[] args) {  
		  launch(args);  
		  Client client = new Client();
		  System.out.println("hi");
		  client.main(args);
	 }
}
