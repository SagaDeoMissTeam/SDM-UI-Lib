package net.sixik.v2.enums;

public enum WidgetRender {
    ONLY_RENDER,
    HIDE,
    ALL_RENDER;

    public boolean isOnlyRender() {
        return this == WidgetRender.ONLY_RENDER;
    }

    public boolean isHide() {
        return this == WidgetRender.HIDE;
    }

    public boolean isAllRender() {
        return this == WidgetRender.ALL_RENDER;
    }
}
