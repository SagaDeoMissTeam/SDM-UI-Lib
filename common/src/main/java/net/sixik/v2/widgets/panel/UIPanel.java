package net.sixik.v2.widgets.panel;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.enums.KeyboardKey;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.enums.WidgetRender;
import net.sixik.v2.render.GLRenderHelper;
import net.sixik.v2.utils.math.Vector2;
import net.sixik.v2.widgets.UIComponent;
import net.sixik.v2.widgets.alight.AlightContainerUIComponent;

import java.util.LinkedList;

public abstract class UIPanel extends UIComponent {

    public LinkedList<UIComponent> components = new LinkedList<>();

    public boolean useScissor = true;

    public abstract void addWidgets();

    public void addWidget(UIComponent component) {
        if(gui != null) component.setGui(gui);
        if(component instanceof UIPanel panel) {
            panel.addWidgets();
        }
        components.addLast(component.setParent(this).setWidgetIndex(components.size()).setOffset(this.getX(), this.getY()).setRenderContext(this.renderContext));
    }

    public void addWidget(UIComponent component, WidgetRender renderType){
        addWidget(component.setRenderType(renderType));
    }

    public boolean removeWidget(UIComponent component) {
        return components.remove(component);
    }

    public UIPanel setUseScissor() {
        useScissor = true;
        return this;
    }

    public UIComponent removeLastWidget() {
        return components.removeLast();
    }

    public UIComponent removeFirstWidget() {
        return components.removeFirst();
    }

    public void clearWidgets(){
        this.components.clear();
    }

    public void clearWidgets(WidgetRender renderType){
        components.removeIf(component -> component.renderType == renderType);
    }


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
        alightWidgets();
        refreshWidgets();
    }

    public void refreshWidgets() {
        for (int i = components.size() - 1; i >= 0; i--) {
            components.get(i).refreshWidget();
        }
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scroll) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && component.isMouseOver && renderContext.isFocusedComponent(component) && component.mouseScrolled(x, y, scroll)) return false;
        }

        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, MouseClick mouseClick, double dragX, double dragY) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && component.isMouseOver && renderContext.isFocusedComponent(component) && component.mouseDragged(mouseX, mouseY, mouseClick, dragX, dragY)) return false;
        }

        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, MouseClick mouseClick) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && component.isMouseOver && renderContext.isFocusedComponent(component) && component.mouseReleased(mouseX, mouseY, mouseClick)) return false;
        }

        return true;
    }

    @Override
    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && renderContext.isFocusedComponent(component)) {
                if(component.isMouseOver && component.mousePressed(mouseX, mouseY, mouseClick)) {
                    component.isFocused = true;
                    return false;
                }
                else if(!component.isMouseOver) component.isFocused = false;
            } else component.isFocused = false;
        }

        return true;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && renderContext.isFocusedComponent(component)) {
                component.mouseMoved(mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && renderContext.isFocusedComponent(component) && component.isMouseOver(mouseX,mouseY)) return true;
        }


        isMouseOver = mouseX >= getX() && mouseX < getX() + width && mouseY >= getY() && mouseY < getY() + height;
        return isMouseOver;
    }

    @Override
    public boolean keyPressed(KeyboardKey key) {
        for (int i = components.size() - 1; i >= 0; i--) {
            if(components.get(i).keyPressed(key)) return false;
        }
        return true;
    }

    @Override
    public boolean keyReleased(KeyboardKey key) {
        for (int i = components.size() - 1; i >= 0; i--) {
            if(components.get(i).keyReleased(key)) return false;
        }
        return true;
    }

    @Override
    public boolean charTyped(char keyChar, int modifiers) {
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isAllRender() && component.isFocused && component.charTyped(keyChar, modifiers)) return false;
        }
        return true;
    }

    @Override
    public void drawWidget(GuiGraphics graphics, int x, int y, int w, int h) {
        tickWidget();
        if(useScissor) GLRenderHelper.pushScissor(graphics, new Vector2(x,y), new Vector2(w,h));
        drawBackground(graphics, x, y, w, h);
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isOnlyRender() || component.renderType.isAllRender()) {
                GLRenderHelper.pushTransform(graphics, new Vector2(x,y), new Vector2(1,1), component.widgetScale, 0);
                component.drawWidget(graphics, x + component.x, y + component.y, component.width, component.height);
                GLRenderHelper.popTransform(graphics);
            }
        }
        if(useScissor) GLRenderHelper.popScissor(graphics);
    }

}
