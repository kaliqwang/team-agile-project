package model;

import javafx.beans.property.*;

import java.util.Date;

public class WaterSourceReport {
    private final IntegerProperty _reportNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> _date = new SimpleObjectProperty<>();
    private final StringProperty _author = new SimpleStringProperty();
    private final ObjectProperty<Location> _location = new SimpleObjectProperty<>();
    private final ObjectProperty<WaterSourceType> _waterType = new SimpleObjectProperty<>();
    private final ObjectProperty<WaterSourceCondition> _waterCondition = new SimpleObjectProperty<>();

    public Date getDate() { return _date.get(); }
    public void setDate(Date theDate) { _date.set(theDate); }

    public Integer getReportNumber() { return _reportNumber.get(); }
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

    public String getAuthor() { return _author.get(); }
    public void setAuthor(String author) { _author.set(author); }

    public Location getLocation() { return _location.get(); }
    public void setLocation(Location location) { _location.set(location); }

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
        _location.set(new Location(plainData.location));
        _waterType.set(plainData.waterType);
        _waterCondition.set(plainData.waterCondition);
    }

    public Data getPlainData() {
        return new Data(
            _reportNumber.get(),
            _date.get(),
            _author.get(),
            _location.get(),
            _waterType.get(),
            _waterCondition.get()
        );
    }

    public class Data {
        private int rptNum;
        private Date rptDate;
        private String author;
        private Location.Data location;
        private WaterSourceType waterType;
        private WaterSourceCondition waterCondition;

        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                Location location,
                WaterSourceType type,
                WaterSourceCondition condition
        ) {
            this.rptNum = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.location = location.getPlainData();
            this.waterType = type;
            this.waterCondition = condition;
        }
    }

}
