package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.sixik.sdmuilibrary.client.render.api.ISDMAdditionRender;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.ShapesRender;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.widgetsFake.RGBFakeWidget;
import org.joml.Matrix4f;


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
        // Убираем символ #, если он присутствует
        hex = hex.replace("#", "");

        // Парсим цвет и возвращаем массив с RGB значениями
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new RGB(r, g, b);
    }

    public RGBFakeWidget toFakeWidget(){
        return new RGBFakeWidget(this);
    }

    public RGBFakeWidget toFakeWidget(Vector2 pos, Vector2 size){
        return (RGBFakeWidget) new RGBFakeWidget(this).setPosition(pos).setSize(size);
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
     * Interpolates between two RGB colors using linear interpolation.
     *
     * @param colorA The first RGB color to interpolate from.
     * @param colorC The second RGB color to interpolate to.
     * @param t The interpolation factor, ranging from 0.0 to 1.0. A value of 0.0 returns {@code colorA},
     *          a value of 1.0 returns {@code colorC}, and values in between perform linear interpolation.
     * @return A new RGB color representing the interpolated result.
     */
    public RGB interpolate(RGB colorA, RGB colorC, float t){
        int r = (int) (colorA.r * (1 - t) + colorC.r * t);
        int g = (int) (colorA.g * (1 - t) + colorC.g * t);
        int b = (int) (colorA.b * (1 - t) + colorC.b * t);
        return new RGB(r, g, b);
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
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if (width > 0 && height > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            RenderHelper.addFillToBuffer(graphics, buffer, x, y, width, height, this);

            BufferUploader.drawWithShader(buffer.buildOrThrow());
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
    public void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth) {
//        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderHelper.drawLine(graphics, x, y, x2, y2, lineWidth, this);
//        RenderSystem.disableBlend();
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
    public void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Matrix4f matrix = graphics.pose().last().pose();
        ShapesRender.drawCircle(matrix,new Vector2f(x,y),radius,segments, this);
        RenderSystem.disableBlend();
    }

    @Override
    public void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h) {
         if (w > 0 && h > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer =  tesselator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            RenderHelper.addFillTriangleToBuffer(graphics,buffer,x,y,w,h,this);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.disableBlend();
        }
    }

    @Override
    public void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderHelper.drawRoundedRect(guiGraphics,x,y,width,height, radius,this);
        RenderSystem.disableBlend();
    }
}
