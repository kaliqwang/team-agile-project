package model;

import javafx.beans.property.*;

import java.util.Date;

public class WaterPurityReport {
    private final IntegerProperty _reportNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> _date = new SimpleObjectProperty<>();
    private final StringProperty _author = new SimpleStringProperty();
    private final ObjectProperty<Location> _location = new SimpleObjectProperty<>();
    private final DoubleProperty _virusPPM = new SimpleDoubleProperty();
    private final DoubleProperty _contaminantPPM = new SimpleDoubleProperty();
    private final ObjectProperty<WaterPurityCondition> _waterPurityCondition = new SimpleObjectProperty<>();


    public Date getDate() { return _date.get(); }
    public void setDate(Date theDate) { _date.set(theDate); }

    public Integer getReportNumber() { return _reportNumber.get(); }
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

    public String getAuthor() { return _author.get(); }
    public void setAuthor(String author) { _author.set(author); }

    public Location getLocation() { return _location.get(); }
    public void setLocation(Location location) { _location.set(location); }

    public WaterPurityCondition getWaterPurityCondition() { return _waterPurityCondition.get(); }
    public void setWaterPurityCondition(WaterPurityCondition waterPurityCondition) { _waterPurityCondition.set(waterPurityCondition); }
    public ObjectProperty<WaterPurityCondition> getWaterPurityConditionProperty() { return _waterPurityCondition; }

    public Double getVirusPPM() { return _virusPPM.get(); }
    public void setVirusPPM(Double virusPPM) { _virusPPM.set(virusPPM); }
    public DoubleProperty getVirusPPMProperty() { return _virusPPM; }

    public Double getContaminantPPM() { return _contaminantPPM.get(); }
    public void setContaminantPPM(Double contaminantPPM) { _contaminantPPM.set(contaminantPPM); }
    public DoubleProperty getContaminantPPMProperty() { return _contaminantPPM; }


    public WaterPurityReport() {}

    public WaterPurityReport(Data plainData) {
        _reportNumber.set(plainData.rptNum);
        _date.set(plainData.rptDate);
        _author.set(plainData.author);
        _location.set(new Location(plainData.location));
        _virusPPM.set(plainData.virusPPM);
        _contaminantPPM.set(plainData.contaminantPPM);
        _waterPurityCondition.set(plainData.waterPurityCondition);
    }

    public Data getPlainData() {
        return new Data(
            _reportNumber.get(),
            _date.get(),
            _author.get(),
            _location.get(),
            _virusPPM.get(),
            _contaminantPPM.get(),
            _waterPurityCondition.get()
        );
    }

    public class Data {
        private int rptNum;
        private Date rptDate;
        private String author;
        private Location.Data location;
        private double virusPPM;
        private double contaminantPPM;
        private WaterPurityCondition waterPurityCondition;

        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                Location location,
                double virusPPM,
                double contaminantPPM,
                WaterPurityCondition condition
        ) {
            this.rptNum = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.location = location.getPlainData();
            this.virusPPM = virusPPM;
            this.contaminantPPM = contaminantPPM;
            this.waterPurityCondition = condition;
        }
    }

}
