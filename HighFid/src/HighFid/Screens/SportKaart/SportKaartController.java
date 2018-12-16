package HighFid.Screens.SportKaart;

import HighFid.Model.Model;
import HighFid.Model.SportKaart;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SportKaartController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;

    //FXML members
    @FXML
    private Pane pnActive;
    @FXML
    private Label lblStatus, lblRequested, lblValidUntil, lblMut;
    @FXML
    private Pane pnInactive;
    @FXML
    private Pane pnPopup, pnPopupText;
    @FXML
    private ImageView backBtn;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);
        backBtn.setOnMouseClicked(mouseEvent -> _controller.goToPreviousScreen());
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
        if(_model.getProfile().getSportKaart().getSkStatus() == SportKaart.SK_STATUS.ACTIVE) {
            lblStatus.setText("Actief");
            lblStatus.setTextFill(Color.web("#267533"));
            pnActive.setVisible(true);
            pnInactive.setVisible(false);
            lblRequested.setText(_model.getProfile().getSportKaart().getActivated());
            lblValidUntil.setText(_model.getProfile().getSportKaart().getValidUntil());
            lblMut.setText(_model.getProfile().getSportKaart().getMut());
        } else {
            lblStatus.setText("Niet aangevraagd");
            lblStatus.setTextFill(Color.web("#F00"));
            pnActive.setVisible(false);
            pnInactive.setVisible(true);
        }
    }

    @FXML
    public void openPopup(ActionEvent event) {
        pnPopup.setVisible(true);
        pnPopupText.setVisible(true);
    }

    @FXML
    public void closePopup(ActionEvent event) {
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);
    }
}
