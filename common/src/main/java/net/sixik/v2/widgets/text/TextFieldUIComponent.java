package net.sixik.v2.widgets.text;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.v2.color.RGB;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.render.GLRenderHelper;
import net.sixik.v2.render.TextRenderHelper;
import net.sixik.v2.utils.math.Vector2;
import net.sixik.v2.widgets.UIComponent;

import java.util.List;

public class TextFieldUIComponent extends UIComponent {

    public Component fieldText = Component.empty();

    public float textSize = 1f;
    public int textSpace = 0;
    public RGBA color = RGBA.create(255,255,255,255);

    public TextFieldUIComponent() {}

    public TextFieldUIComponent(Component fieldText) {
        setText(fieldText);
    }

    public TextFieldUIComponent(String fieldText) {
        setText(fieldText);
    }

    public TextFieldUIComponent setText(Component fieldText) {
        this.fieldText = fieldText;
        calculateHeight();
        return this;
    }

    public TextFieldUIComponent calculateHeight(){
        List<String> list = TextRenderHelper.splitTextToLines(fieldText.getString(),textSize, this.width);

        setHeight((int) (list.size() * TextRenderHelper.getTextHeight(textSize) + list.size() * textSpace));
        return this;
    }

    public TextFieldUIComponent setTextSpace(int textSpace) {
        this.textSpace = textSpace;
        return this;
    }

    public TextFieldUIComponent setText(String fieldText) {
        return setText(Component.literal(fieldText));
    }

    public TextFieldUIComponent setTextTranslatable(String fieldText) {
        return setText(Component.translatable(fieldText));
    }

    public TextFieldUIComponent setTextSize(float textSize) {
        this.textSize = textSize;
        calculateHeight();
        return this;
    }

    public TextFieldUIComponent setColor(RGB color) {
        this.color = color.toARGB();
        return this;
    }

    @Override
    public void drawWidget(GuiGraphics graphics, int x, int y, int w, int h) {
        super.drawWidget(graphics, x, y, w, h);

    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        graphics.pose().pushPose();
        GLRenderHelper.pushTransform(graphics, new Vector2(x,y), new Vector2(1,1), textSize, 0);
        drawTextField(graphics, x, y, (int) (w / textSize), h);
        List<String> list = TextRenderHelper.splitTextToLines(fieldText.getString(),textSize, w);
        for (int i = 0; i < list.size(); i++) {
            TextRenderHelper.drawText(graphics, list.get(i), x + 2, (int) (y + 2 + i * TextRenderHelper.getTextHeight(textSize) + textSpace), color);
        }

        GLRenderHelper.popTransform(graphics);
        graphics.pose().popPose();
    }

    public void drawTextField(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(0,0,0,255/3).draw(graphics,x,y,w,h);
    }
}
