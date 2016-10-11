package model;

/**
 * Created by Rayner Kristanto on 10/10/16.
 */
public enum WaterCondition {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String displayText;

    WaterCondition(String dispText) {
        displayText = dispText;
    }

    public String getDisplayText() { return displayText; }

    public static WaterCondition getFromString(String str) {
        if (WaterCondition.WASTE.displayText.equals(str))
            return WaterCondition.WASTE;
        else if (WaterCondition.TREATABLECLEAR.displayText.equals(str))
            return WaterCondition.TREATABLECLEAR;
        else if (WaterCondition.TREATABLEMUDDY.displayText.equals(str))
            return WaterCondition.TREATABLEMUDDY;
        else
            return WaterCondition.POTABLE;
    }

    @Override
    public String toString() { return displayText; }
}
