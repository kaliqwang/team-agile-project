package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.User;
import model.persist.GenericDAO;

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

    private GenericDAO<User, String> _users;

    private User _currUser;

    @FXML
    private void initialize() {
        firstNameField.setTextFormatter(new TextFormatter<String>((change)->restrictName(change)));
        lastNameField.setTextFormatter(new TextFormatter<String>((change)->restrictName(change)));
    }

    private TextFormatter.Change restrictName(TextFormatter.Change change) {
        for (char i : change.getText().toCharArray()) {
            if (!(Character.isLetterOrDigit(i)|| i == '-'))
                return null;
        }
        return change;
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(GenericDAO<User, String> dao) { _users = dao; }

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
