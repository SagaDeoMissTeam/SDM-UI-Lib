package net.sixik.sdmuilibrary.client.utils.renders;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;
import net.sixik.sdmuilibrary.client.widgetsFake.text.SingleLineFakeWidget;

public class TooltipHelper {

    public static void renderTooltip(GuiGraphics graphics, int x, int y, Component text) {
        graphics.pose().pushPose();
        GLRenderHelper.setDepth(graphics, 900);

        String txt = text.getString();

        int width = (int) TextRenderHelper.getTextWidth(txt, 1f) + 2;

        Vector2f f = TextRenderHelper.getTextRenderSize(txt, width - 2, 1f, 50);

        RenderHelper.drawHollowRect(graphics,x - 1, y -1, width - 1, RenderHelper.getTextHeight() - 1, Colors.POLAR_NIGHT_1, false);
        Colors.POLAR_NIGHT_0.draw(graphics,x,y,width,RenderHelper.getTextHeight());
        TextRenderHelper.drawText(graphics,x + 1,y + 1,1f, text, 0);

        graphics.pose().popPose();
    }

    public static void renderTooltipWithScale(GuiGraphics graphics, int x, int y, int width, int height, Component text){
        graphics.pose().pushPose();
        GLRenderHelper.setDepth(graphics, 900);

        String txt = text.getString();
        Vector2f f = TextRenderHelper.getTextRenderSize(txt, width - 2, 1f, 50);

        RenderHelper.drawHollowRect(graphics,x - 1, y -1, width - 1, height - 1, Colors.POLAR_NIGHT_1, false);
        Colors.POLAR_NIGHT_0.draw(graphics,x,y,width,height);
        SingleLineFakeWidget widget = new SingleLineFakeWidget(Component.literal(txt));
        widget.textSize = f.y;
        widget.draw(graphics, x + 1, y + 1, width, height, 0);

        graphics.pose().popPose();
    }

    public static void pushTooltip(GuiGraphics graphics, int x, int y, int width, int height, RGBA background){
        graphics.pose().pushPose();
        GLRenderHelper.setDepth(graphics, 900);
        background.draw(graphics,x,y,width,height);
    }

    public static void popTooltip(GuiGraphics graphics){
        graphics.pose().popPose();
    }
}
