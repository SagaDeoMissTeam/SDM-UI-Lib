package net.sixik.sdmuilibrary.client.utils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.RandomSource;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgetsFake.RGBFakeWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for rendering various objects with random properties.
 */
public class RandomRender {



    /**
     * Renders an object with a random size and position within the specified bounds.
     *
     * @param graphics The graphics context to draw on.
     * @param minPos   The minimum position for the object's top-left corner.
     * @param maxPos   The maximum position for the object's top-left corner.
     * @param minScale The minimum scale for the object's size.
     * @param maxScale The maximum scale for the object's size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     */
    public static void renderObjectWithSizeAndPos(GuiGraphics graphics, Vector2 minPos, Vector2 maxPos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb) {
        Vector2 size = getRandomScale(minScale, maxScale);
        Vector2 pos = getRandomPosInZone(minPos, maxPos);

        rO(graphics,pos, size, object, rgb);
    }

    /**
     * Renders an object with a random size and position within the specified bounds, and returns it as a widget.
     *
     * @param minPos   The minimum position for the object's top-left corner.
     * @param maxPos   The maximum position for the object's top-left corner.
     * @param minScale The minimum scale for the object's size.
     * @param maxScale The maximum scale for the object's size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     * @return A widget that renders the object.
     */
    public static RGBFakeWidget renderObjectWithSizeAndPos(Vector2 minPos, Vector2 maxPos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb) {
        Vector2 size = getRandomScale(minScale, maxScale);
        Vector2 pos = getRandomPosInZone(minPos, maxPos);

        return new RGBFakeWidget(rgb) {
            @Override
            public void draw(GuiGraphics graphics) {
                Vector2 size = getRandomScale(minScale, maxScale);
                Vector2 pos = getRandomPosInZone(minPos, maxPos);

                rO(graphics,pos, size, object, rgb);
            }
        };
    }


    /**
     * Renders an object with a random size within the specified bounds at a given position.
     * @param graphics The graphics context to draw on.
     * @param pos      The position for the object's top-left corner.
     * @param minScale The minimum scale for the object's size.
     * @param maxScale The maximum scale for the object's size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     */
    public static void renderObjectWithSize(GuiGraphics graphics, Vector2 pos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb) {
        Vector2 size = getRandomScale(minScale, maxScale);
        rO(graphics,pos, size, object, rgb);
    }


    /**
     * This function renders an object with a random size within the specified bounds at a given position.
     * It returns a widget that can be used to draw the object.
     *
     * @param pos      The position for the object's top-left corner.
     * @param minScale The minimum scale for the object's size.
     * @param maxScale The maximum scale for the object's size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * @return A widget that renders the object.
     */
    public static RGBFakeWidget renderObjectWithSize(Vector2 pos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb) {
        Vector2 size = getRandomScale(minScale, maxScale);
        return new RGBFakeWidget(rgb){
            @Override
            public void draw(GuiGraphics graphics) {
                rO(graphics,pos, size, object, rgb);
            }
        };
    }


    /**
     * Renders an object with a random rotation within the specified bounds at a given position.
     *
     * @param graphics The graphics context to draw on.
     * @param pos      The position for the object's top-left corner.
     * @param size     The size of the object.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * The function uses a random rotation between 0 and 360 degrees to render the object.
     * The rotation is applied to the object before drawing it.
     * The transformation is pushed onto the graphics context before rendering and popped afterward.
     */
    public static void renderObjectWithRotation(GuiGraphics graphics, Vector2 pos, Vector2 size, Object object, RGB rgb) {
        RandomSource source = RandomSource.create();
        GLHelper.pushTransform(graphics, pos, size, 1, source.nextFloat() * 360f);
        rO(graphics,pos, size, object, rgb);
        GLHelper.popTransform(graphics);
    }


    /**
     * This function renders an object with a random rotation within the specified bounds at a given position.
     * It returns a widget that can be used to draw the object.
     *
     * @param pos      The position for the object's top-left corner.
     * @param size     The size of the object.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * @return A widget that renders the object.
     *
     * The function uses a random rotation between 0 and 360 degrees to render the object.
     * The rotation is applied to the object before drawing it.
     * The transformation is pushed onto the graphics context before rendering and popped afterward.
     */
    public static RGBFakeWidget renderObjectWithRotation(Vector2 pos, Vector2 size, Object object, RGB rgb) {
        RandomSource source = RandomSource.create();
        return new RGBFakeWidget(rgb){
            @Override
            public void draw(GuiGraphics graphics) {
                GLHelper.pushTransform(graphics, pos, size, 1, source.nextFloat() * 360f);
                rO(graphics,pos, size, object, rgb);
                GLHelper.popTransform(graphics);
            }
        };
    }


