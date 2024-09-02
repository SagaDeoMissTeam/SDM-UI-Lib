package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sixik.sdmuilibrary.client.render.api.ISDMAdditionRender;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;


/**
 * Represents an RGB color with additional methods for rendering and color manipulation.
 * Implements the {@link ISDMRender} and {@link ISDMAdditionRender} interfaces for rendering support.
 */
public class RGB implements ISDMRender, ISDMAdditionRender {

    public int r;
    public int g;
    public int b;

    /**
     * Constructor for RGB class.
     *
     * @param r The red component of the color (0-255).
     * @param g The green component of the color (0-255).
     * @param b The blue component of the color (0-255).
     */
    protected RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Creates a new RGBA color by adding an alpha component to the current RGB color.
     *
     * @param alpha The alpha component of the color (0-255).
     * @return A new RGBA color instance.
     */
    public RGBA withAlpha(int alpha) {
        return new RGBA(r, g, b, alpha);
    }

    /**
     * Creates a new RGB color from a hexadecimal string.
     *
     * @param hex The hexadecimal string representing the color (e.g., "#FF0000" for red).
     * @return A new RGB color instance.
     */
    public static RGB fromHex(String hex) {
        int color = Integer.parseInt(hex.substring(1), 16);
        return new RGB((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
    }

    /**
     * Creates a new RGB color from an ARGB integer.
     *
     * @param argb The ARGB integer representing the color (e.g., 0xFFFF0000 for red).
     * @return A new RGB color instance.
     */
    public static RGB fromARGB(int argb) {
        return new RGB((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF);
    }

    /**
     * Creates a new RGB color with the given components.
     *
     * @param r The red component of the color (0-255).
     * @param g The green component of the color (0-255).
     * @param b The blue component of the color (0-255).
     * @return A new RGB color instance.
     */
    public static RGB create(int r, int g, int b) {
        return new RGB(r, g, b);
    }

    /**
     * Converts the current RGB color to an ARGB color with an alpha component of 255.
     *
     * @return A new RGBA color instance.
     */
    public RGBA toARGB(){
        return new RGBA(r, g, b, 255);
    }

    /**
     * Converts the current RGB color to an integer representation.
     *
     * @return The integer representation of the color (e.g., 0xFF0000 for red).
     */
    public int toInt(){
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Converts the current RGB color to a float representation.
     *
     * @return The float representation of the color (e.g., 1.0 for white).
     */
    public float toFloat(){
        return (r / 255.0f) + (g / 255.0f) + (b / 255.0f);
    }

    /**
     * Renders a filled rectangle with the current RGB color.
     *
     * @param graphics The GUI graphics context.
     * @param x The x-coordinate of the top-left corner of the rectangle.
     * @param y The y-coordinate of the top-left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param tick The game tick (unused).
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if (width > 0 && height > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            RenderHelper.addFillToBuffer(graphics, buffer, x, y, width, height, this);
            tesselator.end();
        }
    }

    /**
     * Renders a line with the current RGB color.
     *
     * @param graphics The GUI graphics context.
     * @param x The x-coordinate of the start point of the line.
     * @param y The y-coordinate of the start point of the line.
     * @param x2 The x-coordinate of the end point of the line.
     * @param y2 The y-coordinate of the end point of the line.
     * @param lineWidth The width of the line.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.drawLine(graphics, x, y, x2, y2, lineWidth, this);
    }

    /**
     * Renders a filled arc with the current RGB color.
     *
     * @param graphics The GUI graphics context.
     * @param x The x-coordinate of the center of the arc.
     * @param y The y-coordinate of the center of the arc.
     * @param radius The radius of the arc.
     * @param start The start angle of the arc in degrees.
     * @param end The end angle of the arc in degrees.
     */
    @Override
    public void drawArc(GuiGraphics graphics, int x, int y, int radius, int start, int end) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.drawFillArc(graphics, x, y, radius,start,end, this);
    }

    /**
     * Renders a filled circle with the current RGB color.
     *
     * @param graphics The GUI graphics context.
     * @param x The x-coordinate of the center of the circle.
     * @param y The y-coordinate of the center of the circle.
     * @param radius The radius of the circle.
     */
    @Override
    public void drawCircle(GuiGraphics graphics, int x, int y, int radius) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.drawFillCircle(graphics, x, y, radius, this);
    }
}
