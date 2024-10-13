package net.sixik.v2.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.enums.CursorType;
import net.sixik.v2.enums.KeyboardKey;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.enums.WidgetRender;
import net.sixik.v2.interfaces.IUIRender;
import net.sixik.v2.widgets.panel.UIPanel;
import net.sixik.v2.widgets.screen.UIScreen;
import org.jetbrains.annotations.Nullable;

public abstract class UIComponent implements IUIRender {

    public boolean isDragged = false;
    public boolean isFocused = false;
    public int x = 0;
    public int y = 0;
    public int offsetX;
    public int offsetY;
    public int width = 10;
    public int height = 10;
    public static double mouseX = Minecraft.getInstance().mouseHandler.xpos();
    public static double mouseY = Minecraft.getInstance().mouseHandler.ypos();
    public WidgetRender renderType = WidgetRender.ALL_RENDER;
    public boolean isMouseOver = false;
    protected int widgetIndex = -1;

    public float widgetScale = 1f;
    public UIScreen.RenderContext renderContext = new UIScreen.RenderContext();

    @Nullable
    public UIPanel parentPanel = null;
    @Nullable
    public UIScreen gui = null;

    public UIComponent() {
        offsetX = 0;
        offsetY = 0;
    }

    public UIComponent setParent(UIPanel parent){
        this.parentPanel = parent;
        return this;
    }

    public UIComponent setGui(UIScreen gui){
        this.gui = gui;
        return this;
    }

    public UIComponent setOffset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
        return this;
    }

    public UIComponent setWidgetScale(float widgetScale) {
        this.widgetScale = widgetScale;
        return this;
    }

    public UIComponent setSizeAndPos(int x, int y, int width, int height) {
        setSize(width,height);
        setPos(x,y);
        return this;
    }

    public UIComponent setSize(int w, int h){
        setWidth(w);
        setHeight(h);
        return this;
    }

    public UIComponent setPos(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public UIComponent setHeight(int height) {
        this.height = height;
        return this;
    }

    public UIComponent setWidth(int width) {
        this.width = width;
        return this;
    }

    public UIComponent setRenderType(WidgetRender renderType) {
        this.renderType = renderType;
        return this;
    }

    public UIComponent setRenderContext(UIScreen.RenderContext renderContext) {
        this.renderContext = renderContext;
        return this;
    }

    public final UIComponent setWidgetIndex(int widgetIndex) {
        this.widgetIndex = widgetIndex;
        return this;
    }

    public int getX() {
        return x + offsetX;
    }

    public int getY() {
        return y + offsetY;
    }

    public int getWidgetIndex() {
        return widgetIndex;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        isMouseOver = mouseX >= getX() && mouseX < getX() + width && mouseY >= getY() && mouseY < getY() + height;
        return isMouseOver;
    }

    public boolean checkMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public boolean mouseScrolled(double x, double y, double scroll) {
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, MouseClick mouseClick, double dragX, double dragY) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, MouseClick mouseClick) {
        return false;
    }

    public void onMouseAnotherClick(double mouseX, double mouseY, MouseClick mouseClick) {}

    public void mouseMoved(double mouseX, double mouseY) {}

    public boolean mousePressed(double mouseX, double mouseY, MouseClick mouseClick) {
        return true;
    }

    public void onClick(double mouseX, double mouseY, MouseClick mouseClick) {

    }

    public boolean keyReleased(KeyboardKey key) {
        return false;
    }

    public boolean keyPressed(KeyboardKey key) {
        return false;
    }

    public boolean charTyped(char keyChar, int modifiers){
        return true;
    }

    public void refreshWidget() {

    }

    public void tickWidget() {
    }


    @Override
    public void drawWidget(GuiGraphics graphics, int x, int y, int w, int h) {
        tickWidget();
        drawBackground(graphics, x, y, w, h);
    }

    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h){}

    @Nullable
    public UIComponent copyWidget() {
        return null;
    }

    @Nullable
    public CursorType getCursorType() {
        return null;
    }
}
