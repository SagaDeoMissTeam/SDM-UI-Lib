package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;

public interface ILineRender {

    void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth);
}
