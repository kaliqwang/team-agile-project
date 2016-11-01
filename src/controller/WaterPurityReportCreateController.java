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
    private TextField waterSearchLocation;

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

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

    private User _currUser;

    private GeocodingService _geoSrv;

    @FXML
    private void initialize() {
        overallConditionField.setItems(FXCollections.observableArrayList(WaterPurityCondition.values()));
        lastSearchQuery = "";
        mapView.addMapInializedListener(this::onMapInitialized);
        entryMapping = new HashMap<>();
        mapMarkers = new HashMap<>();
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setReportDao(GenericDAO<WaterPurityReport, Integer> dao) { _reportData = dao; }

    public void setCurrUser(User currUser) { _currUser = currUser; }

    public WaterPurityReport getWaterPurityReport() {
        return reportResult;
    }

    @FXML
    private void handleWaterPurityReportSubmitPressed() {
        double lat, lon;
        try {
            lat = Double.parseDouble(latitudeField.getText());
            lon = Double.parseDouble(longitudeField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            WaterPurityReportDAO i = (WaterPurityReportDAO) _reportData;
            WaterPurityReport report = new WaterPurityReport();
            report.setReportNumber(i.nextIndex());
            report.setDate(new Date());
            report.setAuthor(_currUser.getUsername());
            report.setWaterLatitude(lat);
            report.setWaterLongitude(lon);
            report.setVirusPPM(virusPPM.getText());
            report.setContaminantPPM(contaminantPPM.getText());
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

    private void onMapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.7490, -84.3880))
                .mapType(MapTypeIdEnum.TERRAIN)
                .overviewMapControl(true)
                .panControl(true)
                .rotateControl(true)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        _geoSrv = new GeocodingService();
        waterSearchLocation.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (!lastSearchQuery.equals(waterSearchLocation.getText())) {
                    lastSearchQuery = waterSearchLocation.getText();
                    _geoSrv.geocode(waterSearchLocation.getText(), this::onMapSearchFinished);
                }
            }
        });

        latitudeField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onEnterLatLong();
            }
        });
        longitudeField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onEnterLatLong();
            }
        });

    }

    private void onMapSearchFinished(GeocodingResult[] results, GeocoderStatus status) {
        if (infoWindowSelect != null)
            infoWindowSelect.close();
        selectedResult = null;
        mapResults.getChildren().clear();
        entryMapping.clear();
        if (!mapMarkers.containsValue(currMarker) && currMarker != null) {
            map.removeMarker(currMarker);
        }
        for (Marker m : mapMarkers.values()) {
            map.removeMarker(m);
        }
        mapMarkers.clear();
        if (status == GeocoderStatus.ZERO_RESULTS) {
            VBox resultsZero = new VBox(2);
            resultsZero.setAlignment(Pos.CENTER);
            resultsZero.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY),
                    new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(1.0,1.0,1.0,1.0))));
            resultsZero.getChildren().add(new Label("No results found."));
            resultsZero.setMaxWidth(mapResults.getWidth());
            resultsZero.setMinHeight(70.0);
        } else if (status == GeocoderStatus.OK) {
            for (GeocodingResult g : results) {
                List<GeocoderAddressComponent> components = g.getAddressComponents();
                LatLong point = g.getGeometry().getLocation();
                VBox entry = new VBox(0);
                entry.setAlignment(Pos.CENTER);
                Label shortName = new Label(components.get(0).getShortName());
                shortName.setFont(Font.font(shortName.getFont().getFamily(),
                        FontWeight.BOLD,
                        shortName.getFont().getSize()));

                entry.getChildren().add(shortName);
                entry.getChildren().add(new Label("Latitude: " + Double.toString(point.getLatitude()).substring(0, 10)));
                entry.getChildren().add(new Label("Longitude: " + Double.toString(point.getLongitude()).substring(0, 10)));

                entry.setMaxWidth(mapResults.getWidth());
                entry.setMinHeight(70.0);
                entry.setOnMouseClicked(this::onSelectEntry);
                entryMapping.put(entry, g);
                mapResults.getChildren().add(entry);
                mapMarkers.put(g, new Marker(new MarkerOptions().position(point)));
                map.addMarker(mapMarkers.get(g));
            }
        } else {
            //TODO: this shouldn't normally happen
        }
        return;
    }

    private void onEnterLatLong() {
        double lat, lon;
        try {
            lat = Double.parseDouble(latitudeField.getText());
            lon = Double.parseDouble(longitudeField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        if (infoWindowSelect != null)
            infoWindowSelect.close();
        selectedResult = null;
        mapResults.getChildren().clear();
        entryMapping.clear();
        if (!mapMarkers.containsValue(currMarker) && currMarker != null) {
            map.removeMarker(currMarker);
        }
        for (Marker m : mapMarkers.values()) {
            map.removeMarker(m);
        }
        LatLong pt = new LatLong(lat, lon);
        currMarker = new Marker(new MarkerOptions().position(pt));
        map.addMarker(currMarker);
        InfoWindowOptions windowOptions = new InfoWindowOptions();
        windowOptions.content("<h3>Selected location</h3>"
                + "Latitude: " + pt.getLatitude() + "<br>"
                + "Longitude: " + pt.getLongitude());
        infoWindowSelect = new InfoWindow(windowOptions);
        infoWindowSelect.open(map, currMarker);
    }

    private void onSelectEntry(MouseEvent evt) {
        Object vboxSelected = evt.getSource();
        for (Node entry : mapResults.getChildren()) {
            if (entry instanceof VBox) {
                ((VBox) entry).setBackground(Background.EMPTY);
            }
        }
        if (vboxSelected instanceof VBox) {
            ((VBox) vboxSelected).setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            GeocodingResult selected = entryMapping.get(vboxSelected);
            if (selectedResult != selected) {
                selectedResult = selected;
                LatLong point = selectedResult.getGeometry().getLocation();
                latitudeField.setText(Double.toString(point.getLatitude()));
                longitudeField.setText(Double.toString(point.getLongitude()));
                InfoWindowOptions windowOptions = new InfoWindowOptions();
                windowOptions.content("<h3>Selected location</h3>"
                        + "Latitude: " + point.getLatitude() + "<br>"
                        + "Longitude: " + point.getLongitude());
                infoWindowSelect = new InfoWindow(windowOptions);
                infoWindowSelect.open(map, mapMarkers.get(selected));
            }
        }
    }


}
