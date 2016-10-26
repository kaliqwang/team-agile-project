package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import model.WaterSourceReport;

public class HomeScreenController {

    private MainFXApplication mainApplication;

    @FXML
    private void initialize() {}

    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    @FXML
    public void logoutPressed() {
        mainApplication.showWelcomeScreen(mainApplication.getMainScreen());
    }

    @FXML
    public void editProfilePressed() { mainApplication.showProfileChangeDialog(); }

    @FXML
    public void showWaterSourceReportDialogPressed() {
        boolean submitted = mainApplication.showWaterSourceReportDialog();
        if (submitted) {

        }
    }

    @FXML
    public void showWaterSourceReportsDialogPressed() { mainApplication.showWaterSourceReportsDialog(); }
}