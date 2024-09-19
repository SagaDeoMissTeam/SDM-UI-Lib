package net.sixik.sdmuilib.client.utils.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.render.api.ISDMRender;

import java.util.List;


public interface AnimationContainer extends ISDMRender {

    List<ISDMRender> getWidgets();
    AnimationType getType();

    default void customAnimation(ISDMRender widget, GuiGraphics graphics, int x, int y, int width, int height, float tick){

    }

    enum AnimationType {
        ALL_OBJECT,
        SEQUENCE_OBJECT,
        REVERSE_SEQUENCE_OBJECT,
        CUSTOM
    }
}
