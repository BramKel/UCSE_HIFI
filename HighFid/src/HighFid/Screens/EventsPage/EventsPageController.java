package HighFid.Screens.EventsPage;

//Personal imports
import HighFid.Model.Event;
import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

//Java Imports
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class MainMenuController
 * Handles MainMenu screen actions
 */
public class EventsPageController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;
    private URL url;

    @FXML
    Pane campusrunPane, zweefvliegenPane;
    @FXML
    TextField searchField;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        campusrunPane.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.ShowEventDetail("Campusrun");
            }
        });
        zweefvliegenPane.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.ShowEventDetail("Zweefvliegen");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ApplySearchTerm(newValue);
        });
    }

    private void ApplySearchTerm(String search) {
        Event[] events= _model.getEvents();
        if(search.length() < 3) {
            for(int i = 0; i < events.length; i++) {
                showEvent(events[i].naam);
            }
            return;
        }
        for(int i = 0; i < events.length; i++) {
            if(!events[i].ContainsSearchTerm(search))
                hideEvent(events[i].naam);
            else
                showEvent(events[i].naam);
        }
        System.out.println(search);
    }
    private void hideEvent(String name ) {
        if(name.compareTo("Campusrun") == 0)
        {
            campusrunPane.setVisible(false);
        }
        if(name.compareTo("Zweefvliegen") == 0) {
            zweefvliegenPane.setVisible(false);
        }

        PlaceBlocks();
    }
    private void PlaceBlocks() {
        double yCoor = 10;
        if(campusrunPane.isVisible())
        {
            campusrunPane.setLayoutY(yCoor);
            yCoor += 140;
        }
        if(zweefvliegenPane.isVisible()) {
            zweefvliegenPane.setLayoutY(yCoor);
            yCoor +=140;
        }

    }
    private void showEvent(String name) {
        if(name.compareTo("Campusrun") == 0)
        {
            campusrunPane.setVisible(true);
        }
        if(name.compareTo("Zweefvliegen") == 0) {
            zweefvliegenPane.setVisible(true);
        }

        PlaceBlocks();
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
    private void showEventsPage(ActionEvent event) {_controller.showEventsPage();}

}
