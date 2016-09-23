package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private Stage _dialogStage;

    private boolean _okClicked = false;

    @FXML
    private void initialize() {}

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return _okClicked;
    }

    @FXML
    private void handleOKPressed() {
        if (isInputValid()) {
            _okClicked = true;
            _dialogStage.close();
        }
    }

    @FXML
    private void handleCancelPressed() {
        _dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (!usernameField.getText().equals("username") || !passwordField.getText().equals("password")) {
            errorMessage += "Invalid username or password";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message if bad data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Login Attempt Failed");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
