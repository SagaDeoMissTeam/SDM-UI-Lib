package net.sixik.sdmuilibrary.client.utils.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Методы для рендера и работы с текстом
 */
public class TextRenderHelper {

    public static void drawText(GuiGraphics poseStack, int x, int y, float size, Component text, int textColor) {
        drawText(poseStack, Minecraft.getInstance().font, x, y, size, text, textColor);
    }

    public static void drawText(GuiGraphics poseStack, Font font, int x, int y, float size, Component text, int textColor) {
        poseStack.pose().pushPose();
        GLRenderHelper.setScale(poseStack, size, x,y);
        poseStack.drawString(font, text, (int) x, (int) y, textColor);
        poseStack.pose().popPose();
    }

    public static void drawText(GuiGraphics graphics, Component text, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text.toString(), x, y, RGB.create(255, 255, 255).toInt());
    }

    public static void drawText(GuiGraphics graphics, String text, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, RGB.create(255, 255, 255).toInt());
    }

    public static void drawText(GuiGraphics graphics, Component text, int x, int y, RGB rgb) {
        graphics.drawString(Minecraft.getInstance().font, text.toString(), x, y, rgb.toInt());
    }

    public static void drawText(GuiGraphics graphics, String text, int x, int y, RGB rgb) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, rgb.toInt());
    }

    public static void drawTextOverWight(GuiGraphics graphics, String text, Vector2 pos, int wight) {
        drawTextOverWight(graphics, Minecraft.getInstance().font, text, pos, wight, RGB.create(255, 255, 255));
    }

    public static void drawTextOverWight(GuiGraphics graphics, String text, Vector2 pos, int wight, RGB rgb) {
        drawTextOverWight(graphics, Minecraft.getInstance().font, text, pos, wight, rgb);
    }

    public static void drawTextOverWight(GuiGraphics graphics, Font font, String text, Vector2 pos, int wight, RGB rgb) {
        if (font.width(text) > wight - 10) {
            while (font.width(text + "...") > wight - 10) {
                text = text.substring(0, text.length() - 1);
            }
            text += "...";
        }
        graphics.drawString(Minecraft.getInstance().font, text, pos.x, pos.y, rgb.toInt());
    }

    public static void drawTextInCenter(GuiGraphics graphics, Object text, Vector2 pos, Vector2 size, float scale, RGB color) {
        int w = size.x;
        int h = size.y;
        int fontSize = Minecraft.getInstance().font.lineHeight;
        int textWidth = getTextWight(text, scale);

        float scaledFontHeight = fontSize * scale;

        float textX = pos.x + (w - textWidth) / 2.0f;
        float textY = pos.y + (h - scaledFontHeight) / 2.0f;

        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);


        int colorInt = color.toInt();


        if (text instanceof FormattedCharSequence) {
            graphics.drawString(Minecraft.getInstance().font, (FormattedCharSequence) text, (int) (textX / scale), (int) (textY / scale), colorInt);
        } else if (text instanceof String) {
            graphics.drawString(Minecraft.getInstance().font, Component.literal((String) text), (int) (textX / scale), (int) (textY / scale), colorInt);
        }

        graphics.pose().popPose();
    }

    public static Vector2f getTextRenderSize(Object text, int wight, float scale, int steps){
        float s = scale;
        float w = 0;

        String t = getText(text);

        for (int i = 0; i < steps; i++) {
            w = getTextWidth(t, s);
            s -= 0.01f;
            if(w <= wight)
                return new Vector2f(w,s);
        }

        return new Vector2f(s,w);
    }

    public static int getTextHeight(){
        return Minecraft.getInstance().font.lineHeight;
    }

    public static float getTextHeight(float scale){
        int d1 = getTextHeight();
        int d2 = (int) (d1 * scale);
        return d1 + (d1 - d2);
    }

    public static int getTextWidth(Object text){
        return Minecraft.getInstance().font.width(getText(text));
    }

    public static float getTextWidth(Object text, float scale) {
        int baseWidth = Minecraft.getInstance().font.width(getText(text));
        return baseWidth * scale;
    }

    public static int getTextWight(Object text, float size) {
        return (int) getTextWidth(getText(text), size);
    }

    /**
     * Вычисляет разницу между изначальной шириной текста и шириной после масштабирования.
     */
    public static float getAdjustedTextWidth(Object text, float scale){
        int d1 = Minecraft.getInstance().font.width(getText(text));
        int d2 = (int) (d1 * scale);
        int result = d1 + (d1 - d2);
        return result == d1 ? 0 : result;

    }

    /**
     * Вычисляет разницу между изначальной высотой текста и высотой после масштабирования.
     */
    public static float getAdjustedTextHeight(float scale) {
        int d1 = getTextHeight();
        int d2 = (int) (d1 * scale);
        int result = d1 + (d1 - d2);
        return result == d1 ? 0 : result;
    }

    public static List<String> splitTextToLines(String text, float textScale, int maxWidth) {
        if (text.isEmpty()) return Collections.emptyList();
        if (!text.contains(" ") && !text.contains("\n")) return Collections.singletonList(text);

        List<String> lines = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        int index = 0;
        int wordStartIndex = 0;
        boolean wordProcessing = false;
        char prevSymbol = '0';

        for (char symbol : text.toCharArray()) {
            if (symbol != ' ') {
                wordProcessing = true;
                if (prevSymbol == ' ') {
                    wordStartIndex = index;
                }
            }

            if (symbol == '\n') {
                lines.add(builder.toString());
                builder.delete(0, builder.length());
                index = 0;
                continue;
            }

            if (getTextWidth(builder.toString() + symbol, textScale) <= maxWidth) {
                builder.append(symbol);
            } else {
                if (symbol == '.' || symbol == ',' || symbol == '!' || symbol == '?') {
                    builder.append(symbol);
                }
                if (wordProcessing) {
                    lines.add(builder.toString().substring(0, wordStartIndex));
                    builder.delete(0, wordStartIndex);
                } else {
                    lines.add(builder.toString());
                    builder.delete(0, builder.length());
                }
                if (symbol != ' ') {
                    builder.append(symbol);
                }
                index = builder.length() - 1;
            }

            wordProcessing = false;
            prevSymbol = symbol;
            index++;
        }

        if (builder.length() != 0) {
            lines.add(builder.toString());
        }
        return lines;
    }

    public static String getText(Object object){
        if(object instanceof String str)
            return str;

        else if(object instanceof Component component)
            return component.toString();

        else if(object instanceof FormattedText formattedText)
            return formattedText.getString();

        else return object.toString();
    }
}
