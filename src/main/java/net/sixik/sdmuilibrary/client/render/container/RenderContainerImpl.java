package net.sixik.sdmuilibrary.client.render.container;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.CenterOperators;



public class RenderContainerImpl extends RenderContainer{



    public RenderContainerImpl(GuiGraphics graphics, Vector2 mousePosition) {
        super(graphics, mousePosition);
    }

    public void pushScale(int scale, Vector2 position) {
        RenderHelper.pushScale(graphics, position.x, position.y, scale);
    }

    public void pushScale(float scale, Vector2 position) {
        RenderHelper.pushScale(graphics, position.x, position.y, scale);
    }

    public void popScale() {
        RenderHelper.popScale(graphics);
    }

    public void pushUpper(){
        RenderHelper.pushUpper(graphics);
    }

    public void pushUpper(float pos){
        RenderHelper.pushUpper(graphics, pos);
    }

    public void popUpper(){
        RenderHelper.popUpper(graphics);
    }

    public void pushRotation(Vector2 position, Vector2 size, float angle) {
        RenderHelper.pushRotate(graphics, position.x, position.y, size.x, size.y, angle);
    }

    public void popRotation() {
        RenderHelper.popRotate(graphics);
    }

    public void pushTransparent(float strange){
        RenderHelper.setTransparent(graphics, strange);
    }

    public void popTransparent(){
        RenderHelper.popTransparent();
    }

    public Vector2 getCenter(Vector2 position, Vector2 size, CenterOperators.Type centerType, CenterOperators.Method method){
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

    public Vector2 getCenterWithPos(Vector2 pos, Vector2 size, CenterOperators.Type centerType, CenterOperators.Method method){
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
