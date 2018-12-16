package HighFid.Screens.EventDetail;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class EventDetailController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;
    private Event event;
    @FXML
    ImageView imageView;
    @FXML
    Label title;
    @FXML
    ImageView backBtn;
    @FXML
    Label  descriptionContent, prijsContent, dateContent;
    @FXML
    AnchorPane anchorPane;
    @FXML
    VBox vbox;
    @FXML
    Button btnEnrol, btnUnenrol;

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
    private void ShowImage(String name) {
        String path = "Events/" + name + ".png";

        imageView.setImage(new Image(path));
        double width = imageView.getFitWidth();
        double xCoor = (393 - width) / 2;
        imageView.setLayoutX(xCoor);


    }
    private String makePriceTxt(int prijsZK, int prijsMK) {
        String p1 = "€ " + prijsZK;
        String p2 = "€ " + prijsMK;
        if(prijsMK == 0 && prijsZK == 0)
            return "Gratis";
        if(prijsZK == 0)
            p1 = "gratis";
        if(prijsMK == 0)
            p2 = "gratis";
        String str = "Prijs met kaart: " + p2 + "\nPrijs zonder kaart: " + p1;

        return str;
    }

    public void ShowEvent(Event e) {
        title.setText(e.naam);
        ShowImage(e.naam);
        descriptionContent.setText(e.beschrijving);
        prijsContent.setText(makePriceTxt(e.prijsZK, e.prijsMK));
        dateContent.setText(e.getDateStr());

        for(int i = 0; i < _model.getProfile().enrolments.length; ++i) {
            Enrolment enrol = _model.getProfile().enrolments[i];
            if(enrol.type == Enrolment.ENROLMENT_TYPE.EVENT && enrol.event.naam.equals(e.naam)) {
                btnEnrol.setVisible(false);
                btnUnenrol.setVisible(true);
                break;
            } else {
                btnEnrol.setVisible(true);
                btnUnenrol.setVisible(false);
            }
        }

        this.event = e;
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
    private void showEventEnrollment(ActionEvent event) {_controller.showEventEnrollment(this.event.naam);}
}
