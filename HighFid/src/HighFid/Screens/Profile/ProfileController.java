package HighFid.Screens.Profile;

import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;

    @FXML
    private Label lblName;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblOrg;
    @FXML
    private ImageView imSTUD_SP;
    @FXML
    private ImageView imSTUD_NO_SP;
    @FXML
    private ImageView imOTHER;
    @FXML
    private Region regNONE;
    @FXML
    private Button btnSK, btnSport;

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
        lblName.setText(_model.getProfile().getfName() + " " + _model.getProfile().getlName());
        lblAge.setText(_model.getProfile().getAge());
        lblOrg.setText(_model.getProfile().getOrg());
        switch(_model.getProfile().getId()) {
            case STUD_SP:
                imSTUD_SP.setVisible(true);
                imSTUD_NO_SP.setVisible(false);
                imOTHER.setVisible(false);
                regNONE.setVisible(false);
                break;
            case STUD_NO_SP:
                imSTUD_SP.setVisible(false);
                imSTUD_NO_SP.setVisible(true);
                imOTHER.setVisible(false);
                regNONE.setVisible(false);
                break;
            case COORD:
                imSTUD_SP.setVisible(false);
                imSTUD_NO_SP.setVisible(false);
                imOTHER.setVisible(true);
                regNONE.setVisible(false);
                btnSK.setVisible(false);
                break;
            case SPORTLK:
                imSTUD_SP.setVisible(false);
                imSTUD_NO_SP.setVisible(false);
                imOTHER.setVisible(true);
                regNONE.setVisible(false);
                btnSK.setVisible(false);
                btnSport.setVisible(true);
                break;
            case NONE:
                imSTUD_SP.setVisible(false);
                imSTUD_NO_SP.setVisible(false);
                imOTHER.setVisible(false);
                regNONE.setVisible(true);
                break;
        }
    }

    /**
     * Private method handleEditButton
     * Handles the edit process
     *
     * @param event unused
     */
    @FXML
    private void handleEditButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fout");
        alert.setHeaderText("Sorry, deze feature is niet uitgewerkt, probeer iets anders.");
        alert.showAndWait();
    }


    @FXML
    public void showMainMenu(ActionEvent event) {
        _controller.showMainMenu();
    }

    @FXML
    public void showSportKaart(ActionEvent event) {
        _controller.showSportKaart();
    }
    @FXML
    public void showEnrolment(ActionEvent event) {
        _controller.showEnrollment();
    }
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview(); }

    @FXML
    public void showLogin(ActionEvent event) {
        _controller.showLogin();
        _model.getProfile().logout();
    }
    @FXML
    private void showCalendar(ActionEvent event){_controller.showCalendar(0); }
    @FXML
    private void showEventsPage(ActionEvent event) {_controller.showEventsPage();}

    @FXML
    private void showSport(ActionEvent event) { _controller.ShowSportDetail("Basketbal"); }
}
