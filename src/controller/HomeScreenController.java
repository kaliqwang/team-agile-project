package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import model.User;
import model.WaterSourceReport;
import model.persist.GenericDAO;
import model.persist.WaterSourceReportDAO;

import java.util.HashMap;
import java.util.Map;

public class HomeScreenController {

    private MainFXApplication mainApplication;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private GeocodingService _geoSrv;

    private GenericDAO<User, String> _userData;

    private WaterSourceReportDAO _reportData;

    private Map<WaterSourceReport, Marker> points;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this::onMapInitialized);
        points = new HashMap<>();
    }

    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }
    public void setUserDao(GenericDAO<User,String> dao) {
        _userData = dao;
    }

    public void setReportDao(GenericDAO<WaterSourceReport, Integer> dao) {
        _reportData = (WaterSourceReportDAO) dao;
    }

    @FXML
    public void logoutPressed() {
        mainApplication.showWelcomeScreen(mainApplication.getMainStage());
    }

    @FXML
    public void showUserEditDialogPressed() { mainApplication.showUserEditDialog(); }

    @FXML
    public void showWaterSourceReportCreateDialogPressed() {
        WaterSourceReport submitted = mainApplication.showWaterSourceReportCreateDialog();
        if (submitted != null)
            addMarkerForReport(submitted);
    }

    @FXML
    public void showWaterPurityReportCreateDialogPressed() {
        boolean submitted = mainApplication.showWaterPurityReportCreateDialog();
        if (submitted) {

        }
    }

    @FXML
    public void showWaterSourceReportListDialogPressed() {
        mainApplication.showWaterSourceReportListDialog();
    }

    private void addMarkerForReport(final WaterSourceReport r) {
        LatLong coords = new LatLong(r.getWaterLatitude(), r.getWaterLongitude());
        final Marker newPt = new Marker(new MarkerOptions().position(coords));
        map.addMarker(newPt);
        points.put(r, newPt);
        User author = _userData.get(r.getAuthor());
        map.addUIEventHandler(newPt, UIEventType.click, jsObject -> {
            if (points.get(r).equals(newPt)) {
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
        for (WaterSourceReport r : _reportData.getAll()) {
            addMarkerForReport(r);
        }
    }
}
