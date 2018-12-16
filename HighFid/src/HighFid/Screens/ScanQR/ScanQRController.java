package HighFid.Screens.ScanQR;

import HighFid.Model.Enrolment;
import HighFid.Model.Event;
import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

public class ScanQRController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;
    private Event event;

    @FXML
    ImageView backBtn, imgQR;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backBtn.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.goToPreviousScreen();
            }
        });

        String path = "QR/scan.jpg";
        imgQR.setImage(new Image(path));
        imgQR.setVisible(true);


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
    @FXML
    private void showMainMenu(ActionEvent event){_controller.showMainMenu();}
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview(); }
    @FXML
    private void showCalendar(ActionEvent event){_controller.showCalendar(0); }
    @FXML
    private void scan(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ingescand");
        alert.setHeaderText("QR code ingescand! Michiel Marien is bevestigd als aanwezig.");
        alert.showAndWait();
    }
}
