package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import model.persist.IDao;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    private Stage _dialogStage;

    private IDao<User, String> _users;

    private boolean _okClicked = false;

    @FXML
    private void initialize() {}

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(IDao<User, String> dao) { _users = dao; }

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
        User tryUser = _users.get(usernameField.getText());
        if (tryUser == null) {
            errorMessage = "Username does not exist.";
        } else if (!passwordField.getText().equals(tryUser.getPassword())) {
            errorMessage = "Invalid username or password";
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
