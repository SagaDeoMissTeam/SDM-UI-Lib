package net.sixik.v2.widgets.screen;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.enums.CenterOperators;
import net.sixik.v2.utils.math.Vector2;
import net.sixik.v2.widgets.UIComponent;
import net.sixik.v2.widgets.alight.AlightContainerUIComponent;
import net.sixik.v2.widgets.panel.UIPanel;

public class UIScreen extends UIPanel {

    public Window window = Minecraft.getInstance().getWindow();
    public CenterOperators.Type centerOperator = CenterOperators.Type.CENTER_Y;
    public boolean isPauseGame = false;

    public UIScreen() {
        renderContext = new RenderContext();
    }

    public boolean onInit() {
        setSize(128,176);
        return true;
    }

    @Override
    public void addWidget(UIComponent component) {
        Vector2 d = getStartPos();
        if(gui != null) component.setGui(gui);
        component.setRenderContext(renderContext);
        if(component instanceof UIPanel panel) {
            panel.addWidgets();
        }
        components.addLast(component.setParent(this).setWidgetIndex(components.size()).setOffset(d.x, d.y));
    }

    public final void initGui() {

        if(onInit()) {
            addWidgets();
            refreshWidget();
            onPostInit();
        }
    }

    public void onPostInit() {}

    public void openGui() {
        Minecraft.getInstance().setScreen(new UIScreenWrapper(this));
    }

    public UIScreen setCenterOperator(CenterOperators.Type centerOperator) {
        this.centerOperator = centerOperator;
        return this;
    }

    public void closeGui() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.closeContainer();
            if (mc.screen == null) {
                mc.setWindowActive(true);
            }
        }

        clearWidgets();
    }

    @Override
    public void addWidgets() {

    }

    @Override
    public void alightWidgets() {
        if(renderType.isOnlyRender() || renderType.isAllRender()) {
            for (int i = components.size() - 1; i >= 0; i--) {
                UIComponent component = components.get(i);
                if(component instanceof UIPanel panel) panel.alightWidgets();
                if(component instanceof AlightContainerUIComponent d) d.alightWidgets();
            }
        }
    }

    @Override
    public void refreshWidget() {
        renderContext.setFocusedComponent(null);
        alightWidgets();
        refreshWidgets();
    }

    @Override
    public void drawWidget(GuiGraphics graphics, int x, int y, int w, int h) {
        switch (centerOperator){
            case NONE -> super.drawWidget(graphics, this.y, this.x, width, height);
            case CENTER_X -> super.drawWidget(graphics, window.getGuiScaledWidth() / 2 - width / 2 + this.x, this.y, width, height);
            case CENTER_Y -> super.drawWidget(graphics, this.x, window.getGuiScaledHeight() / 2 - height / 2 + this.y, width, height);
            case CENTER_XY -> super.drawWidget(graphics, window.getGuiScaledWidth() / 2 - width / 2 + this.x, window.getGuiScaledHeight() / 2 - height / 2 + this.y, width, height);
        }
    }

    public final Vector2 getStartPos() {
        switch (centerOperator) {
            case NONE -> {
                return new Vector2(this.y, this.x);
            }
            case CENTER_X -> {
                return new Vector2(window.getGuiScaledWidth() / 2 - width / 2 + this.x, this.y);
            }
            case CENTER_Y -> {
                return new Vector2(this.x, window.getGuiScaledHeight() / 2 - height / 2 + this.y);
            }
            case CENTER_XY -> {
                return new Vector2(window.getGuiScaledWidth() / 2 - width / 2 + this.x, window.getGuiScaledHeight() / 2 - height / 2 + this.y);
            }

        }
        return new Vector2(0,0);
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(0,0,0, 255/3).drawRoundFill(graphics, x, y, w, h, 10);
    }

    public static class RenderContext {

        public UIComponent focusedComponent = null;

        public RenderContext() {

        }

        public boolean isFocusedComponent(UIComponent component){
            return focusedComponent == null || focusedComponent.equals(component);
        }

        public RenderContext setFocusedComponent(UIComponent component){
            focusedComponent = component;
            return this;
        }
    }

    public static void refreshScreen() {
        if(Minecraft.getInstance().screen instanceof UIScreenWrapper screenWrapper){
            screenWrapper.getScreen().refreshWidget();
        }
    }

    public static void refreshIfOpen(UIScreen screen) {
        if(Minecraft.getInstance().screen instanceof UIScreenWrapper screenWrapper){

            if(screenWrapper.getScreen().equals(screen))
                screenWrapper.getScreen().refreshWidget();
        }
    }
}
