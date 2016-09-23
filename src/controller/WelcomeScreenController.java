package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class WelcomeScreenController {

    private MainFXApplication mainApplication;

    @FXML
    private void initialize() {}

    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    @FXML
    public void showSignUpDialogPressed() {
        // Show the error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApplication.getMainScreen());
        alert.setTitle("Unimplemented");
        alert.setHeaderText("Unimplemented Feature");
        alert.setContentText("Sorry, Sign Up is not yet implemented!");
        alert.showAndWait();
    }

    @FXML
    public void showLoginDialogPressed() {
        boolean okClicked = mainApplication.showLoginDialog();
        if (okClicked) {
            mainApplication.showHomeScreen(mainApplication.getMainScreen());
        }
    }

}
