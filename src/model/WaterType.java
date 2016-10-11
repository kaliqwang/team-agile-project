package model;

/**
 * Created by Rayner Kristanto on 10/10/16.
 */
public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private final String displayText;

    WaterType(String dispText) {
        displayText = dispText;
    }

    public String getDisplayText() {
        return displayText;
    }

    public static WaterType getFromString(String str) {
        if (WaterType.BOTTLED.displayText.equals(str))
            return WaterType.BOTTLED;
        else if (WaterType.WELL.displayText.equals(str))
            return WaterType.WELL;
        else if (WaterType.STREAM.displayText.equals(str))
            return WaterType.LAKE;
        else if (WaterType.SPRING.displayText.equals(str))
            return WaterType.SPRING;
        else
            //if unrecognized, user is default
            return WaterType.OTHER;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
