package net.sixik.sdmuilib.client.widgets.text;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.math.Vector2;

public class MultiLineSizableTextWidget extends MultiLineTextWidget {

    public float textSize = 1.0f;

    public MultiLineSizableTextWidget(Component text, float textSize) {
        super(text);
        this.textSize = textSize;
    }

    public MultiLineSizableTextWidget(Component text, float textSize, Vector2 size){
        super(text, null, size);
        this.textSize = textSize;
    }

    public MultiLineSizableTextWidget(Component text, float textSize, Vector2 position, Vector2 size) {
        super(text, position, size);
        this.textSize = textSize;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        container.pushScale(textSize, new Vector2(x,y));
        super.draw(graphics, x, y, width, height, mouseX, mouseY, tick);
        container.popScale();
    }
}
