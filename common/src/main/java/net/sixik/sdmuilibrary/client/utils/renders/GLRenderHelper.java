package net.sixik.sdmuilibrary.client.utils.renders;


import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.math.Vector2f;

/**
 * Методы для взаимодействия с {@link PoseStack}, вращения, масштабирования, перемещения
 */
public class GLRenderHelper {

    /**
     * Метод добавляет резчик
     * @param pos Координаты
     * @param size Размер
     */
    public static void enableScissor(GuiGraphics guiGraphics, Vector2 pos, Vector2 size) {
        guiGraphics.enableScissor(pos.x, pos.y, pos.x + size.x, pos.y + size.y);
    }

    /**
     * Метод добавляет резчик для блока кода
     * @param pos Координаты
     * @param size Размер
     * @param runnable Блок кода
     */
    public static void enableScissorFor(GuiGraphics guiGraphics, Vector2 pos, Vector2 size, Runnable runnable) {
        enableScissor(guiGraphics, pos, size);
        runnable.run();
        disableScissor(guiGraphics);
    }

    /**
     * Метод добавляет резчик
     * @param posX Координаты X
     * @param posY Координаты Y
     * @param weight Размер Ширина
     * @param height Размер Высота
     */
    public static void enableScissor(GuiGraphics guiGraphics, int posX, int posY, int weight, int height) {
        guiGraphics.enableScissor(posX, posY, posX + weight, posY + height);
    }


    /**
     * Метод добавляет резчик для блока кода
     * @param posX Координаты X
     * @param posY Координаты Y
     * @param weight Размер Ширина
     * @param height Размер Высота
     * @param runnable Блок кода
     */
    public static void enableScissorFor(GuiGraphics guiGraphics, int posX, int posY, int weight, int height, Runnable runnable) {
        enableScissor(guiGraphics, posX, posY, weight, height);
        runnable.run();
        disableScissor(guiGraphics);
    }

    /**
     * Метод отключает резчик
     */
    public static void disableScissor(GuiGraphics guiGraphics) {
        guiGraphics.disableScissor();
    }

    /**
     * Метод масштабирования
     * @param scale Уровень масштабирования
     * @param pos Координаты от которых происходит масштабирования (Обычно это координаты виджета)
     */
    public static void setScale(GuiGraphics guiGraphics, float scale, Vector2f pos) {
        setScale(guiGraphics, scale, pos.x, pos.y);
    }

    /**
     * Метод масштабирования для блока кода
     * @param scale Уровень масштабирования
     * @param pos Координаты от которых происходит масштабирования (Обычно это координаты виджета)
     * @param runnable Блок кода
     */
    public static void setScale(GuiGraphics guiGraphics, float scale, Vector2f pos, Runnable runnable) {
        setScale(guiGraphics,scale,pos.x, pos.y, runnable);
    }

    /**
     * Метод масштабирования
     * @param scale Уровень масштабирования
     * @param centerX Координаты X от которых происходит масштабирования (Обычно это координаты виджета)
     * @param centerY Координаты Y от которых происходит масштабирования (Обычно это координаты виджета)
     */
    public static void setScale(GuiGraphics guiGraphics, float scale, float centerX, float centerY) {
        PoseStack poseStack = guiGraphics.pose();

        poseStack.translate(centerX, centerY, 0);
        poseStack.scale(scale, scale, 1);

        poseStack.translate(-centerX, -centerY, 0);

    }

    /**
     * Метод масштабирования для блока кода
     * @param scale Уровень масштабирования
     * @param centerX Координаты X от которых происходит масштабирования (Обычно это координаты виджета)
     * @param centerY Координаты Y от которых происходит масштабирования (Обычно это координаты виджета)
     * @param runnable Блок кода
     */
    public static void setScale(GuiGraphics guiGraphics, float scale, float centerX, float centerY, Runnable runnable) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(centerX, centerY, 0);
        poseStack.scale(scale, scale, 1);
        poseStack.translate(-centerX, -centerY, 0);

        runnable.run();

