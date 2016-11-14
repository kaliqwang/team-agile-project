package model;

public enum AuthLevel {
    USER("User"),
    WORKER("Worker"),
    ADMINISTRATOR("Administrator"),
    MANAGER("Manager");

    private final String displayText;

    AuthLevel(String dispText) {
        displayText = dispText;
    }

    /**
     * This method displays text
     * @return the test to be displayed
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * This method takes in a string and returns the Authorization level from the string
     * @param str The string to be passed in a translated to an Auth Level
     * @return the Authorization level that was assigned to a user.
     */
    public static AuthLevel getFromString(String str) {
        if (AuthLevel.WORKER.displayText.equals(str))
            return AuthLevel.WORKER;
        else if (AuthLevel.ADMINISTRATOR.displayText.equals(str))
            return AuthLevel.ADMINISTRATOR;
        else if (AuthLevel.MANAGER.displayText.equals(str))
            return AuthLevel.MANAGER;
        else
            //if unrecognized, user is default
            return AuthLevel.USER;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
