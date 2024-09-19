package net.sixik.sdmuilib.fabric;

import net.sixik.sdmuilib.SDMUILib;
import net.fabricmc.api.ModInitializer;

public class SDMUILibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SDMUILib.init();
    }
}