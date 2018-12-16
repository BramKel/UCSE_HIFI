package HighFid.Screens.SportEnrolment;

import javafx.scene.control.CheckBox;

public class TableCol {
    private CheckBox colSelect;
    private String colHour;
    private String colLocation;

    public TableCol(CheckBox colSelect, String colHour, String colLocation) {
        setColSelect(colSelect);
        setColHour(colHour);
        setColLocation(colLocation);
    }

    public CheckBox getColSelect() {
        return colSelect;
    }
    public void setColSelect(CheckBox colSelect) {
        this.colSelect = colSelect;
    }
    public String getColHour() {
        return colHour;
    }
    public void setColHour(String colHour) {
        this.colHour = colHour;
    }
    public String getColLocation() {
        return colLocation;
    }
    public void setColLocation(String colLocation) {
        this.colLocation = colLocation;
    }
}
