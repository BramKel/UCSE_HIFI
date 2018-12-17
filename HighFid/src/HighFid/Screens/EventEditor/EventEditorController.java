package HighFid.Screens.EventEditor;

import HighFid.Model.Event;
import HighFid.Model.Model;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EventEditorController implements Initializable, ControlledScreen {

    //Private members
    private ScreensController _controller;
    private Model _model;
    private Event e;
    private boolean isNewEvent = false;

    @FXML
    private ImageView backBtn, imageView;
    @FXML
    private Label title;
    @FXML
    private TextField txtTitle, txtPriceSK, txtPriceNoSK, txtDate;
    @FXML
    private TextArea txtDescription;

    @FXML
    private Pane pnPopup, pnPopupText, pnDiscard, pnSuccess, pnDemo;
    @FXML
    private Label lblErrorMessage, lblSuccessTitle, lblSuccessText;
    @FXML
    private Button btnDiscard, btnSuccess;

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
        btnDiscard.setOnMouseClicked(mouseEvent -> _controller.goToPreviousScreen());
        btnSuccess.setOnMouseClicked(mouseEvent -> {
            if(isNewEvent) {
                _controller.showEventsPage();
            } else {
                _controller.goToPreviousScreen();
            }
        });
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);
        pnDiscard.setVisible(false);
        pnSuccess.setVisible(false);
        pnDemo.setVisible(false);
    }

    private void ShowImage(String name) {
        if(name.compareTo("") == 0)
            name = "default";
        String path = "Events/" + name + ".png";

        imageView.setImage(new Image(path));
        double width = imageView.getFitWidth();
        double xCoor = (393 - width) / 2;
        imageView.setLayoutX(xCoor);
    }

    public void showEvent(Event e) {
        this.e = e;
        if(e.naam.length() == 0)
            isNewEvent = true;
        if(isNewEvent) {
            title.setText("Nieuw evemenent aanmaken");
            txtTitle.setText("Ijsberen");
            txtTitle.setDisable(true);
            ShowImage("Ijsberen");
            showDemo();
        }
        else {
            title.setText("Evenement bewerken");
            txtTitle.setText(e.naam);
            ShowImage(e.naam);
        }
        txtDescription.setText(e.beschrijving);
        txtPriceSK.setText(Integer.toString(e.prijsMK));
        txtPriceNoSK.setText(Integer.toString(e.prijsZK));
        if(e.getDateStr().equals("01-01-1970")) {
            txtDate.setText("");
        } else {
            txtDate.setText(e.getDateStr());
        }
    }

    @FXML
    public void validate(ActionEvent event) {
        String errormessage = "De volgende gegevens zijn niet correct: \n";
        boolean error = false;
        if(txtTitle.getText().equals("")) {
            error = true;
            errormessage += "-Titel \n";
        }
        if(txtDescription.getText().equals("")) {
            error = true;
            errormessage += "-Beschrijving \n";
        }
        if(txtPriceSK.getText().equals("")) {
            error = true;
            errormessage += "-Prijs met sportkaart \n";
        }
        if(txtPriceNoSK.getText().equals("")) {
            error = true;
            errormessage += "-Prijs zonder sportkaart \n";
        }
        if(txtDate.getText().equals("")) {
            error = true;
            errormessage += "-Wanneer \n";
        }
        try{
            Integer.parseInt(txtPriceSK.getText());
        } catch(Exception e) {
            error = true;
            errormessage += "-Prijs met sportkaart \n";
        }
        try{
            Integer.parseInt(txtPriceNoSK.getText());
        } catch(Exception e) {
            error = true;
            errormessage += "-Prijs zonder sportkaart \n";
        }
        try{
            String d = txtDate.getText();
            this.e.dateFormat.parse(d);
        } catch(Exception e) {
            error = true;
            errormessage += "-Wanneer \n";
        }
        if(error) {
            openErrorPopup(errormessage);
        } else {
            if(this.isNewEvent) {
                this.e = _model.eventByName("Ijsberen");
                this.e.isRemoved = false;
            }
            this.e.naam = txtTitle.getText();
            this.e.beschrijving = txtDescription.getText();
            try {
               this.e.date = this.e.dateFormat.parse(txtDate.getText());
            } catch (ParseException e1) {
                e1.printStackTrace();
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                 this.e.date = today.getTime();
            }
            this.e.prijsMK = Integer.parseInt(txtPriceSK.getText());
            this.e.prijsZK = Integer.parseInt(txtPriceNoSK.getText());

            String title;
            String text;
            if(isNewEvent) {
                title = "Aanmaken evenement";
                text = "Het evenement is aangemaakt";
            } else {
                title = "Bewerken evenement";
                text = "Het evenement is bewerkt";
            }
            openSuccessPopup(title, text);
        }
    }

    @FXML
    public void handleChooser(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fout");
        alert.setHeaderText("Sorry, deze feature is niet uitgewerkt, probeer iets anders.");
        alert.showAndWait();
    }

    public void openErrorPopup(String m) {
        pnPopup.setVisible(true);
        pnPopupText.setVisible(true);
        lblErrorMessage.setText(m);
    }
    @FXML
    public void closeErrorPopup(ActionEvent event) {
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);
    }


    @FXML
    public void openDiscardPopup(ActionEvent event) {
        pnPopup.setVisible(true);
        pnDiscard.setVisible(true);
    }
    @FXML
    public void closeDiscardPopup(ActionEvent event) {
        pnPopup.setVisible(false);
        pnDiscard.setVisible(false);
    }

    public void openSuccessPopup(String title, String text) {
        pnPopup.setVisible(true);
        lblSuccessTitle.setText(title);
        lblSuccessText.setText(text);
        pnSuccess.setVisible(true);
    }

    public void showDemo() {
        pnPopup.setVisible(true);
        pnDemo.setVisible(true);
    }
    @FXML
    public void closeDemo(ActionEvent event) {
        pnPopup.setVisible(false);
        pnDemo.setVisible(false);
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
}
