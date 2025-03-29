package net.sixik.sdmuilibrary.client.integration.imgui.screen.nodes;

import java.util.ArrayList;
import java.util.List;

public class ImNodePin {
    public ImPinType type;
    public long pinID;
    public List<ImNode> connectedPins;

    public ImNodePin(long pinID, ImPinType type) {
        this.type = type;
        this.pinID = pinID;
        this.connectedPins = new ArrayList<>();
    }
}
