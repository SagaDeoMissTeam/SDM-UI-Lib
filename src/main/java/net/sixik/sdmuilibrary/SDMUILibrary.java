package net.sixik.sdmuilibrary;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SDMUILibrary.MODID)
public class SDMUILibrary {
    public static final String MODID = "sdm_ui_library";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SDMUILibrary(IEventBus modEventBus, ModContainer modContainer) {}

}
