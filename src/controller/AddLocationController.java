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

/**
 * This class creates an AddLocationController
 * Created by kaliq on 11/1/2016.
 */
public class AddLocationController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private VBox mapResults;

    private Location locationResult;

    private GoogleMap map;

    private String lastSearchQuery;

    private Map<VBox, GeocodingResult> entryMapping;

    private Map<GeocodingResult, Marker> mapMarkers;

    private Marker currMarker;

    private GeocodingResult selectedResult;

    private InfoWindow infoWindowSelect;

    private Stage _dialogStage;

    private GenericDAO<Location, Integer> _locationData;

    private User _currUser;

    private GeocodingService _geoSrv;

    @FXML
    private void initialize() {
        lastSearchQuery = "";
        mapView.addMapInializedListener(this::onMapInitialized);
        entryMapping = new HashMap<>();
        mapMarkers = new HashMap<>();
    }

    /**
     * Method to set the stage passed in as the dialogStage
     * @param dialogStage the stage that is passed in to be assigned
     */
    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the location dao.
     * @param dao the data access object to be passed in.
     */
    public void setLocationDao(GenericDAO<Location, Integer> dao) { _locationData = dao; }

    /**
     * This method sets the user inputted as the current user.
     * @param currUser the user that is passed in to be the current user.
     */
    public void setCurrUser(User currUser) { _currUser = currUser; }

    /**
     * This method returns the location of the the object it's called on
     * @return locationResult the location of the object it was called on
     */
    public Location getLocation() {
        return locationResult;
    }

    @FXML
    private void handleAddLocationSubmitPressed() {
        double lat, lon;
        try {
            lat = Double.parseDouble(latitudeField.getText());
            lon = Double.parseDouble(longitudeField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            LocationDAO i = (LocationDAO) _locationData;
            Location location = new Location();
            location.setPK(i.nextIndex());
            location.setName(nameField.getText());
            location.setLatitude(lat);
            location.setLongitude(lon);
            locationResult = location;
            _locationData.persist(locationResult);
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
        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (!lastSearchQuery.equals(searchField.getText())) {
                    lastSearchQuery = searchField.getText();
                    _geoSrv.geocode(searchField.getText(), this::onMapSearchFinished);
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
