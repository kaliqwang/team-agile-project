package model;

import javafx.beans.property.*;

import java.util.Date;

public class WaterSourceReport {
    private final IntegerProperty _reportNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> _date = new SimpleObjectProperty<>();
    private final StringProperty _author = new SimpleStringProperty();
    private final DoubleProperty _waterLatitude = new SimpleDoubleProperty();
    private final DoubleProperty _waterLongitude = new SimpleDoubleProperty();
    private final ObjectProperty<WaterSourceType> _waterType = new SimpleObjectProperty<>();
    private final ObjectProperty<WaterSourceCondition> _waterCondition = new SimpleObjectProperty<>();

    public Date getDate() { return _date.get(); }
    public void setDate(Date theDate) { _date.set(theDate); }

    public Integer getReportNumber() { return _reportNumber.get(); }
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

    public String getAuthor() { return _author.get(); }
    public void setAuthor(String author) { _author.set(author); }

    public double getWaterLatitude() { return _waterLatitude.get(); }
    public void setWaterLatitude(double latitude) { _waterLatitude.set(latitude); }
    public double getWaterLongitude() { return _waterLongitude.get(); }
    public void setWaterLongitude(double longitude) { _waterLongitude.set(longitude); }
    public WaterSourceType getWaterSourceType() { return _waterType.get(); }
    public void setWaterSourceType(WaterSourceType waterType) { _waterType.set(waterType); }

    public WaterSourceCondition getWaterSourceCondition() { return _waterCondition.get(); }
    public void setWaterSourceCondition(WaterSourceCondition waterCondition) { _waterCondition.set(waterCondition); }

    public WaterSourceReport() {

    }

    public WaterSourceReport(Data plainData) {
        _reportNumber.set(plainData.rptNum);
        _date.set(plainData.rptDate);
        _author.set(plainData.author);
        _waterLatitude.set(plainData.latitude);
        _waterLongitude.set(plainData.longitude);
        _waterType.set(plainData.waterType);
        _waterCondition.set(plainData.waterCondition);
    }

    public Data getPlainData() {
        return new Data(
                _reportNumber.get(),
                _date.get(),
                _author.get(),
                _waterLatitude.get(),
                _waterLongitude.get(),
                _waterType.get(),
                _waterCondition.get()
        );
    }

    public class Data {
        private int rptNum;
        private Date rptDate;
        private String author;
        private double latitude;
        private double longitude;
        private WaterSourceType waterType;
        private WaterSourceCondition waterCondition;

        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                double latitude,
                double longitude,
                WaterSourceType type,
                WaterSourceCondition condition
        ) {
            this.rptNum = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.latitude = latitude;
            this.longitude = longitude;
            this.waterType = type;
            this.waterCondition = condition;
        }
    }

}
