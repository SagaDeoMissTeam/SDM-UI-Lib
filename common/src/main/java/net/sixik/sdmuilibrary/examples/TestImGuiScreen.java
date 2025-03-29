package net.sixik.sdmuilibrary.examples;

import imgui.ImGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Items;
import net.sixik.sdmuilibrary.client.integration.imgui.IRenderable;
import net.sixik.sdmuilibrary.client.integration.imgui.ImGuiExtension;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;

/**
 * Чтобы открыть экран вызовите {@link IRenderable#openScreenImGui()}
 */
public class TestImGuiScreen implements IRenderable {

    @Override
    public void renderImGui(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RGBA.create(0,255,0, 255).draw(guiGraphics,  100, 100, 100, 100);

        ImGui.begin("HelloTest");
        ImGuiExtension.drawItem("TestItem", Items.DIAMOND.getDefaultInstance(), 128,128);
        ImGui.pushID("TestRender");

        ImGuiExtension.drawMcRender(256,256, 0, RGBA.DEFAULT, (
            (pos, clicked) -> {
                guiGraphics.drawString(Minecraft.getInstance().font, "Hello World", (int) pos.x, (int) pos.y, 100);
                RGBA.create(255, 0, 0, 255).drawRoundFill(guiGraphics, (int) pos.x, (int) (pos.y + 40), 20, 20, 6);
            }
        ));
        ImGui.popID();

        ImGui.end();

    }
}
