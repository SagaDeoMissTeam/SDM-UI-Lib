package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.client.utils.renders.TextureRenderHelper;
import org.joml.Matrix4f;

public class Texture extends RGBA{

    public ImageType imageType = ImageType.NORMAL;
    public int sliceSize = 10;

    public ResourceLocation textureID;
    public double tileSize;
    public float minU;
    public float minV;
    public float maxU;
    public float maxV;

    protected Texture(ResourceLocation textureID, ImageType imageType, int sliceSize) {
        this(textureID);
        this.imageType = imageType;
        this.sliceSize = sliceSize;
    }

    protected Texture(ResourceLocation textureID) {
        super(255,255,255, 255);
        this.textureID = textureID;
        this.minU = 0.0F;
        this.minV = 0.0F;
        this.maxU = 1.0F;
        this.maxV = 1.0F;
        this.tileSize = 0.0;
    }

    public static Texture create(ResourceLocation textureID){
        return new Texture(textureID);
    }

    @Override
    public Texture copy() {
        return new Texture(textureID, imageType, sliceSize).withColor(RGBA.create(r,g,b,a)).withUV(minU, minV, maxU, maxV);
    }

    public Texture setSliced(int sliceSize) {
        this.sliceSize = sliceSize;
        this.imageType = ImageType.SLICED;
        return this;
    }

    public Texture withColor(RGBA setColor) {
        this.r = setColor.r;
        this.g = setColor.g;
        this.b = setColor.b;
        this.a = setColor.a;
        return this;
    }

    public Texture withUV(float minU, float minV, float maxU, float maxV) {
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
        return this;
    }

    public void setupTexture() {
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        AbstractTexture tex = manager.getTexture(this.textureID);
        if (tex == null) {
            tex = new SimpleTexture(this.textureID);
            manager.getTexture(this.textureID, (AbstractTexture)tex);
        }

        RenderSystem.setShaderTexture(0, ((AbstractTexture)tex).getId());
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
        this.setupTexture();
        switch (imageType) {
            case NORMAL -> {
                if (tileSize <= 0) {
                    TextureRenderHelper.renderTextureRect(graphics, x, y, w, h, this, this.minU, this.minV, this.maxU, this.maxV);
                } else {
                    int r = this.r;
                    int g = this.g;
                    int b = this.b;
                    int a = this.a;
                    Matrix4f m = graphics.pose().last().pose();
                    Tesselator tesselator = Tesselator.getInstance();
                    RenderSystem.setShader(GameRenderer::getPositionColorShader);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                    BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX_COLOR);
                    buffer.addVertex(m, (float) x, (float) (y + h), 0.0F).setColor(r, g, b, a).setUv((float) ((double) x / this.tileSize), (float) ((double) (y + h) / this.tileSize));
                    buffer.addVertex(m, (float) (x + w), (float) (y + h), 0.0F).setColor(r, g, b, a).setUv((float) ((double) (x + w) / this.tileSize), (float) ((double) (y + h) / this.tileSize));
                    buffer.addVertex(m, (float) (x + w), (float) y, 0.0F).setColor(r, g, b, a).setUv((float) ((double) (x + w) / this.tileSize), (float) ((double) y / this.tileSize));
                    buffer.addVertex(m, (float) x, (float) y, 0.0F).setColor(r, g, b, a).setUv((float) ((double) x / this.tileSize), (float) ((double) y / this.tileSize));
                    BufferUploader.drawWithShader(buffer.buildOrThrow());

                    RenderSystem.disableBlend();
                }
            }
            case SLICED -> TextureRenderHelper.renderSlicedTextureRect(graphics, x,y,w,h, 10, (int) tileSize, (int) tileSize);
        }
    }

}
