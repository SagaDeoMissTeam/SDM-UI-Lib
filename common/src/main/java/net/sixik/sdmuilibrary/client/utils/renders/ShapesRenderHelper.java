package net.sixik.sdmuilibrary.client.utils.renders;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.BufferUploader;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.DrawDirection;
import net.sixik.sdmuilibrary.client.utils.buffers.RenderBuffer2D;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;
import org.joml.Matrix4f;

/**
 * Метод для отрисовки фигур
 */
public class ShapesRenderHelper {


    public static void drawQuad(Matrix4f m, Vector2 pos, Vector2 size, RGB rgb) {
        drawQuad(m, pos.toVector2f(), size.toVector2f(), rgb);
    }

    public static void drawQuad(Matrix4f m, Vector2f pos, Vector2f size, RGB rgb) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba)
            a = rgba.a;
        drawQuad(m, pos, size, r, g, b, a);
    }

    public static void drawQuad(Matrix4f m, Vector2f pos, Vector2f size, int r, int g, int b, int a) {
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        RenderBuffer2D.addQuadToBuffer(m, buffer, pos, size, r, g, b, a);

        BufferUploader.drawWithShader(buffer.buildOrThrow());
    }

    private static void drawFillRect(GuiGraphics guiGraphics, int x, int y, int width, int height, RGB rgb) {
        guiGraphics.fill(x, y, x + width, y + height, rgbaToInt(rgb.r, rgb.g, rgb.b, rgb instanceof RGBA ? ((RGBA) rgb).a : 255));
    }

    public static void drawTriangle(Matrix4f m, Vector2 pos, Vector2 size, DrawDirection direction, RGB rgb) {
        drawTriangle(m, pos.toVector2f(), size.toVector2f(), direction, rgb);
    }

    public static void drawTriangle(Matrix4f m, Vector2f pos, Vector2f size, DrawDirection direction, RGB rgb) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba)
            a = rgba.a;
        drawTriangle(m, pos, size, direction, r, g, b, a);

    }

    public static void drawTriangle(Matrix4f m, Vector2 pos, Vector2 size, DrawDirection direction, int r, int g, int b, int a) {
        drawTriangle(m, pos.toVector2f(), size.toVector2f(), direction, r, g, b, a);
    }

    public static void drawTriangle(Matrix4f m, Vector2f pos, Vector2f size, DrawDirection direction, int r, int g, int b, int a) {
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        RenderSystem.enableBlend();

        RenderBuffer2D.addTriangleToBuffer(m, buffer, pos, size, direction, r, g, b, a);

        BufferUploader.drawWithShader(buffer.buildOrThrow());

        RenderSystem.disableBlend();
    }

    public static void drawCircle(Matrix4f m, Vector2 pos, float size, int segments, RGB rgb) {
        drawCircle(m, pos.toVector2f(), size, segments, rgb);
    }

    public static void drawCircle(Matrix4f m, Vector2f pos, float size, int segments, RGB rgb) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba)
            a = rgba.a;
        drawCircle(m, pos, size, segments, r, g, b, a);
    }

    public static void drawCircle(Matrix4f m, Vector2f pos, float size, int segments, int r, int g, int b, int a) {
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);


        RenderSystem.enableBlend();

        RenderBuffer2D.addCircleToBuffer(m, buffer, pos, size, segments, r, g, b, a);

        BufferUploader.drawWithShader(buffer.buildOrThrow());

        RenderSystem.disableBlend();
    }

    public static void drawArc(GuiGraphics guiGraphics, Vector2 pos, int radius, int startAngle, int endAngle, RGB rgb) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

        RenderSystem.enableBlend();

        RenderBuffer2D.addArcToBuffer(guiGraphics.pose().last().pose(), bufferBuilder, pos.x, pos.y, radius, startAngle, endAngle, rgb);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

        RenderSystem.disableBlend();
    }

    public static void drawRoundedRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB rgb) {
        Matrix4f m = guiGraphics.pose().last().pose();

        // Отрисовка центрального прямоугольника (без углов)
        drawFillRect(guiGraphics, x, y + radius, width, height - radius * 2, rgb); // Верхняя и нижняя части
        drawFillRect(guiGraphics, x + radius, y, width - radius * 2, radius, rgb); // Левая и правая части
        drawFillRect(guiGraphics, x + radius, y + height - radius, width - radius * 2, radius, rgb); // Левая и правая части

        // Отрисовка закругленных углов (дуги)
        drawArc(guiGraphics, new Vector2(x + radius, y + radius), radius, 270, 180, rgb); // Левый верхний угол
        drawArc(guiGraphics, new Vector2(x + width - radius, y + radius), radius, 0, -90, rgb); // Правый верхний угол
        drawArc(guiGraphics, new Vector2(x + radius, y + height - radius), radius, -180, -270, rgb); // Левый нижний угол
        drawArc(guiGraphics, new Vector2(x + width - radius, y + height - radius), radius, 90, 0, rgb); // Правый нижний угол
    }

    public static void drawRoundedRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB rgb, DrawDirection direction) {
        Matrix4f m = guiGraphics.pose().last().pose();

        switch (direction) {
            case UP -> {
                drawFillRect(guiGraphics, x, y + radius, width, height - radius, rgb);
                drawFillRect(guiGraphics, x + radius, y, width - radius * 2, radius, rgb);

                // Отрисовка закругленных углов (дуги)
                drawArc(guiGraphics, new Vector2(x + radius,   y + radius), radius, 270, 180, rgb); // Левый верхний угол
                drawArc(guiGraphics, new Vector2(x + width - radius, y + radius), radius, 0, -90, rgb); // Правый верхний угол
                return;
            }
            case DOWN -> {
                drawFillRect(guiGraphics, x, y, width, height - radius, rgb);
                drawFillRect(guiGraphics, x + radius, y + height - radius, width - radius * 2, radius, rgb);

                // Отрисовка закругленных углов (дуги)
                drawArc(guiGraphics, new Vector2(x + radius, y + height - radius), radius, -180, -270, rgb); // Левый нижний угол
                drawArc(guiGraphics, new Vector2(x + width - radius, y + height - radius), radius, 90, 0, rgb); // Правый нижний угол
                return;
            }
        }

        throw new UnsupportedOperationException("Not implemented yet for this operation '" + direction.name() + "'");
    }


    private static int rgbaToInt(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}