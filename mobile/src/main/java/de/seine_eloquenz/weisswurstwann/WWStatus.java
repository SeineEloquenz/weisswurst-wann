package de.seine_eloquenz.weisswurstwann;

public enum WWStatus {
    AVAILABLE,
    NOT_AVAILABLE,
    ERROR;

    public static WWStatus[] error() {
        return new WWStatus[] {ERROR, ERROR, ERROR, ERROR, ERROR};
    }
}
