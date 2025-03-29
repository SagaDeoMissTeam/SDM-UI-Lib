package net.sixik.sdmuilibrary.client.integration.imgui.screen;


import imgui.ImGui;
import imgui.extension.nodeditor.NodeEditor;
import imgui.extension.nodeditor.NodeEditorConfig;
import imgui.extension.nodeditor.NodeEditorContext;
import imgui.type.ImBoolean;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.integration.imgui.screen.nodes.ImNode;
import net.sixik.sdmuilibrary.client.integration.imgui.screen.nodes.NodesData;

public class ImGuiNodeEditorScreen<T extends ImNode> extends AbstractImGuiScreen {

    protected final NodeEditorConfig nodeEditorConfig;
    protected final NodeEditorContext nodeEditorContext;
    protected final NodesData<T> nodesData = new NodesData<>();

    public ImGuiNodeEditorScreen(String screenName) {
        this(screenName, true, createDefaultConfig());
    }

    public ImGuiNodeEditorScreen(String screenName, NodeEditorConfig nodeEditorConfig) {
        this(screenName, true, nodeEditorConfig);
    }

    public ImGuiNodeEditorScreen(String screenName, boolean showScreen, NodeEditorConfig nodeEditorConfig) {
        super(screenName, showScreen);
        this.nodeEditorConfig = nodeEditorConfig;
        this.nodeEditorContext = NodeEditor.createEditor(nodeEditorConfig);
    }

    public NodesData<T> getNodesData() {
        return nodesData;
    }

    /**
     * Создаёт стандартный конфиг файл без каких либо настроек
     */
    public static NodeEditorConfig createDefaultConfig() {
        NodeEditorConfig config = new NodeEditorConfig();
        config.setSettingsFile(null);
        return config;
    }

    @Override
    protected void initImGuiScreen(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGui.setNextWindowSize(screenSize.x, screenSize.y, imGuiCond);
        ImGui.setNextWindowPos(position.x, position.y, imGuiCond);
        if(ImGui.begin(screenName, new ImBoolean(showScreen), imGuiFlags)) {
            preRender(imGuiFlags, guiGraphics, mouseX, mouseY, partialTick);
            NodeEditor.setCurrentEditor(nodeEditorContext);
            NodeEditor.begin("Node Editor");
            render(imGuiFlags, guiGraphics, mouseX, mouseY, partialTick);
            NodeEditor.end();
        }
        ImGui.end();
    }


    public void preRender(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void render(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }


}
