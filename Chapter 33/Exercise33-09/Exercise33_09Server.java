/*
 * Kevin Drake
 * 3/14/22
 * This is the Server program for messaging back and forth from the Server to client
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.*;

public class Exercise33_09Server extends Application {
	private TextArea taServer = new TextArea();
	private TextArea taClient = new TextArea();
	private DataInputStream serverInput;
	private DataOutputStream serverOutput;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		taServer.setWrapText(true);
		taClient.setWrapText(true);
		taServer.setEditable(false);

		BorderPane pane1 = new BorderPane();
		pane1.setTop(new Label("History"));
		pane1.setCenter(new ScrollPane(taServer));
		BorderPane pane2 = new BorderPane();
		pane2.setTop(new Label("New Message"));
		pane2.setCenter(new ScrollPane(taClient));

		VBox vBox = new VBox(5);
		vBox.getChildren().addAll(pane1, pane2);

		// Create a scene and place it in the stage
		Scene scene = new Scene(vBox, 400, 400);
		primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		taClient.requestFocus();
		taClient.setOnKeyPressed(ov -> {
			try {
				if (ov.getCode() == KeyCode.ENTER) {
					String messageTo = taClient.getText().trim();
					taClient.clear();
					
					serverOutput.writeUTF(messageTo);
					taServer.appendText("\nS: " + messageTo);
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		new Thread(() -> {
			try {
				ServerSocket ss = new ServerSocket(8000);
				Socket socket = ss.accept();
				taServer.appendText("Connected to Client on: " + new Date());
				serverInput = new DataInputStream(socket.getInputStream());
				serverOutput = new DataOutputStream(socket.getOutputStream());
				
				while (true) {
					String messageFrom = serverInput.readUTF();
					taServer.appendText("\nC: " + messageFrom);
				}

			}
			catch (IOException io) {
				io.printStackTrace();
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
