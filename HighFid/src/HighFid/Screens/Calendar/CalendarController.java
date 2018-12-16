package HighFid.Screens.Calendar;

import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

public class CalendarController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;
    private Sport s;

    @FXML
    GridPane ourGrid;

    @FXML ImageView backArrow, forwardArrow;
    @FXML Label monthLabel;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String path1 = "calendar/back.png";
        backArrow.setImage(new Image(path1));
        String path2 = "calendar/forward.png";
        forwardArrow.setImage(new Image(path2));
    }

    public void buildCalendar(int month) {
        backArrow.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.showCalendar((month - 1) % 3);
            }
        });

        forwardArrow.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                _controller.showCalendar((month + 1) % 3);
            }
        });

        if (month == 0) {
            backArrow.setDisable(true);
            backArrow.setVisible(false);
        }

        if (month == 2) {
            forwardArrow.setDisable(true);
            forwardArrow.setVisible(false);
        }

        //Determines which day of the week the month starts.
        int offset = 0;
        int days = 31;

        if (month == 0) {
            monthLabel.setText("December 2018");
            offset = 5;
        }
        else if (month == 1) {
            monthLabel.setText("Januari 2019");
            offset = 1;
        } else {
            monthLabel.setText("Februari 2019");
            offset = 4;
            days = 28;
        }

        for (int i = 0; i < days; i++) {
            boolean session = i % 3 == 0;
            Image ig;

            if(session) {
                ig = new Image("/calendar/blue_dot.png");
            } else {
                ig = new Image("/calendar/grey_dot.png");
            }

            ImageView iv = new ImageView(ig);
            Text inftx = new Text("" + (i+1));
            StackPane pane = new StackPane();

            pane.getChildren().add(iv);
            pane.getChildren().add(inftx);

            pane.setAlignment(Pos.CENTER);

            int row = (i + offset) / 7;
            int col = (i + offset) % 7;

            ourGrid.add(pane, col, row, 1, 1);
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
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview(); }
    @FXML
    private void showCalendar(ActionEvent event){_controller.showCalendar(0); }
    @FXML
    private void showEventsPage(ActionEvent event) {_controller.showEventsPage();}

}
