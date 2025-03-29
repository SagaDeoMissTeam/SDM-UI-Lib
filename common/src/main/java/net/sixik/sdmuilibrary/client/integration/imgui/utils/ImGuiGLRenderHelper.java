package net.sixik.sdmuilibrary.client.integration.imgui.utils;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/**
 * Методы для интеграции Minecraft Render в контексте ImGui
 */
public class ImGuiGLRenderHelper {

    public static void renderItemStack(ItemStack itemStack, PoseStack stack, float x, float y, float width, float height, float scale, float rotation) {
        float xOffset = x + width / 2;
        float yOffset = y + height / 2;
        stack.translate(xOffset, yOffset, 0f);
        float newScale = Math.min(width, height) * 0.95f * scale;
        stack.scale(newScale, -newScale, newScale);
        stack.mulPose(new Quaternionf().rotateZ(rotation * Mth.DEG_TO_RAD));

        MultiBufferSource.BufferSource src = Minecraft.getInstance().renderBuffers().bufferSource();
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(itemStack, Minecraft.getInstance().level, null, 0);
        boolean flat = !model.usesBlockLight();

        if (flat) Lighting.setupForFlatItems();
        Minecraft.getInstance().getItemRenderer().render(
                itemStack, ItemDisplayContext.GUI, false, stack, src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model
        );

        src.endBatch();
        if (flat) Lighting.setupFor3DItems();
    }

    public static void renderItemDecorations(ItemStack stack, PoseStack poseStack, int x, int y, float width, float height) {
        if (stack.isBarVisible()) {
            float i = stack.getBarWidth() / 16f;
            int j = stack.getBarColor();
            float k = (x + width * 0.125f);
            float l = (y + height * 0.8125f);
            fill(
                    poseStack,
                    RenderType.guiOverlay(),
                    (int) k,
                    (int) l,
                    (int) (k + width * 0.8125f),
                    (int) (l + height * 0.125f),
                    0,
                    -16777216
            );
            fill(
                    poseStack, RenderType.guiOverlay(), (int) k, (int) l, (int) (k + i * width),
                    (int) (l + height * 0.0625f), 10, FastColor.ABGR32.opaque(j)
            );
        }



        float f = Minecraft.getInstance().player.getCooldowns().getCooldownPercent(
                stack.getItem(), Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true)
        );

        if (f > 0) {
            float k = y + width * (Mth.floor(16.0f * (1.0f - f)) / 16f);
            float l = k + height * Mth.ceil(16.0f * f) / 16f;
            fill(poseStack, RenderType.guiOverlay(), x, (int) k, (int) (x + width), (int) l, 0, Integer.MAX_VALUE);
        }
    }

    public static void fill(PoseStack stack, RenderType renderType, int minX, int minY, int maxX, int maxY, int z, int color) {

        int i;
        Matrix4f matrix4f = stack.last().pose();
        if (minX < maxX) {
            i = minX;
            minX = maxX;
            maxX = i;
        }
        if (minY < maxY) {
            i = minY;
            minY = maxY;
            maxY = i;
        }
        MultiBufferSource.BufferSource src = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = src.getBuffer(renderType);
        vertexConsumer.addVertex(matrix4f, minX, minY, z).setColor(color);
        vertexConsumer.addVertex(matrix4f, minX, maxY, z).setColor(color);
        vertexConsumer.addVertex(matrix4f, maxX, maxY, z).setColor(color);
        vertexConsumer.addVertex(matrix4f, maxX, minY, z).setColor(color);
        RenderSystem.disableDepthTest();
        src.endBatch();
        RenderSystem.enableDepthTest();
    }
}
