package model;

/**
 * Created by Rayner Kristanto on 10/10/16.
 */
public enum OverallCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String displayText;

    OverallCondition(String dispText) {
        displayText = dispText;
    }

    public String getDisplayText() { return displayText; }

    public static OverallCondition getFromString(String str) {
        if (OverallCondition.SAFE.displayText.equals(str))
            return OverallCondition.SAFE;
        else if (OverallCondition.TREATABLE.displayText.equals(str))
            return OverallCondition.TREATABLE;
        else
            return OverallCondition.UNSAFE;
    }

    @Override
    public String toString() { return displayText; }
}
