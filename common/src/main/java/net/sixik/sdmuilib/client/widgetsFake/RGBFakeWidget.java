package net.sixik.sdmuilib.client.widgetsFake;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.utils.misc.RGB;

public class RGBFakeWidget extends SDMFakeWidget{

    public RGB rgb;

    public RGBFakeWidget(RGB rgb) {
        this.rgb = rgb;
    }

    @Override
    public void draw(GuiGraphics graphics) {
        rgb.draw(graphics, position.x, position.y, size.x, size.y, 0);
    }
}
