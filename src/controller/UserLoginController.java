package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.persist.GenericDAO;

public class UserLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    private Stage _dialogStage;

    private GenericDAO<User, String> _users;

    private User selectedUser = null;


    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(GenericDAO<User, String> dao) {
        _users = dao;
    }

    public User getSelectedUser() {
        return selectedUser;
    }


    @FXML
    private void initialize() {}

    @FXML
    private void handleOKPressed() {
        selectedUser = getUser();
        if (selectedUser != null) {
            _dialogStage.close();
        }
    }

    @FXML
    private void handleCancelPressed() {
        _dialogStage.close();
    }

    private User getUser() {
        String errorMessage = "";
        User tryUser = _users.get(usernameField.getText());
        if (tryUser == null) {
            errorMessage = "Username does not exist.";
        } else if (!passwordField.getText().equals(tryUser.getPassword())) {
            errorMessage = "Invalid username or password";
        }
        if (errorMessage.length() == 0) {
            return tryUser;
        } else {
            // Show the error message if bad data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Login Attempt Failed");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return null;
        }
    }

}
