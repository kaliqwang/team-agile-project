package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import model.persist.IGenericDAO;

import java.util.Date;
import java.util.Map;

public class WaterSourceReportCreateController {

    @FXML
    private TextField searchField;

//    @FXML
//    private TextField latitudeField;
//
//    @FXML
//    private TextField longitudeField;

    @FXML
    private ComboBox<Location> locationField;

    @FXML
    private ComboBox<WaterSourceType> waterTypeField;

    @FXML
    private ComboBox<WaterSourceCondition> waterConditionField;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private VBox mapResults;

    private WaterSourceReport reportResult;

    private GoogleMap map;

    private String lastSearchQuery;

    private Map<VBox, GeocodingResult> entryMapping;

    private Map<GeocodingResult, Marker> mapMarkers;

    private Marker currMarker;

    private GeocodingResult selectedResult;

    private InfoWindow infoWindowSelect;

    private Stage _dialogStage;

    private IGenericDAO<WaterSourceReport, Integer> _reportData;

    private IGenericDAO<Location, Integer> _locationData;

    private User _currUser;

    private GeocodingService _geoSrv;

    @FXML
    private void initialize() {
        waterTypeField.setItems(FXCollections.observableArrayList(WaterSourceType.values()));
        waterConditionField.setItems(FXCollections.observableArrayList(WaterSourceCondition.values()));
    }

    /**
     * This method sets the Dialog Stage
     * @param dialogStage the stage passed in to be the Dialog Stage
     */
    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the Report dao.
     * @param dao the data access object to be passed in.
     */
    public void setReportDao(IGenericDAO<WaterSourceReport, Integer> dao) { _reportData = dao; }

    /**
     * This method takes in a data access object and assigns
     * it to be the Location dao.
     * @param dao the data access object to be passed in.
     */
    public void setLocationDao(IGenericDAO<Location, Integer> dao) { _locationData = dao; }

    /**
     * This method sets the current user
     * @param currUser the user object passed in that will be assigned as the current user.
     */
    public void setCurrUser(User currUser) { _currUser = currUser; }

    /**
     * this method initializes the Locations onto the combo box
     */
    public void initializeLocations() {
        locationField.setItems(FXCollections.observableArrayList(_locationData.getAll()));
    }

    /**
     * This method gets the Water Purity report
     * @return the water purity report of the object this method was called on
     */
    public WaterSourceReport getWaterSourceReport() {
        return reportResult;
    }

    @FXML
    private void handleWaterSourceReportSubmitPressed() {
        try {
            WaterSourceReport report = new WaterSourceReport();
            report.setDate(new Date());
            report.setAuthor(_currUser.getUsername());
            report.setLocation(locationField.getValue());
            report.setWaterSourceType(waterTypeField.getValue());
            report.setWaterSourceCondition(waterConditionField.getValue());
            reportResult = report;
            _reportData.persist(report);
            _dialogStage.close();
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleCancelPressed() { _dialogStage.close(); }
}
