package net.sixik.sdmuilibrary.client.utils.renders;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.GameRenderer;
import net.sixik.sdmuilibrary.client.utils.buffers.RenderBuffer2D;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import org.joml.Matrix4f;

/**
 * Методы для рендера и работы с Текстурами
 */
public class TextureRenderHelper {

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH){
        renderTexture(guiGraphics, texture, x,y,width,height, textureX, textureY, textureW, textureH, 256);
    }

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH, int textureSize){
        guiGraphics.blit(ResourceLocation.parse(texture), x,y,width,height, textureX, textureY, textureW, textureH, textureSize, textureSize );
    }

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH, int textureSizeX, int textureSizeY){
        guiGraphics.blit(ResourceLocation.parse(texture), x,y,width,height, textureX, textureY, textureW, textureH, textureSizeX, textureSizeY );
    }

    public static void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height, int textureX, int textureY, int textureW){
        guiGraphics.blit(texture, x,y,width,height, textureX, textureY, textureW, 256, 256);
    }

    public static void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH, int textureSize){
        guiGraphics.blit(texture, x,y,width,height, textureX, textureY, textureW, textureH, textureSize);
    }

    public static void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH, int textureSizeX, int textureSizeY){
        guiGraphics.blit(texture, x,y,width,height, textureX, textureY, textureW, textureH, textureSizeX, textureSizeY );
    }

    public static void renderTextureRect(GuiGraphics graphics, int x, int y, int w, int h, RGB col, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX_COLOR);
        RenderSystem.enableBlend();
        RenderBuffer2D.addRectToBufferWithUV(graphics.pose().last().pose(), buffer, x, y, w, h, col, u0, v0, u1, v1);

        BufferUploader.drawWithShader(buffer.buildOrThrow());
        RenderSystem.disableBlend();
    }

    public static void renderSlicedTextureRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int sliceSize, int textureW, int textureH) {
        int rightX = x + width - sliceSize;
        int bottomY = y + height - sliceSize;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // Левый верхний угол
        blit(guiGraphics, x, y, sliceSize, sliceSize, 0, 0, sliceSize, sliceSize, textureW, textureH);
        // Правый верхний угол
        blit(guiGraphics, rightX, y, sliceSize, sliceSize, textureW - sliceSize, 0, sliceSize, sliceSize, textureW, textureH);
        // Левый нижний угол
        blit(guiGraphics, x, bottomY, sliceSize, sliceSize, 0, textureH - sliceSize, sliceSize, sliceSize, textureW, textureH);
        // Правый нижний угол
        blit(guiGraphics, rightX, bottomY, sliceSize, sliceSize, textureW - sliceSize, textureH - sliceSize, sliceSize, sliceSize, textureW, textureH);

        // Верхняя сторона
        blit(guiGraphics, x + sliceSize, y, width - sliceSize * 2, sliceSize, sliceSize, 0, textureW - sliceSize * 2, sliceSize, textureW, textureH);
        // Нижняя сторона
        blit(guiGraphics, x + sliceSize, bottomY, width - sliceSize * 2, sliceSize, sliceSize, textureH - sliceSize, textureW - sliceSize * 2, sliceSize, textureW, textureH);
        // Левая сторона
        blit(guiGraphics, x, y + sliceSize, sliceSize, height - sliceSize * 2, 0, sliceSize, sliceSize, textureH - sliceSize * 2, textureW, textureH);
        // Правая сторона
        blit(guiGraphics, rightX, y + sliceSize, sliceSize, height - sliceSize * 2, textureW - sliceSize, sliceSize, sliceSize, textureH - sliceSize * 2, textureW, textureH);

        // Центральная часть
        blit(guiGraphics, x + sliceSize, y + sliceSize, width - sliceSize * 2, height - sliceSize * 2, sliceSize, sliceSize, textureW - sliceSize * 2, textureH - sliceSize * 2, textureW, textureH);
        RenderSystem.disableBlend();
    }


    public static void renderSlicedTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height, int sliceSize, int textureW, int textureH) {
        int rightX = x + width - sliceSize;
        int bottomY = y + height - sliceSize;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // Левый верхний угол
        guiGraphics.blit(texture, x, y, sliceSize, sliceSize, 0, 0, sliceSize, sliceSize, textureW, textureH);
        // Правый верхний угол
        guiGraphics.blit(texture, rightX, y, sliceSize, sliceSize, textureW - sliceSize, 0, sliceSize, sliceSize, textureW, textureH);
        // Левый нижний угол
        guiGraphics.blit(texture, x, bottomY, sliceSize, sliceSize, 0, textureH - sliceSize, sliceSize, sliceSize, textureW, textureH);
        // Правый нижний угол
        guiGraphics.blit(texture, rightX, bottomY, sliceSize, sliceSize, textureW - sliceSize, textureH - sliceSize, sliceSize, sliceSize, textureW, textureH);

        // Верхняя сторона
        guiGraphics.blit(texture, x + sliceSize, y, width - sliceSize * 2, sliceSize, sliceSize, 0, textureW - sliceSize * 2, sliceSize, textureW, textureH);
        // Нижняя сторона
        guiGraphics.blit(texture, x + sliceSize, bottomY, width - sliceSize * 2, sliceSize, sliceSize, textureH - sliceSize, textureW - sliceSize * 2, sliceSize, textureW, textureH);
        // Левая сторона
        guiGraphics.blit(texture, x, y + sliceSize, sliceSize, height - sliceSize * 2, 0, sliceSize, sliceSize, textureH - sliceSize * 2, textureW, textureH);
        // Правая сторона
        guiGraphics.blit(texture, rightX, y + sliceSize, sliceSize, height - sliceSize * 2, textureW - sliceSize, sliceSize, sliceSize, textureH - sliceSize * 2, textureW, textureH);

        // Центральная часть
        guiGraphics.blit(texture, x + sliceSize, y + sliceSize, width - sliceSize * 2, height - sliceSize * 2, sliceSize, sliceSize, textureW - sliceSize * 2, textureH - sliceSize * 2, textureW, textureH);
        RenderSystem.disableBlend();
    }


    /**
     * @return x = width, y = height
     */
    public static Vector2 getTextureSize(ResourceLocation texture){
        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        AbstractTexture abstractTexture = textureManager.getTexture(texture);

        if (abstractTexture != null) {
            NativeImage nativeImage = (abstractTexture instanceof DynamicTexture) ? ((DynamicTexture) abstractTexture).getPixels() : null;

            if (nativeImage != null) {
                int textureWidth = nativeImage.getWidth();
                int textureHeight = nativeImage.getHeight();

                return new Vector2(textureWidth, textureHeight);
            }
        }

        return new Vector2(0,0);
    }


    public static void blit(GuiGraphics guiGraphics, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        blit(guiGraphics, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void blit(GuiGraphics guiGraphics, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        blit(guiGraphics, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    private static void blit(GuiGraphics guiGraphics, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(guiGraphics, x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
    }

    private static void innerBlit(GuiGraphics guiGraphics, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y1, (float)blitOffset).setUv(minU, minV);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y2, (float)blitOffset).setUv(minU, maxV);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, (float)blitOffset).setUv(maxU, maxV);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y1, (float)blitOffset).setUv(maxU, minV);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());

    }
}

