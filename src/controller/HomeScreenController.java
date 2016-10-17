package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

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
    public void showWaterSourceReportDialogPressed() { mainApplication.showWaterSourceReportDialog(); }

    @FXML
    public void showWaterSourceReportsDialogPressed() { mainApplication.showWaterSourceReportsDialog(); }
}