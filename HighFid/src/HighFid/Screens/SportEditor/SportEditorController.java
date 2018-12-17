package HighFid.Screens.SportEditor;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SportEditorController implements Initializable, ControlledScreen {
    //Private members
    private ScreensController _controller;
    private Model _model;
    private Sport s;
    private boolean isNewEvent = false;
    @FXML
    ImageView imageView;
    @FXML
    Label title;
    @FXML
    ImageView backBtn, imgQR;
    @FXML
    TextArea descriptionContent;
    @FXML
    TextField aanbodContent, prijsContent, wanneerContent, niveauContent;
    @FXML
    AnchorPane anchorPane;
    @FXML
    VBox vbox;
    @FXML
    private Button btnEnrol, btnUnenrol, btnView;
    @FXML
    TableView<DateWrapper> dateTable;
    @FXML
    TableColumn<DateWrapper, String> dateDay, dateStart, dateEnd, datePlace;

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
        /*pnPopup.setVisible(false);
        pnPopupText.setVisible(false);*/

        //dataTable.lookup("TableHeaderRow").setVisible(false);


    }
    private void ShowImage(String name) {
        if(name.compareTo("") == 0)
            name = "default";
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
        List<DateWrapper> dates = new ArrayList<DateWrapper>();

        for(int i = 0; i < amount; i++) {
            DateWrapper d = new DateWrapper(days[i], begins[i], ends[i], places[i]);
            dates.add(d);
        }

        dateDay.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("day"));
        dateDay.setCellFactory(TextFieldTableCell.forTableColumn());

        dateStart.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("begin"));
        dateStart.setCellFactory(TextFieldTableCell.forTableColumn());

        dateEnd.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("end"));
        dateEnd.setCellFactory(TextFieldTableCell.forTableColumn());

        datePlace.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("place"));
        datePlace.setCellFactory(TextFieldTableCell.forTableColumn());

        dateTable.getItems().setAll(dates);


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
        if(s.name.length() == 0)
            isNewEvent = true;
        title.setText(s.name);
        if(isNewEvent)
            title.setText("Nieuwe sport aanmaken");
        ShowImage(s.name);
        descriptionContent.setText(s.description);
        aanbodContent.setText(s.description);
        niveauContent.setText(s.niveau);
        wanneerContent.setText(s.wanneer);
        if(!isNewEvent)
            prijsContent.setText(makePriceTxt(s.prijsZonderkaart, s.prijsMetKaart));
        makeDateTxt(s.days, s.beginTimes, s.endTimes, s.places);

        this.s = s;
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
    private void showMainMenu(ActionEvent event){_controller.showMainMenu();}
    @FXML
    private void showChallengeOverview(ActionEvent event){_controller.showChallengeOverview(); }
    @FXML
    private void showCalendar(ActionEvent event){_controller.showCalendar(0); }
    @FXML
    private void showInschrijvingen(ActionEvent event) { _controller.showInschrijvingen(); }
    @FXML
    private void showScanQR(ActionEvent event) { _controller.showScanQR(); }
}
