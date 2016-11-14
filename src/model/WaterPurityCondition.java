package model;

/**
 * This class is a enum for Water Purity Condition
 */
public enum WaterPurityCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String displayText;

    WaterPurityCondition(String dispText) {
        displayText = dispText;
    }

    /**
     * This method displays text.
     * @return the text to be displayed.
     */
    public String getDisplayText() { return displayText; }

    /**
     * This method gets the level of Water Purity from a string.
     * @param str the string that translates into levels of Water Purity.
     * @return   the water purity level of the water.
     */
    public static WaterPurityCondition getFromString(String str) {
        if (WaterPurityCondition.SAFE.displayText.equals(str))
            return WaterPurityCondition.SAFE;
        else if (WaterPurityCondition.TREATABLE.displayText.equals(str))
            return WaterPurityCondition.TREATABLE;
        else
            return WaterPurityCondition.UNSAFE;
    }

    @Override
    public String toString() { return displayText; }
}
