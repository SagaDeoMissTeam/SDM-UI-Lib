package net.sixik.sdmuilibrary.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;

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

}
