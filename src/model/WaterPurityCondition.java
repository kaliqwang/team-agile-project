package model;

public enum WaterPurityCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String displayText;

    WaterPurityCondition(String dispText) {
        displayText = dispText;
    }

    public String getDisplayText() { return displayText; }

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
