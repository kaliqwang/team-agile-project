package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.WaterCondition;
import model.WaterSourceReport;
import model.WaterType;
import model.persist.IDao;
import model.persist.WaterSourceReportDaoImpl;

import java.util.Date;

/**
 * Created by Rayner Kristanto on 10/10/16.
 */
public class WaterSourceReportController {

    @FXML
    private TextField waterLocation;

    @FXML
    private ComboBox<WaterType> waterTypeField;

    @FXML
    private ComboBox<WaterCondition> waterConditionField;

    @FXML
    private Button WaterSourceReportSubmitButton;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private Stage _dialogStage;

    private IDao<WaterSourceReport, Integer> _reportData;

    private User _currUser;

    private GeocodingService _geoSrv;

    @FXML
    private void initialize() {
        waterTypeField.setItems(FXCollections.observableArrayList(WaterType.values()));
        waterConditionField.setItems(FXCollections.observableArrayList(WaterCondition.values()));
        mapView.addMapInializedListener(this::onMapInitialized);
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setReportDao(IDao<WaterSourceReport, Integer> dao) { _reportData = dao; }

    public void setCurrUser(User currUser) { _currUser = currUser; }

    public boolean getWaterSourceReport() {
        return true;
    }

    @FXML
    private void handleWaterSourceReportSubmitButton() {
        try {
            WaterSourceReportDaoImpl i = (WaterSourceReportDaoImpl) _reportData;
            WaterSourceReport report = new WaterSourceReport();
            report.setReportNumber(i.nextIndex());
            report.setDate(new Date());
            report.setAuthor(_currUser.getUsername());
            report.setWaterLocation(waterLocation.getText());
            report.setWaterType(waterTypeField.getValue());
            report.setWaterCondition(waterConditionField.getValue());
            _reportData.persist(report);

            _dialogStage.close();
        } catch (NullPointerException e) {

        }
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
        waterLocation.textProperty().addListener((observable) -> {
            _geoSrv.geocode(waterLocation.getText(), this::onMapSearchReceived);
        });
    }

    private void onMapSearchReceived(GeocodingResult[] results, GeocoderStatus status) {
        return;
    }
}