        poseStack.popPose();
    }

    /**
     * Метод применяет масштабирования на основе экрана для блока кода
     * @param scale Уровень масштабирования
     * @param runnable Блок кода
     */
    public static void setScaleByScreen(GuiGraphics guiGraphics, float scale, Runnable runnable) {
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        PoseStack poseStack = guiGraphics.pose();

        int w = window.getGuiScaledWidth();
        int h = window.getGuiScaledHeight();

        poseStack.translate((float) w / 2, (float) h / 2, 0);
        poseStack.scale(scale, scale, 1);
        poseStack.translate(-(float) w / 2, -(float) h / 2, 0);
        runnable.run();


    }

    /**
     * Метод для вращения
     * @param rotation Угол поворота
     * @param pos Координаты от которых происходит вращения (Обычно это координаты виджета)
     */
    public static void setRotation(GuiGraphics guiGraphics, float rotation, Vector2f pos) {
        setRotation(guiGraphics, rotation, pos.x, pos.y);
    }

    /**
     * Метод для вращения для блока кода
     * @param rotation Угол поворота
     * @param pos Координаты от которых происходит вращения (Обычно это координаты виджета)
     * @param runnable Блок Кода
     */
    public static void setRotation(GuiGraphics guiGraphics, float rotation, Vector2f pos, Runnable runnable) {
        setRotation(guiGraphics, rotation, pos.x, pos.y, runnable);
    }

    /**
     * Метод для вращения для блока кода
     * @param rotation Угол поворота
     * @param centerX Координаты X от которых происходит масштабирования (Обычно это координаты виджета)
     * @param centerY Координаты Y от которых происходит масштабирования (Обычно это координаты виджета)
     */
    public static void setRotation(GuiGraphics guiGraphics, float rotation, float centerX, float centerY) {
        PoseStack poseStack = guiGraphics.pose();

        poseStack.translate(centerX, centerY, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.translate(-centerX, -centerY, 0);
    }

    /**
     * Метод для вращения для блока кода
     * @param rotation Угол поворота
     * @param centerX Координаты X от которых происходит масштабирования (Обычно это координаты виджета)
     * @param centerY Координаты Y от которых происходит масштабирования (Обычно это координаты виджета)
     * @param runnable Блок Кода
     */
    public static void setRotation(GuiGraphics guiGraphics, float rotation, float centerX, float centerY, Runnable runnable) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(centerX, centerY, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.translate(-centerX, -centerY, 0);

        runnable.run();

        poseStack.popPose();
    }

    /**
     * Метод, который реализует {@link #setRotation(GuiGraphics, float, Vector2f)} {@link #setScale(GuiGraphics, float, Vector2f)}
     */
    public static void setTransform(GuiGraphics guiGraphics, Vector2 pos, float rotation, float scale) {
        setTransform(guiGraphics, pos.x, pos.y, rotation, scale);
    }

    /**
     * Метод, который реализует {@link #setRotation(GuiGraphics, float, Vector2f)} {@link #setScale(GuiGraphics, float, Vector2f)}
     */
    public static void setTransform(GuiGraphics guiGraphics, Vector2 pos, float rotation, float scale, Runnable runnable) {
        setTransform(guiGraphics, pos.x, pos.y, rotation, scale, runnable);
    }

    /**
     * Метод, который реализует {@link #setRotation(GuiGraphics, float, Vector2f)} {@link #setScale(GuiGraphics, float, Vector2f)}
     */
    public static void setTransform(GuiGraphics guiGraphics, float centerX, float centerY, float rotation, float scale) {
        PoseStack poseStack = guiGraphics.pose();

        poseStack.translate(centerX, centerY, 0);
        poseStack.scale(scale, scale, 1);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.translate(-centerX, -centerY, 0);

    }

    /**
     * Метод, который реализует {@link #setRotation(GuiGraphics, float, Vector2f)} {@link #setScale(GuiGraphics, float, Vector2f)}
     */
    public static void setTransform(GuiGraphics guiGraphics, float centerX, float centerY, float rotation, float scale, Runnable runnable) {
        PoseStack poseStack = guiGraphics.pose();

        poseStack.pushPose();

        poseStack.translate(centerX, centerY, 0);
        poseStack.scale(scale, scale, 1);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.translate(-centerX, -centerY, 0);

        runnable.run();

        poseStack.popPose();

    }

    /**
     * Метод для установки глубины
     * @param depth Глубина
     */
    public static void setDepth(GuiGraphics guiGraphics, int depth) {
        guiGraphics.pose().translate(0,0,depth);
    }

    /**
     * Метод для установки глубины для блока кода
     * @param depth Глубина
     * @param runnable Блок Кода
     */
    public static void setDepth(GuiGraphics guiGraphics, int depth, Runnable runnable) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,0,depth);
        runnable.run();
        guiGraphics.pose().popPose();
    }


}
