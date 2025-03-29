package net.sixik.sdmuilibrary.client.integration.imgui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiBuffers;

import java.util.ArrayList;
import java.util.List;

public interface IRenderable {


    /**
     * Метод для рендеринга в контексте Minecraft Render. Используется для Виджетов
     */
    default void draw(GuiGraphics graphics, int x, int y, int width, int height) {

    }

    ////////////////////////////////
    //     Рендеринг в ImGui      //
    ////////////////////////////////

    /**
     * Метод для рендеринга в контексте ImGui и позволяет использовать Minecraft Render для отрисовки элементов {@link ImGuiExtension#drawMcRender}
     */
    default void renderImGui(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    /**
     * Метод откроет окно с рендером метода {@link IRenderable#renderImGui}
     */
    default void openScreenImGui() {
        if(!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall((() -> Minecraft.getInstance().setScreen(new ImGuiScreenWrapper(this))));
        } else {
            Minecraft.getInstance().setScreen(new ImGuiScreenWrapper(this));
        }
    }

    /**
     * Метод добавляет рендеринг ImGui в Overlay
     */
    default void addToOverlay(String id) {
        ImGuiOverlayWrapper.addOverlayElement(id, this);
    }

    /**
     * Метод удаляет рендеринг ImGui из Overlay
     */
    default void removeFromOverlay(String id) {
        ImGuiOverlayWrapper.removeOverlayElement(id);
    }

    default void renderOnScreen(Class<?> cls) {
        List<IRenderable> list = ImGuiBuffers.SCREEN_RENDERS.getOrDefault(cls, new ArrayList<>());
        list.add(this);
        ImGuiBuffers.SCREEN_RENDERS.put(cls, list);
    }

    default void closeOnScreen(Class<?> cls) {
        List<IRenderable> list = ImGuiBuffers.SCREEN_RENDERS.getOrDefault(cls, new ArrayList<>());
        list.remove(this);
        ImGuiBuffers.SCREEN_RENDERS.put(cls, list);
    }
}
