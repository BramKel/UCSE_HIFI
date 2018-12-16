package HighFid.Screens.ChallengeOverview;

import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ChallengeOverviewController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;

    @FXML GridPane ourGrid;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url            the URL of the screen
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

        String[] achievementNames = _model.getAchNames();
        String[] achievementDesc = _model.getAchDesc();
        boolean[] achievementGot = _model.getAchGot();

        for (int i = 0; i < achievementGot.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            ourGrid.getColumnConstraints().add(col);
        }

        for (int i = 0; i < achievementGot.length; i++) {
            Image ig;

            if(achievementGot[i]) {
                ig = new Image("/challenges/gold_trophy.png");
            } else {
                ig = new Image("/challenges/grey_trophy.png");
            }

            ImageView iv = new ImageView(ig);
            Tooltip.install(iv, new Tooltip(achievementDesc[i]));
            Text inftx = new Text(achievementNames[i]);
            StackPane pane = new StackPane();

            pane.getChildren().add(iv);
            pane.getChildren().add(inftx);

            pane.setAlignment(Pos.BOTTOM_CENTER);

            final int id = i;

            pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    _controller.showAchievementDetail(id);
                }
            });

            int row = i / 3;
            int col = i % 3;


            ourGrid.add(pane, col, row, 1, 1);


        }
    }

    @FXML
    private void showProfile(ActionEvent event) {
        _controller.showProfile();
    }
    @FXML
    public void showMainMenu(ActionEvent event) {
        _controller.showMainMenu();
    }
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview();}
}