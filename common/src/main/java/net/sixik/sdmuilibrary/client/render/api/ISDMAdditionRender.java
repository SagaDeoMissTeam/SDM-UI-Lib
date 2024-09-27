package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;

public interface ISDMAdditionRender {

    void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth);
    void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments);
    void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h);
    void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius);
}
