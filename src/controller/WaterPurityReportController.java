package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import model.persist.IDao;
import model.persist.WaterSourceReportDaoImpl;

import java.util.Date;

/**
 * Created by Bongkyun Kang on 10/10/16.
 */
public class WaterPurityReportController {

    @FXML
    private TextField waterLocation;

    @FXML
    private TextField virusPPM;

    @FXML
    private TextField contaminantPPM;

    @FXML
    private ComboBox<OverallCondition> overallConditionField;

    @FXML
    private Button waterPurityReportSubmitButton;

    private Stage _dialogStage;

    private IDao<WaterSourceReport, Integer> _reportData;

    private User _currUser;

    @FXML
    private void initialize() {
        overallConditionField.setItems(FXCollections.observableArrayList(OverallCondition.values()));
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setReportDao(IDao<WaterSourceReport, Integer> dao) { _reportData = dao; }

    public void setCurrUser(User currUser) { _currUser = currUser; }

    public boolean getWaterPurityReport() {
        return true;
    }

    @FXML
    private void handleWaterSourceReportSubmitButton() {
        try {
            WaterSourceReportDaoImpl i = (WaterSourceReportDaoImpl) _reportData;
            WaterPurityReport report = new WaterPurityReport();
            report.setReportNumber(i.nextIndex());
            report.setDate(new Date());
            report.setAuthor(_currUser.getUsername());
            report.setWaterLocation(waterLocation.getText());
            report.setOverallCondition(overallConditionField.getValue());
            report.setVirusPPM(virusPPM.getText());
            report.setContaminantPPM(contaminantPPM.getText());
           // _reportData.persist(report);

            _dialogStage.close();
        } catch (NullPointerException e) {

        }
    }


}
