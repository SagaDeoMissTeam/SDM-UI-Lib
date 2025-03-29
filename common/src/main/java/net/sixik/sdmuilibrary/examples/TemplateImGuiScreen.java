package net.sixik.sdmuilibrary.examples;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.SDMUILibrary;
import net.sixik.sdmuilibrary.client.integration.imgui.ImGuiExtension;
import net.sixik.sdmuilibrary.client.integration.imgui.screen.AbstractImGuiScreen;

public class TemplateImGuiScreen extends AbstractImGuiScreen {

    private static final Minecraft minecraft = Minecraft.getInstance();

    public TemplateImGuiScreen() {
        super("test_screen");
        ImGui.setWindowFocus(null);
    }

    @Override
    protected void initImGuiScreen(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        setScreenSize(minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
        imGuiFlags |= ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.MenuBar;

        super.initImGuiScreen(imGuiFlags, guiGraphics, mouseX, mouseY, partialTick);
    }

    private static int selectedValue = 0;
    private static String[] v = new String[] {
            "Hello World", "Hello Aros"
    };

    @Override
    public void render(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGui.beginChild("ScrollingRegion", new ImVec2(0,0), true, ImGuiWindowFlags.HorizontalScrollbar);

        ImGuiExtension.toolBar("Test Tool Bar", () -> {
            ImGuiExtension.toolBarItem(() -> {
                ImGui.text("Element 1");
            });
            ImGuiExtension.toolBarItemLine(() -> {
                if(ImGui.button("OpenMenu")) {
                    System.out.println("Open WHAT ???");
                }
            });

            if(ImGui.button("SomeButton")) {
                SDMUILibrary.LOGGER.info("I clicked some button!");
            }
            ImGui.sameLine();
            ImGui.text("TestText");
            ImGui.sameLine();
            ImGui.setNextItemWidth(150);
            ImGuiExtension.comboBoxList("##Test Item", selectedValue, v, (comboBox -> {
                selectedValue = comboBox.index();
            }));

            ImGuiExtension.toolBarItemLine(() -> {
                if(ImGui.button("Send BTS Error")) {
                    System.out.println("Send BTS Error!");
                }
            });
        });

        ImGui.setNextItemWidth(-1);
        ImGuiExtension.collapseGroup("TestGroup", ImGuiTreeNodeFlags.DefaultOpen, () -> {
            drawOnGroup();
        });

        if (ImGui.collapsingHeader("Group 2", ImGuiTreeNodeFlags.DefaultOpen)) {
            ImGui.text("Element 3");
            ImGui.text("Element 4");
        }

        ImGui.endChild();
    }


    private void drawOnGroup () {

    }
}
