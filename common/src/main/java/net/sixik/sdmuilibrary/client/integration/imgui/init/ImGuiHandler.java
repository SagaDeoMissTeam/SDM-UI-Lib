package net.sixik.sdmuilibrary.client.integration.imgui.init;

import com.mojang.blaze3d.systems.RenderSystem;
import imgui.*;
import imgui.extension.imnodes.ImNodes;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.SDMUILibrary;
import net.sixik.sdmuilibrary.client.integration.imgui.IRenderable;
import org.lwjgl.glfw.GLFW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImGuiHandler {

    public static final ImGuiHandler INSTANCE = new ImGuiHandler();

    private final ImGuiImplGlfw imGuiImplGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiImplGl3 = new ImGuiImplGl3();
    public long windowHandle = 0;
    private final List<IRenderable> frames = new ArrayList<>();

    private boolean isInitialized = false;
    private boolean callbackInput = false;

    protected ImGuiHandler() {}

    public void setCallback(boolean value) {
        if(!isInitialized) return;

        if(value && !callbackInput) {
            imGuiImplGlfw.installCallbacks(windowHandle);
            callbackInput = true;
        }
        else if(!value && callbackInput) {
            imGuiImplGlfw.restoreCallbacks(windowHandle);
            callbackInput = false;
        }
    }

    public void onGlfwInit(long handle){
        if(isInitialized) return;

        SDMUILibrary.LOGGER.info("Start Initialization ImGuiHandler...");
        initializeImGui(handle);

        SDMUILibrary.LOGGER.info("Initialization ImGui Glfw!");
        imGuiImplGlfw.init(handle, false);

        if (!Minecraft.ON_OSX) {
            imGuiImplGl3.init("#version 430");
        } else {
            imGuiImplGl3.init("#version 120");
        }


        SDMUILibrary.LOGGER.info("Create ImNodes Context!");
        ImNodes.createContext();

        ImGui.styleColorsDark();
        windowHandle = handle;
        SDMUILibrary.LOGGER.info("Initializing ImGui Complete! WindowHandler: {}", windowHandle);

        RenderSystem.recordRenderCall(() -> {
            drawFrame(0, new IRenderable() {
                @Override
                public void renderImGui(int imGuiFlags, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                    SDMUILibrary.LOGGER.info("ImGui successfully loaded!");
                }
            }, null, 0, 0, 0);
        });

        isInitialized =  true;
    }


    private void initializeImGui(long glHandle){
        ImGui.createContext();
        SDMUILibrary.LOGGER.info("ImGui context created!");
        var io = ImGui.getIO();
        io.setIniFilename(null);
        io.addBackendFlags( ImGuiBackendFlags.HasSetMousePos);
        io.setConfigViewportsNoTaskBarIcon(true);
        io.addConfigFlags(  ImGuiConfigFlags.NavEnableKeyboard);    // Enable Keyboard Controls
        io.addConfigFlags(  ImGuiConfigFlags.DockingEnable);        // Enable Docking
//        io.addConfigFlags(  ImGuiConfigFlags.ViewportsEnable);      // Enable MultiRender
        initFonts(io);
        SDMUILibrary.LOGGER.info("ImGui Fonts initialized!");

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            var style = ImGui.getStyle();
            style.setWindowRounding(0.0f);
            style.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(ImGuiCol.WindowBg, 1f));
        }

        SDMUILibrary.LOGGER.info("Main initialization complete!");
    }

    public static ImFont FONT;

    private void initFonts(ImGuiIO io){
//        io.getFonts().addFontDefault();

        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setOversampleH(4);
        fontConfig.setOversampleV(4);
        fontConfig.setPixelSnapH(true);
        //fontConfig.setMergeMode(true);

        final short[] glyphRanges = rangesBuilder.buildRanges();
//        io.getFonts().addFontDefault();
        FONT = io.getFonts().addFontFromMemoryTTF(readResourceBytes(ResourceLocation.tryBuild(SDMUILibrary.MOD_ID, "fonts/perfectdosvga437.ttf")), 18, fontConfig, glyphRanges);

        io.getFonts().build();
        fontConfig.destroy();

    }

    public void addFrame(IRenderable frame){
        frames.add(frame);
    }

    public void addFrame(IRenderable... frame){
        frames.addAll(List.of(frame));
    }

    public void addFrame(Collection<IRenderable> frame){
        frames.addAll(frame);
    }


    public void drawFrame(int imGuiFlags, IRenderable renderable, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ImGuiBuffers.getBuffer().clear(Minecraft.ON_OSX);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        GLFW.glfwSwapInterval(1);
        imGuiImplGl3.newFrame();
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
        ImGui.setNextWindowViewport(ImGui.getMainViewport().getID());
        renderable.renderImGui(imGuiFlags, guiGraphics, mouseX, mouseY, partialTick);
        endFrame();
//        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
//            ImGui.updatePlatformWindows();
//            ImGui.renderPlatformWindowsDefault();
//        }
    }

    public void drawFrames(int imGuiFlags, Collection<IRenderable> renderable, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(renderable.isEmpty()) return;

        ImGuiBuffers.getBuffer().clear(Minecraft.ON_OSX);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        imGuiImplGl3.newFrame();
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
        ImGui.setNextWindowViewport(ImGui.getMainViewport().getID());
        renderable.forEach(frame -> frame.renderImGui(imGuiFlags, guiGraphics, mouseX, mouseY, partialTick));
        endFrame();
    }

    private void endFrame(){
        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            var backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }




    public static byte[] readResourceBytes(ResourceLocation resourceLocation){
        try {
            var resource = Minecraft.getInstance().getResourceManager().getResource(resourceLocation);
            return resource.get().open().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static void loadFileFromAssets(String modid, String fileName) {
        ResourceLocation resourceLocation = ResourceLocation.tryBuild(modid, fileName);

        try {
            var resource = Minecraft.getInstance().getResourceManager().getResource(resourceLocation);

            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.get().open(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
