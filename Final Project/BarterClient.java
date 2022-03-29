/*
 * Kevin Drake
 * 3/28/22
 * This is the client class to be ran simultaneously with the server class to run the program.
 */
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.io.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarterClient extends Application {
	Account mainAccount;
	ObjectInputStream input;
	ObjectOutputStream output;
	Socket socket;
	LogInPane logIn = new LogInPane();
	AccountPane account = new AccountPane();
	CreateAccountPane create = new CreateAccountPane();
	Stage stage;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		Scene scene = new Scene(logIn, 350, 250);
		stage.setTitle("Barter");
		stage.setScene(scene);
		stage.show();
		try {
			socket = new Socket("localHost", 8000);
			logIn.btSubmit.setOnAction(e ->{
			new Thread(new LogInTask()).start();
			});
			logIn.btCreate.setOnAction(e -> {
				Platform.runLater(new Runnable() {
					public void run() {
						stage.setScene(new Scene(create, 350, 250));
					}
				});
			});
			logIn.btCancel.setOnAction(e -> System.exit(0));
			create.btSubmit.setOnAction(e -> {
				try {
					output = new ObjectOutputStream(socket.getOutputStream());
					String name = create.tfName.getText();
					String pass = create.tfPass.getText();
					output.writeUTF(name);
					output.writeUTF(pass);
					output.flush();
					input = new ObjectInputStream(socket.getInputStream());
					mainAccount = (Account)(input.readObject());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							account.setAccount(mainAccount);
							stage.setScene(new Scene(account, 600, 400));
						}
					});
				}
				
				catch (IOException l) {
					l.printStackTrace();
				}
				catch (Exception ev) {
					ev.printStackTrace();
				}
			});
			create.btCancel.setOnAction(e -> System.exit(1));
			account.btAdd.setOnAction(e -> {
				String itemName = account.tfName.getText();
				String itemScript = account.tfDescription.getText();
				String price = account.tfPrice.getText();
				double itemPrice = 0;
				itemPrice = Double.parseDouble(price);
				account.acc.addItem(itemName, itemScript, itemPrice);
				account.tabs.getTabs().clear();
				account.setAccount(mainAccount);
				account.tfName.clear();
				account.tfDescription.clear();
				account.tfPrice.clear();
			});
			account.btRemove.setOnAction(e -> {
				mainAccount.removeItem(account.tabs.getSelectionModel().getSelectedIndex());
				account.tabs.getTabs().clear();
				account.setAccount(mainAccount);
			});
			account.btLogOut.setOnAction(e -> {
				try {
					output.writeObject(mainAccount);
					System.exit(2);
					stage.close();
				}
				catch (IOException eb) {
					eb.printStackTrace();
				}
			});
		}
		catch (IOException io) {
			io.printStackTrace();
		}
	}
	class LogInTask implements Runnable {
		@Override 
		public void run() {
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				String accountName = logIn.tfName.getText();
				String accountPass = logIn.tfPass.getText();

				output.writeUTF(accountName);
				output.writeUTF(accountPass);
				output.flush();

				input = new ObjectInputStream(socket.getInputStream());
				Object o = input.readObject();
				Account a = (Account)o;
				mainAccount = a;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						account.setAccount(a);
						stage.setScene(new Scene(account, 600, 400));
					}
				});
			}
			catch (EOFException ox) {

			}
			catch (IOException io) {
				io.printStackTrace();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	class LogInPane extends BorderPane {
		Label lblName = new Label("Account Name: ");
		Label lblPass = new Label("Password: ");
		TextField tfName = new TextField();
		TextField tfPass = new TextField();
		Button btSubmit = new Button("Submit");
		Button btCreate = new Button("Create Account");
		Button btCancel = new Button("Cancel");
		VBox vbLabels = new VBox(10);
		VBox vbText = new VBox(10);
		HBox hbPanes = new HBox(10);
		HBox hbButtons = new HBox(10);

		public LogInPane() {
			vbLabels.setAlignment(Pos.CENTER);
			vbLabels.getChildren().addAll(lblName, lblPass);
			vbText.setAlignment(Pos.CENTER);
			vbText.getChildren().addAll(tfName, tfPass);
			hbPanes.getChildren().addAll(vbLabels, vbText);
			hbPanes.setAlignment(Pos.CENTER);
			hbButtons.getChildren().addAll(btSubmit, btCreate, btCancel);
			hbButtons.setAlignment(Pos.CENTER);
			setCenter(hbPanes);
			setBottom(hbButtons);
			setPadding(new Insets(15));
		}
	}
	class AccountPane extends BorderPane {
		Account acc = null;
		Label lblName = new Label();
		Label lblDate = new Label();
		Label lblItemCount = new Label();
		VBox vbLabels = new VBox(50);
		TabPane tabs = new TabPane();
		Button btAdd = new Button("Add Item");
		Button btRemove = new Button("Remove Item");
		Button btLogOut = new Button("Log Out");
		HBox hbButtons = new HBox(15);
		HBox hbText = new HBox(15);
		HBox hbLabels = new HBox(90);
		TextField tfName = new TextField();
		TextField tfDescription = new TextField();
		TextField tfPrice = new TextField();
		VBox vbAdd = new VBox(10);

		VBox vbTabs = new VBox(15);
		Label lName = new Label();
		TextArea taDescription = new TextArea();
		Label lPrice = new Label();

		public AccountPane() {
			vbLabels.setAlignment(Pos.TOP_LEFT);
			vbLabels.setPadding(new Insets(15, 15, 15, 15));
			vbLabels.getChildren().addAll(lblName, lblDate, lblItemCount);

			setLeft(vbLabels);
			setRight(tabs);
			setBottom(vbAdd);
			setPadding(new Insets(15, 15, 15, 15));
			tabs.setPrefSize(200, 150);
			taDescription.setPrefSize(125,  80);

			vbAdd.setAlignment(Pos.CENTER);
			hbButtons.setAlignment(Pos.CENTER);
			hbText.setAlignment(Pos.CENTER);
			hbLabels.setAlignment(Pos.CENTER);
			vbTabs.setAlignment(Pos.CENTER);
			vbTabs.getChildren().addAll(lName, taDescription, lPrice);

			hbButtons.getChildren().addAll(btAdd, btRemove, btLogOut);
			hbLabels.getChildren().addAll(new Label("Item Name"), new Label("Item Description"), new Label("Item Price"));
			hbText.getChildren().addAll(tfName, tfDescription, tfPrice);
			vbAdd.getChildren().addAll(hbLabels, hbText, hbButtons);

		}
		public void setAccount(Account a) {
			acc = a;
			lblName.setText("Name: " + a.getAccountName());
			lblDate.setText("Joined: " + a.getDateCreated());
			lblItemCount.setText("Item Count: " + a.getItemCount());
			for (Item i: acc.getList()) {
				tabs.getTabs().add(new AccountTab(i));
			}

		}
	}
	class CreateAccountPane extends BorderPane {
		Label lblName = new Label("Account Name: ");
		Label lblPass = new Label("Password: ");
		TextField tfName = new TextField();
		TextField tfPass = new TextField();
		Button btSubmit = new Button("Submit");
		Button btCancel = new Button("Cancel");
		VBox vbLabels = new VBox(10);
		VBox vbText = new VBox(10);
		HBox hbPanes = new HBox(10);
		HBox hbButtons = new HBox(10);

		public CreateAccountPane() {
			vbLabels.setAlignment(Pos.CENTER);
			vbLabels.getChildren().addAll(lblName, lblPass);
			vbText.setAlignment(Pos.CENTER);
			vbText.getChildren().addAll(tfName, tfPass);
			hbPanes.getChildren().addAll(vbLabels, vbText);
			hbPanes.setAlignment(Pos.CENTER);
			hbButtons.getChildren().addAll(btSubmit, btCancel);
			hbButtons.setAlignment(Pos.CENTER);
			setCenter(hbPanes);
			setBottom(hbButtons);
			setPadding(new Insets(15));
		}

	}
	class AccountTab extends Tab {
		Label itemName = new Label();
		TextArea description = new TextArea();
		Label price = new Label();
		VBox vb = new VBox(15);

		public AccountTab(Item i) {
			super(i.getItemName());
			itemName.setText(i.getItemName());
			description.setText(i.getItemDescription());
			price.setText("$" + i.getPrice() + "0");
			vb.setPadding(new Insets(10, 10, 10, 10));
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(itemName, description, price);
			setContent(vb);
		}
	}
}

