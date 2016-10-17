package model;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Created by Rayner Kristanto on 10/10/16.
 */
public class WaterSourceReport {
    private final IntegerProperty _reportNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> _date = new SimpleObjectProperty<>();
    private final StringProperty _author = new SimpleStringProperty();
    private final StringProperty _waterLocation = new SimpleStringProperty();
    private final ObjectProperty<WaterType> _waterType = new SimpleObjectProperty<>();
    private final ObjectProperty<WaterCondition> _waterCondition = new SimpleObjectProperty<>();

    public Date getDate() { return _date.get(); }
    public void setDate(Date theDate) { _date.set(theDate); }

    public Integer getReportNumber() { return _reportNumber.get(); }
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

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

    public WaterSourceReport(Data plainData) {
        _reportNumber.set(plainData.rptNum);
        _date.set(plainData.rptDate);
        _author.set(plainData.author);
        _waterLocation.set(plainData.waterLocation);
        _waterType.set(plainData.waterType);
        _waterCondition.set(plainData.waterCondition);
    }

    public Data getPlainData() {
        return new Data(
                _reportNumber.get(),
                _date.get(),
                _author.get(),
                _waterLocation.get(),
                _waterType.get(),
                _waterCondition.get()
        );
    }

    public class Data {
        private int rptNum;
        private Date rptDate;
        private String author;
        private String waterLocation;
        private WaterType waterType;
        private WaterCondition waterCondition;

        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                String location,
                WaterType type,
                WaterCondition condition
        ) {
            this.rptNum = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.waterLocation = location;
            this.waterType = type;
            this.waterCondition = condition;
        }
    }

}
