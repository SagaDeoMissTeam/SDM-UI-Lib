package net.sixik.v2.widgets.button;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.v2.color.RGB;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.render.GLRenderHelper;
import net.sixik.v2.render.TextRenderHelper;
import net.sixik.v2.utils.math.Vector2;
import net.sixik.v2.widgets.UIComponent;

public abstract class ButtonUIComponent extends UIComponent {

    public RGB icon;
    public Component title;
    public float textScale = 1f;

    public ButtonUIComponent(Component title, RGB icon) {
        this.title = title;
        this.icon = icon;
    }

    public ButtonUIComponent setTextScale(float scale) {
        this.textScale = scale;
        return this;
    }

    public abstract void onButtonClicked(double mouseX, double mouseY, MouseClick click);

    @Override
    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        onButtonClicked(mouseX, mouseY, mouseClick);
        return true;
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        if(isMouseOver) drawBackgroundIsMouseOver(graphics, x, y, w, h);
        else RGBA.create(0,0,0,255/3).draw(graphics, x, y, w, h);
        drawTextAndIcon(graphics, x, y, w, h);
    }

    public void drawBackgroundIsMouseOver(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(255,255,255,255/3).draw(graphics, x, y, w, h);
    }

    public void drawTextAndIcon(GuiGraphics graphics, int x, int y, int w, int h) {
        int sY = h >= 16 ? 16 : 8;
        icon.draw(graphics,  x + 1, y + 1, sY - 2, sY - 2);
        GLRenderHelper.pushTransform(graphics, new Vector2(x,y), new Vector2(1,1), textScale, 0);
        TextRenderHelper.drawText(graphics, title, x + 2 + sY, y + (h - TextRenderHelper.getTextHeight() + 1) / 2, RGBA.create(128,128,128, 255));
        GLRenderHelper.popTransform(graphics);
    }
}
