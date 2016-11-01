package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.User;

public class WelcomeScreenController {

    private MainFXApplication mainApplication;

    @FXML
    private void initialize() {}

    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    @FXML
    public void showUserCreateDialogPressed() {
        boolean userRegistered = mainApplication.showUserCreateDialog();
        if (userRegistered) {
            //logged in
            mainApplication.showHomeScreen(mainApplication.getMainStage());
        }
    }

    @FXML
    public void showUserLoginDialogPressed() {
        boolean userLoggedOn = mainApplication.showUserLoginDialog();
        if (userLoggedOn) {
            mainApplication.showHomeScreen(mainApplication.getMainStage());
        }
    }

}
