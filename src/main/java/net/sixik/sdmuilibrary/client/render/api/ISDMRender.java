package net.sixik.sdmuilibrary.client.render.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISDMRender {

    @OnlyIn(Dist.CLIENT)
    void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick);
}
