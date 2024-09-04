package net.sixik.sdmuilibrary.client.utils.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;

import java.util.List;

@Deprecated
public interface AnimationContainer extends ISDMRender {

    Vector2 getPosition();
    Vector2 getSize();
    List<ISDMRender> getWidgets();
    AnimationType getType();

    default void customAnimation(GuiGraphics graphics, int x, int y, int width, int height, float tick){

    }

    enum AnimationType {
        ALL_OBJECT,
        SEQUENCE_OBJECT,
        REVERSE_SEQUENCE_OBJECT,
        CUSTOM
    }
}
