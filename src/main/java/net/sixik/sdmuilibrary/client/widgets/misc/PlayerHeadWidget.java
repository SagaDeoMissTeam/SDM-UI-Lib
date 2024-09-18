package net.sixik.sdmuilibrary.client.widgets.misc;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;

import java.util.Map;

public class PlayerHeadWidget extends SDMWidget {

    public ResourceLocation playerSkin;

    public PlayerHeadWidget(Vector2 size) {
        super(null, size);
        getSkin();
    }

    public PlayerHeadWidget(Vector2 position, Vector2 size) {
        super(position, size);
        getSkin();
    }

    public void getSkin(){
        for (Map.Entry<MinecraftProfileTexture.Type, MinecraftProfileTexture> d1 : Minecraft.getInstance().getSkinManager().getInsecureSkinInformation(Minecraft.getInstance().player.getGameProfile()).entrySet()) {
            if(d1.getKey() == MinecraftProfileTexture.Type.SKIN){
                playerSkin = Minecraft.getInstance().getSkinManager().registerTexture(d1.getValue(),d1.getKey());
                return;
            }
        }

        playerSkin = DefaultPlayerSkin.getDefaultSkin(Minecraft.getInstance().player.getGameProfile().getId());
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        graphics.blit(playerSkin, x,y,width,height, 8.0f, 8, 8, 8, 64, 64);
    }
}
