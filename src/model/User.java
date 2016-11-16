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

    /**
     * This method is a getter for the Username.
     * @return the Username of the User.
     */
    public String getUsername() { return _username.get(); }

    /**
     * This method is the setter for the Username.
     * @param name the Username of the User.
     */
    public void setUsername(String name) { _username.set(name); }

    /**
     * This method is a getter for the password.
     * @return the password of the user.
     */
    public String getPassword() { return _password.get(); }

    /**
     * This is the setter for the Password
     * @param password the password of the user.
     */
    public void setPassword(String password) { _password.set(password); }

    /**
     * This method is a getter for the authorization.
     * @return the Authorization of the user.
     */
    public AuthLevel getAuthorization() { return _authLevel.get(); }

    /**
     * This is the setter for the Authorization.
     * @param auth the Authorization Level of the user.
     */
    public void setAuthorization(AuthLevel auth) { _authLevel.set(auth); }

    /**
     * This method is a getter for the Email.
     * @return the Email of the user.
     */
    public String getEmail() { return _email.get(); }

    /**
     * This is the setter for the Email
     * @param email the Email of the user.
     */
    public void setEmail(String email) { _email.set(email); }

    /**
     * This method is a getter for the FirstName.
     * @return the FirstName of the User.
     */
    public String getFirstName() { return _firstName.get(); }

    /**
     * This is the setter for the FirstName.
     * @param fName the FirstName of the user.
     */
    public void setFirstName(String fName) { _firstName.set(fName); }

    /**
     * This method is a getter for the LastName.
     * @return the LastName of the User.
     */
    public String getLastName() { return _lastName.get(); }

    /**
     * This is the setter for the LastName.
     * @param lName the LastName of the user.
     */
    public void setLastName(String lName) { _lastName.set(lName); }

    /**
     * This method creates a User with default Username and Password.
     */
    public User() {
        this("Username", "Password");
    }

    /**
     * This method creates a User with the inputted data.
     * @param username the username of the User
     * @param password the password of the User
     */
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

    /**
     * This method creates a new User with the inputted data.
     * @param uData the data to be assigned to the user.
     */
    public User(Data uData) {
        _username.set(uData.username);
        _password.set(uData.password);
        _authLevel.set(uData.authLevel);
        _email.set(uData.email);
        _firstName.set(uData.firstName);
        _lastName.set(uData.lastName);
    }

    @Override
    public String toString() { return _username.get(); }

    @Override
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

    /**
     * This is the class that defines a data object and its creation
     */
    public class Data {
        private String username;
        private String password;
        private AuthLevel authLevel;
        private String email;
        private String firstName;
        private String lastName;

        /**
         * This method creates a data object
         * @param uname the username of the data object
         * @param pwd   the password of the data object
         * @param auth  the authorization level of the data object
         * @param email the email of the data object
         * @param fName the first name of the data object
         * @param lName the last name of the data object
         */
        public Data(
                String uname,
                String pwd,
                AuthLevel auth,
                String email,
                String fName,
                String lName
        ) {
            this.username = uname;
            this.password = pwd;
            this.authLevel = auth;
            this.email = email;
            this.firstName = fName;
            this.lastName = lName;
        }
    }

    /**
     * This method returns the Plain Data
     * @return a data object with the attributes of the object passed in.
     */
    public Data getPlainData() {
        return new Data(_username.get(),
                        _password.get(),
                        _authLevel.get(),
                        _email.get(),
                        _firstName.get(),
                        _lastName.get());
    }
}