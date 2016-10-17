package controller;

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

    private Stage _dialogStage;

    private IDao<User, String> _users;

    @FXML
    private void initialize() {
        waterTypeField.setItems(FXCollections.observableArrayList(WaterType.values()));
        waterConditionField.setItems(FXCollections.observableArrayList(WaterCondition.values()));
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(IDao<User, String> dao) { _users = dao; }

    public WaterSourceReport getWaterSourceReport() {
        try {
            WaterSourceReport report = new WaterSourceReport();
            report.setWaterLocation(waterLocation.getText());
            report.setWaterType(waterTypeField.getValue());
            report.setWaterCondition(waterConditionField.getValue());
            return report;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @FXML
    private void handleWaterSourceReportSubmitButton() {
        _dialogStage.close();
    }
}
