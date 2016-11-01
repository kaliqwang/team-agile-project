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

    public String getDisplayText() {
        return displayText;
    }

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
