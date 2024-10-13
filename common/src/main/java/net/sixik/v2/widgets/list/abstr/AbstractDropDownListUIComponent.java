package net.sixik.v2.widgets.list.abstr;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.sixik.sdmuilib.SDMUILib;
import net.sixik.v2.color.Colors;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.render.TextRenderHelper;
import net.sixik.v2.widgets.UIComponent;

import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractDropDownListUIComponent<T> extends UIComponent {

    public T selectedContent = null;
    public LinkedList<T> components = new LinkedList<>();

    public int scrollValue;
    public int displayedValue;
    public int maxScrollValue;
    public int maxDisplayedValue;

    public boolean isMouseOverList = false;
    public boolean isOpenedList = false;

    public String ghostText = "";

    public AbstractDropDownListUIComponent() {
        this(1);
    }

    public AbstractDropDownListUIComponent(int maxDisplayedValue) {
        this.maxDisplayedValue = maxDisplayedValue;
        this.scrollValue = 0;
        this.maxScrollValue = 0;
        this.displayedValue = 0;
    }

    public AbstractDropDownListUIComponent<T> setSelectedValue(T value) {
        this.selectedContent = value;
        return this;
    }

    public AbstractDropDownListUIComponent<T> setGhostText(String ghostText) {
        this.ghostText = ghostText;
        return this;
    }

    public AbstractDropDownListUIComponent<T> setGhostText(Object ghostText) {
        this.ghostText = TextRenderHelper.getText(ghostText);
        return this;
    }

    public AbstractDropDownListUIComponent<T> addContent(T content) {
        this.components.add(content);
        return resize();
    }

    public AbstractDropDownListUIComponent<T> addContent(Collection<T> content) {
        this.components.addAll(content);
        return resize();
    }

    public AbstractDropDownListUIComponent<T> resize() {
        this.displayedValue = Math.min(maxDisplayedValue,  components.size());
        this.maxScrollValue = components.size() - displayedValue;
        return this;
    }

    public AbstractDropDownListUIComponent<T> scroll(int scroll) {
        this.scrollValue = Math.min(maxScrollValue, Math.max(0, scroll));
        return this;
    }

    public int getListHeight() {
        return isOpenedList ? this.height + maxDisplayedValue * getContentHeight() : this.height;
    }

    public int getContentHeight() {
        return TextRenderHelper.getTextHeight() + 1;
    }

    public abstract void onClickContent(T content);

    @Override
    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        onClick(mouseX, mouseY, mouseClick);
        return true;
    }

    @Override
    public void onClick(double mouseX, double mouseY, MouseClick mouseClick) {

        if(isOpenedList && isMouseOverList) {
            int clickedLine = ((int) mouseY - getY() - getContentHeight()) / getContentHeight() + scrollValue;
            clickedLine -= 1;
            if(clickedLine < 0 || clickedLine >= components.size()) {
                SDMUILib.LOGGER.error("Selected line is out of bound: " + clickedLine + "/" + components.size());
                return;
            }
            selectedContent = components.get(clickedLine);
            onClickContent(components.get(clickedLine));
        }
        isOpenedList = !isOpenedList;
        if(isOpenedList) {
            renderContext.setFocusedComponent(this);
        } else renderContext.setFocusedComponent(null);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if(isOpenedList) {
            isMouseOver = checkMouseOver(mouseX, mouseY, getX(), getY() + height, this.width, getListHeight());

            if(isMouseOver) {
                isMouseOverList = true;
                return true;
            }
        }
        isMouseOverList = false;
        return super.isMouseOver(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scroll) {
        if(isOpenedList) {
            scroll(scrollValue - Mth.sign(scroll));
            return true;
        }
        return false;
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        graphics.pose().pushPose();
        graphics.pose().translate(0,0,900f);
        drawList(graphics,x,y,w,h);
        graphics.pose().popPose();
    }

    public abstract void drawList(GuiGraphics graphics, int x, int y, int w, int h);

    public void drawScrollBar(GuiGraphics graphics, int x, int y, int w, int h) {
        int sizePerScroll = Math.max(h / maxScrollValue, h / (maxScrollValue + 1));
        int scrollBarY = y + (int) ((float)scrollValue / maxScrollValue * (h - sizePerScroll));

        Colors.UI_GOLD_0.draw(graphics, x + w - getWeightScrollBar(), scrollBarY, getWeightScrollBar(), sizePerScroll);
    }

    public int getWeightScrollBar() {
        return 2;
    }
}
