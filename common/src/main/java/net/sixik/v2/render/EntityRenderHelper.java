package net.sixik.v2.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.sixik.v2.utils.math.Vector2d;

public class EntityRenderHelper {

    public static void drawLivingEntity(PoseStack guiGraphics, int x, int y, double scale, double yaw, LivingEntity livingEntity) {
        drawLivingEntity(guiGraphics,x,y,scale,yaw, 180, livingEntity);
    }

    public static void drawLivingEntity(PoseStack guiGraphics, int x, int y, double scale, double yaw, float pitch, LivingEntity livingEntity) {
        if (livingEntity.level == null) return;
        PoseStack poseStack = guiGraphics;
        poseStack.pushPose();
        poseStack.translate((float) x, (float) y, 50f);
        poseStack.scale((float) scale, (float) scale, (float) scale);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(pitch));
        // Rotate entity
        poseStack.mulPose(Vector3f.XP.rotationDegrees(((float) Math.atan((-40 / 40.0F))) * 10.0F));

        livingEntity.yBodyRot = (float) -(yaw / 40.F) * 20.0F;
        livingEntity.setYRot((float) -(yaw / 40.F) * 20.0F);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();


        poseStack.translate(0.0F, livingEntity.getMyRidingOffset(), 0.0F);
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.overrideCameraOrientation(new Quaternion(0.0F, 0.0F, 0.0F, 1.0F));
        entityRenderDispatcher.setRenderShadow(false);
        final MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, bufferSource, 15728880);
        });

        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        poseStack.popPose();
    }

    public static Vector2d getEntitySize(LivingEntity livingEntity, double scale) {
        AABB d = livingEntity.getBoundingBox();
        return new Vector2d(d.maxX * scale, d.minY * scale);
    }
}
