package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class serves as a driver class of our ateam Milk Weight program
 * 
 * @author Xuancheng Tu
 *
 */
public class Main extends Application {

  // The instance of GUI class is responsible for displaying GUI and supporting user control.
  private GUI gui = new GUI();

  /**
   * Create elements on the stage, add all of them to the stage, show the stage.
   *
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane main = new BorderPane();
    BorderPane top = new BorderPane();
    VBox center = new VBox();
    // allow users to take actions on the left part of the dashboard. Supported actions include
    // load a local .csv file, add/edit/remove milk weight records, get a report, and output
    // data or report to a local file.
    center.getChildren().addAll(gui.loadFile(), gui.addEditRemove(), gui.getReport(),
        gui.fileOutput());
    Label title = new Label("Milk Weight Manager Dashboard");
    top.setCenter(title);
    main.setTop(top);
    main.setCenter(center);
    // allow users to view the report they most recently generated on the right part of the
    // dashboard
    main.setRight(gui.reportView());
    Scene mainScene = new Scene(main);
    primaryStage.setTitle("Milk Weight Manager");
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  /**
   * The main method launches the program
   */
  public static void main(String[] args) {
    launch(args);

  }

}
