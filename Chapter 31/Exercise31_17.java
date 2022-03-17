/*
 * Kevin Drake
 * 3/17/22
 * This program calculates the total amount youll have on a loan after x amount of years.
 * Can use Button to calculate or MenuItem, or MenuItem to exit program.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Exercise31_17 extends Application {
		TextField tfAmount = new TextField();
		TextField tfYears = new TextField();
		TextField tfInterest = new TextField();
		TextField tfFuture = new TextField();
	@Override
	public void start(Stage stage) {
		BorderPane bp = new BorderPane();
		HBox hBox1 = new HBox(10);
		HBox hBox2 = new HBox(10);
		VBox vBox1 = new VBox(21);
		VBox vBox2 = new VBox(15);
		MenuBar menuBar = new MenuBar();
		Menu menuOperation = new Menu("Operation");
		MenuItem miCalculate = new MenuItem("Calculate");
		MenuItem miExit = new MenuItem("Exit");
		
		Button btCalculate = new Button("Calculate");
		bp.setTop(menuBar);
		bp.setCenter(hBox1);
		bp.setBottom(hBox2);
		hBox1.getChildren().addAll(vBox1, vBox2);
		
		hBox1.setAlignment(Pos.CENTER);
		vBox1.setAlignment(Pos.CENTER);
		vBox2.setAlignment(Pos.CENTER);
		hBox2.setAlignment(Pos.BOTTOM_RIGHT);
		
		menuBar.getMenus().add(menuOperation);
		menuOperation.getItems().addAll(miCalculate, miExit);
		
		vBox1.getChildren().addAll(new Label("Investment Amount: "), new Label("Number of Years: "),
				new Label("Annual Interest Rate: "), new Label("Future Value: "));
		vBox2.getChildren().addAll(tfAmount, tfYears, tfInterest, tfFuture);
		hBox2.getChildren().add(btCalculate);
		
		tfAmount.setPrefColumnCount(10);
		tfYears.setPrefColumnCount(10);
		tfInterest.setPrefColumnCount(10);
		tfFuture.setPrefColumnCount(10);
		
		Scene scene = new Scene(bp, 450, 350);
		stage.setTitle("Exercise 31_17");
		stage.setScene(scene);
		stage.show();
		
		miCalculate.setOnAction(e -> calculate());
		miExit.setOnAction(e -> System.exit(0));
		btCalculate.setOnAction(e -> calculate());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void calculate() {
		double amount = Double.parseDouble(tfAmount.getText());
		double years = Double.parseDouble(tfYears.getText());
		
		double interest = Double.parseDouble(tfInterest.getText());
		interest = interest / 100;
		interest = interest / 12;
		interest = interest + 1;
		years = years * 12;
		double future = amount * Math.pow(interest, years);
		String result = String.format("$%.2f", future);
		tfFuture.setText(result);
	}
}
