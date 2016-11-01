package model;

import javafx.beans.property.*;

import java.util.Date;

public class WaterPurityReport {
    private final IntegerProperty _reportNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> _date = new SimpleObjectProperty<>();
    private final StringProperty _author = new SimpleStringProperty();
    private final DoubleProperty _waterLatitude = new SimpleDoubleProperty();
    private final DoubleProperty _waterLongitude = new SimpleDoubleProperty();
    private final StringProperty _virusPPM = new SimpleStringProperty();
    private final StringProperty _contaminantPPM = new SimpleStringProperty();
    private final ObjectProperty<WaterPurityCondition> _waterPurityCondition = new SimpleObjectProperty<>();


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

    public WaterPurityCondition getWaterPurityCondition() { return _waterPurityCondition.get(); }
    public void setWaterPurityCondition(WaterPurityCondition waterPurityCondition) { _waterPurityCondition.set(waterPurityCondition); }
    public ObjectProperty<WaterPurityCondition> getWaterPurityConditionProperty() { return _waterPurityCondition; }

    public String getVirusPPM() { return _virusPPM.get(); }
    public void setVirusPPM(String virusPPM) { _virusPPM.set(virusPPM); }
    public StringProperty getVirusPPMProperty() { return _virusPPM; }

    public String getContaminantPPM() { return _contaminantPPM.get(); }
    public void setContaminantPPM(String contaminantPPM) { _contaminantPPM.set(contaminantPPM); }
    public StringProperty getContaminantPPMProperty() { return _contaminantPPM; }


    public WaterPurityReport() {

    }

    public WaterPurityReport(Data plainData) {
        _reportNumber.set(plainData.rptNum);
        _date.set(plainData.rptDate);
        _author.set(plainData.author);
        _waterLatitude.set(plainData.latitude);
        _waterLongitude.set(plainData.longitude);
        _virusPPM.set(plainData.virusPPM);
        _contaminantPPM.set(plainData.contaminantPPM);
        _waterPurityCondition.set(plainData.waterPurityCondition);
    }

    public Data getPlainData() {
        return new Data(
            _reportNumber.get(),
            _date.get(),
            _author.get(),
            _waterLatitude.get(),
            _waterLongitude.get(),
            _virusPPM.get(),
            _contaminantPPM.get(),
            _waterPurityCondition.get()
        );
    }

    public class Data {
        private int rptNum;
        private Date rptDate;
        private String author;
        private double latitude;
        private double longitude;
        private String virusPPM;
        private String contaminantPPM;
        private WaterPurityCondition waterPurityCondition;

        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                double latitude,
                double longitude,
                String virusPPM,
                String contaminantPPM,
                WaterPurityCondition condition
        ) {
            this.rptNum = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.latitude = latitude;
            this.longitude = longitude;
            this.virusPPM = virusPPM;
            this.contaminantPPM = contaminantPPM;
            this.waterPurityCondition = waterPurityCondition;
        }
    }

}