    /**
     * Renders an object with a random rotation and position within the specified bounds.
     *
     * @param graphics The graphics context to draw on.
     * @param minPos   The minimum position for the object's top-left corner.
     * @param maxPos   The maximum position for the object's top-left corner.
     * @param size     The size of the object.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * The function generates a random position within the specified bounds using {@link #getRandomPosInZone(Vector2, Vector2)}.
     * It then applies a random rotation between 0 and 360 degrees to the object using {@link GLHelper#pushTransform(GuiGraphics, Vector2, Vector2, float, float)}.
     * The object is rendered using the provided {@link GuiGraphics}, position, size, type, and color.
     * Finally, the transformation is popped from the graphics context using {@link GLHelper#popTransform(GuiGraphics)}.
     */
    public static void renderObjectWithRotationAndPos(GuiGraphics graphics, Vector2 minPos, Vector2 maxPos, Vector2 size, Object object, RGB rgb) {
        RandomSource source = RandomSource.create();
        Vector2 pos = getRandomPosInZone(minPos, maxPos);
        GLHelper.pushTransform(graphics, pos, size, 1, source.nextFloat() * 360f);
        rO(graphics,pos, size, object, rgb);
        GLHelper.popTransform(graphics);
    }

    /**
     * This function renders an object with a random rotation and position within the specified bounds.
     * It returns a widget that can be used to draw the object.
     *
     * @param minPos   The minimum position for the object's top-left corner.
     * @param maxPos   The maximum position for the object's top-left corner.
     * @param size     The size of the object.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * @return A widget that renders the object.
     *
     * The function generates a random position within the specified bounds using {@link #getRandomPosInZone(Vector2, Vector2)}.
     * It then applies a random rotation between 0 and 360 degrees to the object using {@link GLHelper#pushTransform(GuiGraphics, Vector2, Vector2, float, float)}.
     * The object is rendered using the provided {@link GuiGraphics}, position, size, type, and color.
     * Finally, the transformation is popped from the graphics context using {@link GLHelper#popTransform(GuiGraphics)}.
     */
    public static RGBFakeWidget renderObjectWithRotationAndPos(Vector2 minPos, Vector2 maxPos, Vector2 size, Object object, RGB rgb) {
        RandomSource source = RandomSource.create();
        Vector2 pos = getRandomPosInZone(minPos, maxPos);
        return new RGBFakeWidget(rgb){
            @Override
            public void draw(GuiGraphics graphics) {
                GLHelper.pushTransform(graphics, pos, size, 1, source.nextFloat() * 360f);
                rO(graphics,pos, size, object, rgb);
                GLHelper.popTransform(graphics);
            }
        };
    }


    /**
     * Renders an object with random position, size, and rotation within the specified bounds.
     *
     * @param graphics The graphics context to draw on.
     * @param minPos   The minimum position for the object's top-left corner.
     * @param maxPos   The maximum position for the object's top-left corner.
     * @param minScale The minimum scale for the object's size.
     * @param maxScale The maximum scale for the object's size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * The function generates a random position within the specified bounds using {@link #getRandomPosInZone(Vector2, Vector2)}.
     * It then generates a random size within the specified bounds using {@link #getRandomScale(Vector2, Vector2)}.
     * A random rotation between 0 and 360 degrees is applied to the object using {@link GLHelper#pushTransform(GuiGraphics, Vector2, Vector2, float, float)}.
     * The object is rendered using the provided {@link GuiGraphics}, position, size, type, and color.
     * Finally, the transformation is popped from the graphics context using {@link GLHelper#popTransform(GuiGraphics)}.
     */
    public static void renderObject(GuiGraphics graphics, Vector2 minPos, Vector2 maxPos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb) {
        RandomSource source = RandomSource.create();
        Vector2 pos = getRandomPosInZone(minPos, maxPos);
        Vector2 size = getRandomScale(minScale, maxScale);
        GLHelper.pushTransform(graphics, pos, size, 1, source.nextFloat() * 360f);
        rO(graphics,pos, size, object, rgb);
        GLHelper.popTransform(graphics);
    }

