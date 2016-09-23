package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _password = new SimpleStringProperty();

    public String getName() { return _username.get(); }
    public void setName(String name) { _username.set(name); }

    public String getPassword() { return _password.get(); }
    public void setPassword(String password) { _password.set(password); }

    public User(String username, String password) {
        _username.set(username);
        _password.set(password);
    }

    public User() {
        this("Username", "Password");
    }

    public String toString() { return _username.get(); }
}