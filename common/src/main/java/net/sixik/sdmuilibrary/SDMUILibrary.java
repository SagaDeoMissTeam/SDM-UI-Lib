package net.sixik.sdmuilibrary;


import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.fabricmc.api.EnvType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiHandler;
import net.sixik.sdmuilibrary.client.screen.TestScreen;
import net.sixik.sdmuilibrary.examples.TemplateImGuiScreen;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;


public final class SDMUILibrary {
    public static final String MOD_ID = "sdm_ui_library";
    public static final Logger LOGGER = LogUtils.getLogger();


    public static final String SDMSHOP_CATEGORY = "key.category.sdmshopr";
    public static final String KEY_NAME = "key.sdm.debug";
    public static KeyMapping KEY_SHOP = new KeyMapping(KEY_NAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, SDMSHOP_CATEGORY);

    public static void init() {

        if(Platform.getEnv() == EnvType.CLIENT) {
            RenderSystem.recordRenderCall(() -> ImGuiHandler.INSTANCE.onGlfwInit(Minecraft.getInstance().getWindow().getWindow()));
        }

        if(Platform.isDevelopmentEnvironment())
            developer();

    }

    public static void developer() {
        KeyMappingRegistry.register(KEY_SHOP);

        ClientTickEvent.CLIENT_PRE.register((instance -> {
            if (KEY_SHOP.consumeClick()) {
                new TemplateImGuiScreen().openScreenImGui();
            }
        }));
    }


}
