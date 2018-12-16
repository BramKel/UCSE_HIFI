package HighFid.Screens.MainMenu;

//Personal imports
import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

//Java Imports
import java.awt.event.MouseEvent;
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
    @FXML Pane atletiekPane;
    @FXML Pane basketPane;
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
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ApplySearchTerm(newValue);
        });
    }
    private void ApplySearchTerm(String search) {
        Sport[] sports = _model.getSports();
        if(search.length() < 3) {
            for(int i = 0; i < sports.length; i++)
                showSport(sports[i].name);
            return;
        }
        for(int i = 0; i < sports.length; i++) {
            if(!sports[i].ContainsSearchTerm(search))
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
            basketPane.setLayoutY(10);
        }
        if(name.compareTo("Basketbal") == 0) {
            basketPane.setVisible(false);
        }
    }
    private void showSport(String name) {
        if(name.compareTo("Atletiek") == 0)
        {
            atletiekPane.setVisible(true);
            basketPane.setLayoutY(150);
        }
        if(name.compareTo("Basketbal") == 0) {
            basketPane.setVisible(true);
        }
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

}
