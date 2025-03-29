package net.sixik.sdmuilibrary.client.integration.imgui.modules.editor;

import com.mojang.blaze3d.systems.RenderSystem;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Vec3i;
import net.sixik.sdmuilibrary.client.integration.imgui.IRenderable;
import net.sixik.sdmuilibrary.client.integration.imgui.extern.BTSImGui;
import org.joml.Matrix4f;

public class BTSEngineEditor implements IRenderable {

    protected float mainX;
    protected float mainY;
    protected float mainWidth;
    protected float mainHeight;

    protected static boolean additionalMenu = false;
    protected static boolean additionalMenu2 = false;
    protected static boolean configEditorOpen = false;
    protected static Matrix4f modelViewMatrix = RenderSystem.getModelViewMatrix();
    protected static Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();


    private ImVec2 getScreenSize() {
        return new ImVec2(
                Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight()
        );
    }

    @Override
    public void renderImGui(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);
        ImGui.setNextWindowSize(getScreenSize(), ImGuiCond.Always);
        ImGui.begin("Main Panel",
                ImGuiWindowFlags.NoResize |  ImGuiWindowFlags.MenuBar |
                ImGuiWindowFlags.NoBringToFrontOnFocus |    ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoMove |                   ImGuiWindowFlags.NoDocking
        );

        updateMainScreenFields();

        renderMainToolBar();

        int dockspaceID = ImGui.getID("MainDockSpace");
        ImGui.dockSpace(dockspaceID);

        ImGui.end();



        if(additionalMenu) {
            ImGui.setNextWindowSize(200, 150, ImGuiCond.Once);
            ImBoolean imBoolean = new ImBoolean(additionalMenu);
            if(ImGui.begin("Inspector", imBoolean)) {
                additionalMenu = imBoolean.get();
            }

            checkIsWindowOutside();

            BTSEnginePropertiesEditors.objectTransformEditor("TestEdit", new BTSEnginePropertiesEditors.ObjectTransformWithRotation<>(new Vec3i(0,0,0), 20, 20), s -> {}, imBoolean);


            if(ImGui.collapsingHeader("Model Matrix")) {
                BTSImGui.beginEditElementMatrix4f("Model", modelViewMatrix, (s) -> {
                });
            }
            if(ImGui.collapsingHeader("Projection Matrix")) {
                BTSImGui.beginEditElementMatrix4f("Projection", projectionMatrix, (s) -> {
                });
            }

            ImGui.end();
        }



        if(additionalMenu2) {
            ImGui.setNextWindowSize(200, 150, ImGuiCond.Once);
            ImBoolean imBoolean = new ImBoolean(additionalMenu2);
            if(ImGui.begin("Child Panel 2", imBoolean)) {
                additionalMenu2 = imBoolean.get();
            }
            checkIsWindowOutside();
            ImGui.text("Another movable panel!");
            ImGui.end();
        }


    }

    protected void renderConfigEditor() {
        if(!configEditorOpen) return;

        ImGui.setNextWindowSize(200, 150, ImGuiCond.Once);

        ImBoolean imBoolean = new ImBoolean(configEditorOpen);
        if(ImGui.begin("Config Editor", imBoolean))
            configEditorOpen = imBoolean.get();

        checkIsWindowOutside();

        ImGui.end();
    }

    protected void renderMainToolBar() {
        ImGui.pushID("MainToolBar");
        if (ImGui.beginMenuBar()) {
            if (ImGui.beginMenu("File", false)) {
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("View")) {
                ImGui.separatorText("Windows");

                if(ImGui.menuItem("AdditionalMenu " + (additionalMenu ? "✔ " : ""), additionalMenu)) {
                    additionalMenu =!additionalMenu;
                }
                if(ImGui.menuItem("AdditionalMenu2" + (additionalMenu2 ? "✔ " : ""), additionalMenu2)) {
                    additionalMenu2 =!additionalMenu2;
                }
                if(ImGui.menuItem("Config Editor" + (configEditorOpen ? "✔ " : ""), configEditorOpen)) {
                    configEditorOpen =!configEditorOpen;
                }
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
        }
        ImGui.popID();
    }

    protected void updateMainScreenFields() {
        mainX = ImGui.getWindowPosX();
        mainY = ImGui.getWindowPosY();
        mainWidth = ImGui.getWindowWidth();
        mainHeight = ImGui.getWindowHeight();
    }

    protected void checkIsWindowOutside() {
        float childX = ImGui.getWindowPosX();
        float childY = ImGui.getWindowPosY();
        float childWidth = ImGui.getWindowWidth();
        float childHeight = ImGui.getWindowHeight();

        ///////////////////////////////////////////////////
        // НЕ ДАЁМ ОКНУ ВЫХОДИТЬ ЗА ГЛАВНОЕ ОКНО
        ///////////////////////////////////////////////////
        childX = Math.max(mainX, Math.min(childX, mainX + mainWidth - childWidth));
        childY = Math.max(mainY, Math.min(childY, mainY + mainHeight - childHeight));

        ImGui.setWindowPos(childX, childY);

        ImGui.setWindowSize(Math.clamp(childWidth, 1, mainWidth), Math.clamp(childHeight, 1, mainHeight));
    }

}
