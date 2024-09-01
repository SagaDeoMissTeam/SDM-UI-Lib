package net.sixik.sdmuilibrary.client.widgets.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;
import net.sixik.sdmuilibrary.client.utils.misc.CursorType;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;
import org.jetbrains.annotations.Nullable;

public abstract class BasicButtonWidget extends SDMWidget {

    public Component title = Component.empty();

    public BasicButtonWidget() {
        super(null,null);
    }

    public BasicButtonWidget(Vector2 size) {
        super(null,size);
    }

    public BasicButtonWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public abstract void onClick(double mouseX, double mouseY);

    public void setTitle(Component title) {
        this.title = title;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        drawBackground(graphics, x, y, width, height, mouseX, mouseY, tick);
        drawTitle(graphics, x, y, width, height, mouseX, mouseY, tick);
    }

    public void drawBackground(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        Colors.POLAR_NIGHT_0.draw(graphics, x, y, width, height, tick);
        RenderHelper.drawHollowRect(graphics, x, y, width, height, Colors.POLAR_NIGHT_1, false);
    }

    public void drawTitle(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick){
        RenderHelper.drawText(graphics, title, x,y);
    }


    @Override
    public @Nullable CursorType getCursor() {
        return CursorType.HAND;
    }
}
