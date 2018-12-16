package HighFid.Screens.SportDetail;

import HighFid.Model.Enrolment;
import HighFid.Model.Model;
import HighFid.Model.Profile;
import HighFid.Model.Sport;
import HighFid.Screens.ControlledScreen;
import HighFid.Screens.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

public class SportDetailController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;
    private Sport s;
    @FXML
    ImageView imageView;
    @FXML
    Label title;
    @FXML
    ImageView backBtn, imgQR;
    @FXML
    Label aanbodContent, descriptionContent, prijsContent, wanneerContent, niveauContent, dayContent, timeContent, placeContent;
    @FXML
    AnchorPane anchorPane;
    @FXML
    VBox vbox;
    @FXML
    private Button btnEnrol, btnUnenrol, btnView;

    @FXML
    private Pane pnPopup, pnPopupText;
    @FXML
    private Label lblPopupText;

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
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);

        //dataTable.lookup("TableHeaderRow").setVisible(false);


    }
    private void ShowImage(String name) {
        String path = "Sports/" + name + ".png";

        imageView.setImage(new Image(path));
        double width = imageView.getFitWidth();
        double xCoor = (393 - width) / 2;
        imageView.setLayoutX(xCoor);


    }
    private String makePriceTxt(int prijsZK, int prijsMK) {
        String p1 = "€ " + prijsZK;
        String p2 = "€ " + prijsMK;
        if(prijsZK == 0)
            p1 = "gratis";
        if(prijsMK == 0)
            p2 = "gratis";
        String str = "Prijs met kaart: " + p2 + "\nPrijs zonder kaart: " + p1;
        return str;
    }
    private void makeDateTxt(DayOfWeek[] days, Time[] begins, Time[] ends, String []places) {

        int amount = days.length;
        String dayTxt = "";
        String timeTxt = "";
        String placeTxt = "";

        for(int i = 0; i < amount; i++) {

            dayTxt += DayToStr(days[i]) + "\n";
            timeTxt += begins[i].toString().substring(0,5) + " - " + ends[i].toString().substring(0,5) + "\n";
            placeTxt += places[i] + "\n";
        }
        dayContent.setText(dayTxt);
        timeContent.setText(timeTxt);
        placeContent.setText(placeTxt);

    }
    private String DayToStr(DayOfWeek day) {
        String result = "";
        switch(day) {
            case MONDAY:
                result = "Maandag";
                break;
            case TUESDAY:
                result ="Dinsdag";
                break;
            case WEDNESDAY:
                result = "Woensdag";
                break;
            case THURSDAY:
                result = "Donderdag";
                break;
            case FRIDAY:
                result = "Vrijdag";
                break;
            case SATURDAY:
                result = "Zaterdag";
                break;
            case SUNDAY:
                result = "Zondag";
                break;
        }
        return result;
    }
    public void ShowSport(Sport s) {
        this.s = s;
        title.setText(s.name);
        ShowImage(s.name);
        descriptionContent.setText(s.description);
        aanbodContent.setText(s.description);
        niveauContent.setText(s.niveau);
        wanneerContent.setText(s.wanneer);
        prijsContent.setText(makePriceTxt(s.prijsZonderkaart, s.prijsMetKaart));
        makeDateTxt(s.days, s.beginTimes, s.endTimes, s.places);
        for(int i = 0; i < _model.getProfile().enrolments.length; ++i) {
            Enrolment e = _model.getProfile().enrolments[i];
            if(e.type == Enrolment.ENROLMENT_TYPE.SPORT && e.sport.name.equals(s.name)) {
                btnEnrol.setVisible(false);
                btnUnenrol.setVisible(true);
                break;
            } else {
                btnEnrol.setVisible(true);
                btnUnenrol.setVisible(false);
            }
        }

        this.s = s;

        if (_model.getProfile().getId() == Profile.ID_types.SPORTLK && s.name.equals("Basketbal")) {
            btnView.setVisible(true);
            String path = "QR/icon.png";
            imgQR.setImage(new Image(path));
            imgQR.setVisible(true);
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
    public void showSportEnrollment(){
        _controller.showSportEnrollment(s.name);
    }

    @FXML
    public void openPopup(ActionEvent event) {
        lblPopupText.setText("Uitschrijven voor " + s.name);
        pnPopup.setVisible(true);
        pnPopupText.setVisible(true);
    }

    @FXML
    public void handleUnEnrolment(ActionEvent event){
        _model.getProfile().removeEnrolment(s.name);
        _controller.ShowSportDetail(s.name);
    }

    @FXML
    public void closePopup(ActionEvent event) {
        pnPopup.setVisible(false);
        pnPopupText.setVisible(false);
    }

    @FXML
    private void showMainMenu(ActionEvent event){_controller.showMainMenu();}
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview(); }
    @FXML
    private void showCalendar(ActionEvent event){_controller.showCalendar(0); }
    @FXML
    private void showInschrijvingen(ActionEvent event) { _controller.showInschrijvingen(); }
}
