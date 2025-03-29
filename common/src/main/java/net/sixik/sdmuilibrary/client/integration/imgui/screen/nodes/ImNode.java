package net.sixik.sdmuilibrary.client.integration.imgui.screen.nodes;

import java.util.ArrayList;
import java.util.List;

public class ImNode {
    public long nodeID;
    public List<ImNodePin> nodePins = new ArrayList<>();


    public List<ImNodePin> getInputsPin() {
        return nodePins.stream().filter(pin -> pin.type.isInput()).toList();
    }

    public List<ImNodePin> getOutputsPin() {
        return nodePins.stream().filter(pin -> pin.type.isOutput()).toList();
    }
}
