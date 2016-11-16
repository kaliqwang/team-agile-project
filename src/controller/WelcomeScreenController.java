package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

public class WelcomeScreenController {

    private MainFXApplication mainApplication;

    @FXML
    private void initialize() {}

    /**
     * This method sets the Main Application
     * @param mainFXApplication the application to be set as the Main FX Application
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    @FXML
    /**
     * This method shows the User Creation Dialog when called.
     */
    public void showUserCreateDialogPressed() {
        boolean userRegistered = mainApplication.showUserCreateDialog();
        if (userRegistered) {
            //logged in
            mainApplication.showHomeScreen(mainApplication.getMainStage());
        }
    }

    @FXML
    /**
     * This method shows the User Login dialog when called.
     */
    public void showUserLoginDialogPressed() {
        boolean userLoggedOn = mainApplication.showUserLoginDialog();
        if (userLoggedOn) {
            mainApplication.showHomeScreen(mainApplication.getMainStage());
        }
    }

}
