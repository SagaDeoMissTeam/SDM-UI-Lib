package net.sixik.sdmuilibrary.client.utils.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;

public class Texture implements ISDMRender {

    public ResourceLocation texture;
    public Vector2 size = null;
    public Vector2 uv;
    public Vector2 textureSize;

    protected Texture(ResourceLocation texture, Vector2 UV, Vector2 textureSize){
        this.texture = texture;
        this.uv = UV;
        this.textureSize = textureSize;
    }

    protected Texture(ResourceLocation texture, Vector2 size, Vector2 UV, Vector2 textureSize){
        this.texture = texture;
        this.size = size;
        this.uv = UV;
        this.textureSize = textureSize;
    }

    public static Texture create(ResourceLocation texture, Vector2 uv, Vector2 textureSize){
        return new Texture(texture, uv, textureSize);
    }

    public static Texture create(ResourceLocation texture, Vector2 size, Vector2 uv, Vector2 textureSize){
        return new Texture(texture, size, uv, textureSize);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if(size == null)
            RenderHelper.renderTexture(graphics, this.texture.toString(), x,y,width,height, uv.x, uv.y, textureSize.x, textureSize.y);
        else
            RenderHelper.renderTexture(graphics, this.texture.toString(), x,y,size.x,size.y, uv.x, uv.y, textureSize.x, textureSize.y);
    }
}
