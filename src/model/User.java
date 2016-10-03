package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _password = new SimpleStringProperty();
    private final ObjectProperty<AuthLevel> _authLevel = new SimpleObjectProperty<>();
    private final StringProperty _email = new SimpleStringProperty();
    private final StringProperty _firstName = new SimpleStringProperty();
    private final StringProperty _lastName = new SimpleStringProperty();

    public String getUsername() { return _username.get(); }
    public void setUsername(String name) { _username.set(name); }

    public String getPassword() { return _password.get(); }
    public void setPassword(String password) { _password.set(password); }

    public AuthLevel getAuthorization() { return _authLevel.get(); }
    public void setAuthorization(AuthLevel auth) { _authLevel.set(auth); }

    public String getEmail() { return _email.get(); }
    public void setEmail(String email) { _email.set(email); }

    public String getFirstName() { return _firstName.get(); }
    public void setFirstName(String fName) { _firstName.set(fName); }

    public String getLastName() { return _lastName.get(); }
    public void setLastName(String lName) { _lastName.set(lName); }

    public User() {
        this("Username", "Password");
    }

    public User(String username, String password) {
        this(username, password, AuthLevel.USER);
    }

    /**
     * Creates a new User.
     * @param username username of the user
     * @param password password of the user
     * @param auth authorization level of the user
     */
    public User(String username, String password, AuthLevel auth) {
        _username.set(username);
        _password.set(password);
        _authLevel.set(auth);
    }

    public String toString() { return _username.get(); }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof User))
            return false;
        User b = (User) o;
        if (this == b)
            return true;
        return this._username.equals(b._username);

    }
}