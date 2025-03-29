package net.sixik.sdmuilibrary.client.integration.imgui.screen;

import imgui.ImGui;
import imgui.ImVec2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.sixik.sdmuilibrary.client.integration.imgui.extern.BTSImGui;
import net.sixik.sdmuilibrary.client.integration.imgui.extern.HelloImGui;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.Random;

public class DebugScreen extends AbstractImGuiScreen{



    public DebugScreen() {
        super("debug_screen");

        nbt.putString("SomeID", "Hello World");
        nbt.putString("SomeID2", "Hello ArosOS");
        nbt.putInt("Count", 20);

        ListTag listTag = new ListTag();
        listTag.add(StringTag.valueOf("Item 1"));
        listTag.add(StringTag.valueOf("Item 2"));
        listTag.add(StringTag.valueOf("Item 2"));
        listTag.add(StringTag.valueOf("Item 3"));
        nbt.put("List", listTag);
    }

    private float value = 0;

    private boolean back = false;

    private float sizeFont = 1;

    private static int testValue = 5;
    private static Matrix4f test = new Matrix4f().identity();
    private static CompoundTag nbt = new CompoundTag();

    @Override
    protected void initImGuiScreen(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGui.getStyle().setItemSpacing(new ImVec2(6,4));
        super.initImGuiScreen(imGuiFlags, guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void render(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGui.text("Test Text");

        ImGui.progressBar(value, HelloImGui.emToVec2(7.0f, 1.0f));

        if(ImGui.button("Random Font Size")) {
//            sizeFont = new Random().nextFloat(1, 6);
            ImGui.getIO().setFontGlobalScale(new Random().nextFloat(0.5F, 4));
//            ImGuiHandler.FONT.setScale(sizeFont);
//            ImGui.getFont().setFontSize(sizeFont);
        }


        Quaternionf quaternionf;

        BTSImGui.beginEditElementMatrix4f("Render", test , s -> test = s);
        BTSImGui.beginEditElementTag("Tag Editor", nbt, s -> nbt = s);

        if (back) {
            value -= 0.01f;
            if (value <= 0) {
                back = false;
                value = 0;  // чтобы не уйти в отрицательные значения
            }
        } else {
            value += 0.01f;
            if (value >= 1) {
                back = true;
                value = 1;  // чтобы не превысить 1
            }
        }

    }
}
