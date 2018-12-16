package HighFid.Screens.EventEnrolment;

import HighFid.Model.*;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

public class EventEnrolmentController implements Initializable, ControlledScreen {
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
    Label prijsContent, dateContent;



    @FXML
    private Pane pnPopup, pnPopupText, pnPopupPayment;
    @FXML
    private Label lblPopupText;
    @FXML
    private Button btnPopupOk;

    /**
     * Public function initialize
     * Initialize the screen
     *
     * @param url the URL of the screen
     * @param resourceBundle the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backBtn.setOnMouseClicked(mouseEvent -> _controller.goToPreviousScreen());
        //dataTable.lookup("TableHeaderRow").setVisible(false);
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);
        btnPopupOk.setOnMouseClicked(mouseEvent -> _controller.goToPreviousScreen());
        pnPopupPayment.setVisible(false);
    }
    private void ShowImage(String name) {
        String path = "Events/" + name + ".png";

        imageView.setImage(new Image(path));
        double width = imageView.getFitWidth();
        double xCoor = (393 - width) / 2;
        imageView.setLayoutX(xCoor);
    }
    private String makePriceTxt(int prijsZK, int prijsMK) {
        String price = "€ " + prijsMK;
        boolean SK = _model.getProfile().getSportKaart().getSkStatus() == SportKaart.SK_STATUS.ACTIVE;
        if(SK) {
            price = "€ " + prijsZK;
        }
        return price;
    }


    public void ShowEvent(Event e) {
        this.event = e;
        title.setText("Inschrijven voor " + e.naam);
        ShowImage(e.naam);
        prijsContent.setText(makePriceTxt(e.prijsZK, e.prijsMK));
        dateContent.setText(e.getDateStr());
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
    public void handleEnrolment(ActionEvent event) {
        Enrolment e = new Enrolment(this.event.date, this.event);
        _model.getProfile().addEnrolment(e);

        if(_model.getProfile().getSportKaart().getSkStatus() == SportKaart.SK_STATUS.ACTIVE) {
            openPopup(null);
        } else {
            openPaymentPopup();
        }
    }

    public void openPaymentPopup() {
        pnPopup.setVisible(true);
        pnPopupPayment.setVisible(true);
    }

    @FXML
    public void closePaymentPopup(ActionEvent event) {
        pnPopup.setVisible(false);
        pnPopupPayment.setVisible(false);
    }

    @FXML
    public void switchPopup(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("De methode om te betalen is niet uitgewerkt, we simuleren nu een sucessvolle betaling...");
        alert.showAndWait();
        pnPopupPayment.setVisible(false);
        openPopup(null);
    }

    @FXML
    public void openPopup(ActionEvent event) {
        pnPopup.setVisible(true);
        pnPopupText.setVisible(true);
        lblPopupText.setText("Inschrijving voor " + this.event.naam);
    }
}
