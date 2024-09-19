package net.sixik.sdmuilib.client.render.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;

public interface ISDMRender {

    @Environment(EnvType.CLIENT)
    void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick);
}
