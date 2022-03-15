// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
/*
 * Kevin Drake
 * 3/14/22
 * This is the server program for the client to send data and receive data form
 */
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();


  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
	  
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread(() -> {
    	try {
    		ServerSocket server = new ServerSocket(8000);
    		
    		ta.appendText("Server started on: " + new Date());
    		
    		Socket socket = server.accept();
    		ta.appendText("\nConnected to client on: " + new Date());
    		DataInputStream serverInput = new DataInputStream(
    		          socket.getInputStream());
    		DataOutputStream serverOutput = new DataOutputStream(
    		          socket.getOutputStream());
    		
    		while (true) {
    			double interest = serverInput.readDouble();
    			int numOfYears = serverInput.readInt();
    			double amount = serverInput.readDouble();
    			Loan loan = new Loan(interest, numOfYears, amount);
    			double monthly = loan.getMonthlyPayment();
    			double total = loan.getTotalPayment();
    			
    			serverOutput.writeDouble(monthly);
    			serverOutput.writeDouble(total);
    			
    			
    			ta.appendText("\nAnnual Interest Rate: " + interest + "\nNumber of Years: " + numOfYears + "\nLoan Amount: " + amount +
        				"\nMonthly Payment: " + monthly + "\nTotal Payment: " + total);
    		}
    	}
    	catch (IOException ex) {
    		ex.printStackTrace();
    		}
    }).start();
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
