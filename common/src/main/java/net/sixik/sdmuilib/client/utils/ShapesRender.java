package net.sixik.sdmuilib.client.utils;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.sixik.sdmuilib.client.utils.math.QuadVector;
import net.sixik.sdmuilib.client.utils.math.TriangleVector;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.math.Vector2f;
import net.sixik.sdmuilib.client.utils.misc.RGB;
import net.sixik.sdmuilib.client.utils.misc.RGBA;
import org.joml.Matrix4f;

public class ShapesRender {

    public static void drawQuad(Matrix4f m, BufferBuilder buffer, Vector2 pos, Vector2 size, RGB rgb){
        drawQuad(m, buffer, pos.toVector2f(), size.toVector2f(), rgb);
    }

    public static void drawQuad(Matrix4f m, BufferBuilder buffer, Vector2f pos, Vector2f size, RGB rgb){
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;
        drawQuad(m, buffer, pos, size, r,g,b,a);
    }

    public static void drawQuad(Matrix4f m, BufferBuilder buffer, Vector2f pos, Vector2f size, int r, int g, int b, int a){

        QuadVector quadVector = QuadVector.create(
                Vector2f.of(pos.x, pos.y - size.y),
                Vector2f.of(pos.x, pos.y),
                Vector2f.of(pos.x + size.x, pos.y),
                Vector2f.of(pos.x + size.x, pos.y - size.y)
        );

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(m, quadVector.pos1.x, quadVector.pos1.y, 0.0F).color(r, g, b, a).endVertex();
        buffer.vertex(m, quadVector.pos2.x, quadVector.pos2.y, 0.0F).color(r, g, b, a).endVertex();
        buffer.vertex(m, quadVector.pos3.x, quadVector.pos3.y, 0.0F).color(r, g, b, a).endVertex();
        buffer.vertex(m, quadVector.pos4.x, quadVector.pos4.y, 0.0F).color(r, g, b, a).endVertex();
    }

    public static void drawTriangle(Matrix4f m, BufferBuilder buffer, Vector2 pos, Vector2 size, DrawDirection direction, RGB rgb){
        drawTriangle(m, buffer, pos.toVector2f(), size.toVector2f(), direction, rgb);
    }

    public static void drawTriangle(Matrix4f m, BufferBuilder buffer, Vector2f pos, Vector2f size, DrawDirection direction, RGB rgb){
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;
        drawTriangle(m, buffer, pos, size, direction, r,g,b,a);

    }
    public static void drawTriangle(Matrix4f m, BufferBuilder buffer, Vector2 pos, Vector2 size, DrawDirection direction, int r, int g, int b, int a) {
        drawTriangle(m, buffer, pos.toVector2f(), size.toVector2f(), direction, r, g, b, a);
    }

    public static void drawTriangle(Matrix4f m, BufferBuilder buffer, Vector2f pos, Vector2f size, DrawDirection direction, int r, int g, int b, int a){
        TriangleVector triangle = null;

        switch (direction) {
            case UP -> triangle = TriangleVector.create(
                    Vector2f.of(pos.x + (float) size.x / 2, pos.y - size.y),
                    Vector2f.of(pos.x, pos.y),
                    Vector2f.of(pos.x + size.x, pos.y)
            );
            case DOWN -> triangle = TriangleVector.create(
                    Vector2f.of(pos.x, pos.y - size.y),
                    Vector2f.of(pos.x + (float)size.x / 2, pos.y + size.y - size.y),
                    Vector2f.of(pos.x + size.x, pos.y - size.y)
            );
            case LEFT -> triangle = TriangleVector.create(
                    Vector2f.of(pos.x, pos.y - size.y / 2),
                    Vector2f.of(pos.x + size.x, pos.y),
                    Vector2f.of(pos.x + size.x, pos.y - size.y)
            );
            case RIGHT -> triangle = TriangleVector.create(
                    Vector2f.of(pos.x, pos.y - size.y),
                    Vector2f.of(pos.x, pos.y),
                    Vector2f.of(pos.x + size.x, pos.y - size.y / 2)
            );
        }
        buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(m, triangle.pos1.x, triangle.pos1.y, 0.0F).color(r,g,b,a).endVertex();
        buffer.vertex(m, triangle.pos2.x, triangle.pos2.y, 0.0F).color(r,g,b,a).endVertex();
        buffer.vertex(m, triangle.pos3.x, triangle.pos3.y, 0.0F).color(r,g,b,a).endVertex();
    }

    public static void drawCircle(Matrix4f m, BufferBuilder buffer, Vector2 pos, float size, int segments, RGB rgb){
        drawCircle(m, buffer, pos.toVector2f(), size, segments, rgb);
    }
    public static void drawCircle(Matrix4f m, BufferBuilder buffer, Vector2f pos, float size, int segments, RGB rgb){
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;
        drawCircle(m, buffer, pos, size, segments, r,g,b,a);
    }

    public static void drawCircle(Matrix4f m, BufferBuilder buffer, Vector2f pos, float size, int segments, int r, int g, int b, int a){
        buffer.vertex(m, pos.x, pos.y, 0).color(r, g, b, a).endVertex();

        float angleStep = (float)(2 * Math.PI / segments);

        for (int i = segments; i >= 0; i--) {
            float angle = i * angleStep;
            float x = (float) (Math.cos(angle) * size) + pos.x;
            float y = (float) (Math.sin(angle) * size) + pos.y;
            buffer.vertex(m, x, y, 0).color(r, g, b, a).endVertex();
        }
    }


    public enum DrawDirection {
        UP, DOWN, LEFT, RIGHT
    }
}
