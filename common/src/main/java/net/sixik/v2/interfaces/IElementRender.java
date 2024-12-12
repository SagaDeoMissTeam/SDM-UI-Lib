package net.sixik.v2.interfaces;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IElementRender {

    void draw(PoseStack graphics, int x, int y, int width, int height);

    void drawLine(PoseStack graphics, int x, int y, int x2, int y2, float lineWidth);

    void drawCircle(PoseStack graphics, int x, int y, int radius, int segments);

    void drawTriangle(PoseStack graphics, int x, int y, int w, int h);

    void drawRoundFill(PoseStack guiGraphics, int x, int y, int width, int height, int radius);
}
