package net.sixik.sdmuilib.client.render.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;

public interface ISDMAdditionRender {

    @Environment(EnvType.CLIENT)
    void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth);

    @Environment(EnvType.CLIENT)
    void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments);

    @Environment(EnvType.CLIENT)
    void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h);

    @Environment(EnvType.CLIENT)
    void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius);
}
