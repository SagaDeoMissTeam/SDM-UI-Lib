package net.sixik.v2.render;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.sixik.v2.color.RGB;
import net.sixik.v2.utils.math.Vector2;

public class TextureRenderHelper {

    public static void renderTexture(GuiGraphics guiGraphics, String texture, Vector2 pos, Vector2 size, Vector2 uv, Vector2 textureSize){
        RenderHelper.renderTexture(guiGraphics, texture, pos.x,pos.y,size.x,size.y, uv.x, uv.y, textureSize.x, textureSize.y);
    }

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH){
        RenderHelper.renderTexture(guiGraphics, texture, x,y,width,height, textureX, textureY, textureW, textureH);
    }

    public static void renderTextureRect(GuiGraphics graphics, int x, int y, int w, int h, RGB col, float u0, float v0, float u1, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        RenderHelper.addRectToBufferWithUV(graphics, buffer, x, y, w, h, col, u0, v0, u1, v1);
        BufferUploader.drawWithShader(buffer.end());
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
     * Retrieves the size of a texture from the Minecraft texture manager.
     *
     * @param texture The resource location of the texture.
     * @return A Vector2 object representing the width and height of the texture.
     *         If the texture is not found or the size cannot be determined, returns a Vector2 with both dimensions set to 0.
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
}
