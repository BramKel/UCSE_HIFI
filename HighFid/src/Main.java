package HighFid;

//Personal Imports
import HighFid.Model.FileIO.JsonIO;
import HighFid.Model.Model;

//Java Imports
import HighFid.Model.Sport;
import HighFid.Screens.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;

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

        Sport s = new Sport("Atletiek","Moeder der sporten!","Studentensport Limburg in samenwerking met Atletiekclub ADD Diepenbeek: Uithouding, kracht, snelheid! Blijf en/of word atletisch door de moeder der sporten: atletiek!\n" +
                "Voor compititie-atleten maar ook voor recreanten.", "Vast avondprogramma", "Beginners tot gevorderden", "Vanaf oktober 2018 tot 31 mei 2019\n" +
                "Geen lessen tijdens de schoolvakanties, geen lessen op feest- en brugdagen", 0,2);
        s.AddSession(DayOfWeek.TUESDAY, new Time(19,0,0), new Time(20,30,0));
        s.AddSession(DayOfWeek.THURSDAY, new Time(19,0,0), new Time(20,30,0));
        s.AddSession(DayOfWeek.FRIDAY, new Time(19,0,0), new Time(20,30,0));
        s.AddSession(DayOfWeek.WEDNESDAY, new Time(19,0,0), new Time(20,30,0));
        try{
            JsonIO.saveJSONFile("sports", s.toJSON());
        }catch(Exception e) {

        }
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
