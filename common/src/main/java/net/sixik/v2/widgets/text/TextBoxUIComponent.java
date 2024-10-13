package net.sixik.v2.widgets.text;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.sixik.v2.color.Colors;
import net.sixik.v2.enums.KeyboardKey;
import net.sixik.v2.render.TextRenderHelper;
import net.sixik.v2.widgets.UIComponent;
import org.lwjgl.glfw.GLFW;

public class TextBoxUIComponent extends UIComponent {

    public String boxText = "";
    private int cursorPos = 0;

    @Override
    public boolean keyPressed(KeyboardKey key) {

        if(key.keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            deleteCharacter();
        }
        if(key.keyCode == GLFW.GLFW_KEY_LEFT){
            setCursorPos(cursorPos - 1);
        }
        if(key.keyCode == GLFW.GLFW_KEY_RIGHT){
            setCursorPos(cursorPos + 1);
        }

        return super.keyPressed(key);
    }

    @Override
    public boolean charTyped(char keyChar, int modifiers) {
        insertText(Character.toString(keyChar));
        return true;
    }

    public void insertText(String string) {
        boxText = boxText.substring(0, cursorPos) + string + boxText.substring(cursorPos);
        cursorPos += string.length();
    }

    public TextBoxUIComponent setCursorPos(int cursorPos) {
        this.cursorPos = Mth.clamp(cursorPos,0, boxText.length());
        return this;
    }

    public TextBoxUIComponent setText(String text) {
        this.boxText = text;
        return this;
    }

    public TextBoxUIComponent setText(Object text) {
        return setText(TextRenderHelper.getText(text));
    }

    public void deleteCharacter() {
        if(cursorPos > 0) {
            boxText = boxText.substring(0, cursorPos - 1) + boxText.substring(cursorPos);
            cursorPos--;
        }
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        Colors.UI_NIGHT_1.draw(graphics, x, y, w, h);
        TextRenderHelper.drawText(graphics, boxText, x, y);
    }
}
