package net.sixik.sdmuilibrary.client.widgets.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;

public class SingleLineSizableTextWidget extends SingleLineTextWidget {

    public float textSize = 1.0f;

    public SingleLineSizableTextWidget(Component text, float textSize){
        super(text, null, null);
        this.textSize = textSize;
    }

    public SingleLineSizableTextWidget(Component text, float textSize, Vector2 position) {
        super(text, position, null);
        this.textSize = textSize;
    }

    public SingleLineSizableTextWidget(Component text, float textSize, Vector2 position, Vector2 size) {
        super(text, position, size);
        this.textSize = textSize;
    }


    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        container.pushScale(textSize, new Vector2(x,y));
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x, y, RGB.create(255,255,255).toInt());
        container.popScale();
    }
}
