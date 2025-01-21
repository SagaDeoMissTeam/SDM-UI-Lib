package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class AtlasTextureColor extends Texture{

    protected AtlasTextureColor(ResourceLocation textureID) {
        super(textureID);
    }

    public static AtlasTextureColor create(ResourceLocation textureID){
        return new AtlasTextureColor(textureID);
    }

    @Override
    public AtlasTextureColor copy() {
        return (AtlasTextureColor) new AtlasTextureColor(textureID).withColor(RGBA.create(r,g,b,a)).withUV(minU, minV, maxU, maxV);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(this.textureID);
        if (sprite != null) {
            Matrix4f m = graphics.pose().last().pose();
            int r = this.r;
            int g = this.g;
            int b = this.b;
            int a = this.a;
            float minU = sprite.getU0();
            float minV = sprite.getV0();
            float maxU = sprite.getU1();
            float maxV = sprite.getV1();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, sprite.atlasLocation());
            BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            buffer.addVertex(m, (float)x, (float)y, 0.0F).setColor(r, g, b, a).setUv(minU, minV);
            buffer.addVertex(m, (float)x, (float)(y + h), 0.0F).setColor(r, g, b, a).setUv(minU, maxV);
            buffer.addVertex(m, (float)(x + w), (float)(y + h), 0.0F).setColor(r, g, b, a).setUv(maxU, maxV);
            buffer.addVertex(m, (float)(x + w), (float)y, 0.0F).setColor(r, g, b, a).setUv(maxU, minV);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.disableBlend();
        }
    }
}
