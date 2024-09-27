package net.sixik.sdmuilibrary.client.widgets.text.fields;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class BasicTextFieldWidget extends EditBox {

    public Consumer<String> onEnterPressed = s -> {};

    public BasicTextFieldWidget(String defaultText, int x, int y, int width, int height) {
        this(Minecraft.getInstance().font, x, y, width, height, defaultText);
    }

    public void onEnterPressed(Consumer<String> onEnterPressed) {
        this.onEnterPressed = onEnterPressed;
    }

    public BasicTextFieldWidget(Font font, int x, int y, int width, int height, String defaultText) {
        super(font, x, y, width, height, Component.empty());
        setMaxLength(80);
        setValue(defaultText);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int key) {
        if (!isMouseOver(mouseX, mouseY)) {
            this.setFocused(false);
            return false;
        }
        return super.mouseClicked(mouseX, mouseY, key);
    }

    @Override
    public boolean keyPressed(int keyCode, int p_94133_, int p_94134_) {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            this.setFocused(false);
            onEnterPressed(getValue());
            return true;
        }

        return super.keyPressed(keyCode, p_94133_, p_94134_);
    }

    private void onEnterPressed(String value) {
        onEnterPressed.accept(value);
    }
}
