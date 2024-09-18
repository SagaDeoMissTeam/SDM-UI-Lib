package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;

/**
 * A class representing a gradient color that extends the RGB color class.
 * It provides a method to draw a gradient rectangle using the provided start and end colors.
 */
public class GradientRBG extends RGB{

    /**
     * The start color of the gradient.
     */
    public RGB start;

    /**
     * The end color of the gradient.
     */
    public RGB end;

    /**
     * Constructor for the GradientRBG class.
     *
     * @param start The start color of the gradient.
     * @param end The end color of the gradient.
     */
    protected GradientRBG(RGB start, RGB end) {
        super(start.r, start.g, start.b);
        this.start = start;
        this.end = end;
    }

    /**
     * Static factory method to create a new GradientRBG instance.
     *
     * @param start The start color of the gradient.
     * @param end The end color of the gradient.
     * @return A new GradientRBG instance.
     */
    public static GradientRBG create(RGB start, RGB end){
        return new GradientRBG(start, end);
    }

    /**
     * Draws a gradient rectangle using the provided start and end colors.
     *
     * @param graphics The GuiGraphics object used for drawing.
     * @param x The x-coordinate of the top-left corner of the rectangle.
     * @param y The y-coordinate of the top-left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param tick The current game tick.
     */
    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if(width > 0 && height > 0){
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            RenderHelper.addFillToBufferGradient(graphics, buffer, x, y, width, height, start, end);
            tesselator.end();
        }
    }

    @Override
    public void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h) {
        if(w > 0 && h > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();

            RenderHelper.addFillTriangleToBufferGradient(graphics,buffer,x,y,w,h,start, end);
            tesselator.end();



            RenderSystem.disableBlend();
        }
    }
}
