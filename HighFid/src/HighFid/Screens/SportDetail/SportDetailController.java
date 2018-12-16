package HighFid.Screens.SportDetail;

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
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

class DataStruct {
    public String day, time, place;
}
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
    ImageView backBtn;
    @FXML
    Label aanbodContent, descriptionContent, prijsContent, wanneerContent, niveauContent;
    @FXML
    TableView dataTable;
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
        String p1 = "" + prijsZK;
        String p2 = "" + prijsMK;
        if(prijsZK == 0)
            p1 = "gratis";
        if(prijsMK == 0)
            p2 = "gratis";
        String str = "Prijs met kaart: " + p2 + "\nPrijs zonder kaart: " + p1;
        return str;
    }
    private void makeDateTxt(DayOfWeek[] days, Time[] begins, Time[] ends, String []places) {
        TableColumn dayCol = new TableColumn("Day");
        dayCol.setCellValueFactory( new PropertyValueFactory<DataStruct, String>("day"));

        TableColumn timeCol = new TableColumn("Times");
        dayCol.setCellValueFactory( new PropertyValueFactory<DataStruct, String>("time"));

        TableColumn placeCol = new TableColumn("Place");
        dayCol.setCellValueFactory( new PropertyValueFactory<DataStruct, String>("place"));

        dataTable.getColumns().addAll(dayCol, timeCol, placeCol);

        int amount = days.length;

        for(int i = 0; i < amount; i++) {
            DataStruct data = new DataStruct();
            data.day = DayToStr(days[i]);
            data.time = begins[i].toString().substring(0,5) + " - " + ends[i].toString().substring(0,5);
            data.place = places[i];
            dataTable.getItems().add(data);
        }


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
        title.setText(s.name);
        ShowImage(s.name);
        descriptionContent.setText(s.description);
        aanbodContent.setText(s.description);
        niveauContent.setText(s.niveau);
        wanneerContent.setText(s.wanneer);
        prijsContent.setText(makePriceTxt(s.prijsZonderkaart, s.prijsMetKaart));
        makeDateTxt(s.days, s.beginTimes, s.endTimes, s.places);
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