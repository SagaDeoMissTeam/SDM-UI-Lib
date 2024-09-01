package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ISDMRender {

    @OnlyIn(Dist.CLIENT)
    void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick);
}
