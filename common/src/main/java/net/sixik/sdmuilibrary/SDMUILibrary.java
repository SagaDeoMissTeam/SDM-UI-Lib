package net.sixik.sdmuilibrary;


import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.sixik.sdmuilibrary.client.screen.TestScreen;
import org.lwjgl.glfw.GLFW;


public final class SDMUILibrary {
    public static final String MOD_ID = "sdm_ui_library";

    public static final String SDMSHOP_CATEGORY = "key.category.sdmshopr";
    public static final String KEY_NAME = "key.sdmshop.shopr";
    public static KeyMapping KEY_SHOP = new KeyMapping(KEY_NAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, SDMSHOP_CATEGORY);

    public static void init() {

        KeyMappingRegistry.register(KEY_SHOP);

        ClientTickEvent.CLIENT_PRE.register((instance -> {
            if (KEY_SHOP.consumeClick()) {
                Minecraft.getInstance().setScreen(new TestScreen());
            }
        }));

    }


}