    /**
     * This function renders an object with a random rotation and position within the specified bounds.
     * It returns a widget that can be used to draw the object.
     *
     * @param minPos   The minimum position for the object's top-left corner.
     * @param maxPos   The maximum position for the object's top-left corner.
     * @param minScale The minimum scale for the object's size.
     * @param maxScale The maximum scale for the object's size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the object.
     *
     * @return A widget that renders the object.
     *
     * The function generates a random position within the specified bounds using {@link #getRandomPosInZone(Vector2, Vector2)}.
     * It then applies a random rotation between 0 and 360 degrees to the object using {@link GLHelper#pushTransform(GuiGraphics, Vector2, Vector2, float, float)}.
     * The object is rendered using the provided {@link GuiGraphics}, position, size, type, and color.
     * Finally, the transformation is popped from the graphics context using {@link GLHelper#popTransform(GuiGraphics)}.
     */
    public static RGBFakeWidget renderObject(Vector2 minPos, Vector2 maxPos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb) {
        RandomSource source = RandomSource.create();
        Vector2 pos = getRandomPosInZone(minPos, maxPos);
        Vector2 sizeD = getRandomScale(minScale, maxScale);
        return new RGBFakeWidget(rgb){
            @Override
            public void draw(GuiGraphics graphics) {
                GLHelper.pushTransform(graphics, pos, sizeD, 1, source.nextFloat() * 360f);
                rO(graphics,pos, sizeD, object, rgb);
                GLHelper.popTransform(graphics);
            }
        };
    }

    /**
     * Renders a specified number of objects with random properties within a given zone.
     *
     * @param graphics The graphics context to draw on.
     * @param count    The number of objects to render.
     * @param minPos   The minimum position for the objects' top-left corner.
     * @param maxPos   The maximum position for the objects' top-left corner.
     * @param minScale The minimum scale for the objects' size.
     * @param maxScale The maximum scale for the objects' size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the objects.
     *
     * This function iterates over the specified count and calls the {@link #renderObject(GuiGraphics, Vector2, Vector2, Vector2, Vector2, Object, RGB)}
     * method for each iteration. This results in a specified number of objects being rendered with random properties within the given zone.
     */
    public static void renderObjectCount(GuiGraphics graphics, int count, Vector2 minPos, Vector2 maxPos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb){
        for (int i = 0; i < count; i++) {
            renderObject(graphics, minPos, maxPos, minScale, maxScale, object, rgb);
        }
    }

    /**
     * This function renders a specified number of objects with random properties within a given zone.
     *
     * @param count    The number of objects to render.
     * @param minPos   The minimum position for the objects' top-left corner.
     * @param maxPos   The maximum position for the objects' top-left corner.
     * @param minScale The minimum scale for the objects' size.
     * @param maxScale The maximum scale for the objects' size.
     * @param object   The type of object to render.
     * @param rgb      The color to use for rendering the objects.
     *
     * @return A widget that renders the specified number of objects.
     *
     * The function iterates over the specified count and calls the {@link #renderObject(Vector2, Vector2, Vector2, Vector2, Object, RGB)}
     * method for each iteration. This results in a specified number of objects being rendered with random properties within the given zone.
     */
    public static RGBFakeWidget renderObjectCount(int count, Vector2 minPos, Vector2 maxPos, Vector2 minScale, Vector2 maxScale, Object object, RGB rgb){
        List<RGBFakeWidget> widgets = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            widgets.add(renderObject(minPos, maxPos, minScale, maxScale, object, rgb));
        }

        return new RGBFakeWidget(rgb){
            @Override
            public void draw(GuiGraphics graphics) {
                for (int i = 0; i < widgets.size(); i++) {
                    widgets.get(i).draw(graphics);
                }
            }
        };
    }



    private static void rO(GuiGraphics graphics, Vector2 pos, Vector2 size,Object object, RGB rgb){
        switch (object) {
            case CUBE -> rgb.draw(graphics, pos.x, pos.y, size.x, size.y, 0f);
            case TRIANGLE -> rgb.drawTriangle(graphics, pos.x, pos.y, size.x, size.y);
        }
    }

    public static Vector2 getRandomScale(Vector2 minScale, Vector2 maxScale){
        RandomSource source = RandomSource.create();
        return new Vector2(
                source.nextInt(minScale.x, maxScale.x),
                source.nextInt(minScale.y, maxScale.y)
        );
    }

    public static Vector2 getRandomPosInZone(Vector2 startPos, Vector2 endPos){
        RandomSource source = RandomSource.create();
        return new Vector2(
                source.nextInt(startPos.x, endPos.x),
                source.nextInt(startPos.y, endPos.y)
        );
    }


    public enum Object {
        CUBE,
        TRIANGLE;
    }
}
