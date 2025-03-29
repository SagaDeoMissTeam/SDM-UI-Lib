package net.sixik.sdmuilibrary.client.integration.imgui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiBuffers;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiHandler;
import org.jetbrains.annotations.NotNull;

public class ImGuiScreenWrapper extends Screen {

    private final IRenderable renderable;

    public ImGuiScreenWrapper(IRenderable renderable) {
        super(Component.empty());
        this.renderable = renderable;
    }

    @Override
    public final void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGuiBuffers.setBuffer(ImGuiBuffers.Type.SCREEN);
        ImGuiHandler.INSTANCE.drawFrame(0, renderable, guiGraphics, mouseX, mouseY, partialTick);
    }
}
