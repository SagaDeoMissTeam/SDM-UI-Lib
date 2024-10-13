package net.sixik.v2.render;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.widgetsFake.text.SingleLineFakeWidget;
import net.sixik.v2.render.GLRenderHelper;
import net.sixik.v2.render.RenderHelper;
import net.sixik.v2.render.TextureRenderHelper;
import net.sixik.v2.utils.math.Vector2f;
import net.sixik.v2.color.Colors;
import net.sixik.v2.color.RGBA;
//import net.sixik.v2.widgetsFake.text.SingleLineFakeWidget;

public class TooltipRenderHelper {

    public static void renderTooltip(GuiGraphics graphics, int x, int y, Component text) {
        graphics.pose().pushPose();
        GLRenderHelper.pushUpper(graphics, 900f);

        String txt = text.getString();

        int width = (int) TextRenderHelper.getTextWidth(txt, 1f) + 2;

        Vector2f f = TextRenderHelper.getTextRenderSize(txt, width - 2, 1f, 50);

        RenderHelper.drawHollowRect(graphics,x - 1, y -1, width - 1, RenderHelper.getTextHeight() - 1, Colors.POLAR_NIGHT_1, false);
        Colors.POLAR_NIGHT_0.draw(graphics,x,y,width,RenderHelper.getTextHeight());
        TextRenderHelper.drawText(graphics,x + 1,y + 1,1f, text, 0);

        GLRenderHelper.popUpper(graphics);
        graphics.pose().popPose();
    }

    public static void renderTooltipWithScale(GuiGraphics graphics, int x, int y, int width, int height, Component text){
        graphics.pose().pushPose();
        GLRenderHelper.pushUpper(graphics, 900f);

        String txt = text.getString();
        Vector2f f = TextRenderHelper.getTextRenderSize(txt, width - 2, 1f, 50);

        RenderHelper.drawHollowRect(graphics,x - 1, y -1, width - 1, height - 1, Colors.POLAR_NIGHT_1, false);
        Colors.POLAR_NIGHT_0.draw(graphics,x,y,width,height);
        SingleLineFakeWidget widget = new SingleLineFakeWidget(Component.literal(txt));
        widget.textSize = f.y;
        widget.draw(graphics, x + 1, y + 1, width, height, 0);

        GLRenderHelper.popUpper(graphics);
        graphics.pose().popPose();
    }

    public static void pushTooltip(GuiGraphics graphics, int x, int y, int width, int height, RGBA background){
        graphics.pose().pushPose();
        GLRenderHelper.pushUpper(graphics, 900f);
        background.draw(graphics,x,y,width,height);
    }

    public static void popTooltip(GuiGraphics graphics){
        GLRenderHelper.popUpper(graphics);
        graphics.pose().popPose();
    }
}
