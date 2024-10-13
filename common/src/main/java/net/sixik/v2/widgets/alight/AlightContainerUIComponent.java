package net.sixik.v2.widgets.alight;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.enums.KeyboardKey;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.render.GLRenderHelper;
import net.sixik.v2.utils.math.Vector2;
import net.sixik.v2.widgets.UIComponent;

import java.util.LinkedList;

public class AlightContainerUIComponent extends UIComponent {

    private LinkedList<UIComponent> components = new LinkedList<>();
    private int[][] matrix = new int[][]{{0}};


    public AlightContainerUIComponent setMatrix(int[][] matrix) {
        this.matrix = matrix;
        return this;
    }

    public AlightContainerUIComponent addWidget(UIComponent component) {
        components.add(component.setParent(this.parentPanel).setWidgetIndex(components.size()).setOffset(this.getX(), this.getY()).setRenderContext(this.renderContext));
        return this;
    }

    @Override
    public void drawWidget(GuiGraphics graphics, int x, int y, int w, int h) {
        tickWidget();
        drawBackground(graphics, x, y, w, h);
        for (int i = components.size() - 1; i >= 0; i--) {
            UIComponent component = components.get(i);
            if(component.renderType.isOnlyRender() || component.renderType.isAllRender()) {
                GLRenderHelper.pushTransform(graphics, new Vector2(x,y), new Vector2(1,1), component.widgetScale, 0);
                component.drawWidget(graphics, x + component.x, y + component.y, component.width, component.height);
                GLRenderHelper.popTransform(graphics);
            }
        }
    }


    public int getWeightByLine(int line) {
        if(line >= 0 && line < matrix.length) {
            int counts = matrix[line].length - 1;
            if(counts > 0) {
                return width / counts;
            }
        }
        return 0;
    }

    public int getHeightElements() {
        if(matrix.length > 0) {
            return height / matrix.length;
        }
        return 0;
    }

    public void alightWidgets() {
        int f = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (f >= components.size()) {
                    break;  // Останавливаемся, если компонентов меньше, чем элементов в матрице
                }
                UIComponent component = components.get(f);

                // Вычисляем позицию и размер для каждого элемента на основе индекса строки (i) и столбца (j)
                component.x = j * getWeightByLine(i);
                component.y = i * getHeightElements();
                component.width = getWeightByLine(i);
                component.height = getHeightElements();

                f++;  // Переходим к следующему компоненту
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

}
