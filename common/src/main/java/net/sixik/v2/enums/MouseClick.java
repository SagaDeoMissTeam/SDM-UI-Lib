package net.sixik.v2.enums;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public enum MouseClick {
    LEFT,
    RIGHT,
    MIDDLE,
    NONE;

    public static MouseClick from(int buttonID) {
        switch (buttonID) {
            case GLFW.GLFW_MOUSE_BUTTON_LEFT:
                return LEFT;
            case GLFW.GLFW_MOUSE_BUTTON_RIGHT:
                return RIGHT;
            case GLFW.GLFW_MOUSE_BUTTON_MIDDLE:
                return MIDDLE;
            default:
                return NONE;
        }
    }

    public boolean isLeft() {
        return this == LEFT;
    }

    public boolean isRight() {
        return this == RIGHT;
    }

    public boolean isMiddle() {
        return this == MIDDLE;
    }

    public static MouseClick getMouseClick(){
        if(Minecraft.getInstance().mouseHandler.isLeftPressed())
            return LEFT;
        else if(Minecraft.getInstance().mouseHandler.isRightPressed())
            return RIGHT;
        else if(Minecraft.getInstance().mouseHandler.isMiddlePressed())
            return MIDDLE;
        return NONE;
    }

    public boolean isPressed() {
        return this != NONE;
    }
}
