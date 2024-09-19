package net.sixik.sdmuilib.client.widgets.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.misc.RGB;
import net.sixik.sdmuilib.client.widgets.SDMWidget;

import java.util.ArrayList;
import java.util.List;

public class SingleLineTextWidget extends SDMWidget {

    public Component text;
    public List<String> lines = new ArrayList<>();

    public SingleLineTextWidget(Component text){
        super(null, null);
        this.text = text;
    }

    public SingleLineTextWidget(Component text, Vector2 position) {
        super(position, null);
        this.text = text;
    }

    public SingleLineTextWidget(Component text, Vector2 position, Vector2 size) {
        super(position, size);
        this.text = text;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x,y, RGB.create(255,255,255).toInt());
    }
}
