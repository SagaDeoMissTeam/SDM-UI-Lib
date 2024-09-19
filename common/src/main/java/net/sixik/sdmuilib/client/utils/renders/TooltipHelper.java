package net.sixik.sdmuilib.client.utils.renders;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.GLHelper;
import net.sixik.sdmuilib.client.utils.RenderHelper;
import net.sixik.sdmuilib.client.utils.TextHelper;
import net.sixik.sdmuilib.client.utils.math.Vector2f;
import net.sixik.sdmuilib.client.utils.misc.Colors;
import net.sixik.sdmuilib.client.utils.misc.RGBA;
import net.sixik.sdmuilib.client.widgetsFake.text.SingleLineFakeWidget;

public class TooltipHelper {

    public static void renderTooltip(GuiGraphics graphics, int x, int y, Component text) {
        graphics.pose().pushPose();
        GLHelper.pushUpper(graphics, 900f);

        String txt = text.getString();

        int width = (int) TextHelper.getTextWidth(txt, 1f) + 2;

        Vector2f f = TextHelper.getTextRenderSize(txt, width - 2, 1f, 50);

        RenderHelper.drawHollowRect(graphics,x - 1, y -1, width - 1, RenderHelper.getTextHeight() - 1, Colors.POLAR_NIGHT_1, false);
        Colors.POLAR_NIGHT_0.draw(graphics,x,y,width,RenderHelper.getTextHeight(),0);
        TextHelper.drawText(graphics,x + 1,y + 1,1f, text, 0);

        GLHelper.popUpper(graphics);
        graphics.pose().popPose();
    }

    public static void renderTooltipWithScale(GuiGraphics graphics, int x, int y, int width, int height, Component text){
        graphics.pose().pushPose();
        GLHelper.pushUpper(graphics, 900f);

        String txt = text.getString();
        Vector2f f = TextHelper.getTextRenderSize(txt, width - 2, 1f, 50);

        RenderHelper.drawHollowRect(graphics,x - 1, y -1, width - 1, height - 1, Colors.POLAR_NIGHT_1, false);
        Colors.POLAR_NIGHT_0.draw(graphics,x,y,width,height,0);
        SingleLineFakeWidget widget = new SingleLineFakeWidget(Component.literal(txt));
        widget.textSize = f.y;
        widget.draw(graphics, x + 1, y + 1, width, height, 0);

        GLHelper.popUpper(graphics);
        graphics.pose().popPose();
    }

    public static void pushTooltip(GuiGraphics graphics, int x, int y, int width, int height, RGBA background){
        graphics.pose().pushPose();
        GLHelper.pushUpper(graphics, 900f);
        background.draw(graphics,x,y,width,height, 1);
    }

    public static void popTooltip(GuiGraphics graphics){
        GLHelper.popUpper(graphics);
        graphics.pose().popPose();
    }
}
