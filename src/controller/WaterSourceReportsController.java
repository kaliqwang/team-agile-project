package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import model.User;
import model.WaterSourceReport;
import persist.IDao;

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
    private TableColumn<WaterSourceReport, String> dateColumn;

    @FXML
    private TableColumn<WaterSourceReport, String> locationColumn;

    private Stage _dialogStage;

    private IDao<User, String> _users;

    @FXML
    private void initialize() {
        // dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().getWaterLocationProperty());
        
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(IDao<User, String> dao) { _users = dao; }


}
