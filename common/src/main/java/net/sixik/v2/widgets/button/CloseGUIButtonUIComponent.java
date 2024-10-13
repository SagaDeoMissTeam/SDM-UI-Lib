package net.sixik.v2.widgets.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.enums.MouseClick;
import net.sixik.v2.render.TextureRenderHelper;

public class CloseGUIButtonUIComponent extends ButtonUIComponent {

    public CloseGUIButtonUIComponent() {
        super(Component.empty(), RGBA.create(0,0,0,0));
        setSize(16,16);
    }

    @Override
    public void onButtonClicked(double mouseX, double mouseY, MouseClick click) {
        if(click.isLeft()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.closeContainer();
                if (mc.screen == null) {
                    mc.setWindowActive(true);
                }
            }
        }
    }


    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(0,0,0,255/3).drawRoundFill(graphics,x,y,w,h,2);
        TextureRenderHelper.renderTexture(graphics, "sdm_ui_library:textures/ui/cross_icons.png",
                x + 2,y + 2,
                w - 4, h - 4, 128,0,64,64);
    }
}
