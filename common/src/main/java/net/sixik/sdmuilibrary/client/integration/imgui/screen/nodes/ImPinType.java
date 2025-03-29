package net.sixik.sdmuilibrary.client.integration.imgui.screen.nodes;

public enum ImPinType {
    INPUT,
    OUTPUT;


    public boolean isInput() {
        return this == INPUT;
    }

    public boolean isOutput() {
        return this == OUTPUT;
    }
}
