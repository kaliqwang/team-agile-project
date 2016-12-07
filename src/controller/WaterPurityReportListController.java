package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.User;
import model.WaterPurityReport;
import model.persist.IGenericDAO;
import model.persist.IQueryableReportDAO;

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
    private Label latLongLabel;

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

    private IGenericDAO<User, String> _userData;

    private IQueryableReportDAO<WaterPurityReport, Integer> _reportData;

    private ObservableList<WaterPurityReport> members = FXCollections.emptyObservableList();

    @FXML
    private void initialize() {
        reportTable.setItems(members);
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate().toString()));
        locationColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLocation().getName()));
        reportTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showReportDetails(newValue));
    }

    @FXML
    private void handleCancelPressed() { _dialogStage.close(); }

    /**
     * This method sets the Dialog Stage
     * @param dialogStage the stage passed in to be the Dialog Stage
     */
    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the User dao.
     * @param dao the data access object to be passed in.
     */
    public void setUserDao(IGenericDAO<User,String> dao) {
        _userData = dao;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the Report dao.
     * @param dao the data access object to be passed in.
     */
    public void setReportDao(IQueryableReportDAO<WaterPurityReport, Integer> dao) {
        _reportData = dao;
        members = FXCollections.observableArrayList(_reportData.getAll());
        reportTable.setItems(members);
    }

    /**
     * This method shows the report details of the object passed in
     * @param report the report object passed in whose details will be shown
     */
    private void showReportDetails(WaterPurityReport report) {
        dateAndTimeLabel.setText(report.getDate().toString());
        reportNumberLabel.setText(report.getReportNumber().toString());
        User reportingUser = _userData.get(report.getAuthor());
        nameOfReporterLabel.setText(reportingUser.getFirstName() + " "
                                    + reportingUser.getLastName() + " ("
                                    + reportingUser.getUsername() + ")");
        locationOfWaterLabel.setText(report.getLocation().getName());
        latLongLabel.setText("Lat: "+report.getLocation().getLatitude()+", Lon: "+report.getLocation().getLongitude());
        conditionOfWaterLabel.setText(report.getWaterPurityCondition().getDisplayText());
    }
}
