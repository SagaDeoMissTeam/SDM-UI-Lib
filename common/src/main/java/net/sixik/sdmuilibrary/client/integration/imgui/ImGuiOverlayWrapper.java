package net.sixik.sdmuilibrary.client.integration.imgui;

import com.mojang.blaze3d.pipeline.TextureTarget;
import imgui.flag.ImGuiWindowFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiBuffers;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiHandler;

import java.util.HashMap;
import java.util.Map;

public class ImGuiOverlayWrapper {

    private static Map<String, IRenderable> OVERLAY_ELEMENTS = new HashMap<>();


    public static void addOverlayElement(String id, IRenderable element) {
        final var window = Minecraft.getInstance().getWindow();
        final TextureTarget framebuffer = ImGuiBuffers.imguiOverlayBuffer;
        if(framebuffer.width != window.getWidth() || framebuffer.height != window.getHeight()) {
            framebuffer.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
        }
        OVERLAY_ELEMENTS.put(id, element);
    }

    public static void removeOverlayElement(String id) {
        OVERLAY_ELEMENTS.remove(id);
    }

    public static IRenderable getByID(String id) {
        return OVERLAY_ELEMENTS.get(id);
    }

    public static void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(OVERLAY_ELEMENTS.isEmpty()) return;
        guiGraphics.pose().translate(0,0, 200.0F);
        ImGuiBuffers.setBuffer(ImGuiBuffers.Type.OVERLAY);
        ImGuiHandler.INSTANCE.drawFrames(IMGUI_FLAG, OVERLAY_ELEMENTS.values(), guiGraphics, mouseX, mouseY, partialTick);
    }

    private static final int IMGUI_FLAG = ImGuiWindowFlags.NoMouseInputs | ImGuiWindowFlags.NoInputs;

}
