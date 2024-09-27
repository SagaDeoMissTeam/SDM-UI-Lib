package net.sixik.sdmuilibrary.client.render.container;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;

import java.util.HashMap;
import java.util.function.Consumer;


public class RenderContainer {

    public Vector2 mousePosition;
    public GuiGraphics graphics;
    public final HashMap<String, ContainerObject<?>> objectHashMap = new HashMap<String, ContainerObject<?>>();

    public Consumer<RenderContainerImpl> renderContainerConsumer = s -> {
    };

    public RenderContainer(GuiGraphics graphics, Vector2 mousePosition) {
        this.graphics = graphics;
        this.mousePosition = mousePosition;
    }

    public void addConfig(String id, ContainerObject<?> object) {
        objectHashMap.put(id, object);
    }

    public ContainerObject<?> getConfig(String id) {
        return objectHashMap.getOrDefault(id, null);
    }
}
