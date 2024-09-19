package net.sixik.sdmuilib.client.widgets.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.misc.CursorType;
import net.sixik.sdmuilib.client.utils.misc.RGBA;
import net.sixik.sdmuilib.client.widgets.SDMWidget;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ColorButtonWidget extends SDMWidget {

    public Consumer<RGBA> responder = s -> {};
    @Nullable
    public RGBA selectedColor;

    public int currentColorId = 0;
    public List<RGBA> colors;


    public ColorButtonWidget(List<RGBA> colors, Vector2 position, Vector2 size){
        this(colors.isEmpty() ? null : colors.get(0), colors, position, size);
    }
    public ColorButtonWidget(@Nullable RGBA selectedColor, List<RGBA> colors, Vector2 position, Vector2 size) {
        super(position, size);
        this.selectedColor = selectedColor;
        this.colors = colors;
        drawMouseOver = true;
    }

    public ColorButtonWidget setResponder(Consumer<RGBA> responder) {
        this.responder = responder;
        return this;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        if(selectedColor == null) return;
        selectedColor.draw(graphics, x, y, width, height, tick);
    }


    @Override
    public @Nullable CursorType getCursor() {
        return CursorType.HAND;
    }

    @Override
    public void onClick(double p_93634_, double p_93635_) {
        if(colors.isEmpty()) return;
        selectedColor = colors.get(currentColorId);
        responder.accept(selectedColor);
        currentColorId++;

        if(currentColorId >= colors.size()) {
            currentColorId = 0;
        }
    }
}
