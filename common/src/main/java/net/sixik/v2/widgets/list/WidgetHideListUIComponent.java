package net.sixik.v2.widgets.list;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.render.GLRenderHelper;
import net.sixik.v2.utils.math.Vector2;
import net.sixik.v2.widgets.UIComponent;
import net.sixik.v2.widgets.panel.UIPanel;


public abstract class WidgetHideListUIComponent extends UIPanel {

    public boolean isListHide = true;

    public WidgetHideListUIComponent setListHide(boolean isListHide) {
        this.isListHide = isListHide;
        return this;
    }

    @Override
    public void addWidget(UIComponent component) {
        if(gui != null) component.setGui(gui);
        if(component instanceof UIPanel panel) {
            panel.addWidgets();
        }
        components.addLast(component.setParent(this).setWidgetIndex(components.size()).setOffset(this.getX(), this.getY()).setRenderContext(this.renderContext));
    }



    @Override
    public UIComponent setHeight(int height) {
        for (UIComponent component : components) {
            component.offsetY += height;
        }
        return super.setHeight(height);
    }

    @Override
    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        if(mouseClick.isLeft()) {

            if(isMouseOver) {
                isListHide = !isListHide;
                if(parentPanel != null)
                    parentPanel.refreshWidget();
                return true;
            }

            if(!isListHide) {
                return super.mousePressed(mouseX, mouseY, mouseClick);
            }
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if(!isListHide) {
            for (int i = components.size() - 1; i >= 0; i--) {
                UIComponent component = components.get(i);
                if (component.renderType.isAllRender() && renderContext.isFocusedComponent(component) && component.isMouseOver(mouseX, mouseY))
                    return true;
            }
        }


        isMouseOver = mouseX >= getX() && mouseX < getX() + width && mouseY >= getY() && mouseY < getY() + height;
        return isMouseOver;
    }

    @Override
    public int getHeight() {

        if(isListHide) {
            return this.height;
        }

        int height = this.height;
        for (UIComponent component : components) {
            height += component.getHeight();
        }

        return height;
    }

    @Override
    public int getWidth() {
        if(isListHide) {
            return this.width;
        }

        int width = this.width;

        for (UIComponent component : components) {
            width = Math.max(width, component.getWidth());
        }
        return width;
    }

    @Override
    public void drawWidget(GuiGraphics graphics, int x, int y, int w, int h) {
        tickWidget();
        drawBackground(graphics, x, y, w, h);
        if(!isListHide) {
            for (int i = components.size() - 1; i >= 0; i--) {
                UIComponent component = components.get(i);
                if (component.renderType.isOnlyRender() || component.renderType.isAllRender()) {
                    GLRenderHelper.pushTransform(graphics, new Vector2(x, y), new Vector2(1, 1), component.widgetScale, 0);
                    component.drawWidget(graphics, x + component.x, y + height + component.y, component.width, component.height);
                    GLRenderHelper.popTransform(graphics);
                }
            }
        }
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(0,0,0,255/3).draw(graphics,x,y,w,h);

        if(!isListHide || isMouseOver) {
            RGBA.create(255,255,255,255).draw(graphics,x + 2,y + 1,1,h - 2);
        } else {
            RGBA.create(128,128,128,255).draw(graphics,x + 2,y + 1,1,h - 2);
        }
    }
}
