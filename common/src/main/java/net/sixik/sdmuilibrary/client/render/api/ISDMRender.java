package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;

public interface ISDMRender {

    void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick);
}
