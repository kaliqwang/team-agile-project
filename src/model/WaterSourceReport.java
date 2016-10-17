package model;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Created by Rayner Kristanto on 10/10/16.
 */
public class WaterSourceReport {
   // private final Date _date = new Date();
    private final Integer _reportNumber = 0;
    private final StringProperty _author = new SimpleStringProperty();
    private final StringProperty _waterLocation = new SimpleStringProperty();
    private final ObjectProperty<WaterType> _waterType = new SimpleObjectProperty<>();
    private final ObjectProperty<WaterCondition> _waterCondition = new SimpleObjectProperty<>();

  //  public Date getDate() { return _date; }

    public Integer getReportNumber() { return _reportNumber; }

    public String getAuthor() { return _author.get(); }
    public void setAuthor(String author) { _author.set(author); }

    public String getWaterLocation() { return _waterLocation.get(); }
    public void setWaterLocation(String waterLocation) { _waterLocation.set(waterLocation); }
    public StringProperty getWaterLocationProperty() { return _waterLocation; }
    public WaterType getWaterType() { return _waterType.get(); }
    public void setWaterType(WaterType waterType) { _waterType.set(waterType); }

    public WaterCondition getWaterCondition() { return _waterCondition.get(); }
    public void setWaterCondition(WaterCondition waterCondition) { _waterCondition.set(waterCondition); }

    public WaterSourceReport() {


    }

}
