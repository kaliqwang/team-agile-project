package model;

import javafx.beans.property.*;

import java.util.Date;

public class WaterPurityReport {
    private final IntegerProperty _reportNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> _date = new SimpleObjectProperty<>();
    private final StringProperty _author = new SimpleStringProperty();
    private final StringProperty _waterLocation = new SimpleStringProperty();
    private final ObjectProperty<WaterPurityCondition> _overallCondition = new SimpleObjectProperty<>();
    private final StringProperty _virusPPM = new SimpleStringProperty();
    private final StringProperty _contaminantPPM = new SimpleStringProperty();


    public Date getDate() { return _date.get(); }
    public void setDate(Date theDate) { _date.set(theDate); }

    public Integer getReportNumber() { return _reportNumber.get(); }
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

    public String getAuthor() { return _author.get(); }
    public void setAuthor(String author) { _author.set(author); }

    public String getWaterLocation() { return _waterLocation.get(); }
    public void setWaterLocation(String waterLocation) { _waterLocation.set(waterLocation); }
    public StringProperty getWaterLocationProperty() { return _waterLocation; }
    public WaterPurityCondition getWaterPurityCondition() { return _overallCondition.get(); }
    public void setWaterPurityCondition(WaterPurityCondition overallCondition) { _overallCondition.set(overallCondition); }

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
        _waterLocation.set(plainData.waterLocation);
        _overallCondition.set(plainData.overallCondition);
    }

    public Data getPlainData() {
        return new Data(
                _reportNumber.get(),
                _date.get(),
                _author.get(),
                _waterLocation.get(),
                _overallCondition.get(),
                _virusPPM.get(),
                _contaminantPPM.get()
        );
    }

    public class Data {
        private int rptNum;
        private Date rptDate;
        private String author;
        private String waterLocation;
        private WaterPurityCondition overallCondition;
        private String virusPPM;
        private String contaminantPPM;

        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                String location,
                WaterPurityCondition condition,
                String virusPPM,
                String contaminantPPM
        ) {
            this.rptNum = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.waterLocation = location;
            this.overallCondition = overallCondition;
            this.virusPPM = virusPPM;
            this.contaminantPPM = contaminantPPM;
        }
    }

}
