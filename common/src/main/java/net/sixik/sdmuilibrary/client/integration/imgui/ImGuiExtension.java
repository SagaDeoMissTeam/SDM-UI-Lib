package net.sixik.sdmuilibrary.client.integration.imgui;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiBuffers;
import net.sixik.sdmuilibrary.client.integration.imgui.struct.ImGuiStructs;
import net.sixik.sdmuilibrary.client.integration.imgui.utils.ImGuiGLRenderHelper;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import oshi.util.tuples.Pair;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Расширяет возможности ImGui
 */
public class ImGuiExtension {

    public static void createID(String id, Runnable runnable) {
        ImGui.pushID(id);
        runnable.run();
        ImGui.popID();
    }


    /**
     * {@link imgui.flag.ImGuiComboFlags}
     */

    public static void comboBoxList(String id, int currentValue, Collection<String> array, Consumer<ImGuiStructs.ComboBox> onSelected) {
        comboBoxList(id, 0, currentValue, array, onSelected);
    }

    public static void comboBoxList(String id, int imGuiComboFlags, int currentValue, Collection<String> array, Consumer<ImGuiStructs.ComboBox> onSelected) {
        comboBoxList(id, currentValue, imGuiComboFlags, array.toArray(new String[0]), onSelected);
    }

    public static void comboBoxList(String id, int currentValue, String[] array, Consumer<ImGuiStructs.ComboBox> onSelected) {
        comboBoxList(id, 0, currentValue, array, onSelected);
    }

    public static void comboBoxList(String id, int imGuiComboFlags, int currentValue, String[] array, Consumer<ImGuiStructs.ComboBox> onSelected) {
        String d = "NULL";
        int currentItem = -1;

        if (currentValue >= 0 && currentValue < array.length) {
            d = array[currentValue];
            currentItem = currentValue;
        }


        if (ImGui.beginCombo(id, d, imGuiComboFlags)) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    continue;
                }

                boolean s = currentItem == i;
                if (ImGui.selectable(array[i], s)) {
                    currentItem = i;
                    if (onSelected != null) {
                        onSelected.accept(new ImGuiStructs.ComboBox(array[i], i));
                    }
                }

