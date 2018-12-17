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
import javafx.scene.input.MouseEvent;
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
    Label title, msgTitle, msgContent;
    @FXML
    ImageView backBtn, imgQR;
    @FXML
    TextArea descriptionContent;
    @FXML
    TextField aanbodContent, txtPriceSK, txtPriceNoSK, wanneerContent, niveauContent, titleContent;
    @FXML
    AnchorPane anchorPane;
    @FXML
    VBox vbox;
    @FXML
    private Button btnEnrol, btnUnenrol, btnView, uploadPhoto, saveBtn, cancelBtn, closeMsg,addDateBtn;
    @FXML
    TableView<DateWrapper> dateTable;
    @FXML
    TableColumn<DateWrapper, String> dateDay, dateStart, dateEnd, datePlace;
    @FXML
    Pane msgBox;

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
                pnPopupText.setVisible(true);
            }
        });
        closeMsg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                msgBox.setVisible(false);
            }
        });
        uploadPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ShowMessage("Demo versie", "In de huidige versie van de demo is het niet mogelijk een afbeelding te kiezen. Dit wordt vanzelf afgehandeld.");
            }
        });
        saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Sport sport = formSport();
                if(sport == null)
                    return;
                _model.ReplaceSport(s.name, sport);
                _controller.showMainMenu();
            }
        });
        addDateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AddDummyDate();
            }
        });
        cancelBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pnPopupText.setVisible(true);
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
        dateDay.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DateWrapper, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DateWrapper, String> t) {
                String day = t.getNewValue();
                if(DateWrapper.StringToDay(day) == null){
                    String old = t.getOldValue();
                    ShowFormatError(day, "dag");
                    ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDay(old);
                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);
                } else {
                    ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDay(day);
                }
            }
        });

        dateStart.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("begin"));
        dateStart.setCellFactory(TextFieldTableCell.forTableColumn());
        dateStart.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DateWrapper, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DateWrapper, String> t) {
                String time = t.getNewValue();
                if(DateWrapper.StringToTime(time) == null) {
                    String old = t.getOldValue();
                    ShowFormatError(time, "tijd");
                    ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBegin(old);
                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);
                } else {
                    ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBegin(time);
                }
            }
        });

        dateEnd.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("end"));
        dateEnd.setCellFactory(TextFieldTableCell.forTableColumn());
        dateEnd.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DateWrapper, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DateWrapper, String> t) {
                String time = t.getNewValue();
                if(DateWrapper.StringToTime(time) == null) {
                    String old = t.getOldValue();
                    ShowFormatError(time, "tijd");
                    ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEnd(old);
                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);
                }else {
                    ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBegin(time);
                }
            }
        });

        datePlace.setCellValueFactory(new PropertyValueFactory<DateWrapper, String>("place"));
        datePlace.setCellFactory(TextFieldTableCell.forTableColumn());
        datePlace.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DateWrapper, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DateWrapper, String> t) {
                ((DateWrapper) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlace(t.getNewValue());
            }
        });

        dateTable.getItems().setAll(dates);


    }
    private void AddDummyDate() {
        DateWrapper wrapper = new DateWrapper(DayOfWeek.MONDAY, Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), "Hasselt");
        dateTable.getItems().add(wrapper);

    }
    private void ShowFormatError(String value, String type) {
        String title = "Geen geldige " + type;
        String msg = "De opgegeven waarde \"" + value + "\" is geen geldige waarde voor \"" + type + "\".";
        ShowMessage(title,msg);

    }
    private void ShowMessage(String title, String message) {
        msgTitle.setText(title);
        msgContent.setText(message);
        msgBox.setVisible(true);
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
        {
            isNewEvent = true;
            ShowMessage("Demo versie", "Doordat dit een demo is, is het enkel mogelijk de sportsessie \"Badminton\" aan te maken.");
        }
        if(isNewEvent){
            title.setText("Nieuwe sport aanmaken");
            titleContent.setText("Badminton");
            titleContent.setDisable(true);
            ShowImage("Badminton");
        }
        else {
            title.setText("Sport bewerken");
            titleContent.setText(s.name);
            ShowImage(s.name);
        }

        descriptionContent.setText(s.description);
        aanbodContent.setText(s.aanbod);
        niveauContent.setText(s.niveau);
        wanneerContent.setText(s.wanneer);
        txtPriceSK.setText("" + s.prijsMetKaart);
        txtPriceNoSK.setText("" + s.prijsZonderkaart);
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
    private Sport formSport() {
        if(!CheckForm())
        {
            ShowMessage("Incorrecte gegevens", "Gelieve alle gegevens correct in te vullen!");
            return null;
        }
        String title = titleContent.getText();
        String descr = descriptionContent.getText();
        String quote = "LEEF JE UIT MET RACKET EN PLUIMPJE!";
        String aanbod = aanbodContent.getText();
        String niveau = niveauContent.getText();
        String wanneer = wanneerContent.getText();
        String prijsMK = txtPriceSK.getText();
        String prijsZK = txtPriceNoSK.getText();

        List<DateWrapper> dates = dateTable.getItems();
        Sport s = new Sport(title, quote, descr, aanbod, niveau, wanneer, Integer.parseInt(prijsMK), Integer.parseInt(prijsZK));
        for(int i = 0; i < dates.size(); i++) {
            String day = dates.get(i).day;

            s.AddSession(DateWrapper.StringToDay(day), dates.get(i).getBeginTime(), dates.get(i).getEndTime(), dates.get(i).place);
        }
        return s;

    }
    private boolean CheckForm() {
        String title = titleContent.getText();
        String descr = descriptionContent.getText();
        String quote = "LEEF JE UIT MET RACKET EN PLUIMPJE!";
        String aanbod = aanbodContent.getText();
        String niveau = niveauContent.getText();
        String wanneer = wanneerContent.getText();
        String prijsMK = txtPriceSK.getText();
        String prijsZK = txtPriceNoSK.getText();
        if(title.length() == 0 || descr.length() == 0 || aanbod.length() == 0 || niveau.length() == 0 || wanneer.length() == 0 || prijsMK.length() == 0 || prijsZK.length() == 0)
            return false;
        try{
            int prijs = Integer.parseInt(prijsMK);
            prijs = Integer.parseInt(prijsZK);
        } catch (Exception e) {
            return false;
        }
        List<DateWrapper> dates = dateTable.getItems();
        if(dates.size() == 0)
            return false;
        return true;
    }
    @FXML void closePopup(ActionEvent event) {
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
    @FXML
    private void showScanQR(ActionEvent event) { _controller.showScanQR(); }
}
