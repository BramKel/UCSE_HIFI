package HighFid.Screens.Login;

//Personal Imports
import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

//Java Imports
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class LoginController
 * Handles Login screen actions
 *
 * @author Tim Mesotten
 */
public class LoginController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;

    //FXML members
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtPassword;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Public function setScreenParent
     * Sets the Controlling parent for navigation
     *
     * @param screenParent the controller that handles navigation
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        _controller = screenParent;
    }

    //Setter
    public void setModel(Model model) {
        _model = model;
    }

    //FXML mthods
    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void showMainMenu(ActionEvent event){
        _controller.showMainMenu();
    }
}
