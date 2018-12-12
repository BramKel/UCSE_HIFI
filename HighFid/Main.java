package HighFid;

//Personal Imports
import HighFid.Model.Model;

//Java Imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HighFid
 * Main class for the application
 *
 * @author Tim Mesotten
 */
public class Main extends Application {

    private Model _model;

    public Main() {
        _model = new Model();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
