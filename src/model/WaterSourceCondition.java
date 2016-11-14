package model;

public enum WaterSourceCondition {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String displayText;

    WaterSourceCondition(String dispText) {
        displayText = dispText;
    }

    /**
     * This method displays text.
     * @return the text to be displayed.
     */
    public String getDisplayText() { return displayText; }

    /**
     * This method gets the level of Water Source Condition from a string.
     * @param str the string that translates into levels of Water Condition.
     * @return   the water condition level of the water.
     */
    public static WaterSourceCondition getFromString(String str) {
        if (WaterSourceCondition.WASTE.displayText.equals(str))
            return WaterSourceCondition.WASTE;
        else if (WaterSourceCondition.TREATABLECLEAR.displayText.equals(str))
            return WaterSourceCondition.TREATABLECLEAR;
        else if (WaterSourceCondition.TREATABLEMUDDY.displayText.equals(str))
            return WaterSourceCondition.TREATABLEMUDDY;
        else
            return WaterSourceCondition.POTABLE;
    }

    @Override
    public String toString() { return displayText; }
}
