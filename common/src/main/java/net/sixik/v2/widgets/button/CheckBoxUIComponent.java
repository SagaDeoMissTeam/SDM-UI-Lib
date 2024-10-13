package net.sixik.v2.widgets.button;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.color.RGB;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.widgets.UIComponent;

public abstract class CheckBoxUIComponent extends UIComponent {

    public RGB iconTrue;
    public RGB iconFalse;
    public boolean value = false;

    public CheckBoxUIComponent(RGB iconTrue, RGB iconFalse) {
        this.iconTrue = iconTrue;
        this.iconFalse = iconFalse;
        setSize(16,16);
    }

    public abstract void onValueChange(boolean value);

    @Override
    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        value = !value;
        onValueChange(value);
        return true;
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        if(isMouseOver) drawBackgroundIsMouseOver(graphics, x, y, w, h);
        else RGBA.create(0,0,0,255/3).draw(graphics, x, y, w, h);
        drawIcon(graphics, x, y, w, h);
    }

    public void drawBackgroundIsMouseOver(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(255,255,255,255/3).draw(graphics, x, y, w, h);
    }

    public void drawIcon(GuiGraphics graphics, int x, int y, int w, int h) {
        int sY = h >= 16 ? 16 : 8;
        if(value)
            iconTrue.draw(graphics,  x, y, sY, sY);
        else
            iconFalse.draw(graphics,  x, y, sY, sY);
    }
}
