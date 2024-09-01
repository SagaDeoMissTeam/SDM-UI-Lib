package net.sixik.sdmuilibrary.client.integration;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;

public class FTBLibraryCompact {


    public static RGBA createRGBA(Color4I color){
        return RGBA.create(color.redi(), color.greeni(), color.bluei(), color.alphai());
    }
}
