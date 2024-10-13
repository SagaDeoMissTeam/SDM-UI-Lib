package net.sixik.v2.widgets.slieder;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.color.Colors;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.enums.SliderType;
import net.sixik.v2.widgets.UIComponent;

public class SliderUIComponent extends UIComponent {

    public int maxValue = 1;
    public int minValue = 0;
    public int currentValue = 0;

    public SliderType sliderType = SliderType.HORIZONTAL;

    private double lastDragPos = 0;
    public int postScrollButton = 0;

    public int scrollButtonX = 0;
    public int scrollButtonY = 0;
    public int scrollButtonWidth = 10;
    public int scrollButtonHeight = 10;

    public SliderUIComponent() {

    }

    public void onValueChanged(int value) {

    }

    public void onFilled() {

    }

    public SliderUIComponent setScrollButtonSize(int w, int h) {
        if(w >= this.width)
            scrollButtonWidth = w;
        else scrollButtonWidth = this.width;

        if(h >= this.height)
            scrollButtonHeight = h;
        else scrollButtonHeight = this.height;

        return this;
    }

    @Override
    public UIComponent setPos(int x, int y) {
        super.setPos(x, y);
        scrollButtonX = getX();
        scrollButtonY = getY();
        return this;
    }

    public SliderUIComponent resetValue(){
        this.currentValue = minValue;
        return this;
    }

    public SliderUIComponent setValue(int value) {
        this.currentValue = Math.min(maxValue, Math.max(value, minValue));
        changeScroll(1);
        return this;
    }

    public SliderUIComponent setMinValue(int minValue) {
        this.minValue = minValue;
        setValue(minValue);
        return this;
    }

    public SliderUIComponent setMaxValue(int maxValue) {
        if(maxValue > minValue)
            this.maxValue = maxValue;
        else {
            this.maxValue = minValue + 1;
            throw new RuntimeException("Try to set maxValue who less than minValue");
        }

        return this;
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        drawSliderBackground(graphics, x, y, w, h);
        drawSlider(graphics, scrollButtonX, scrollButtonY, scrollButtonWidth, scrollButtonHeight);
    }

    public void drawSliderBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        Colors.POLAR_NIGHT_1.draw(graphics, x, y, w, h);
    }
    public void drawSlider(GuiGraphics graphics, int x, int y, int w, int h) {
        Colors.UI_GOLD_0.draw(graphics, x, y, w, h);
    }

    @Override
    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        boolean flag = mouseX >= scrollButtonX && mouseX < scrollButtonX + scrollButtonWidth && mouseY >= scrollButtonY && mouseY < scrollButtonY + scrollButtonHeight;
        if(flag) {
            isDragged = true;
            return true;
        }
        isDragged = false;
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, MouseClick mouseClick, double dragX, double dragY) {
        if(isDragged) {
            double deltaDrag = sliderType.isHorizontal() ? dragX : dragY;
            deltaDrag = deltaDrag < 0 ? -1 : 1;

            if(sliderType.isHorizontal() && mouseX >= scrollButtonX && mouseX < scrollButtonX + scrollButtonWidth)
                changeScroll(deltaDrag);
            else if(sliderType.isVertical() && mouseY >= scrollButtonY && mouseY < scrollButtonY + scrollButtonHeight)
                changeScroll(deltaDrag);

        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scroll) {
        changeScroll(scroll < 0 ? scroll : + Math.round(scroll));
        return true;
    }

    public void changeScroll(double drag) {
        // Высота или ширина (в зависимости от типа слайдера)
        int h = sliderType.isHorizontal() ? this.width - scrollButtonWidth : this.height - scrollButtonHeight;

        // Размер одного значения
        int valueSize = h / maxValue;

        // Смещение ползунка
        int j = (int) (drag * valueSize);

        // Обновление положения ползунка
        if (sliderType.isHorizontal()) {
            scrollButtonX = (int) Math.max(getX(), Math.min(getX() + h, scrollButtonX + j));
        } else {
            scrollButtonY = (int) Math.max(getY(), Math.min(getY() + h, scrollButtonY + j));
        }

        // Обновляем значение ползунка только после смещения
        if (drag < 0 && currentValue - 1 >= minValue) {
            currentValue -= 1;
            onValueChanged(currentValue);
        } else if (drag > 0 && currentValue + 1 <= maxValue) {
            currentValue += 1;

            if(currentValue == maxValue)
                onFilled();
            else
                onValueChanged(currentValue);
        }
    }
}
