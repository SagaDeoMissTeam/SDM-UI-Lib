package net.sixik.v2.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class ItemRenderHelper {

    public static void drawItem(GuiGraphics graphics, ItemStack stack, int hash, boolean renderOverlay, @Nullable String text) {
        if (!stack.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            ItemRenderer itemRenderer = mc.getItemRenderer();
            BakedModel bakedModel = itemRenderer.getModel(stack, (Level)null, mc.player, hash);
            Minecraft.getInstance().getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS).setFilter(false, false);
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            PoseStack modelViewStack = RenderSystem.getModelViewStack();
            modelViewStack.pushPose();
            modelViewStack.mulPoseMatrix(graphics.pose().last().pose());
            modelViewStack.scale(1.0F, -1.0F, 1.0F);
            modelViewStack.scale(16.0F, 16.0F, 16.0F);
            RenderSystem.applyModelViewMatrix();
            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
            boolean flatLight = !bakedModel.usesBlockLight();
            if (flatLight) {
                Lighting.setupForFlatItems();
            }

            itemRenderer.render(stack, ItemDisplayContext.GUI, false, new PoseStack(), bufferSource, 15728880, OverlayTexture.NO_OVERLAY, bakedModel);
            bufferSource.endBatch();
            RenderSystem.enableDepthTest();
            if (flatLight) {
                Lighting.setupFor3DItems();
            }

            modelViewStack.popPose();
            RenderSystem.applyModelViewMatrix();
            if (renderOverlay) {
                Tesselator t = Tesselator.getInstance();
                Font font = mc.font;
                if (stack.getCount() != 1 || text != null) {
                    String s = text == null ? String.valueOf(stack.getCount()) : text;
                    graphics.pose().pushPose();
                    graphics.pose().translate(9.0 - (double)font.width(s), 1.0, 20.0);
                    font.drawInBatch(s, 0.0F, 0.0F, 16777215, true, graphics.pose().last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                    bufferSource.endBatch();
                    graphics.pose().popPose();
                }

                if (stack.isBarVisible()) {
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableBlend();
                    int barWidth = stack.getBarWidth();
                    int barColor = stack.getBarColor();

                    draw(graphics, t, -6, 5, 13, 2, 0, 0, 0, 255);
                    draw(graphics, t, -6, 5, barWidth, 1, barColor >> 16 & 255, barColor >> 8 & 255, barColor & 255, 255);
                    RenderSystem.enableBlend();
                    RenderSystem.enableDepthTest();
                }

                float cooldown = mc.player == null ? 0.0F : mc.player.getCooldowns().getCooldownPercent(stack.getItem(), mc.getFrameTime());
                if (cooldown > 0.0F) {
                    RenderSystem.disableDepthTest();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    draw(graphics, t, -8, Mth.floor(16.0F * (1.0F - cooldown)) - 8, 16, Mth.ceil(16.0F * cooldown), 255, 255, 255, 127);
                    RenderSystem.enableDepthTest();
                }
            }

        }
    }

    private static void draw(GuiGraphics graphics, Tesselator t, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        if (width > 0 && height > 0) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Matrix4f m = graphics.pose().last().pose();
            BufferBuilder renderer = t.getBuilder();
            renderer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            renderer.vertex(m, (float)x, (float)y, 0.0F).color(red, green, blue, alpha).endVertex();
            renderer.vertex(m, (float)x, (float)(y + height), 0.0F).color(red, green, blue, alpha).endVertex();
            renderer.vertex(m, (float)(x + width), (float)(y + height), 0.0F).color(red, green, blue, alpha).endVertex();
            renderer.vertex(m, (float)(x + width), (float)y, 0.0F).color(red, green, blue, alpha).endVertex();
            t.end();
        }
    }

}
