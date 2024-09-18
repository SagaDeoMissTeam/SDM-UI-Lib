package net.sixik.sdmuilibrary.client.widgets.buttons;

import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class BaseTooltipButton extends BasicButtonWidget{

    public List<Component> tooltips = new ArrayList<>();

    public BaseTooltipButton(Vector2 size) {
        super(null,size);
    }

    public BaseTooltipButton(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        drawBackground(graphics, x, y, width, height, mouseX, mouseY, tick);
    }

    public abstract void drawBackground(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick);

    public void drawTooltip(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick){
        RenderHelper.pushUpper(graphics, 900f);
        Colors.POLAR_NIGHT_2.draw(graphics, x, y, width, height, mouseX);

        RenderHelper.popUpper(graphics);
    }

    @Override
    public abstract void onClick(double mouseX, double mouseY);
}
