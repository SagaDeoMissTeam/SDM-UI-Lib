package net.sixik.sdmuilibrary.client.widgets.buttons.checkBox;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.buttons.BasicButtonWidget;

public abstract class BasicCheckBoxWidget extends BasicButtonWidget {

    public boolean value;

    public BasicCheckBoxWidget(boolean value) {
        this(value, null,null);
    }

    public BasicCheckBoxWidget(boolean value, Vector2 size) {
        this(value, null,size);
    }

    public BasicCheckBoxWidget(boolean value, Vector2 position, Vector2 size) {
        super(position, size);
        this.value = value;
    }

    public abstract void onValueChanged(boolean value, double mouseX, double mouseY);

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.value = !value;
        onValueChanged(this.value, mouseX, mouseY);
    }

    @Override
    public void drawTitle(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        super.drawTitle(graphics, x, y, width, height, mouseX, mouseY, tick);
    }
}
