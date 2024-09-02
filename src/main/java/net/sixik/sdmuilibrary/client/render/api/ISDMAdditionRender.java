package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISDMAdditionRender {

    @OnlyIn(Dist.CLIENT)
    void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth);

    @OnlyIn(Dist.CLIENT)
    void drawArc(GuiGraphics graphics, int x, int y, int radius, int start, int end);

    @OnlyIn(Dist.CLIENT)
    void drawCircle(GuiGraphics graphics, int x, int y, int radius);

    @OnlyIn(Dist.CLIENT)
    void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h);
}
