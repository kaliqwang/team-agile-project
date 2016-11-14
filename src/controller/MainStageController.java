package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainStageController {

    private MainFXApplication mainApplication;

    /**
     * This method sets the Main Application
     * @param main the application to be set as the Main FX Application
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private void handleCloseMenu() {
        System.exit(0);
    }

    @FXML
    private void handleAboutMenu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Team Agile Project - Water App");
        alert.setHeaderText("About");
        alert.setContentText("An app that maps water source quality by location");
        alert.showAndWait();
    }

}
