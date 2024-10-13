package net.sixik.v2.enums;

public class CenterOperators {

    public enum Type {
        CENTER_X,
        CENTER_Y,
        CENTER_XY,
        NONE
    }

    public enum Method {
        ABSOLUTE,
        NORMAL;


        public boolean isAbsolute() {
            return this == ABSOLUTE;
        }
    }
}
