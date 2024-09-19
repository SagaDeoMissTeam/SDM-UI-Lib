package net.sixik.sdmuilib.client.widgets.progressBar;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.utils.misc.RGB;

public class FillingProgressBarWidget extends BasicProgressBarWidget{

    public RGB backgroundColor = RGB.create(0,0,0);
    public RGB fillColor = RGB.create(255,255,255);

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        backgroundColor.draw(graphics, x, y, width, height, tick);
        fillColor.draw(graphics, x, y, getFilling(), height, tick);
    }
}
