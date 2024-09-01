package net.sixik.sdmuilibrary.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;
import org.joml.*;

import java.lang.Math;

public class RenderHelper {


    public static void prepareTextureRendering(ResourceLocation textureLocation) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, textureLocation);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }

    public static void drawText(GuiGraphics poseStack, int x, int y, float size, Component text, int textColor){
        drawText(poseStack, Minecraft.getInstance().font,x,y,size,text,textColor);
    }
    public static void drawText(GuiGraphics poseStack, Font font, int x, int y, float size, Component text, int textColor){
        poseStack.pose().pushPose();
        poseStack.pose().scale(size, size, 1.0f);
        poseStack.pose().translate(x, y, 0);
        poseStack.drawString(font,text, (int) x, (int) y, textColor);
        poseStack.pose().popPose();
    }

    public static void drawText(GuiGraphics graphics, Component text, int x, int y){
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x,y, RGB.create(255,255,255).toInt());
    }


    public static void addFillToBuffer(GuiGraphics graphics, BufferBuilder buffer, int x, int y, int w, int h, RGB rgb){
        if (w > 0 && h > 0) {
            Matrix4f m = graphics.pose().last().pose();
            int r = rgb.r;
            int g = rgb.g;
            int b = rgb.b;
            int a = 255;
            if(rgb instanceof RGBA rgba)
                a = rgba.a;

            buffer.vertex(m, (float)x, (float)(y + h), 0.0F).color(r, g, b, a).endVertex();
            buffer.vertex(m, (float)(x + w), (float)(y + h), 0.0F).color(r, g, b, a).endVertex();
            buffer.vertex(m, (float)(x + w), (float)y, 0.0F).color(r, g, b, a).endVertex();
            buffer.vertex(m, (float)x, (float)y, 0.0F).color(r, g, b, a).endVertex();
        }
    }

    public static void addFillToBufferGradient(GuiGraphics graphics, BufferBuilder buffer, int x, int y, int w, int h, RGB startRgb, RGB endRgb){
        if (w > 0 && h > 0) {
            RGBA s = startRgb.toARGB();
            RGBA e = endRgb.toARGB();

            Matrix4f m = graphics.pose().last().pose();
            buffer.vertex(m, (float)x, (float)(y + h), 0.0F).color(s.r, s.g, s.b, s.a).endVertex();
            buffer.vertex(m, (float)(x + w), (float)(y + h), 0.0F).color(s.r, s.g, s.b, s.a).endVertex();
            buffer.vertex(m, (float)(x + w), (float)y, 0.0F).color(e.r,e.g,e.b,e.a).endVertex();
            buffer.vertex(m, (float)x, (float)y, 0.0F).color(e.r,e.g,e.b,e.a).endVertex();
        }
    }

    public static void drawLine(GuiGraphics graphics, float x1, float y1, float x2, float y2, float lineWidth, RGB rgb) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        Matrix4f m = graphics.pose().last().pose();
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba)
            a = rgba.a;

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.lineWidth(lineWidth);

        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);


        bufferBuilder.vertex(m, x1, y1, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(m, x2, y2, 0).color(r, g, b, a).endVertex();

        tesselator.end();

        RenderSystem.lineWidth(1f);
    }

    public static void drawArc(GuiGraphics graphics, int cX, int cY, int radius, int start, int end, RGB rgb){
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f m = graphics.pose().last().pose();

        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba)
            a = rgba.a;



        for (int i = start - 90; i <= end - 90; i++) {
            double angle = Math.toRadians(i);
            float x = (float)(Math.cos(angle) * radius) + cX;
            float y = (float)(Math.sin(angle) * radius) + cY;
            bufferBuilder.vertex(m, x, y, 0).color(r,g,b,a).endVertex();
        }

        tesselator.end();
    }

    public static void drawFillArc(GuiGraphics graphics, int cX, int cY, int radius, int start, int end, RGB rgb){
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f m = graphics.pose().last().pose();

        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba)
            a = rgba.a;

        for (int i = start - 90; i <= end - 90; i++) {
            double angle = Math.toRadians(i);
            float x = (float)(Math.cos(angle) * radius) + cX;
            float y = (float)(Math.sin(angle) * radius) + cY;
            bufferBuilder.vertex(m, x, y, 0).color(r,g,b,a).endVertex();
        }

        tesselator.end();
    }

    public static void drawCircle(GuiGraphics graphics, int x, int y, int radius, RGB rgb) {
        drawArc(graphics, x, y, radius, 0, 360, rgb);
    }

    public static void drawFillCircle(GuiGraphics graphics, int x, int y, int radius, RGB rgb) {
        drawFillArc(graphics, x, y, radius, 0, 360, rgb);
    }

    public static void drawHollowRect(GuiGraphics graphics, int x, int y, int w, int h, RGB col, boolean roundEdges) {
        if (w > 1 && h > 1 && col != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            addFillToBuffer(graphics, buffer, x, y + 1, 1, h - 2, col);
            addFillToBuffer(graphics, buffer, x + w - 1, y + 1, 1, h - 2, col);
            if (roundEdges) {
                addFillToBuffer(graphics, buffer, x + 1, y, w - 2, 1, col);
                addFillToBuffer(graphics, buffer, x + 1, y + h - 1, w - 2, 1, col);
            } else {
                addFillToBuffer(graphics, buffer, x, y, w, 1, col);
                addFillToBuffer(graphics, buffer, x, y + h - 1, w, 1, col);
            }

            tesselator.end();
        } else {
            col.draw(graphics, x, y, w, h, 0);
        }
    }

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH){
        renderTexture(guiGraphics, texture, x,y,width,height, textureX, textureY, textureW, textureH, 256);
    }

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH, int textureSize){
        guiGraphics.blit(new ResourceLocation(texture), x,y,width,height, textureX, textureY, textureW, textureH, textureSize, textureSize );
    }

    public static void renderTexture(GuiGraphics guiGraphics, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH, int textureSizeX, int textureSizeY){
        guiGraphics.blit(new ResourceLocation(texture), x,y,width,height, textureX, textureY, textureW, textureH, textureSizeX, textureSizeY );
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

    /**
     * Позволяет изменить маштаб рендера когда ты закончил весь рендер с изменённым размером то вызови {@link RenderHelper#popScale}
     * @param x,y Координаты чтобы можно было коректно сместить рендер
     * @param scale Маштаб
     */
    public static void pushScale(GuiGraphics guiGraphics, int x, int y, int scale){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1f);
        guiGraphics.pose().translate((int) (x / scale), (int) (y / scale), 0f);
    }

    public static void pushScale(GuiGraphics guiGraphics, int x, int y, float scale){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1f);
        guiGraphics.pose().translate((int) (x / scale), (int) (y / scale), 0f);
    }

    public static void popScale(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
    }

    public static int getScaleSize(int size, float scale){
        return (int) (size * scale);
    }

    public static Vector2 getScaleSize(Vector2 size, float scale){
        return new Vector2((int) (size.x * scale), (int) (size.y * scale));
    }


    public static void pushUpper(GuiGraphics guiGraphics){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 40f);
    }

    public static void pushUpper(GuiGraphics guiGraphics, float pos){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, pos);
    }

    public static void popUpper(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
    }


    public static void pushRotate(GuiGraphics guiGraphics, int x, int y, int w, int h, float angle){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x + w / 2.0, y + h / 2.0, 0);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(45));
        guiGraphics.pose().translate(-w / 2.0, -h / 2.0, 0);
    }

    public static void popRotate(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
    }
    /**
     * Устанавливает прозрачность
     * @param alpha степень прозрачности
     */
    public static void setTransparent(GuiGraphics guiGraphics, float alpha){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
    }

    public static void popTransparent(){
        RenderSystem.disableBlend();
    }
}
