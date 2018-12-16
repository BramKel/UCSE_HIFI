package HighFid.Screens.SportEnrolment;

import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Model.SportKaart;
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

public class SportEnrolmentController implements Initializable, ControlledScreen {
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
    Label prijsContent;
    @FXML
    private Label lblTotal;

    @FXML
    private TableColumn<TableCol, CheckBox> colSelect;
    @FXML
    private TableColumn<TableCol, String> colHour;
    @FXML
    private TableColumn<TableCol, String> colLocation;

    @FXML
    private TableView<TableCol> tblView;

    private ObservableList<TableCol> list = FXCollections.observableArrayList();

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
        for(int i = 0; i < days.length; ++i) {
            CheckBox ch = new CheckBox(DayToStr(days[i]));
            ch.setOnMouseClicked(event -> recalculatePrice());
            list.add(new TableCol(ch, begins[i].toString() + "-" + ends[i].toString(), places[i]));
        }
        tblView.setItems(list);

        colSelect.setCellValueFactory(new PropertyValueFactory<>("colSelect"));
        colHour.setCellValueFactory(new PropertyValueFactory<>("colHour"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("colLocation"));

        tblView.setRowFactory(tv -> {
            TableRow<TableCol> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                int index = tblView.getSelectionModel().selectedIndexProperty().get();
                list.get(index).getColSelect().setSelected(!list.get(index).getColSelect().isSelected());
                recalculatePrice();
            });
            return row ;
        });

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
        title.setText("Inschrijven voor " + s.name);
        ShowImage(s.name);
        prijsContent.setText(makePriceTxt(s.prijsZonderkaart, s.prijsMetKaart));
        makeDateTxt(s.days, s.beginTimes, s.endTimes, s.places);
        lblTotal.setText("0 Euro");
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

    private void recalculatePrice() {
        int count = 0;
        for(int i = 0; i < tblView.getItems().size(); ++i) {
            if(tblView.getItems().get(i).getColSelect().isSelected()) {
                count++;
            }
        }
        boolean SK = _model.getProfile().getSportKaart().getSkStatus() == SportKaart.SK_STATUS.ACTIVE;
        if(SK) {
            lblTotal.setText( (count * s.prijsMetKaart) + " Euro");
        } else {
            lblTotal.setText( (count * s.prijsZonderkaart) + " Euro");
        }
    }

    @FXML
    public void handleEnrolment(ActionEvent event) {
        for(int i = 0; i < tblView.getItems().size(); ++i) {
            if(tblView.getItems().get(i).getColSelect().isSelected()) {
                //TODO
            }
        }
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
        lblPopupText.setText("Inschrijving voor " + s.name);
    }
}
