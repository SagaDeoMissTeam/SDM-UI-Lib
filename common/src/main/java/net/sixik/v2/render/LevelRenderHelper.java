package net.sixik.v2.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.sixik.v2.color.RGB;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.utils.math.BoundDoubleBox;
import net.sixik.v2.utils.math.QuadVector3D;

public class LevelRenderHelper {

    public static void renderPlaneColor(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, QuadVector3D posQuad, RGB color) {
        renderPlaneColor(poseStack,bufferSource.getBuffer(RenderType.lines()), Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), posQuad, color);
    }

    public static void renderPlaneColor(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, Vec3 pos, QuadVector3D posQuad, RGB color) {
        renderPlaneColor(poseStack,bufferSource.getBuffer(RenderType.lines()), pos, posQuad, color);
    }

    public static void renderPlaneColor(PoseStack poseStack, VertexConsumer vertexConsumer, QuadVector3D posQuad, RGB color) {
        renderPlaneColor(poseStack,vertexConsumer, Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), posQuad, color);
    }

    public static void renderPlaneColor(PoseStack poseStack, VertexConsumer vertexConsumer, Vec3 pos, QuadVector3D posQuad, RGB color) {
        Matrix4f matrix = poseStack.last().pose();
        poseStack.translate(posQuad.pos1.x - pos.x(), posQuad.pos1.y - pos.y(), posQuad.pos1.z - pos.z());
        int r = color.r;
        int g = color.g;
        int b = color.b;
        int a = 255;
        if(color instanceof RGBA rgba)
            a = rgba.a;

        vertexConsumer.vertex(matrix, (float) posQuad.pos1.x, (float) posQuad.pos1.y, (float) posQuad.pos1.z)
                .color(r,g,b,a)
                .endVertex();
        vertexConsumer.vertex(matrix, (float) posQuad.pos2.x, (float) posQuad.pos2.y, (float) posQuad.pos2.z)
                .color(r,g,b,a)
                .endVertex();
        vertexConsumer.vertex(matrix, (float) posQuad.pos3.x, (float) posQuad.pos3.y, (float) posQuad.pos3.z)
                .color(r,g,b,a)
                .endVertex();
        vertexConsumer.vertex(matrix, (float) posQuad.pos4.x, (float) posQuad.pos4.y, (float) posQuad.pos4.z)
                .color(r,g,b,a)
                .endVertex();
    }

    public static void renderLineBox(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, BoundDoubleBox zone, RGB color) {
        renderLineBox(poseStack,bufferSource.getBuffer(RenderType.lines()), zone, color);
    }

    public static void renderLineBox(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, Vec3 pos, BoundDoubleBox zone, RGB color) {
        renderLineBox(poseStack,bufferSource.getBuffer(RenderType.lines()),pos, zone, color);
    }

    public static void renderLineBox(PoseStack poseStack, VertexConsumer vertexConsumer, BoundDoubleBox zone, RGB color) {
        renderLineBox(poseStack,vertexConsumer, Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), zone, color);
    }

    public static void renderLineBox(PoseStack poseStack, VertexConsumer vertexConsumer, Vec3 pos, BoundDoubleBox zone, RGB color) {
        poseStack.pushPose();
        poseStack.translate(zone.x1 - pos.x(), zone.y1 - pos.y(), zone.z1 - pos.z());
        int r = color.r;
        int g = color.g;
        int b = color.b;
        int a = 255;
        if(color instanceof RGBA rgba)
            a = rgba.a;
        LevelRenderer.renderLineBox(poseStack, vertexConsumer, zone.x1,zone.y1,zone.z1,zone.x2, zone.y2, zone.z2, r,g,b,a);
        poseStack.popPose();
    }

    public static void renderLineBox(PoseStack poseStack, VertexConsumer vertexConsumer, BlockPos pos, BoundDoubleBox zone, RGB color) {
        poseStack.pushPose();
        poseStack.translate(zone.x1 - pos.getX(), zone.y1 - pos.getY(), zone.z1 - pos.getZ());
        int r = color.r;
        int g = color.g;
        int b = color.b;
        int a = 255;
        if(color instanceof RGBA rgba)
            a = rgba.a;
        LevelRenderer.renderLineBox(poseStack, vertexConsumer, zone.x1,zone.y1,zone.z1,zone.x2, zone.y2, zone.z2, r,g,b,a);
        poseStack.popPose();
    }
}
