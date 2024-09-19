package net.sixik.sdmuilib.client.utils;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.misc.CenterOperators;
import net.sixik.sdmuilib.client.utils.misc.RGB;

public class GLHelper {

    public static void pushColor(RGB rgb){
        RenderHelper.pushColor(rgb);
    }

    public static void popColor(){
        RenderHelper.popColor();
    }

    public static void pushScissor(GuiGraphics guiGraphics, Vector2 pos, Vector2 size){
        RenderHelper.pushScissor(guiGraphics, pos, size);
    }

    public static void pushScissor(GuiGraphics guiGraphics, int x, int y, int w, int h){
        RenderHelper.pushScissor(guiGraphics, new Vector2(x,y), new Vector2(w,h));
    }

    public static void popScissor(GuiGraphics guiGraphics){
        RenderHelper.popScissor(guiGraphics);
    }

    public static void pushScale(GuiGraphics graphics, int scale, Vector2 position) {
        RenderHelper.pushScale(graphics, position.x, position.y, scale);
    }

    public static void pushScale(GuiGraphics graphics, float scale, Vector2 position) {
        RenderHelper.pushScale(graphics, position.x, position.y, scale);
    }

    public static void pushScale(GuiGraphics graphics, int scale, Vector2 position, Vector2 size) {
        RenderHelper.pushScale(graphics, position.x, position.y, size.x, size.y, scale);
    }

    public static void pushScale(GuiGraphics graphics, float scale, Vector2 position, Vector2 size) {
        RenderHelper.pushScale(graphics, position.x, position.y, size.x, size.y, scale);
    }

    public static void popScale(GuiGraphics graphics) {
        RenderHelper.popScale(graphics);
    }

    public static void pushUpper(GuiGraphics graphics){
        RenderHelper.pushUpper(graphics);
    }

    public static void pushUpper(GuiGraphics graphics, float pos){
        RenderHelper.pushUpper(graphics, pos);
    }

    public static void popUpper(GuiGraphics graphics){
        RenderHelper.popUpper(graphics);
    }

    public static void pushRotation(GuiGraphics graphics, Vector2 pivot, int angle) {
        RenderHelper.pushRotation(graphics, pivot, angle);
    }

    public static void pushRotation(GuiGraphics graphics, Vector2 pivot, float angle) {
        RenderHelper.pushRotation(graphics, pivot, angle);
    }

    public static void pushRotation(GuiGraphics graphics, Vector2 position, Vector2 size, int angle) {
        RenderHelper.pushRotate(graphics, position.x, position.y, size.x, size.y, angle);
    }

    public static void pushRotation(GuiGraphics graphics, Vector2 position, Vector2 size, float angle) {
        RenderHelper.pushRotate(graphics, position.x, position.y, size.x, size.y, angle);
    }

    public static void popRotation(GuiGraphics graphics) {
        RenderHelper.popRotate(graphics);
    }

    public static void pushTransparent(GuiGraphics graphics, float strange){
        RenderHelper.setTransparent(graphics, strange);
    }

    public static void popTransparent(){
        RenderHelper.popTransparent();
    }

    public static void pushTransform(GuiGraphics guiGraphics, Vector2 pos, Vector2 size, float scale, float rotationAngle) {
        RenderHelper.pushTransform(guiGraphics, pos, size, scale, rotationAngle);
    }

    public static void pushTransform(GuiGraphics guiGraphics, Vector2 pos, Vector2 size, Vector2 screenSize, float scale, float rotationAngle) {
        RenderHelper.pushTransform(guiGraphics, pos, size, screenSize, scale, rotationAngle);
    }

    public static void popTransform(GuiGraphics guiGraphics) {
        RenderHelper.popTransform(guiGraphics);
    }

    /**
     * Calculates the center position of a rectangle based on the given parameters.
     *
     * @param position The top-left corner position of the rectangle.
     * @param size     The dimensions of the rectangle.
     * @param centerType The type of center to calculate (X, Y, or both).
     * @param method   The method to calculate the center (absolute or relative).
     * @return The calculated center position of the rectangle.
     */
    @Deprecated
    public static Vector2 getCenter(Vector2 position, Vector2 size, CenterOperators.Type centerType, CenterOperators.Method method){
        switch (centerType) {
            case CENTER_X -> {
                return new Vector2(position.x + (method.isAbsolute() ? size.x / 3 : size.x / 2), position.y);
            }
            case CENTER_Y -> {
                return new Vector2(position.x, position.y + (method.isAbsolute() ? size.y / 3 : size.y / 2));
            }
            case CENTER_XY -> {
                return new Vector2((method.isAbsolute() ? size.x / 3 : size.x / 2), position.y + (method.isAbsolute() ? size.y / 3 : size.y / 2));
            }
            default -> {
                return new Vector2(position.x, position.y);
            }
        }
    }

    /**
     * Calculates the center position of a rectangle based on the given parameters.
     *
     * @param pos The position of the rectangle.
     * @param size The dimensions of the rectangle.
     * @param centerType The type of center to calculate (X, Y, or both).
     * @param method The method to calculate the center (absolute or relative).
     * @return The calculated center position of the rectangle.
     *
     * The function calculates the center position of a rectangle based on the provided parameters.
     * It supports calculating the center for X, Y, or both axes, and it can use absolute or relative
     * positioning methods. The calculation is based on the given position, size, center type, and method.
     *
     * If the center type is CENTER_X, the function calculates the center position along the X-axis.
     * If the method is absolute, the center is calculated as (pos.x + size.x / 3, pos.y).
     * If the method is relative, the center is calculated as (pos.x + size.x / 2, pos.y).
     *
     * If the center type is CENTER_Y, the function calculates the center position along the Y-axis.
     * If the method is absolute, the center is calculated as (pos.x, pos.y + size.y / 3).
     * If the method is relative, the center is calculated as (pos.x, pos.y + size.y / 2).
     *
     * If the center type is CENTER_XY, the function calculates the center position for both axes.
     * If the method is absolute, the center is calculated as (pos.x + size.x / 3, pos.y + size.y / 3).
     * If the method is relative, the center is calculated as (pos.x + size.x / 2, pos.y + size.y / 2).
     *
     * If the center type is not recognized, the function returns the original position (pos.x, pos.y).
     */
    public static Vector2 getCenterWithPos(Vector2 pos, Vector2 size, CenterOperators.Type centerType, CenterOperators.Method method){
        switch (centerType) {
            case CENTER_X -> {
                return new Vector2(pos.x + (method.isAbsolute() ? size.x / 3 : size.x / 2), pos.y);
            }
            case CENTER_Y -> {
                return new Vector2(pos.x, pos.y + (method.isAbsolute() ? size.y / 3 : size.y / 2));
            }
            case CENTER_XY -> {
                return new Vector2(pos.x + (method.isAbsolute() ? size.x / 3 : size.x / 2), pos.y + (method.isAbsolute() ? size.y / 3 : size.y / 2));
            }
            default -> {
                return new Vector2(pos.x, pos.y);
            }
        }
    }
}
