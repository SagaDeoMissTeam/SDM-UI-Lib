package net.sixik.sdmuilibrary.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextHelper {


    /**
     * Draws text on the screen using the specified font and color.
     *
     * @param poseStack      The pose stack for rendering.
     * @param x              The x-coordinate of the text's position.
     * @param y              The y-coordinate of the text's position.
     * @param size           The size of the text.
     * @param text           The text to be rendered.
     * @param textColor      The color of the text.
     */
    public static void drawText(GuiGraphics poseStack, int x, int y, float size, Component text, int textColor) {
        drawText(poseStack, Minecraft.getInstance().font, x, y, size, text, textColor);
    }

    /**
     * Draws text on the screen using the specified font and color.
     *
     * @param poseStack      The pose stack for rendering.
     * @param font           The font to be used for rendering the text.
     * @param x              The x-coordinate of the text's position.
     * @param y              The y-coordinate of the text's position.
     * @param size           The size of the text.
     * @param text           The text to be rendered.
     * @param textColor      The color of the text.
     */
    public static void drawText(GuiGraphics poseStack, Font font, int x, int y, float size, Component text, int textColor) {
        poseStack.pose().pushPose();
        poseStack.pose().scale(size, size, 1.0f);
        poseStack.pose().translate(x, y, 0);
        poseStack.drawString(font, text, (int) x, (int) y, textColor);
        poseStack.pose().popPose();
    }

    /**
     * Draws text on the screen using the default font and color.
     *
     * @param graphics       The graphics context for rendering.
     * @param text           The text to be rendered.
     * @param x              The x-coordinate of the text's position.
     * @param y              The y-coordinate of the text's position.
     */
    public static void drawText(GuiGraphics graphics, Component text, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x, y, RGB.create(255, 255, 255).toInt());
    }

    public static void drawText(GuiGraphics graphics, String text, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, RGB.create(255, 255, 255).toInt());
    }

    public static void drawText(GuiGraphics graphics, Component text, int x, int y, RGB rgb) {
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x, y, rgb.toInt());
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


    /**
     * Calculates the size of the given text when rendered with the specified scale,
     * ensuring that the width does not exceed the given maximum width.
     *
     * @param text      The text to calculate the size for.
     * @param wight     The maximum width allowed for the text.
     * @param scale     The initial scale at which to render the text.
     * @param steps     The number of iterations to adjust the scale to fit within the maximum width.
     *
     * @return A Vector2f representing the width and scale of the text.
     *         The width is the actual width of the rendered text, and the scale is the final scale used.
     *         If the text cannot fit within the maximum width after the specified number of steps,
     *         the scale will be less than the initial scale, and the width will be the maximum allowed width.
     */
    public static Vector2f getTextRenderSize(String text, int wight, float scale, int steps){
        float s = scale;
        float w = 0;

        for (int i = 0; i < steps; i++) {
            w = getTextWidth(text, s);
            s -= 0.01f;
            if(w <= wight)
                return new Vector2f(w,s);
        }

        return new Vector2f(w,s);
    }

    public static int getTextHeight(){
        return Minecraft.getInstance().font.lineHeight;
    }

    public static float getTextHeight(float scale){
        return getTextHeight() * scale;
    }

    public static int getTextWidth(String text){
        return Minecraft.getInstance().font.width(text);
    }

    public static float getTextWidth(String text, float scale){
        return Minecraft.getInstance().font.width(text) * scale;
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

}
