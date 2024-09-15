package net.sixik.sdmuilibrary.client.widgets.list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.sixik.sdmuilibrary.client.utils.GLHelper;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.TextHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2d;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;
import net.sixik.sdmuilibrary.client.utils.misc.CursorType;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;
import net.sixik.sdmuilibrary.client.widgets.buttons.BasicButtonWidget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseDropDownListWidget<T> extends BasicButtonWidget {

    private Function<T, Component> toNameFunc = t -> Component.literal(t.toString());
    private Consumer<T> responder = t -> {};
    private final List<T> possibleValues;
    private T value;
    private final int maxDisplayed;
    private final int maxScroll;
    private int scroll;
    private boolean opened;
    public Vector2 size;
    private int sizeOfTextField = RenderHelper.getTextHeight() + 1;

    public BaseDropDownListWidget(
            Vector2 pos,
            int weight,
            int maxDisplayed,
            Collection<T> possibleValues,
            T value
    ) {
        super(pos,new Vector2(weight, RenderHelper.getTextHeight() + 1));
        this.size = new Vector2(weight, sizeOfTextField);
        this.possibleValues = new ArrayList<>(possibleValues);
        this.value = value;
        maxDisplayed = Math.min(maxDisplayed, possibleValues.size());
        this.maxDisplayed = maxDisplayed;
        this.maxScroll = possibleValues.size() - maxDisplayed;
        this.opened = false;
    }

    public BaseDropDownListWidget<T> setSizeOfTextField(int sizeOfTextField) {
        this.sizeOfTextField = sizeOfTextField;
        return this;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        if(!opened) return false;
        setScroll(scroll - Mth.sign(scrollDelta));

        return false;
    }

    @Override
    public void drawMouseOver(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if(isMouseOver((double) x,y)){
            CursorType.set(getCursor());
        } else CursorType.set(null);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {

        Colors.POLAR_NIGHT_0.draw(graphics, x, y, size.x, sizeOfTextField, tick);
        String line = toNameFunc.apply(value).getString();
        TextHelper.drawTextOverWight(graphics, line, new Vector2(x + 1, y + 1), width - 1);
        drawList(graphics, x, y + sizeOfTextField, size.x, size.y, mouseX, mouseY, tick);

    }

    public void drawList(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick){
        if(opened){
            GLHelper.pushScissor(graphics, new Vector2(x, y), new Vector2(width, sizeOfTextField * maxDisplayed));
            for (int i = 0; i < maxDisplayed; i++) {
                drawContent(graphics, x, y, width, sizeOfTextField, mouseX, mouseY, tick, i);
                y += sizeOfTextField;
            }
            GLHelper.popScissor(graphics);

        }
    }

    public void drawContent(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick, int num){
        Colors.POLAR_NIGHT_1.draw(graphics, x, y, width, height, tick);


        if(RenderHelper.isMouseOver(
                Vector2d.create(mouseX,mouseY),
                Vector2.of(x,y),
                Vector2.of(width, height)
        ))
            Colors.POLAR_NIGHT_2.draw(graphics, x, y, width, height, tick);

        y += 1;
        x += 1;
        String line = toNameFunc.apply(possibleValues.get(num + scroll)).getString();
        TextHelper.drawTextOverWight(graphics, line, new Vector2(x,y), width - 1);
    }

    public BaseDropDownListWidget<T> setToNameFunc(Function<T, Component> function) {
        this.toNameFunc = function;
        possibleValues.sort(
                (v1, v2) -> {
                    String name1 = function.apply(v1).getString();
                    String name2 = function.apply(v2).getString();
                    return name1.compareTo(name2);
                });
        return this;
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        boolean clicked = super.clicked(mouseX, mouseY);
        if (!clicked) {
            opened = false;
            setSize();
        }
        return clicked;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        opened = !opened;
        if(RenderHelper.isMouseOver(
                Vector2d.create(mouseX,mouseY),
                Vector2.of(getX(),getY() + sizeOfTextField),
                Vector2.of(width, height)
        )){
            int clickedLine = ((int) mouseY - getY() - sizeOfTextField - 1) / sizeOfTextField + scroll;
            value = possibleValues.get(clickedLine);
            responder.accept(value);
        }
        setSize();
    }

    public void setSize(){
        if(opened) {
            width = size.x;
            height = size.y + maxDisplayed * sizeOfTextField;
        } else {
            width = size.x;
            height = size.y;
        }
    }

    public BaseDropDownListWidget<T> setResponder(Consumer<T> responder) {
        this.responder = responder;
        return this;
    }

    private void setScroll(int scroll) {
        this.scroll = Math.min(maxScroll, Math.max(0, scroll));
    }


}
