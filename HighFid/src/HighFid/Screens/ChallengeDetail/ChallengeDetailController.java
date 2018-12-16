package HighFid.Screens.ChallengeDetail;

import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class ChallengeDetailController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;
    private Sport s;
    @FXML
    ImageView imageView;
    @FXML
    Label title;
    @FXML
    ImageView backBtn;
    @FXML
    Label descriptionContent;
    @FXML
    AnchorPane anchorPane;
    @FXML
    VBox vbox;

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


    }
    private void ShowImage(String name) {
        String path = "Sports/" + name + ".png";

        imageView.setImage(new Image(path));
        double width = imageView.getFitWidth();
        double xCoor = (393 - width) / 2;
        imageView.setLayoutX(xCoor);
    }

    public void ShowDetail(int id) {
        String[] achievementNames = _model.getAchNames();
        String[] achievementDesc = _model.getAchDesc();
        boolean[] achievementGot = _model.getAchGot();


        title.setText(achievementNames[id]);
        descriptionContent.setText(achievementDesc[id]);

        String path;

        if (achievementGot[id]) {
            path = "challenges/gold_trophy.png";
        } else {
            path = "challenges/grey_trophy.png";
        }

        imageView.setImage(new Image(path));
        double width = imageView.getFitWidth();
        double xCoor = (393 - width) / 2;
        imageView.setLayoutX(xCoor);
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

}
