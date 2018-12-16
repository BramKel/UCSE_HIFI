package HighFid.Screens.Enrolment;

import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EnrolmentController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;

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
        //TODO: Load enrolments dynamically
    }

    @FXML
    public void showMainMenu(ActionEvent event) {
        _controller.showMainMenu();
    }
    @FXML
    public void showProfile(ActionEvent event) {
        _controller.showProfile();
    }

}
