package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.User;
import model.persist.IGenericDAO;

public class UserEditController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    private Stage _dialogStage;

    private IGenericDAO<User, String> _users;

    private User _currUser;

    @FXML
    private void initialize() {
        firstNameField.setTextFormatter(new TextFormatter<String>(this::restrictName));
        lastNameField.setTextFormatter(new TextFormatter<String>(this::restrictName));
    }

    private TextFormatter.Change restrictName(TextFormatter.Change change) {
        for (char i : change.getText().toCharArray()) {
            if (!(Character.isLetterOrDigit(i)|| i == '-'))
                return null;
        }
        return change;
    }

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
    public void setUserDao(IGenericDAO<User, String> dao) { _users = dao; }

    /**
     * This method sets the current user
     * @param currUser the user object passed in that's going to be the new current user
     */
    public void setCurrUser(User currUser) {
        _currUser = currUser;
        firstNameField.setText(_currUser.getFirstName());
        lastNameField.setText(_currUser.getLastName());
        emailField.setText(_currUser.getEmail());
    }

    @FXML
    private void handleOKPressed() {
        if (firstNameField.getText().length() == 0 ||
                lastNameField.getText().length() == 0 ||
                emailField.getText().length() == 0) {
            errorLabel.setText("Cannot leave fields blank.");
        } else {
            _currUser.setFirstName(firstNameField.getText());
            _currUser.setLastName(lastNameField.getText());
            _currUser.setEmail(emailField.getText());
            _users.update(_currUser.getUsername(), _currUser);
            _dialogStage.close();
        }
    }

    @FXML
    private void handleCancelPressed() {
        _dialogStage.close();
    }

}
