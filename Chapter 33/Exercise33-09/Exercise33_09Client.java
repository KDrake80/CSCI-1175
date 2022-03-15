/*
 * Kevin Drake
 * 3/14/22
 * This is the client program for messaging back and forth from the Server to client
 */
import javafx.application.Application;
import static javafx.application.Application.launch;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Exercise33_09Client extends Application {
	private TextArea taServer = new TextArea();
	private TextArea taClient = new TextArea();
	private DataInputStream clientInput;
	private DataOutputStream clientOutput;

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
		primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		// To complete later
		taClient.requestFocus();
		taClient.setOnKeyPressed(ov -> {
			if (ov.getCode() == KeyCode.ENTER) {
				try {
					String messageTo = taClient.getText().trim();
					taClient.clear();
					clientOutput.writeUTF(messageTo);
					taServer.appendText("\nC: " + messageTo);
				}
				catch (IOException io) {
					io.printStackTrace();
				}
			}
		});
		new Thread(() -> {
			try {
				Socket socket = new Socket("localHost", 8000);
				taServer.appendText("Connected to Host at: " + new Date());
				clientInput = new DataInputStream(socket.getInputStream());
				clientOutput = new DataOutputStream(socket.getOutputStream());

				while (true) {
					String messageFrom = clientInput.readUTF();
					taServer.appendText("\nS: " + messageFrom);
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
