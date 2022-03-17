/*
 * Kevin Drake
 * 3/17/22
 * Rewrote this program from book to add RadioButtons and when buttons are clicked. They're set to 
 * 	move the tabs in the tabPane around on the Orientation of the Sides.
 */
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TabPaneDemo extends Application {   
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("Line");
    StackPane pane1 = new StackPane();
    pane1.getChildren().add(new Line(10, 10, 80, 80));
    tab1.setContent(pane1);
    Tab tab2 = new Tab("Rectangle");
    tab2.setContent(new Rectangle(10, 10, 200, 200));
    Tab tab3 = new Tab("Circle");
    tab3.setContent(new Circle(50, 50, 20));    
    Tab tab4 = new Tab("Ellipse");
    tab4.setContent(new Ellipse(10, 10, 100, 80));
    tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
    BorderPane bp = new BorderPane();
    bp.setCenter(tabPane);
    RadioButton rbTop = new RadioButton("Top");
    RadioButton rbLeft = new RadioButton("Left");
    RadioButton rbRight = new RadioButton("Right");
    RadioButton rbBottom = new RadioButton("Bottom");
    HBox hBox = new HBox(10);
    hBox.getChildren().addAll(rbTop, rbLeft, rbRight, rbBottom);
    bp.setBottom(hBox);
    rbTop.setOnAction(e -> {
    	tabPane.setSide(Side.TOP);
    	rbLeft.setSelected(false);
    	rbRight.setSelected(false);
    	rbBottom.setSelected(false);
    });
    rbLeft.setOnAction(e -> {
    	tabPane.setSide(Side.LEFT);
    	rbTop.setSelected(false);
    	rbRight.setSelected(false);
    	rbBottom.setSelected(false);
    });
    rbRight.setOnAction(e -> {
    	tabPane.setSide(Side.RIGHT);
    	rbLeft.setSelected(false);
    	rbTop.setSelected(false);
    	rbBottom.setSelected(false);
    });
    rbBottom.setOnAction(e -> {
    	tabPane.setSide(Side.BOTTOM);
    	rbTop.setSelected(false);
    	rbRight.setSelected(false);
    	rbLeft.setSelected(false);
    });

    Scene scene = new Scene(bp, 300, 250);  
    primaryStage.setTitle("DisplayFigure"); // Set the window title
    primaryStage.setScene(scene); // Place the scene in the window
    primaryStage.show(); // Display the window
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   * line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}