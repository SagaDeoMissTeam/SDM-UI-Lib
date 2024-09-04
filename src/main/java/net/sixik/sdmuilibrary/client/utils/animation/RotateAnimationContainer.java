package net.sixik.sdmuilibrary.client.utils.animation;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.AnimationContainer;

import java.util.List;

public class RotateAnimationContainer implements AnimationContainer {
    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public Vector2 getSize() {
        return null;
    }

    @Override
    public List<ISDMRender> getWidgets() {
        return List.of();
    }

    @Override
    public AnimationType getType() {
        return AnimationType.ALL_OBJECT;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {

    }
}
