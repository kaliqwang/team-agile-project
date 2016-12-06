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


    /**
     * This method is a getter for the Date.
     * @return The date associated with the Water Purity Report
     */
    public Date getDate() { return _date.get(); }

    /**
     * This method is a setter for the Date.
     * @param theDate the Date associated with the Water Purity Report
     */
    public void setDate(Date theDate) { _date.set(theDate); }

    /**
     * This method is a getter for the Report Number.
     * @return The Report Number associated with the Water Purity Report
     */
    public Integer getReportNumber() { return _reportNumber.get(); }

    /**
     * This method is the setter for the Report Number.
     * @param reportNumber the author associated with the Water Purity Report
     */
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

    /**
     * This method is a getter for the Author.
     * @return The Author associated with the Water Purity Report
     */
    public String getAuthor() { return _author.get(); }

    /**
     * This method is the setter for the Author.
     * @param author the author associated with the Water Purity Report
     */
    public void setAuthor(String author) { _author.set(author); }

    /**
     * This method is a getter for the Location.
     * @return The Location associated with the Water Purity Report
     */
    public Location getLocation() { return _location.get(); }

    /**
     * This method is the setter for the Location.
     * @param location the location associated with the Water Purity Report
     */
    public void setLocation(Location location) { _location.set(location); }

    /**
     * This method is the getter for the Water Purity Condition.
     * @return the Water Purity Condition associated with the Water Purity Report.
     */
    public WaterPurityCondition getWaterPurityCondition() { return _waterPurityCondition.get(); }

    /**
     * This method is a setter for the Water Purity Condition.
     * @param waterPurityCondition the Water Purity Condition associated with the Water Purity Report.
     */
    public void setWaterPurityCondition(WaterPurityCondition waterPurityCondition) { _waterPurityCondition.set(waterPurityCondition); }

    /**
     * This method is the getter for the Water Purity Condition Property.
     * @return the Water Purity Condition Property associated with the Water Purity Report.
     */
    public ObjectProperty<WaterPurityCondition> getWaterPurityConditionProperty() { return _waterPurityCondition; }

    /**
     * This method is the getter for the Virus PPM.
     * @return the Virus PPM associated with the Water Purity Report.
     */
    public Double getVirusPPM() { return _virusPPM.get(); }

    /**
     * This method is a setter for the location.
     * @param virusPPM the VirusPPM associated with the Water Purity Report.
     */
    public void setVirusPPM(Double virusPPM) { _virusPPM.set(virusPPM); }

    /**
     * This method is the getter for the Virus PPM.
     * @return the Virus PPM associated with the Water Purity Report.
     */
    public DoubleProperty getVirusPPMProperty() { return _virusPPM; }

    /**
     * The getter for Contaminant PPM.
     * @return the Contaminant PPM associated with the Water Purity Report.
     */
    public Double getContaminantPPM() { return _contaminantPPM.get(); }

    /**
     * The setter for Contaminant PPM.
     * @param contaminantPPM the Contaminant PPM associated with the Water Purity Report.
     */
    public void setContaminantPPM(Double contaminantPPM) { _contaminantPPM.set(contaminantPPM); }

    /**
     * The getter for Contaminant PPM Property.
     * @return the Contaminant PPM Property associated with the Water Purity Report.
     */
    public DoubleProperty getContaminantPPMProperty() { return _contaminantPPM; }


    /**
     * This creates a Water Purity Report with no data
     */
    public WaterPurityReport() {}

    /**
     * This method creates a Water Purity report with plain data
     * @param plainData the data to be assigned to the water purity report
     */
    public WaterPurityReport(Data plainData) {
        _reportNumber.set(plainData.rptId);
        _date.set(plainData.rptDate);
        _author.set(plainData.author);
        _location.set(new Location(plainData.location));
        _virusPPM.set(plainData.virusPPM);
        _contaminantPPM.set(plainData.contaminantPPM);
        _waterPurityCondition.set(plainData.waterPurityCondition);
    }

    /**
     * This method returns the Plain Data
     * @return a data object with the attributes of the object passed in.
     */
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

    /**
     * This is the class that defines a data object and its creation
     */
    public class Data {
        private final int rptId;
        private final Date rptDate;
        private final String author;
        private final Location.Data location;
        private final double virusPPM;
        private final double contaminantPPM;
        private final WaterPurityCondition waterPurityCondition;

        /**
         *
         * @param reportNum the report number of the Data.
         * @param reportDate the report date of the Data.
         * @param auth the Authorization level of the Data.
         * @param location the Location of the Data.
         * @param virusPPM the Virus PPM of the Data.
         * @param contaminantPPM the Contaminant PPM of the Data.
         * @param condition the condition of the Data.
         */
        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                Location location,
                double virusPPM,
                double contaminantPPM,
                WaterPurityCondition condition
        ) {
            this.rptId = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.location = location.getPlainData();
            this.virusPPM = virusPPM;
            this.contaminantPPM = contaminantPPM;
            this.waterPurityCondition = condition;
        }
    }

}
