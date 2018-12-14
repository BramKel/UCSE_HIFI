package HighFid.Screens.Login;

//Personal Imports
import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

//Java Imports
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
    @FXML
    private Label lblError;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setTextFill(Color.web("#F00"));
        lblError.setVisible(false);
    }

    /**
     * Public function setScreenParent
     * Sets the Controlling parent for navigation
     *
     * @param screenPage the controller that handles navigation
     */
    @Override
    public void setScreenParent(ScreensController screenPage) {
        _controller = screenPage;
    }

    //Setter
    public void setModel(Model model) {
        _model = model;
    }

    //FXML methods
    /**
     * Private method handleLoginButton
     * Handles the login process
     *
     * @param event unused
     */
    @FXML
    private void handleLoginButton(ActionEvent event) {
        String login = txtLogin.getText();
        String pass = txtPassword.getText();
        if(this._model.getProfile().checkProfile(login, pass)) {
            lblError.setVisible(false);
            txtLogin.setText("");
            txtPassword.setText("");
            if(_model.getProfile().fromJSON(_model.getProfile().getId().toString() + ".json")) {
                System.out.println("Logged in as: " + _model.getProfile().getfName() + " " + _model.getProfile().getlName());
                _controller.setLogin(true);
                showMainMenu();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fout");
                alert.setHeaderText("Het JSON-bestand kan niet worden gevonden, vraag hulp.");
                alert.showAndWait();
            }
        } else {
            lblError.setVisible(true);
            txtLogin.setText("");
            txtPassword.setText("");
        }
    }

    /**
     * Private method handleRegisterButton
     * Handles the registration process
     *
     * @param event unused
     */
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fout");
        alert.setHeaderText("Sorry, deze feature is niet uitgewerkt, probeer iets anders.");
        alert.showAndWait();
    }

    private void showMainMenu(){
        _controller.showMainMenu();
    }
}
