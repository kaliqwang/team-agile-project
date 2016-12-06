package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.persist.IGenericDAO;

public class UserLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    private Stage _dialogStage;

    private IGenericDAO<User, String> _users;

    private User selectedUser = null;


    /**
     * This method sets the Dialog Stage
     * @param dialogStage the stage passed in to be the Dialog Stage
     */
    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    /**
     * This method takes in a data access object and assigns
     * it to be the User dao.
     * @param dao the data access object to be passed in.
     */
    public void setUserDao(IGenericDAO<User, String> dao) {
        _users = dao;
    }

    /**
     * This method returns the user of the object it was called on
     * @return the user that was selected.
     */
    public User getSelectedUser() {
        return selectedUser;
    }


//    @FXML
//    private void initialize() {}

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
