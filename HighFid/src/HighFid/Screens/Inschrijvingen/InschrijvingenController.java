package HighFid.Screens.Inschrijvingen;

import HighFid.Model.Model;
import HighFid.Model.Profile;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

public class InschrijvingenController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;

    @FXML
    ImageView backBtn;

    @FXML
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6;

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

        //dataTable.lookup("TableHeaderRow").setVisible(false);


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
    }

    @FXML void checkAll(ActionEvent event) {
        cb1.setSelected(true);
        cb2.setSelected(true);
        cb3.setSelected(true);
        cb4.setSelected(true);
        cb5.setSelected(true);
        cb6.setSelected(true);
    }

    @FXML void checkNone(ActionEvent event) {
        cb1.setSelected(false);
        cb2.setSelected(false);
        cb3.setSelected(false);
        cb4.setSelected(false);
        cb5.setSelected(false);
        cb6.setSelected(false);
    }

    @FXML void checkPaid(ActionEvent event) {
        cb1.setSelected(true);
        cb2.setSelected(true);
        cb3.setSelected(false);
        cb4.setSelected(true);
        cb5.setSelected(true);
        cb6.setSelected(false);
    }

    @FXML void checkNotPaid(ActionEvent event) {
        cb1.setSelected(false);
        cb2.setSelected(false);
        cb3.setSelected(true);
        cb4.setSelected(false);
        cb5.setSelected(false);
        cb6.setSelected(true);
    }

    @FXML void sendPush(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Verzonden");
        alert.setHeaderText("Pushbericht succesvol verzonden!");
        alert.showAndWait();
    }

    @FXML
    private void showMainMenu(ActionEvent event){_controller.showMainMenu();}
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview(); }
    @FXML
    private void showCalendar(ActionEvent event){_controller.showCalendar(0); }

}
