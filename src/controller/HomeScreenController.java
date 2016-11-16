package controller;

import model.*;
import model.persist.*;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

import java.util.HashMap;
import java.util.Map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;

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

//    GeocodingService _geoSrv;

    private GenericDAO<User, String> _userData;

    private WaterSourceReportDAO _sourceReportData;
    private WaterPurityReportDAO _purityReportData;

    private Map<WaterSourceReport, Marker> waterSourcePoints;
    private Map<WaterPurityReport, Marker> waterPurityPoints;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this::onMapInitialized);
        waterSourcePoints = new HashMap<WaterSourceReport, Marker>();
        waterPurityPoints = new HashMap<WaterPurityReport, Marker>();

        showDataGraphDialogButton.setDisable(true);
        showWaterSourceReportListDialogButton.setDisable(true);
        showWaterPurityReportListDialogButton.setDisable(true);
        showWaterPurityReportCreateDialogButton.setDisable(true);
    }

    /**
     * This method sets the Main Application
     * @param mainFXApplication the application to be used as the Main Fx Application
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the User dao.
     * @param dao the data access object to be passed in.
     */
    public void setUserDao(GenericDAO<User,String> dao) {
        _userData = dao;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the Source Report dao.
     * @param dao the data access object to be passed in.
     */
    public void setSourceReportDao(GenericDAO<WaterSourceReport, Integer> dao) {
        _sourceReportData = (WaterSourceReportDAO) dao;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the Purity Report dao.
     * @param dao the data access object to be passed in.
     */
    public void setPurityReportDao(GenericDAO<WaterPurityReport, Integer> dao) {
        _purityReportData = (WaterPurityReportDAO) dao;
    }

    /**
     * This method takes in a user and sets them as the current user
     * @param currUser the user object that's going to be assigned as the current user
     */
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
    /*
      This method shows the Data Graph Dialog when its called.
     */
    public void showDataGraphDialogPressed() {
        mainApplication.showDataGraphDialog();
    }

    @FXML
    /*
      This method shows the Source Report Creation Dialog when called.
     */
    public void showWaterSourceReportCreateDialogPressed() {
        WaterSourceReport submitted = mainApplication.showWaterSourceReportCreateDialog();
        if (submitted != null) {
            addMarkerForSourceReport(submitted);
        }
    }

    @FXML
    /*
      This method shows the Water Purity Report Creation Dialog when called.
     */
    public void showWaterPurityReportCreateDialogPressed() {
        WaterPurityReport submitted = mainApplication.showWaterPurityReportCreateDialog();
        if (submitted != null) {
            addMarkerForPurityReport(submitted);
        }
    }

    @FXML
    /*
      This method shows the Water Source Report list Dialog when called.
     */
    public void showWaterSourceReportListDialogPressed() {
        mainApplication.showWaterSourceReportListDialog();
    }

    @FXML
    /*
      This method shows the Purity Report List Dialog when called.
     */
    public void showWaterPurityReportListDialogPressed() {
        mainApplication.showWaterPurityReportListDialog();
    }

    @FXML
    /*
      This method shows the Add Location Dialog when called.
     */
    public void showAddLocationDialogPressed() { mainApplication.showAddLocationDialog(); }

//    @FXML
    /*
      This method shows Manage Locations Dialog when called.
     */
//    public void showManageLocationsDialogPressed() {
//
//
//    }

    @FXML
    /*
      This method shows the User Edit Dialog when called.
     */
    public void showUserEditDialogPressed() { mainApplication.showUserEditDialog(); }

    @FXML
    /*
      This method logs out the user and returns to the user to the Welcome Screen.
     */
    public void logoutPressed() {
        mainApplication.showWelcomeScreen();
    }

    private void addMarkerForSourceReport(final WaterSourceReport r) {
        LatLong coordinates = new LatLong(r.getLocation().getLatitude(), r.getLocation().getLongitude());
        final Marker newPt = new Marker(new MarkerOptions().position(coordinates));
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
        LatLong coordinates = new LatLong(r.getLocation().getLatitude(), r.getLocation().getLongitude());
        final Marker newPt = new Marker(new MarkerOptions().position(coordinates));
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
//        _geoSrv = new GeocodingService();
        _sourceReportData.getAll().forEach(this::addMarkerForSourceReport);
        _purityReportData.getAll().forEach(this::addMarkerForPurityReport);
    }
}
