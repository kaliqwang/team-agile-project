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

    /**
     * This method is a getter for the Date.
     * @return The date associated with the Water Source Report
     */
    public Date getDate() { return _date.get(); }

    /**
     * This method is a setter for the Date.
     * @param theDate the Date associated with the Water Source Report
     */
    public void setDate(Date theDate) { _date.set(theDate); }


    /**
     * This method is a getter for the Report Number.
     * @return The Report Number associated with the Water Source Report
     */
    public Integer getReportNumber() { return _reportNumber.get(); }

    /**
     * This method is the setter for the Report Number.
     * @param reportNumber the author associated with the Water Source Report
     */
    public void setReportNumber(int reportNumber) { _reportNumber.set(reportNumber);}

    /**
     * This method is a getter for the Author.
     * @return The Author associated with the Water Source Report
     */
    public String getAuthor() { return _author.get(); }

    /**
     * This method is the setter for the Author.
     * @param author the author associated with the Water Source Report
     */
    public void setAuthor(String author) { _author.set(author); }

    /**
     * This method is a getter for the Location.
     * @return The Location associated with the Water Source Report
     */
    public Location getLocation() { return _location.get(); }

    /**
     * This method is the setter for the Location.
     * @param location the location associated with the Water Source Report
     */
    public void setLocation(Location location) { _location.set(location); }

    public WaterSourceType getWaterSourceType() { return _waterType.get(); }
    public void setWaterSourceType(WaterSourceType waterType) { _waterType.set(waterType); }

    /**
     * This method is the getter for the Water Source Condition.
     * @return the Water Source Condition associated with the Water Source Report.
     */
    public WaterSourceCondition getWaterSourceCondition() { return _waterCondition.get(); }

    /**
     * This method is a setter for the Water Source Condition.
     * @param waterCondition the Water Source Condition associated with the Water Source Report.
     */
    public void setWaterSourceCondition(WaterSourceCondition waterCondition) { _waterCondition.set(waterCondition); }

    /**
     * This method initializes a Water Source Report with no Data
     */
    public WaterSourceReport() {

    }


    /**
     * This method creates a Water Source report with plain data
     * @param plainData the data to be assigned to the water source report
     */
    public WaterSourceReport(Data plainData) {
        _reportNumber.set(plainData.rptId);
        _date.set(plainData.rptDate);
        _author.set(plainData.author);
        _location.set(new Location(plainData.location));
        _waterType.set(plainData.sourceType);
        _waterCondition.set(plainData.sourceCondition);
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
            _waterType.get(),
            _waterCondition.get()
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
        private final WaterSourceType sourceType;
        private final WaterSourceCondition sourceCondition;

        /**
         *
         * @param reportNum the report number of the Data.
         * @param reportDate the report date of the Data.
         * @param auth the Authorization level of the Data.
         * @param location the Location of the Data.
         * @param type the water type of the Data
         * @param condition the condition of the Data.
         */
        public Data(
                int reportNum,
                Date reportDate,
                String auth,
                Location location,
                WaterSourceType type,
                WaterSourceCondition condition
        ) {
            this.rptId = reportNum;
            this.rptDate = reportDate;
            this.author = auth;
            this.location = location.getPlainData();
            this.sourceType = type;
            this.sourceCondition = condition;
        }

        public int getRptId() {
            return this.rptId;
        }
    }

}
