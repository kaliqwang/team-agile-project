package controller;

import model.*;
import model.persist.*;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderAddressComponent;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;

public class WaterPurityReportCreateController {

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
    private TextField virusPPM;

    @FXML
    private TextField contaminantPPM;

    @FXML
    private ComboBox<WaterPurityCondition> overallConditionField;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private VBox mapResults;

    private WaterPurityReport reportResult;

    private GoogleMap map;

    private String lastSearchQuery;

    private Map<VBox, GeocodingResult> entryMapping;

    private Map<GeocodingResult, Marker> mapMarkers;

    private Marker currMarker;

    private GeocodingResult selectedResult;

    private InfoWindow infoWindowSelect;

    private Stage _dialogStage;

    private GenericDAO<WaterPurityReport, Integer> _reportData;

    private LocationDAO _locationData;

    private User _currUser;

    private GeocodingService _geoSrv;

    @FXML
    private void initialize() {
        overallConditionField.setItems(FXCollections.observableArrayList(WaterPurityCondition.values()));
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setReportDao(GenericDAO<WaterPurityReport, Integer> dao) { _reportData = dao; }

    public void setLocationDao(LocationDAO dao) { _locationData = dao; }

    public void setCurrUser(User currUser) { _currUser = currUser; }

    public void initializeLocations() {
        locationField.setItems(FXCollections.observableArrayList(_locationData.getAll()));
    }

    public WaterPurityReport getWaterPurityReport() {
        return reportResult;
    }

    @FXML
    private void handleWaterPurityReportSubmitPressed() {
        double cppm, vppm;
        try {
            cppm = Double.parseDouble(contaminantPPM.getText());
            vppm = Double.parseDouble(virusPPM.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            WaterPurityReportDAO i = (WaterPurityReportDAO) _reportData;
            WaterPurityReport report = new WaterPurityReport();
            report.setReportNumber(i.nextIndex());
            report.setDate(new Date());
            report.setAuthor(_currUser.getUsername());
            report.setLocation(locationField.getValue());
            report.setContaminantPPM(cppm);
            report.setVirusPPM(vppm);
            report.setWaterPurityCondition(overallConditionField.getValue());
            reportResult = report;
            _reportData.persist(report);
            _dialogStage.close();
        } catch (NullPointerException e) {

        }
    }

    @FXML
    private void handleCancelPressed() {
        _dialogStage.close();
    }
}
