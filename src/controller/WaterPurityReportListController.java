package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.User;
import model.WaterPurityReport;
import model.persist.GenericDAO;
import model.persist.WaterPurityReportDAO;

public class WaterPurityReportListController {
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
    private TableView<WaterPurityReport> reportTable;

    @FXML
    private TableColumn<WaterPurityReport, String> dateColumn;

    @FXML
    private TableColumn<WaterPurityReport, String> locationColumn;

    private Stage _dialogStage;

    private GenericDAO<User, String> _userData;

    private WaterPurityReportDAO _reportData;

    private ObservableList<WaterPurityReport> members = FXCollections.emptyObservableList();

    @FXML
    private void initialize() {
        reportTable.setItems(members);
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate().toString()));
        //locationColumn.setCellValueFactory(cellData -> cellData.getValue().getWaterLocationProperty());
        reportTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showReportDetails(newValue));
    }

    @FXML
    private void handleCancelPressed() { _dialogStage.close(); }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(GenericDAO<User,String> dao) {
        _userData = dao;
    }

    public void setReportDao(GenericDAO<WaterPurityReport, Integer> dao) {
        _reportData = (WaterPurityReportDAO) dao;
        members = FXCollections.observableArrayList(_reportData.getAll());
        reportTable.setItems(members);
    }

    public void showReportDetails(WaterPurityReport report) {
        dateAndTimeLabel.setText(report.getDate().toString());
        reportNumberLabel.setText(report.getReportNumber().toString());
        User reportingUser = _userData.get(report.getAuthor());
        nameOfReporterLabel.setText(reportingUser.getFirstName() + " "
                                    + reportingUser.getLastName() + " ("
                                    + reportingUser.getUsername() + ")");
        locationOfWaterLabel.setText("("+report.getWaterLatitude()+","+report.getWaterLongitude()+")");
        conditionOfWaterLabel.setText(report.getWaterPurityCondition().getDisplayText());
    }
}
