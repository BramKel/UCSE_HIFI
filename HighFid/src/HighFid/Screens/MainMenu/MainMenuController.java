package HighFid.Screens.MainMenu;

//Personal imports
import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

//Java Imports
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class MainMenuController
 * Handles MainMenu screen actions
 */
public class MainMenuController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;
    private URL url;

    @FXML AnchorPane anchorPane;
    @FXML Pane addPane;
    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(anchorPane.getMinWidth());
        addPane.setVisible(false);
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

    @FXML
    private void showProfile(ActionEvent event) {
        _controller.showProfile();
    }
}
