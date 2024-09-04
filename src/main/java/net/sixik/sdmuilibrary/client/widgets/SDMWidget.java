package net.sixik.sdmuilibrary.client.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.render.container.ContainerObject;
import net.sixik.sdmuilibrary.client.render.container.RenderContainerImpl;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.CursorType;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class SDMWidget extends AbstractWidget implements ISDMRender {

    public boolean drawMouseOver = false;

    public RenderContainerImpl container;
    public final HashSet<ContainerObject<?>> properties = new HashSet<>();

    public SDMWidget(Vector2 position, Vector2 size) {
        super(position.x, position.y, size.x, size.y, Component.empty());

    }

    public void addProperty(ContainerObject<?> property){
        properties.add(property);
    }

    @Nullable
    public ContainerObject<?> getProperty(String propertyName){
        for (ContainerObject<?> property : properties) {
            if(property.name.equals(propertyName)) return property;
        }

        return null;
    }


    public boolean isSizeHaseNull(){
        return width == 0 || height == 0;
    }

    public void setContainer(RenderContainerImpl container) {
        this.container = container;
    }

    public @Nullable CursorType getCursor() {
        return null;
    }

    public boolean isMouseOver(Vector2 mousePos){
        return isMouseOver(mousePos.x, mousePos.y);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return active && visible && mouseX >= getX() && mouseX < getX() + width && mouseY >= getY() && mouseY < getY() + height;
    }

    @Override
    public boolean isMouseOver(double p_93672_, double p_93673_) {
        return isMouseOver((int) p_93672_, (int) p_93673_);
    }

    public void drawMouseOver(GuiGraphics graphics, int x, int y, int width, int height, float tick){
        if(isMouseOver(container.mousePosition)){
            CursorType.set(getCursor());
        } else CursorType.set(null);
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {

    }

    public void drawNoActive(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {

    }

    public void tick(){

    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float tick) {
        if(isActive()) {
            if(drawMouseOver)
                drawMouseOver(graphics, mouseX,mouseY, width, height, tick);
            draw(graphics, getX(), getY(), getWidth(), getHeight(), mouseX, mouseY, tick);
        } else if(!active) {
            drawNoActive(graphics, getX(), getY(), getWidth(), getHeight(), mouseX, mouseY, tick);
        }

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        draw(graphics, getX(), getY(), getWidth(), getHeight(), Float.floatToIntBits(RenderHelper.getMousePosition().x), Float.floatToIntBits(RenderHelper.getMousePosition().y), tick);
    }
}
