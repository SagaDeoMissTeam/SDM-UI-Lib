package net.sixik.sdmuilib.client.utils.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilib.client.utils.RenderHelper;
import net.sixik.sdmuilib.client.utils.math.Vector2;

public class AnimationTexture extends Texture{

    public Vector2 frameSize;
    public int animationSpeed = 1;
    private int tickAnimation = 0;
    public int tickFrame = 0;

    protected AnimationTexture(ResourceLocation texture, Vector2 UV, Vector2 frameSize, Vector2 textureSize) {
        super(texture, new Vector2(0,0), UV, textureSize);
        this.frameSize = frameSize;
    }

    public static AnimationTexture create(ResourceLocation texture, Vector2 UV, Vector2 frameSize, Vector2 textureSize){
        return new AnimationTexture(texture, UV, frameSize, textureSize);
    }

    Vector2 a = new Vector2(uv.x, uv.y);

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        RenderHelper.renderTexture(graphics, this.texture.toString(), x,y,frameSize.x,frameSize.y, a.x, a.y, frameSize.x,frameSize.y);

        updateAnimation();
    }

    public void updateAnimation() {

        if(tickAnimation % animationSpeed == 0){
            int x = a.x + frameSize.x;
            int y = a.y + frameSize.y;
            if(x > textureSize.x || y > textureSize.y) {
                a = new Vector2(uv.x, uv.y);
            }
            else
                a = new Vector2(x,y);


            tickFrame++;
        }

        tickAnimation++;
    }
}
