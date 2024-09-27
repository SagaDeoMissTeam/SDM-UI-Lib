package net.sixik.sdmuilibrary.neoforge;

import net.sixik.sdmuilibrary.SDMUILibrary;
import net.neoforged.fml.common.Mod;

@Mod(SDMUILibrary.MOD_ID)
public final class SDMUILibraryNeoForge {
    public SDMUILibraryNeoForge() {
        // Run our common setup.
        SDMUILibrary.init();
    }
}
