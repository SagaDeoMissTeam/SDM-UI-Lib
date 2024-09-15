package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ISDMAdditionRender {

    @OnlyIn(Dist.CLIENT)
    void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth);

    @OnlyIn(Dist.CLIENT)
    void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments);

    @OnlyIn(Dist.CLIENT)
    void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h);

    @OnlyIn(Dist.CLIENT)
    void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius);
}
