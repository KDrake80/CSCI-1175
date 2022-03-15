// Exercise31_01Client.java: The client sends the input to the server and receives
// result back from the server
/*
 * Kevin Drake
 * 3/14/22
 * This is client program to send/receive data form Server Program
 */
import javafx.application.Application;
import javafx.application.Platform;

import java.io.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.concurrent.*;
import java.net.*;

public class Exercise33_01Client extends Application {
  // Text field for receiving radius
  private TextField tfAnnualInterestRate = new TextField();
  private TextField tfNumOfYears = new TextField();
  private TextField tfLoanAmount = new TextField();
  private Button btSubmit= new Button("Submit");
  private DataInputStream clientInput;
  private DataOutputStream clientOutput;

  // Text area to display contents
  private TextArea ta = new TextArea();
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    GridPane gridPane = new GridPane();
    gridPane.add(new Label("Annual Interest Rate"), 0, 0);
    gridPane.add(new Label("Number Of Years"), 0, 1);
    gridPane.add(new Label("Loan Amount"), 0, 2);
    gridPane.add(tfAnnualInterestRate, 1, 0);
    gridPane.add(tfNumOfYears, 1, 1);
    gridPane.add(tfLoanAmount, 1, 2);
    gridPane.add(btSubmit, 2, 1);
    
    tfAnnualInterestRate.setAlignment(Pos.BASELINE_RIGHT);
    tfNumOfYears.setAlignment(Pos.BASELINE_RIGHT);
    tfLoanAmount.setAlignment(Pos.BASELINE_RIGHT);
    
    tfLoanAmount.setPrefColumnCount(5);
    tfNumOfYears.setPrefColumnCount(5);
    tfLoanAmount.setPrefColumnCount(5);
            
    BorderPane pane = new BorderPane();
    pane.setCenter(new ScrollPane(ta));
    pane.setTop(gridPane);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 400, 250);
    primaryStage.setTitle("Exercise31_01Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
   
    try {
    	Socket socket = new Socket("localHost", 8000);
    	clientInput = new DataInputStream(socket.getInputStream());
    	clientOutput = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
    	 ta.appendText(ex.toString() + '\n');
    }
    
    btSubmit.setOnAction(e -> {
    	try {
    		double interest = Double.parseDouble(tfAnnualInterestRate.getText());
    		int numOfYears = Integer.parseInt(tfNumOfYears.getText());
    		double amount = Double.parseDouble(tfLoanAmount.getText());
    		
    		clientOutput.writeDouble(interest);
    		clientOutput.writeInt(numOfYears);
    		clientOutput.writeDouble(amount);
    		
    		
    		double monthly = clientInput.readDouble();
    		double total = clientInput.readDouble();
    		//String message = String.format("Loan with total amount of $%.2f, Monthly payments of $%.2f, for a total payment of $%.2f", amount, monthly, total);
    		//ta.appendText(message);
    		ta.appendText("\nAnnual Interest Rate: " + interest + "\nNumber of Years: " + numOfYears + "\nLoan Amount: " + amount +
    				"\nMonthly Payment: " + monthly + "\nTotal Payment: " + total);
    	}
    	catch (IOException ex) {
    		ex.printStackTrace();
    	}
    });
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
