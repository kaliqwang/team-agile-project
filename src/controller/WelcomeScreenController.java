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
    public void showSignUpDialogPressed() {
        boolean userRegistered = mainApplication.showRegistrationDialog();
        if (userRegistered) {
            //logged in
            mainApplication.showHomeScreen(mainApplication.getMainScreen());
        }
    }

    @FXML
    public void showLoginDialogPressed() {
        boolean userLoggedOn = mainApplication.showLoginDialog();
        if (userLoggedOn) {
            mainApplication.showHomeScreen(mainApplication.getMainScreen());
        }
    }

}
