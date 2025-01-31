package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.misc.LineVectors;

public interface ILineRender {

    void drawStraight(GuiGraphics graphics, int x, int y, LineVectors vectors, float _long, float lineWidth);
    void drawMagneticLine(GuiGraphics graphics, int x, int y,LineVectors magnet1, int x2, int y2, LineVectors magnet2, float lineWidth);
}
