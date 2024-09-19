package net.sixik.sdmuilib.forge;

import dev.architectury.platform.forge.EventBuses;
import net.sixik.sdmuilib.SDMUILib;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SDMUILib.MOD_ID)
public class SDMUILibForge {
    public SDMUILibForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SDMUILib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SDMUILib.init();
    }
}