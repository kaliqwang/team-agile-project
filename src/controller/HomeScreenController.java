package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import model.User;
import model.WaterSourceReport;
import model.persist.IDao;
import model.persist.WaterSourceReportDaoImpl;

import java.util.HashMap;
import java.util.Map;

public class HomeScreenController {

    private MainFXApplication mainApplication;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private GeocodingService _geoSrv;

    private IDao<User, String> _userData;

    private WaterSourceReportDaoImpl _reportData;

    private Map<WaterSourceReport, Marker> points;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this::onMapInitialized);
        points = new HashMap<>();
    }

    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }
    public void setUserDao(IDao<User,String> dao) {
        _userData = dao;
    }

    public void setReportDao(IDao<WaterSourceReport, Integer> dao) {
        _reportData = (WaterSourceReportDaoImpl) dao;
    }

    @FXML
    public void logoutPressed() {
        mainApplication.showWelcomeScreen(mainApplication.getMainScreen());
    }

    @FXML
    public void editProfilePressed() { mainApplication.showProfileChangeDialog(); }

    @FXML
    public void showWaterSourceReportDialogPressed() {
        WaterSourceReport submitted = mainApplication.showWaterSourceReportDialog();
        if (submitted != null)
            addMarkerForReport(submitted);
    }

    @FXML
    public void showWaterSourceReportsDialogPressed() {
        mainApplication.showWaterSourceReportsDialog();
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
                        + "Water Type: " + r.getWaterType().getDisplayText() + "<br>"
                        + "Water Condition:" + r.getWaterCondition().getDisplayText());
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