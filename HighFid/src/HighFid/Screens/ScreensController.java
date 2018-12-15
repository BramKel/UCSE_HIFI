package HighFid.Screens;

//Personal Imports
import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.SportDetail.SportDetailController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

//Java Imports
import java.util.HashMap;

/**
 * Class ScreensController
 * Controller that allows switching between screens
 *
 * @author Tim Mesotten
 */
public class ScreensController extends StackPane {

    //Private members
    private Model _model;
    private HashMap<String, Node> screens = new HashMap<>();

    //Update components when neccesary
    private boolean newLogin;

    /**
     * Controller
     * Creates a new ScreensController
     */
    public ScreensController(Model model) {
        super();
        _model = model;
        newLogin = true;

        //ADD NEW SCREENS HERE
        loadScreen("Login", "Login/Login.fxml");
        loadScreen("MainMenu", "MainMenu/MainMenu.fxml");
        loadScreen("Profile", "Profile/Profile.fxml");
    }

    //Setter
    public void setLogin(boolean login) {
        newLogin = login;
    }

    /**
     * Public function getScreen
     * Retrieve a specific screen
     *
     * @param name the name of the screen
     * @return the screen that corresponds to the given name
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Public function loadScreen
     * Load FXML-file and add screen to the collection
     *
     * @param name the name of the screen
     * @param resource the FXML-file of the screen
     * @return true if screen was added, else false
     */
    private boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            screens.put(name, loadScreen);
            return true;
        } catch(Exception e) {
            System.out.println("ScreensController: loadScreen, error message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Public function setScreen
     * Display the required screen
     *
     * @param name the name of the screen
     * @return true if screen is successfully displayed, else false
     */
    private boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(200), t -> {
                            getChildren().remove(0);                    //remove the displayed screen
                            getChildren().add(0, screens.get(name));     //add the screen
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!");
            return false;
        }
    }

    /**
     * Public function unloadScreen
     * Removes a given screen from the collection
     *
     * @param name the name of the screen
     * @return true if screen was successfully removed, else false
     */
    private boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    //ADD NAVIGATION METHODS HERE
    public void showLogin() {
        setScreen("Login");
    }

    public void showMainMenu(){
        setScreen("MainMenu");
    }

    public void ShowSportDetail(String name) {
        try{
            Sport s = _model.sportByName(name);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SportDetail/SportDetail.fxml"));
            loader.load();
            SportDetailController contr = (SportDetailController) loader.getController();
            contr.ShowSport(s);
            loadScreen("SportDetail", "SportDetail/SportDetail.fxml");
            setScreen("SportDetail");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void showProfile() {
        if(newLogin){
            //SET LOGIN TRIGGER
            unloadScreen("Profile");
            loadScreen("Profile", "Profile/Profile.fxml");
            System.out.println("Reloaded profile page");
            newLogin = false;
        }
        setScreen("Profile");
    }
}
