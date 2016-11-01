package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.AuthLevel;
import model.User;
import model.persist.GenericDAO;


/**
 * Created by kaliq on 10/31/2016.
 */
public class UserCreateController {
    private boolean passwordMatches = true;
    private boolean passwordLengthValid = true;
    private boolean usernameInUse = false;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField verifyPassField;

    @FXML
    private ComboBox<AuthLevel> userTypeField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button registerButton;

    private Stage _dialogStage;

    private GenericDAO<User, String> _users;

    @FXML
    private void initialize() {
        firstNameField.setTextFormatter(new TextFormatter<String>((change)->restrictName(change)));
        lastNameField.setTextFormatter(new TextFormatter<String>((change)->restrictName(change)));
        usernameField.textProperty().addListener((event) -> handleUsernameChanged() );
        passwordField.textProperty().addListener((event) -> handlePasswordChanged() );
        verifyPassField.textProperty().addListener((event) -> handleVerifyPassword() );
        userTypeField.setItems(FXCollections.observableArrayList(AuthLevel.values()));
    }

    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }

    public void setUserDao(GenericDAO<User, String> dao) { _users = dao; }

    public User getUser() {
        if (validateInputs()) {
            User newUser = new User(usernameField.getText(),
                    passwordField.getText(),
                    userTypeField.getValue());
            newUser.setEmail(emailField.getText());
            newUser.setFirstName(firstNameField.getText());
            newUser.setLastName(lastNameField.getText());
            return newUser;
        }
        else
            return null;
    }

    @FXML
    private void handleOKPressed() {
        if (validateInputs()) {
            _users.persist(getUser());
            _dialogStage.close();
        }
    }

    @FXML
    private void handleCancelPressed() {
        _dialogStage.close();
    }

    private boolean validateInputs() {
        boolean nameNotEmpty = (firstNameField.getText().length() != 0
                && lastNameField.getText().length() != 0);
        boolean emailNotEmpty = emailField.getText().length() != 0;
        return nameNotEmpty && emailNotEmpty
                && !usernameInUse
                && passwordLengthValid
                && passwordMatches;
    }

    private TextFormatter.Change restrictName(TextFormatter.Change change) {
        for (char i : change.getText().toCharArray()) {
            if (!(Character.isLetterOrDigit(i)|| i == '-'))
                return null;
        }
        return change;
    }

    private void handleUsernameChanged() {
        usernameInUse = (_users.get(usernameField.getText()) != null);
        setErrorLabel();
    }

    private void handlePasswordChanged() {
        //validate password
        int length = passwordField.getText().length();
        passwordLengthValid = (length >= 8 && length <= 32);
        setErrorLabel();
    }

    private void handleVerifyPassword() {
        passwordMatches = verifyPassField.getText().equals(passwordField.getText());
        setErrorLabel();
    }

    private void setErrorLabel() {
        String error = "";
        if (usernameInUse) {
            error += "Username already in use.\n";
        }
        if (!passwordLengthValid && passwordField.getText().length() != 0) {
            error += "Password should be between 8 and 32 characters long.\n";
        }
        if (!passwordMatches && (verifyPassField.getText().length() == 0
                || passwordField.getText().length() == 0)) {
            error += "Passwords do not match.";
        }
        errorLabel.setText(error);
    }

}
