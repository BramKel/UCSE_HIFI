package HighFid;

//Personal Imports
import HighFid.Model.Model;

//Java Imports
import HighFid.Screens.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * HighFid
 * Main class for the application
 *
 * @author Tim Mesotten
 */
public class Main extends Application {

    //Private Members
    private ScreensController _controller;

    /**
     * Constructor
     * Creates a new Main
     */
    public Main() {
        _controller = new ScreensController(new Model());
        _controller.showLogin();

        String path = "Sports/Atletiek.png";
        Image img = new Image(path);
    }

    /**
     * Public function start
     * Creates the scene of the application
     *
     * @param primaryStage the stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        root.getChildren().addAll(_controller);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Studentensport Limburg");
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
