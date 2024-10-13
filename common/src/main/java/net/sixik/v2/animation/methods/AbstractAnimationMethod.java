package net.sixik.v2.animation.methods;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.animation.AnimationMethodState;

public abstract class AbstractAnimationMethod {

    public AnimationMethodState state;
    public boolean isStart = false;

    public abstract void applyEffect(GuiGraphics graphics, int x, int y, int width, int height);
    public abstract void disableEffect(GuiGraphics graphics, int x, int y, int width, int height);

    public abstract void tick();

    public abstract boolean isEnd();

    public boolean isStart() {
        return isStart;
    }
}
