package net.sixik.sdmuilibrary.client.utils.buffers;


import com.mojang.blaze3d.vertex.VertexConsumer;
import net.sixik.sdmuilibrary.client.utils.DrawDirection;
import net.sixik.sdmuilibrary.client.utils.math.QuadVector;
import net.sixik.sdmuilibrary.client.utils.math.TriangleVector;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;
import org.joml.Matrix4f;

/**
 * Класс для добавления в буффер рендера разных фигур и элементов
 */
public class RenderBuffer2D {

    /**
     * Добавляет квадрат в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param rgb Цвет фигуры
     */
    public static void addQuadToBuffer(Matrix4f m, VertexConsumer buffer, Vector2 pos, Vector2 size, RGB rgb){
        addQuadToBuffer(m, buffer, pos.toVector2f(), size.toVector2f(), rgb);
    }

    /**
     * Добавляет квадрат в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param rgb Цвет фигуры
     */
    public static void addQuadToBuffer(Matrix4f m, VertexConsumer buffer, Vector2f pos, Vector2f size, RGB rgb){
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;
        addQuadToBuffer(m, buffer, pos, size, r,g,b,a);
    }


    /**
     * Добавляет квадрат в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param r Красный Цвет
     * @param g Зелёный Цвет
     * @param b Синий Цвет
     * @param a Прозрачность
     */
    public static void addQuadToBuffer(Matrix4f m, VertexConsumer buffer, Vector2f pos, Vector2f size, int r, int g, int b, int a){
        QuadVector quadVector = QuadVector.create(
                Vector2f.of(pos.x, pos.y - size.y),
                Vector2f.of(pos.x, pos.y),
                Vector2f.of(pos.x + size.x, pos.y),
                Vector2f.of(pos.x + size.x, pos.y - size.y)
        );

        buffer.addVertex(m, quadVector.pos1.x, quadVector.pos1.y, 0.0F).setColor(r, g, b, a);
        buffer.addVertex(m, quadVector.pos2.x, quadVector.pos2.y, 0.0F).setColor(r, g, b, a);
        buffer.addVertex(m, quadVector.pos3.x, quadVector.pos3.y, 0.0F).setColor(r, g, b, a);
        buffer.addVertex(m, quadVector.pos4.x, quadVector.pos4.y, 0.0F).setColor(r, g, b, a);
    }

    /**
     * Добавляет треугольник в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param rgb Цвет фигуры
     */
    public static void addTriangleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2 pos, Vector2 size, DrawDirection direction, RGB rgb){
        addTriangleToBuffer(m, buffer, pos.toVector2f(), size.toVector2f(), direction, rgb);
    }

    /**
     * Добавляет треугольник в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param rgb Цвет фигуры
     */
    public static void addTriangleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2f pos, Vector2f size, DrawDirection direction, RGB rgb){
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;
        addTriangleToBuffer(m, buffer, pos, size, direction, r,g,b,a);

    }

    /**
     * Добавляет треугольник в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param r Красный Цвет
     * @param g Зелёный Цвет
     * @param b Синий Цвет
     * @param a Прозрачность
     */
    public static void addTriangleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2 pos, Vector2 size, DrawDirection direction, int r, int g, int b, int a) {
        addTriangleToBuffer(m, buffer, pos.toVector2f(), size.toVector2f(), direction, r, g, b, a);
    }


    /**
     * Добавляет треугольник в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param r Красный Цвет
     * @param g Зелёный Цвет
     * @param b Синий Цвет
     * @param a Прозрачность
     */
    public static void addTriangleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2f pos, Vector2f size, DrawDirection direction, int r, int g, int b, int a){
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

        buffer.addVertex(m, triangle.pos1.x, triangle.pos1.y, 0.0F).setColor(r,g,b,a);
        buffer.addVertex(m, triangle.pos2.x, triangle.pos2.y, 0.0F).setColor(r,g,b,a);
        buffer.addVertex(m, triangle.pos3.x, triangle.pos3.y, 0.0F).setColor(r,g,b,a);
    }


    /**
     * Добавляет круг в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param segments Количество сегментов (углов)
     * @param rgb Цвет фигуры
     */
    public static void addCircleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2 pos, float size, int segments, RGB rgb){
        addCircleToBuffer(m, buffer, pos.toVector2f(), size, segments, rgb);
    }

     /**
     * Добавляет круг в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param segments Количество сегментов (углов)
     * @param rgb Цвет фигуры
     */
    public static void addCircleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2f pos, float size, int segments, RGB rgb){
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;
        addCircleToBuffer(m, buffer, pos, size, segments, r,g,b,a);
    }


    /**
     * Добавляет круг в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param pos Координаты для отрисовки
     * @param size Размер фигуры
     * @param segments Количество сегментов (углов)
     * @param r Красный Цвет
     * @param g Зелёный Цвет
     * @param b Синий Цвет
     * @param a Прозрачность
     */
    public static void addCircleToBuffer(Matrix4f m, VertexConsumer buffer, Vector2f pos, float size, int segments, int r, int g, int b, int a){
        buffer.addVertex(m, pos.x, pos.y, 0).setColor(r, g, b, a);


        float angleStep = (float)(2 * Math.PI / segments);

        for (int i = segments; i >= 0; i--) {
            float angle = i * angleStep;
            float x = (float) (Math.cos(angle) * size) + pos.x;
            float y = (float) (Math.sin(angle) * size) + pos.y;
            buffer.addVertex(m, x, y, 0).setColor(r, g, b, a);
        }

    }


    /**
     * Добавляет дугу в буффер
     * @param m Матрица рендера. Обычно это {@link MatrixStack.Entry#getPositionMatrix()}
     * @param buffer Буффер к которому будет добавлена фигура
     * @param cX Координаты для отрисовки
     * @param cY Координаты для отрисовки
     * @param radius Радиус дуги
     * @param startAngle Начальный угол
     * @param endAngle Конечный угол
     * @param rgb Цвет фигуры
     */
    public static void addArcToBuffer(Matrix4f m, VertexConsumer buffer, int cX, int cY, int radius, int startAngle, int endAngle, RGB rgb) {

        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        buffer.addVertex(m, cX, cY, 0).setColor(r, g, b, a); // Центр дуги

        for (int i = startAngle; i >= endAngle; i -= 5) {
            double angle = Math.toRadians(i);
            float x = (float) (Math.cos(angle) * radius) + cX;
            float y = (float) (Math.sin(angle) * radius) + cY;
            buffer.addVertex(m, x, y, 0).setColor(r, g, b, a);
        }

    }


    public static void addRectToBufferWithUV(Matrix4f m, VertexConsumer buffer, int x, int y, int w, int h, RGB rgb, float u0, float v0, float u1, float v1) {
        if (w > 0 && h > 0) {
            int r = rgb.r;
            int g = rgb.g;
            int b = rgb.b;
            int a = 255;
            if(rgb instanceof RGBA rgba)
                a = rgba.a;
            buffer.addVertex(m, (float)x, (float)(y + h), 0.0F).setColor(r, g, b, a).setUv(u0, v1);
            buffer.addVertex(m, (float)(x + w), (float)(y + h), 0.0F).setColor(r, g, b, a).setUv(u1, v1);
            buffer.addVertex(m, (float)(x + w), (float)y, 0.0F).setColor(r, g, b, a).setUv(u1, v0);
            buffer.addVertex(m, (float)x, (float)y, 0.0F).setColor(r, g, b, a).setUv(u0, v0);
        }
    }
}
