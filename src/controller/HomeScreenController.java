package controller;

import model.*;
import model.persist.*;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Button;

public class HomeScreenController {

    private MainFXApplication mainApplication;

    private User _currUser;

    @FXML
    private Button showDataGraphDialogButton;

    @FXML
    private Button showWaterSourceReportListDialogButton;

    @FXML
    private Button showWaterPurityReportListDialogButton;

    @FXML
    private Button showWaterPurityReportCreateDialogButton;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private GeocodingService _geoSrv;

    private GenericDAO<User, String> _userData;

    private WaterSourceReportDAO _sourceReportData;
    private WaterPurityReportDAO _purityReportData;

    private Map<WaterSourceReport, Marker> waterSourcePoints;
    private Map<WaterPurityReport, Marker> waterPurityPoints;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this::onMapInitialized);
        waterSourcePoints = new HashMap<>();
        waterPurityPoints = new HashMap<>();

        showDataGraphDialogButton.setDisable(true);
        showWaterSourceReportListDialogButton.setDisable(true);
        showWaterPurityReportListDialogButton.setDisable(true);
        showWaterPurityReportCreateDialogButton.setDisable(true);
    }

    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    public void setUserDao(GenericDAO<User,String> dao) {
        _userData = dao;
    }

    public void setSourceReportDao(GenericDAO<WaterSourceReport, Integer> dao) {
        _sourceReportData = (WaterSourceReportDAO) dao;
    }

    public void setPurityReportDao(GenericDAO<WaterPurityReport, Integer> dao) {
        _purityReportData = (WaterPurityReportDAO) dao;
    }

    public void setCurrUser(User currUser) {
        _currUser = currUser;

        if (currUser.getAuthorization() == AuthLevel.MANAGER) {
            showDataGraphDialogButton.setDisable(false);
            showWaterSourceReportListDialogButton.setDisable(false);
            showWaterPurityReportListDialogButton.setDisable(false);
            showWaterPurityReportCreateDialogButton.setDisable(false);
        }
        if (currUser.getAuthorization() == AuthLevel.WORKER) {
            showWaterPurityReportCreateDialogButton.setDisable(false);
        }

    }

    @FXML
    public void showDataGraphDialogPressed() {
        mainApplication.showDataGraphDialog();
    }

    @FXML
    public void showWaterSourceReportCreateDialogPressed() {
        WaterSourceReport submitted = mainApplication.showWaterSourceReportCreateDialog();
        if (submitted != null) {
            addMarkerForSourceReport(submitted);
        }
    }

    @FXML
    public void showWaterPurityReportCreateDialogPressed() {
        WaterPurityReport submitted = mainApplication.showWaterPurityReportCreateDialog();
        if (submitted != null) {
            addMarkerForPurityReport(submitted);
        }
    }

    @FXML
    public void showWaterSourceReportListDialogPressed() {
        mainApplication.showWaterSourceReportListDialog();
    }

    @FXML
    public void showWaterPurityReportListDialogPressed() {
        mainApplication.showWaterPurityReportListDialog();
    }

    @FXML
    public void showAddLocationDialogPressed() { mainApplication.showAddLocationDialog(); }

    @FXML
    public void showManageLocationsDialogPressed() {


    }

    @FXML
    public void showUserEditDialogPressed() { mainApplication.showUserEditDialog(); }

    @FXML
    public void logoutPressed() {
        mainApplication.showWelcomeScreen(mainApplication.getMainStage());
    }

    private void addMarkerForSourceReport(final WaterSourceReport r) {
        LatLong coords = new LatLong(r.getLocation().getLatitude(), r.getLocation().getLongitude());
        final Marker newPt = new Marker(new MarkerOptions().position(coords));
        map.addMarker(newPt);
        waterSourcePoints.put(r, newPt);
        User author = _userData.get(r.getAuthor());
        map.addUIEventHandler(newPt, UIEventType.click, jsObject -> {
            if (waterSourcePoints.get(r).equals(newPt)) {
                InfoWindowOptions windowOptions = new InfoWindowOptions();
                windowOptions.content("<h3>Report #" + r.getReportNumber() + "</h3>"
                        + "Date:" + r.getDate() + "<br>"
                        + "Author:" + author.getFirstName() + " " + author.getLastName() + " (" + author.getUsername() + ")<br>"
                        + "Water Type: " + r.getWaterSourceType().getDisplayText() + "<br>"
                        + "Water Condition:" + r.getWaterSourceCondition().getDisplayText());
                InfoWindow newInfo = new InfoWindow(windowOptions);
                newInfo.open(map, newPt);
            }
        });
    }

    private void addMarkerForPurityReport(final WaterPurityReport r) {
        LatLong coords = new LatLong(r.getLocation().getLatitude(), r.getLocation().getLongitude());
        final Marker newPt = new Marker(new MarkerOptions().position(coords));
        map.addMarker(newPt);
        waterPurityPoints.put(r, newPt);
        User author = _userData.get(r.getAuthor());
        map.addUIEventHandler(newPt, UIEventType.click, jsObject -> {
            if (waterPurityPoints.get(r).equals(newPt)) {
                InfoWindowOptions windowOptions = new InfoWindowOptions();
                windowOptions.content("<h3>Report #" + r.getReportNumber() + "</h3>"
                        + "Date:" + r.getDate() + "<br>"
                        + "Author:" + author.getFirstName() + " " + author.getLastName() + " (" + author.getUsername() + ")<br>"
                        + "Water Condition:" + r.getWaterPurityCondition().getDisplayText());
                InfoWindow newInfo = new InfoWindow(windowOptions);
                newInfo.open(map, newPt);
            }
        });
    }

    private void onMapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.7490, -84.3880))
                .mapType(MapTypeIdEnum.TERRAIN)
                .overviewMapControl(true)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        _geoSrv = new GeocodingService();
        for (WaterSourceReport r : _sourceReportData.getAll()) {
            addMarkerForSourceReport(r);
        }
        for (WaterPurityReport r : _purityReportData.getAll()) {
            addMarkerForPurityReport(r);
        }
    }
}
