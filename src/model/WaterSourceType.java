package model;

public enum WaterSourceType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private final String displayText;

    WaterSourceType(String dispText) {
        displayText = dispText;
    }

    /**
     * This method displays text.
     * @return the text to be displayed.
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * This method gets the level of Water Source Type from a string.
     * @param str the string that translates into levels of Water Type.
     * @return   the water type level  of the water.
     */
    public static WaterSourceType getFromString(String str) {
        if (WaterSourceType.BOTTLED.displayText.equals(str))
            return WaterSourceType.BOTTLED;
        else if (WaterSourceType.WELL.displayText.equals(str))
            return WaterSourceType.WELL;
        else if (WaterSourceType.STREAM.displayText.equals(str))
            return WaterSourceType.LAKE;
        else if (WaterSourceType.SPRING.displayText.equals(str))
            return WaterSourceType.SPRING;
        else
            //if unrecognized, user is default
            return WaterSourceType.OTHER;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
