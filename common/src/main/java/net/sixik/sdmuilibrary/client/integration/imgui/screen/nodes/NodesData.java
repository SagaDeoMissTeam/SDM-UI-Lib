package net.sixik.sdmuilibrary.client.integration.imgui.screen.nodes;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class NodesData<T extends ImNode> {
    protected long nextNodeID = 1;
    protected long nextPinID = 50000;

    public final HashMap<Long, T> nodes = new HashMap<>();

    @Nullable
    public T findByInputPin(long pinID) {
        for (T node : nodes.values()) {
            for (ImNodePin pin : node.nodePins) {

                if(pin.type.isInput() && pin.pinID == pinID)
                    return node;
            }
        }
        return null;
    }

    @Nullable
    public T findByOutputPin(long pinID) {
        for (T node : nodes.values()) {
            for (ImNodePin pin : node.nodePins) {
                if(pin.type.isOutput() && pin.pinID == pinID)
                    return node;
            }
        }
        return null;
    }

    @Nullable
    public T findByPin(long pinID) {
        T node = findByInputPin(pinID);
        if (node == null) {
            node = findByOutputPin(pinID);
        }
        return node;
    }

    @Nullable
    public T getNode(long nodeId) {
        return nodes.getOrDefault(nodeId, null);
    }

    public T createNode(T type) {
        type.nodeID = nextNodeID++;
        type.nodePins.forEach(pin -> pin.pinID = nextPinID++);
        nodes.put(type.nodeID, type);
        return type;
    }
}
