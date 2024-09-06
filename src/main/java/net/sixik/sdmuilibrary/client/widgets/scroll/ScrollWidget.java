package net.sixik.sdmuilibrary.client.widgets.scroll;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.RenderWidget;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;

public class ScrollWidget extends RenderWidget {
    private int maxScroll = 0;
    private int scroll;
    public boolean scrolling = false;

    public ScrollWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        if (visible) {
            int totalHeight = 0;
            int lastPosY = 0;

            // Рассчитываем общую высоту содержимого
            for (SDMWidget widget : renderables) {
                if (lastPosY < widget.getY() || totalHeight == 0) {
                    totalHeight += widget.getHeight();  // Используем высоту виджета для корректного расчета
                    lastPosY = widget.getY();
                }

                // Рисуем каждый виджет, смещая их на текущий сдвиг прокрутки
                renderWidget(widget, graphics, x, y - scroll, width, height, mouseX, mouseY, tick);
                widget.setPosition(widget.getX(), widget.getY() - scroll);
            }

            // Обновляем maxScroll, если высота контента больше, чем видимая область
            if (totalHeight > getHeight()) {
                maxScroll = totalHeight - getHeight();
            }

            // Отрисовка ползунка скроллбара, если контент больше видимой области
            if (maxScroll > 0) {
                drawScrollBar(graphics, x + getWidth() - 5, y, height, totalHeight);
            }
        }
    }

    private void drawScrollBar(GuiGraphics graphics, int x, int y, int height, int contentHeight) {
        // Высота ползунка пропорциональна видимой области
        int scrollbarHeight = Math.max(20, (int) ((float) getHeight() / contentHeight * getHeight()));
        int scrollbarY = y + (int) ((float) scroll / maxScroll * (getHeight() - scrollbarHeight));

        // Фон скроллбара
        graphics.fill(x, y, x + 5, y + getHeight(), 0xFFAAAAAA);

        // Ползунок
        graphics.fill(x, scrollbarY, x + 5, scrollbarY + scrollbarHeight, 0xFF000000);
    }



    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        // Прокручиваем содержимое на 1 пиксель за каждый шаг скроллинга

        scrolling = true;

        if(scrolling) {
            scroll -= scrollDelta;
        }
        System.out.println(scroll);
        return super.mouseScrolled(mouseX, mouseY, scrollDelta);
    }

    @Override
    public boolean mouseReleased(double p_93684_, double p_93685_, int p_93686_) {
        scrolling = false;
        return super.mouseReleased(p_93684_, p_93685_, p_93686_);
    }

    private void setScroll(int scroll) {
        // Ограничиваем прокрутку от 0 до maxScroll, чтобы она не выходила за границы
        this.scroll = Math.min(maxScroll, Math.max(0, scroll));
        System.out.println(scroll);
    }
}
