package net.sixik.v2.enums;

public enum SliderType {
    VERTICAL,
    HORIZONTAL;

    public boolean isVertical() {
        return this == VERTICAL;
    }

    public boolean isHorizontal() {
        return this == HORIZONTAL;
    }
}
