package net.sixik.sdmuilibrary.client.utils.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;

/**
 * Represents a texture object that can be rendered using the ISDMRender interface.
 * This class provides methods to create texture objects with different configurations.
 */
public class Texture implements ISDMRender {

    /**
     * The resource location of the texture.
     */
    public ResourceLocation texture;

    /**
     * The size of the texture when rendering. If null, the texture will be rendered using the provided width and height.
     */
    public Vector2 size = null;

    /**
     * The UV coordinates of the texture.
     */
    public Vector2 uv;

    /**
     * The size of the texture in the texture atlas.
     */
    public Vector2 textureSize;

    /**
     * Constructor for creating a texture object with a specific size.
     *
     * @param texture The resource location of the texture.
     * @param size The size of the texture when rendering.
     * @param UV The UV coordinates of the texture.
     * @param textureSize The size of the texture in the texture atlas.
     */
    protected Texture(ResourceLocation texture, Vector2 size, Vector2 UV, Vector2 textureSize){
        this.texture = texture;
        this.size = size;
        this.uv = UV;
        this.textureSize = textureSize;
    }

    /**
     * Constructor for creating a texture object with default size.
     *
     * @param texture The resource location of the texture.
     * @param UV The UV coordinates of the texture.
     * @param textureSize The size of the texture in the texture atlas.
     */
    protected Texture(ResourceLocation texture, Vector2 UV, Vector2 textureSize){
        this.texture = texture;
        this.uv = UV;
        this.textureSize = textureSize;
    }

    /**
     * Static method to create a texture object with a specific size.
     *
     * @param texture The resource location of the texture.
     * @param size The size of the texture when rendering.
     * @param uv The UV coordinates of the texture.
     * @param textureSize The size of the texture in the texture atlas.
     * @return A new Texture object with the specified parameters.
     */
    public static Texture create(ResourceLocation texture, Vector2 size, Vector2 uv, Vector2 textureSize){
        return new Texture(texture, size, uv, textureSize);
    }

    /**
     * Static method to create a texture object with default size.
     *
     * @param texture The resource location of the texture.
     * @param uv The UV coordinates of the texture.
     * @param textureSize The size of the texture in the texture atlas.
     * @return A new Texture object with the specified parameters.
     */
    public static Texture create(ResourceLocation texture, Vector2 uv, Vector2 textureSize){
        return new Texture(texture, uv, textureSize);
    }

    /**
     * Renders the texture using the provided graphics context.
     *
     * @param graphics The graphics context to render the texture.
     * @param x The x-coordinate of the top-left corner of the texture.
     * @param y The y-coordinate of the top-left corner of the texture.
     * @param width The width of the texture when rendering.
     * @param height The height of the texture when rendering.
     * @param tick The current tick count.
     */
    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if(size == null)
            RenderHelper.renderTexture(graphics, this.texture.toString(), x,y,width,height, uv.x, uv.y, textureSize.x, textureSize.y);
        else
            RenderHelper.renderTexture(graphics, this.texture.toString(), x,y,size.x,size.y, uv.x, uv.y, textureSize.x, textureSize.y);
    }
}
