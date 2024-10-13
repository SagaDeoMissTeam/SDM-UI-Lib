package net.sixik.v2.widgets.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.sixik.v2.enums.KeyboardKey;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.widgets.UIComponent;

public final class UIScreenWrapper extends Screen {

    private UIScreen screen;
    UIScreenWrapper(UIScreen screen) {
        super(Component.empty());
        this.screen = screen;
    }

    @Override
    public boolean isPauseScreen() {
        return screen.isPauseGame;
    }

    @Override
    protected void init() {
        screen.initGui();
    }

    public UIScreen getScreen() {
        return screen;
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
        return screen.mouseScrolled(p_94686_, p_94687_, p_94688_) && super.mouseScrolled(p_94686_, p_94687_, p_94688_);
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int buttonid) {
        return screen.mousePressed(p_94695_, p_94696_, MouseClick.from(buttonid)) && super.mouseClicked(p_94695_, p_94696_, buttonid);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int buttonID, double dragX, double dragY) {
        return screen.mouseDragged(mouseX, mouseY, MouseClick.from(buttonID), dragX, dragY) && super.mouseDragged(mouseX, mouseY, buttonID, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        return screen.mouseReleased(p_94722_, p_94723_, MouseClick.from(p_94724_)) && super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }

    @Override
    protected void rebuildWidgets() {
        screen.clearWidgets();
        super.rebuildWidgets();
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        UIComponent.mouseX = mouseX;
        UIComponent.mouseY = mouseY;
        return screen.isMouseOver(mouseX, mouseY) && super.isMouseOver(mouseX, mouseY);
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        super.mouseMoved(p_94758_, p_94759_);
        screen.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    @Deprecated
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        return screen.keyReleased(new KeyboardKey(p_94715_,p_94716_, p_94717_)) && super.keyReleased(p_94715_, p_94716_, p_94717_);
    }

    @Override
    @Deprecated
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        return screen.keyPressed(new KeyboardKey(p_96552_, p_96553_, p_96554_)) && super.keyPressed(p_96552_, p_96553_, p_96554_);
    }

    @Override
    public boolean charTyped(char keyChar, int modifiers) {
        return screen.charTyped(keyChar, modifiers) && super.charTyped(keyChar, modifiers);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float tick) {
        isMouseOver(mouseX,mouseY);
        screen.drawWidget(graphics, screen.x, screen.y, screen.width, screen.height);
    }
}