                if (s) {
                    ImGui.setItemDefaultFocus();
                }
            }
            ImGui.endCombo();
        }
    }

    public static void comboBoxListWithTooltip(String id, String currentKey, Map<String, Runnable> valueMap, Consumer<ImGuiStructs.ComboBox> onSelected) {
        comboBoxListWithTooltip(id, 0, currentKey, valueMap, onSelected);
    }

    public static void comboBoxListWithTooltip(String id, int imGuiComboFlags, String currentKey, Map<String, Runnable> valueMap, Consumer<ImGuiStructs.ComboBox> onSelected) {
        String d = "NULL";
        int currentItem = -1;

        Runnable[] tooltips = valueMap.values().toArray(new Runnable[0]);
        String[] array = valueMap.keySet().toArray(new String[0]);

        for (int i = 0; i < array.length; i++) {
            if(currentKey.equals(array[i])) {
                d = array[i];
                currentItem = i;
                break;
            }
        }

        if (ImGui.beginCombo(id, d, imGuiComboFlags)) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    continue;
                }

                boolean s = currentItem == i;
                if (ImGui.selectable(array[i], s)) {
                    currentItem = i;
                    if (onSelected != null) {
                        onSelected.accept(new ImGuiStructs.ComboBox(array[i], i));
                    }
                }

                if(tooltips[i] != null && ImGui.isItemHovered()) {
                    tooltips[i].run();
                }

                if (s) {
                    ImGui.setItemDefaultFocus();
                }
            }
            ImGui.endCombo();
        }
    }

    /**
     * Создаёт контейнер для Панели
     * @param id ID Панели
     */
    public static void toolBar(String id, Runnable codeBlock) {
        ImGui.pushID(id);
        codeBlock.run();
        ImGui.popID();
    }

    /**
     * Добавляет элемент кода к Панели
     */
    public static void toolBarItem(Runnable codeBlock) {
        ImGui.beginGroup();
        codeBlock.run();
        ImGui.endGroup();
    }

    /**
     * Добавляет элемент кода к Панели и располагает на одной линии (Вызывает {@link ImGui#sameLine()} перед вызовом {@link Runnable}
     */
    public static void toolBarItemLine(Runnable codeBlock) {
        ImGui.sameLine();
        ImGui.beginGroup();
        codeBlock.run();
        ImGui.endGroup();
    }

    /**
     * Создаёт группу с элементами
     * @param id ID группы
     * @param codeBlock Фрагмент кода который вызывается при открытой группы
     */
    public static void collapseGroup(String id, Runnable codeBlock) {
        collapseGroup(id, 0, codeBlock);
    }

    /**
     * Создаёт группу с элементами
     * @param id ID группы
     * @param imGuiTreeNodeFlags Флаги {@link ImGuiTreeNodeFlags}
     * @param codeBlock Фрагмент кода который вызывается при открытой группы
     */
    public static void collapseGroup(String id, int imGuiTreeNodeFlags, Runnable codeBlock) {
        if (ImGui.collapsingHeader(id, imGuiTreeNodeFlags)) {
            codeBlock.run();
        }
    }

    /**
     * Создаёт группу с элементами
     * @param id ID группы
     * @param codeBlock Фрагмент кода который вызывается при открытой группы
     * @param collapsed Закрыта или открыта группа
     */
    public static void collapseGroup(String id, ImBoolean collapsed, Runnable codeBlock) {
        collapseGroup(id, collapsed, 0, codeBlock);
    }

    /**
     * Создаёт группу с элементами
     * @param id ID группы
     * @param imGuiTreeNodeFlags Флаги {@link ImGuiTreeNodeFlags}
     * @param codeBlock Фрагмент кода который вызывается при открытой группы
     * @param collapsed Закрыта или открыта группа
     */
    public static void collapseGroup(String id, ImBoolean collapsed, int imGuiTreeNodeFlags, Runnable codeBlock) {
        if (ImGui.collapsingHeader(id, new ImBoolean(collapsed), imGuiTreeNodeFlags)) {
            codeBlock.run();
        }
    }

    /**
     * Добавляет простой Tooltip для элемента при наведении. Учтите что элемент должен поддерживать {@link ImGui#isItemHovered()} иначе применение будет к последнему элементу который поддерживает функцию
     * <pre>{@code
     * simpleTooltip("someTooltip", () -> {
     *    ImGui.text("Element 1");
     * });
     * }</pre>
     * @param tooltip Текст тултипа
     * @param toAdd Блок кода к которому будет применяться тултип
     */
    public static void simpleTooltip(String tooltip, Runnable toAdd) {
        toAdd.run();
        if(ImGui.isItemHovered()) {
            ImGui.setTooltip(tooltip);
        }
    }

    /**
     * Позволяет рендерить тултип со сложной логикой
     * <pre>{@code
     * advancedTooltip(() -> {
     *     ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 10, 10);
     *     ImGui.text("Detailed Information:");
     *     ImGui.separator();
     *     ImGui.text("Name: John Doe");
     *     ImGui.text("Age: 30");
     *     ImGui.text("Occupation: Developer");
     *     ImGui.popStyleVar();
     * }, () -> {
     *     ImGui.text("Element 1");
     * });
     * }</pre>
     * @param codeBlock Блок кода к тултипа (К коду уже будет применён {@link ImGui#beginTooltip()} и {@link ImGui#endTooltip()}
     * @param toAdd Блок кода к которому будет применяться тултип
     */
    public static void advancedTooltip(Runnable codeBlock, Runnable toAdd) {
        toAdd.run();
        if(ImGui.isItemHovered()) {
            ImGui.beginTooltip();
            codeBlock.run();
            ImGui.endTooltip();
        }
    }

    public static void advancedTooltip(Runnable codeBlock) {
        if(ImGui.isItemHovered()) {
            ImGui.beginTooltip();
            codeBlock.run();
            ImGui.endTooltip();
        }
    }

    /**
     * Позволяет рендерить предметы в контексте ImGui
     * @param name Имя контейнера
     * @param item Предмет
     * @param width Ширина рендера предмета
     * @param height Высота рендера предмета
     * @return Если предмет нажат
     */
    public static boolean drawItem(String name, ItemStack item, float width, float height) {
        return drawItem(name, item, width, height, 0);
    }


    /**
     * Позволяет рендерить предметы в контексте ImGui
     * @param name Имя контейнера
     * @param item Предмет
     * @param width Ширина рендера предмета
     * @param height Высота рендера предмета
     * @param imGuiBTSFlags Флаги рендера {@link ImGuiBTSFlags}
     * @return Если предмет нажат
     */
    public static boolean drawItem(String name, ItemStack item, float width, float height, int imGuiBTSFlags) {
        return drawItem(name, item, width, height, 1, 0, imGuiBTSFlags, RGBA.DEFAULT);
    }

    /**
     * Позволяет рендерить предметы в контексте ImGui
     * @param name Имя контейнера
     * @param item Предмет
     * @param width Ширина контейнера
     * @param height Высота контейнера
     * @param imGuiBTSFlags Флаги рендера {@link ImGuiBTSFlags}
     * @param scale Размер предмета
     * @param rotation Вращение предмета
     * @param color Цвета рендера {@link RGBA}, работает так же как и {@link RenderSystem#setShaderColor(float r, float g, float b, float a)}
     * @return Если предмет нажат
     */
    public static boolean drawItem(String name, ItemStack item, float width, float height, float scale, float rotation, int imGuiBTSFlags, RGBA color) {
        ImGui.pushID(name);
        ImVec2 cPos = ImGui.getCursorPos();
        boolean clicked = drawMcRender(width, height, imGuiBTSFlags, color, (
            (cursor, hovered) -> {
                PoseStack stack = new PoseStack();
                if((imGuiBTSFlags & ImGuiBTSFlags.ALWAYS_ON_TOP) != 0) stack.translate(0f, 0f, 200f);


                stack.pushPose();
                ImGuiGLRenderHelper.renderItemStack(item, stack,
                        cursor.x, cursor.y, width, height,
                        ((hovered || (imGuiBTSFlags & ImGuiBTSFlags.DISABLE_RESIZE) == 0) ? 1.0f : 0.9f) * scale, rotation
                 );
                stack.popPose();

                ImGuiGLRenderHelper.renderItemDecorations(item, stack, (int) cursor.x, (int) cursor.y, width, height);
            }
        ));

        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return false;

        ImVec2 pos = ImGui.getCursorScreenPos();

        if (ImGui.isMouseHoveringRect(
                pos.x,
                pos.y,
                pos.x + width,
                pos.y + height
        ) && !item.isEmpty() && ((imGuiBTSFlags & ImGuiBTSFlags.RENDER_TOOLTIP) == 0)
        ) {
            ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);
            ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0f);
            ImGui.pushStyleVar(ImGuiStyleVar.PopupBorderSize, 0f);
            ImGui.pushStyleColor(ImGuiCol.Border, 1f, 1f, 1f, 1f);
            ImGui.pushStyleColor(ImGuiCol.PopupBg, 0f, 0f, 0f, 0f);

            /*
            tooltip(() -> {
                float borderSize = 5f;
                ImVec2 min = ImGui.getWindowPos();
                ImVec2 max = min.clone().plus(ImGui.getWindowSize());
                int top = ImGui.colorConvertFloat4ToU32(0.19215688f, 0.09607843f, 0.45882353f, 1f);
                int bottom = ImGui.colorConvertFloat4ToU32(0.13725491f, 0.07058824f, 0.23921569f, 1f);

                ImGui.getForegroundDrawList().addRectFilled(min.x, min.y, max.x, min.y + borderSize, top);
                ImGui.getForegroundDrawList()
                        .addRectFilledMultiColor(min.x, min.y, min.x + borderSize, max.y, top, top, bottom, bottom);
                ImGui.getForegroundDrawList().addRectFilled(min.x, max.y - borderSize, max.x, max.y, bottom);
                ImGui.getForegroundDrawList()
                        .addRectFilledMultiColor(max.x - borderSize, min.y, max.x, max.y, top, top, bottom, bottom);

                top = ImGui.colorConvertFloat4ToU32(0.06f, 0.06f, 0.06f, 0.75f);
                bottom = ImGui.colorConvertFloat4ToU32(0.12f, 0.12f, 0.12f, 0.4f);
                ImGui.getWindowDrawList().addRectFilledMultiColor(
                        min.x + borderSize, min.y + borderSize, max.x - borderSize, max.y - borderSize,
                        top, top, top, top
                );

                ImGui.dummy(0f, borderSize / 2);
                item.getTooltipLines(
                        Item.TooltipContext.of(player.level()), player, TooltipFlag.Default.NORMAL
                ).forEach((s) -> {
                    ImGui.setCursorPosX(ImGui.getCursorPosX() + borderSize * 2);
//                    text(s);
                });
                ImGui.dummy(borderSize, borderSize);
            });


             */
            ImGui.popStyleColor(2);
            ImGui.popStyleVar(3);
        }


        if ((imGuiBTSFlags & ImGuiBTSFlags.ALWAYS_ON_TOP) == 0) {
            ImGui.setCursorPos(cPos.x, cPos.y);
            ImGui.dummy(width, height);
        }
        ImGui.popID();

        return clicked;
    }

    /**
     * Позволяет вызывать методы из Minecraft Render
     * @param width Ширина контейнера
     * @param height Высота контейнера
     * @param imGuiBTSFlags Флаги рендера {@link ImGuiBTSFlags}
     * @param color Цвета рендера {@link RGBA}, работает так же как и {@link RenderSystem#setShaderColor(float r, float g, float b, float a)}
     * @param render BiConsumer который вызывается для рендера
     * @return Если контекст нажат
     */
    public static boolean drawMcRender(float width, float height, int imGuiBTSFlags, RGBA color, BiConsumer<ImVec2, Boolean> render) {

        RenderTarget mcBuffer = Minecraft.getInstance().getMainRenderTarget();
        mcBuffer.unbindWrite();

        TextureTarget buffer = ImGuiBuffers.getBuffer();
        buffer.bindWrite(true);

        ImVec2 cursorPos = ImGui.getCursorScreenPos();

        Window window = Minecraft.getInstance().getWindow();

        boolean isHovered = ImGui.isMouseHoveringRect(cursorPos.x, cursorPos.y, cursorPos.x + width, cursorPos.y + height);
        boolean isClicked = isHovered && ImGui.isMouseClicked(0);
        RenderSystem.backupProjectionMatrix();

        if((imGuiBTSFlags & ImGuiBTSFlags.USE_MINECRAFT_MATRIX) != 0) {
            RenderSystem.setProjectionMatrix(
                    new Matrix4f().setOrtho(
                            0.0F,
                            (float) ((double) window.getWidth() / window.getGuiScale()),
                            (float) ((double) window.getHeight() / window.getGuiScale()),
                            0.0F,
                            1000.0F,
                            3000.0F
                    ), VertexSorting.ORTHOGRAPHIC_Z
            );
        } else {
            RenderSystem.setProjectionMatrix(
                    new Matrix4f().setOrtho(
                            0.0F,
                            buffer.width,
                            buffer.height,
                            0.0F,
                            1000.0F,
                            3000.0F
                    ), VertexSorting.ORTHOGRAPHIC_Z
            );
        }

        Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
        matrix4fstack.pushMatrix();
        matrix4fstack.translate(0.0f, 0.0f, -2000.0f);
        RenderSystem.applyModelViewMatrix();

        if ((imGuiBTSFlags & ImGuiBTSFlags.ENABLE_SCISSOR) != 0) RenderSystem.enableScissor(
                (int) cursorPos.x, (int) (buffer.height - cursorPos.y - height),
                (int) width, (int) height
        );
        RenderSystem.enableDepthTest();

        render.accept(cursorPos, isHovered);

        if ((imGuiBTSFlags & ImGuiBTSFlags.ENABLE_SCISSOR) != 0) RenderSystem.disableScissor();
        RenderSystem.restoreProjectionMatrix();

        matrix4fstack.popMatrix();
        RenderSystem.applyModelViewMatrix();

        buffer.unbindWrite();
        mcBuffer.bindWrite(true);

        float u0 = cursorPos.x / buffer.width;
        float u1 = (cursorPos.x + width) / buffer.width;
        float v0 = 1f - cursorPos.y / buffer.height;
        float v1 = 1f - (cursorPos.y + height) / buffer.height;

        ImDrawList list = (imGuiBTSFlags & ImGuiBTSFlags.ALWAYS_ON_TOP) != 0 ? ImGui.getForegroundDrawList() : ImGui.getWindowDrawList();

        ImVec2 cursor = ImGui.getCursorScreenPos();

        pushStyle(List.of(
                new Pair<>(ImGuiCol.Button, ImGui.colorConvertFloat4ToU32(0f, 0f, 0f, 0f)),
                new Pair<>(ImGuiCol.ButtonActive, ImGui.colorConvertFloat4ToU32(0f, 0f, 0f, 0f)),
                new Pair<>(ImGuiCol.ButtonHovered, ImGui.colorConvertFloat4ToU32(0f, 0f, 0f, 0f))
        ),
            () -> {
                list.addImage(
                        buffer.getColorTextureId(), cursor.x, cursor.y,
                        cursor.x + width, cursor.y + height, u0, v0, u1, v1,
                        color.toInt()
                );
                if ((imGuiBTSFlags & ImGuiBTSFlags.BORDER) != 0) list.addRect(
                        cursor.x, cursor.y,
                        cursor.x + width, cursor.y + height, -1
                );
            }
        );

        return isClicked;
    }

    private static void tooltip(Runnable runnable) {
        ImGui.beginTooltip();
        runnable.run();
        ImGui.endTooltip();
    }

