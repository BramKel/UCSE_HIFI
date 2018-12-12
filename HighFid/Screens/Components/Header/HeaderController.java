package HighFid.Screens.Components.Header;

import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable, ControlledScreen {

    @FXML
    private ImageView imLogo;

    @FXML
    public void initialize(){

    }
    @Override
    public void setScreenParent(ScreensController screenPage) {

    }

    @Override
    public void setModel(Model model) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
