package net.sixik.sdmuilibrary.client.utils;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.CenterOperators;

public class GLHelper {

    public static void pushScale(GuiGraphics graphics, int scale, Vector2 position) {
        RenderHelper.pushScale(graphics, position.x, position.y, scale);
    }

    public static void pushScale(GuiGraphics graphics, float scale, Vector2 position) {
        RenderHelper.pushScale(graphics, position.x, position.y, scale);
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
