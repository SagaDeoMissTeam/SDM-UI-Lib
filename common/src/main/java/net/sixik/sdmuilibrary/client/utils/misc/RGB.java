package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.sixik.sdmuilibrary.client.render.api.ILineRender;
import net.sixik.sdmuilibrary.client.render.api.IShapesRender;
import net.sixik.sdmuilibrary.client.utils.DrawDirection;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.utils.renders.ShapesRenderHelper;

public class RGB implements IShapesRender, ILineRender {

    public static final RGB DEFAULT = new RGB(255,255,255);


    public int r;
    public int g;
    public int b;

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGB copy() {
        return new RGB(r, g, b);
    }

    public RGBA withAlpha(int alpha) {
        return new RGBA(r, g, b, alpha);
    }

    public static RGB fromHex(String hex) {
        hex = hex.replace("#", "");

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new RGB(r, g, b);
    }

    public static RGB fromARGB(int argb) {
        return new RGB((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF);
    }

    public static RGB create(int r, int g, int b) {
        return new RGB(r, g, b);
    }

    public RGBA toARGB(){
        return new RGBA(r, g, b, 255);
    }

    public int toInt(){
        return (r << 16) | (g << 8) | b;
    }

    public float toFloat(){
        return (r / 255.0f) + (g / 255.0f) + (b / 255.0f);
    }

    public RGB interpolate(RGB colorA, RGB colorC, float t){
        int r = (int) (colorA.r * (1 - t) + colorC.r * t);
        int g = (int) (colorA.g * (1 - t) + colorC.g * t);
        int b = (int) (colorA.b * (1 - t) + colorC.b * t);
        return new RGB(r, g, b);
    }

    public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            ShapesRenderHelper.drawQuad(graphics.pose().last().pose(), new Vector2(x, y + height), new Vector2(width, height), this);

            RenderSystem.disableBlend();
        }
    }

    @Override
    public void drawStraight(GuiGraphics graphics, int x, int y, LineVectors vectors, float lineLong, float lineWidth) {
        //throw new UnsupportedOperationException("Method does not support drawing line");
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();

        ShapesRenderHelper.drawStraight(graphics.pose().last().pose(), new Vector2(x,y), vectors,lineLong, lineWidth,this);

        RenderSystem.disableBlend();
    }

    @Override
    public void drawMagneticLine(GuiGraphics graphics, int x, int y, LineVectors magnet1, int x2, int y2, LineVectors magnet2, float lineWidth) {
        //throw new UnsupportedOperationException("Method does not support drawing line");
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();

        ShapesRenderHelper.drawMagneticLine(graphics.pose().last().pose(), new Vector2(x,y),magnet1, new Vector2(x2, y2), magnet2, lineWidth,this);

        RenderSystem.disableBlend();
    }

    @Override
    public void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        ShapesRenderHelper.drawCircle(graphics.pose().last().pose(),new Vector2f(x,y),radius,segments, this);

        RenderSystem.disableBlend();
    }

    @Override
    public void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h) {
        if (w > 0 && h > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            ShapesRenderHelper.drawTriangle(graphics.pose().last().pose(),new Vector2(x,y),new Vector2(w,h), DrawDirection.UP,this);

            RenderSystem.disableBlend();
        }
    }

    @Override
    public void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        ShapesRenderHelper.drawRoundedRect(guiGraphics,x,y,width,height, radius,this);
        RenderSystem.disableBlend();
    }
}
