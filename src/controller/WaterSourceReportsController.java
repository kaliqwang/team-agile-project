package controller;

import com.lynden.gmapsfx.javascript.object.LatLong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.User;
import model.WaterSourceReport;
import model.persist.IDao;
import model.persist.WaterSourceReportDaoImpl;
import util.MappableCallback;

/**
 * Created by Rayner Kristanto on 10/11/16.
 */
public class WaterSourceReportsController {
    @FXML
    private Label dateAndTimeLabel;

    @FXML
    private Label reportNumberLabel;

    @FXML
    private Label nameOfReporterLabel;

    @FXML
    private Label locationOfWaterLabel;

    @FXML
    private Label typeOfWaterLabel;

    @FXML
    private Label conditionOfWaterLabel;

    @FXML
    private TableView<WaterSourceReport> reportTable;

    @FXML
    private TableColumn<WaterSourceReport, String> dateColumn;

    @FXML
    private TableColumn<WaterSourceReport, String> locationColumn;

    private Stage _dialogStage;

    private IDao<User, String> _userData;

    private WaterSourceReportDaoImpl _reportData;

    private ObservableList<WaterSourceReport> members = FXCollections.emptyObservableList();

    @FXML
    private void initialize() {
        reportTable.setItems(members);
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate().toString()));
        //locationColumn.setCellValueFactory(cellData -> cellData.getValue().getWaterLocationProperty());
        reportTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showReportDetails(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(IDao<User,String> dao) {
        _userData = dao;
    }

    public void setReportDao(IDao<WaterSourceReport, Integer> dao) {
        _reportData = (WaterSourceReportDaoImpl) dao;
        members = FXCollections.observableArrayList(_reportData.getAll());
        reportTable.setItems(members);
    }

    public void showReportDetails(WaterSourceReport report) {
        dateAndTimeLabel.setText(report.getDate().toString());
        reportNumberLabel.setText(report.getReportNumber().toString());
        User reportingUser = _userData.get(report.getAuthor());
        nameOfReporterLabel.setText(reportingUser.getFirstName() + " "
                                    + reportingUser.getLastName() + " ("
                                    + reportingUser.getUsername() + ")");
        locationOfWaterLabel.setText("("+report.getWaterLatitude()+","+report.getWaterLongitude()+")");
        typeOfWaterLabel.setText(report.getWaterType().getDisplayText());
        conditionOfWaterLabel.setText(report.getWaterCondition().getDisplayText());
    }
}
