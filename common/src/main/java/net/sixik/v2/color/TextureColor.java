package net.sixik.v2.color;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.sixik.v2.render.TextureRenderHelper;
import org.joml.Matrix4f;

public class TextureColor extends RGBA{

    public ResourceLocation textureID;
    public double tileSize;
    public float minU;
    public float minV;
    public float maxU;
    public float maxV;

    protected TextureColor(ResourceLocation textureID) {
        super(255,255,255, 255);
        this.textureID = textureID;
        this.minU = 0.0F;
        this.minV = 0.0F;
        this.maxU = 1.0F;
        this.maxV = 1.0F;
        this.tileSize = 0.0;
    }

    public static TextureColor create(ResourceLocation textureID){
        return new TextureColor(textureID);
    }

    @Override
    public TextureColor copy() {
        return new TextureColor(textureID).withColor(RGBA.create(r,g,b,a)).withUV(minU, minV, maxU, maxV);
    }

    public TextureColor withColor(RGBA color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
        return this;
    }

    public TextureColor withUV(float minU, float minV, float maxU, float maxV) {
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
            manager.register(this.textureID, (AbstractTexture)tex);
        }

        RenderSystem.setShaderTexture(0, ((AbstractTexture)tex).getId());
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
        this.setupTexture();
        if(tileSize <= 0) {
            TextureRenderHelper.renderTextureRect(graphics, x, y, w, h, this, this.minU, this.minV, this.maxU, this.maxV);
        } else {
            int r = this.r;
            int g = this.g;
            int b = this.b;
            int a = this.a;
            Matrix4f m = graphics.pose().last().pose();
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
            buffer.vertex(m, (float)x, (float)(y + h), 0.0F).color(r, g, b, a).uv((float)((double)x / this.tileSize), (float)((double)(y + h) / this.tileSize)).endVertex();
            buffer.vertex(m, (float)(x + w), (float)(y + h), 0.0F).color(r, g, b, a).uv((float)((double)(x + w) / this.tileSize), (float)((double)(y + h) / this.tileSize)).endVertex();
            buffer.vertex(m, (float)(x + w), (float)y, 0.0F).color(r, g, b, a).uv((float)((double)(x + w) / this.tileSize), (float)((double)y / this.tileSize)).endVertex();
            buffer.vertex(m, (float)x, (float)y, 0.0F).color(r, g, b, a).uv((float)((double)x / this.tileSize), (float)((double)y / this.tileSize)).endVertex();
            tesselator.end();
        }
    }

    @Override
    public void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h) {

    }

    @Override
    public void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius) {

    }

    @Override
    public void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments) {

    }

    @Override
    public void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth) {

    }
}
