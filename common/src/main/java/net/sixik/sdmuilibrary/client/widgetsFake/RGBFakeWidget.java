package net.sixik.sdmuilibrary.client.widgetsFake;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;

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