//    private static void text(Component text, float alpha, boolean shadow) {
//        TextColor color = text.getStyle().getColor();
//        if (color != null) {
//            int value = color.getValue();
//            int r = FastColor.ARGB32.red(value);
//            int g = FastColor.ARGB32.green(value);
//            int b = FastColor.ARGB32.blue(value);
//
////            pushColorStyle(ImGuiCol.Text, ImGui.colorConvertFloat4ToU32(r / 255f, g / 255f, b / 255f, alpha)) {
////                drawText(text, alpha, shadow)
////            }
//        } else {
//            drawText(text, alpha, shadow);
//        }
//    }
//
//    private static void drawText(Component text, float alpha, boolean shadow) {
//    }

    private static void pushStyle(Collection<Pair<Integer, Integer>> styles, Runnable runnable) {
        styles.forEach(style -> ImGui.pushStyleColor(style.getA(), style.getB()));
        runnable.run();
        ImGui.popStyleColor(styles.size());
    }


    public static double getGuiScale() {
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
//

//        double frame = ((WindowAccessor)window).getFramebufferWidth();
//        double guiScale = minecraft.getWindow().getGuiScale();
//        int i = (int)((double)minecraft.framebufferWidth / scaleFactor);
        return window.getGuiScale();
    }


    /**
     * Пока не придумал как заставить это работать
     */
    @Deprecated
    public static boolean drawOpenGL(float width, float height, int imGuiBTSFlags, RGBA color, BiConsumer<ImVec2, Boolean> render) {

        RenderTarget mcBuffer = Minecraft.getInstance().getMainRenderTarget();
        mcBuffer.unbindWrite();

        // Создаем и настраиваем FBO (Framebuffer Object)
        int framebuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

        // Создаем текстуру для буфера цвета
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, (int) width, (int) height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        // Привязываем текстуру к FBO
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);

        // Создаем буфер глубины
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, (int) width, (int) height);
        GL30.glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);

        // Проверяем, что FBO корректен
        if (GL30.glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Framebuffer is not complete!");
        }

        // Сохраняем текущий контекст OpenGL
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
        GL11.glViewport(0, 0, (int) width, (int) height);

        // Сохраняем состояние проекции и матрицы
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, width, height, 0.0, -1000.0, 3000.0);

        GL11.glMatrixMode(GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);

        // Включаем Scissor Test, если нужно
        if ((imGuiBTSFlags & ImGuiBTSFlags.ENABLE_SCISSOR) != 0) {
            glEnable(GL_SCISSOR_TEST);
            glScissor(0, 0, (int) width, (int) height);
        }

        // Выполняем пользовательский рендер
        ImVec2 cursorPos = ImGui.getCursorScreenPos();
        boolean isHovered = ImGui.isMouseHoveringRect(cursorPos.x, cursorPos.y, cursorPos.x + width, cursorPos.y + height);
        boolean isClicked = isHovered && ImGui.isMouseClicked(0);
        render.accept(cursorPos, isHovered);

        // Отключаем Scissor Test
        if ((imGuiBTSFlags & ImGuiBTSFlags.ENABLE_SCISSOR) != 0) {
            glDisable(GL_SCISSOR_TEST);
        }

        // Восстанавливаем матрицы
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL_MODELVIEW);
        GL11.glPopMatrix();

        // Восстанавливаем исходный буфер кадра
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, 0);

        // Рисуем результат FBO как текстуру
        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addImage(texture, cursorPos.x, cursorPos.y, cursorPos.x + width, cursorPos.y + height, 0, 0, 1, 1, color.toInt());

        // Удаляем FBO и связанные ресурсы
        glDeleteFramebuffers(framebuffer);
        glDeleteTextures(texture);
        glDeleteRenderbuffers(depthBuffer);

        return isClicked;
    }
}
