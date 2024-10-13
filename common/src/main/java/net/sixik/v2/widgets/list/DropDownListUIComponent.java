package net.sixik.v2.widgets.list;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.render.TextRenderHelper;
import net.sixik.v2.widgets.list.abstr.AbstractDropDownListUIComponent;

public abstract class DropDownListUIComponent<T> extends AbstractDropDownListUIComponent<T> {

    public DropDownListUIComponent() {

    }

    public DropDownListUIComponent(int maxDisplayedValue) {
        super(maxDisplayedValue);
    }

    @Override
    public void drawList(GuiGraphics graphics, int x, int y, int w, int h) {

        if(isMouseOver) RGBA.create(255,255,255,255/3).draw(graphics,x,y,w,h);
        else RGBA.create(0,0,0,255/3).draw(graphics,x,y,w,h);

        if(selectedContent != null) {
            TextRenderHelper.drawTextOverWight(graphics, contentDisplayName(selectedContent), x + 1,y + 1, w);
        } else {
            TextRenderHelper.drawTextOverWight(graphics, Component.literal(ghostText).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC), x + 1,y + 1, w);
        }

        if(isOpenedList) {
            int f = y;
            y += height;
            for (int i = 0; i < displayedValue; i++) {
                if (scrollValue + i >= components.size()) {
                    break;
                }

                drawContent(graphics, x,y, w, getContentHeight(), components.get(scrollValue + i), i);
                y += getContentHeight();
            }

            drawScrollBar(graphics, x, f + height, w, getContentHeight() * maxDisplayedValue);
        }
    }

    public void drawContent(GuiGraphics graphics, int x, int y, int w, int h, T content, int i) {
        if(checkMouseOver(mouseX, mouseY, x,y,w,h))
            RGBA.create(255,255,255,255/3).draw(graphics,x,y,w,h);
        else RGBA.create(0,0,0,255/3).draw(graphics,x,y,w,h);
        TextRenderHelper.drawText(graphics, contentDisplayName(content), x + 1,y + 1);
    }


    public Component contentDisplayName(T content) {
        return Component.literal(content.toString());
    }
}
