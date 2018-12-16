package HighFid.Screens.MainMenu;

//Personal imports
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
public class MainMenuController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;
    private URL url;

    @FXML AnchorPane anchorPane;
    @FXML Pane addPane;
    @FXML Pane atletiekPane, badmintonPane;
    @FXML Pane basketPane;
    @FXML
    TextField searchField;
    @FXML
    Pane filterPane;
    @FXML
    Button filterBtn;
    @FXML
    CheckBox djopCheck, hasseltCheck;
    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPane.setVisible(false);

        atletiekPane.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.ShowSportDetail("Atletiek");
            }
        });
        basketPane.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.ShowSportDetail("Basketbal");
            }
        });
        badmintonPane.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.ShowSportDetail("Badminton");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ApplySearchTerm(newValue);
        });
        filterBtn.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                boolean status = filterPane.isVisible();
                filterPane.setVisible(!status);
            }
        });
        djopCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                ApplySearchTerm(searchField.getText());
            }
        });
        hasseltCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                ApplySearchTerm(searchField.getText());
            }
        });
        filterPane.setVisible(false);
    }
    private boolean isOutFiltered(Sport s) {
        if(djopCheck.isSelected() && s.CheckPlace("Diepenbeek")){
            return false;
        }
        if(hasseltCheck.isSelected() && s.CheckPlace("Hasselt"))
            return false;
        return true;
    }

    private void ApplySearchTerm(String search) {
        Sport[] sports = _model.getSports();
        if(search.length() < 3) {
            for(int i = 0; i < sports.length; i++) {
                if(!isOutFiltered(sports[i]))
                    showSport(sports[i].name);
                else
                    hideSport(sports[i].name);
            }
            return;
        }
        for(int i = 0; i < sports.length; i++) {
            if(!sports[i].ContainsSearchTerm(search) || isOutFiltered(sports[i]))
                hideSport(sports[i].name);
            else
                showSport(sports[i].name);
        }
        System.out.println(search);
    }
    private void hideSport(String name ) {
        if(name.compareTo("Atletiek") == 0)
        {
            atletiekPane.setVisible(false);
        }
        if(name.compareTo("Basketbal") == 0) {
            basketPane.setVisible(false);
        }
        if(name.compareTo("Badminton") == 0)
            badmintonPane.setVisible(false);

        PlaceBlocks();
    }
    private void PlaceBlocks() {
        double yCoor = 10;
        if(atletiekPane.isVisible())
        {
            atletiekPane.setLayoutY(yCoor);
            yCoor += 140;
        }
        if(basketPane.isVisible()) {
            basketPane.setLayoutY(yCoor);
            yCoor +=140;
        }
        if(badmintonPane.isVisible()) {
            badmintonPane.setLayoutY(yCoor);
            yCoor +=140;
        }

    }
    private void showSport(String name) {
        if(name.compareTo("Atletiek") == 0)
        {
            atletiekPane.setVisible(true);
        }
        if(name.compareTo("Basketbal") == 0) {
            basketPane.setVisible(true);
        }
        if(name.compareTo("Badminton") == 0)
            badmintonPane.setVisible(true);

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
    private void showCalendar(ActionEvent event){_controller.showCalendar(2); }

}
