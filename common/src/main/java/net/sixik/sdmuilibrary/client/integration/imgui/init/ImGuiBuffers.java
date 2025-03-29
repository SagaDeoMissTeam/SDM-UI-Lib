package net.sixik.sdmuilibrary.client.integration.imgui.init;


import com.mojang.blaze3d.pipeline.TextureTarget;
import net.minecraft.client.Minecraft;
import net.sixik.sdmuilibrary.client.integration.imgui.IRenderable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImGuiBuffers {

    public static Map<Class<?>, List<IRenderable>> SCREEN_RENDERS = new HashMap<>();

    private static Type currentBuffer = Type.SCREEN;

    public static TextureTarget imguiScreenBuffer = new TextureTarget(512, 512, true, Minecraft.ON_OSX);
    public static TextureTarget imguiOverlayBuffer = new TextureTarget(512, 512, true, Minecraft.ON_OSX);
    public static TextureTarget imguiNoneBuffer = new TextureTarget(512, 512, true, Minecraft.ON_OSX);

    public static void setBuffer(Type type) {
        currentBuffer = type;
    }

    public static TextureTarget getBuffer() {
        if(currentBuffer == null) return imguiNoneBuffer;
        return currentBuffer == Type.SCREEN ? imguiScreenBuffer : imguiOverlayBuffer;
    }

    public enum Type {
        SCREEN,
        OVERLAY;
    }
}
