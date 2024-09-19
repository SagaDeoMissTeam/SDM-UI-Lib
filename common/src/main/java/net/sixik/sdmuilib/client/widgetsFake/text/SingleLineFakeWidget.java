package net.sixik.sdmuilib.client.widgetsFake.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.RenderHelper;
import net.sixik.sdmuilib.client.utils.misc.RGB;
import net.sixik.sdmuilib.client.widgetsFake.SDMFakeWidget;

import java.util.ArrayList;
import java.util.List;

public class SingleLineFakeWidget extends SDMFakeWidget {
    public Component text;
    public List<String> lines = new ArrayList<>();
    public RGB color = RGB.create(255,255,255);
    public float textSize = 1.0f;

    public SingleLineFakeWidget(Component text){
        this.text = text;
    }

    public SingleLineFakeWidget setColor(RGB color) {
        this.color = color;
        return this;
    }

    @Override
    public void draw(GuiGraphics graphics) {
        RenderHelper.pushScale(graphics, position.x, position.y, textSize);
        graphics.drawString(Minecraft.getInstance().font, text.getString(), position.x,position.y, color.toInt());
        RenderHelper.popScale(graphics);
    }
}
