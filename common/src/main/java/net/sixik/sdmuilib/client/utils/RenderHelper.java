package net.sixik.sdmuilib.client.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.math.Vector2d;
import net.sixik.sdmuilib.client.utils.math.Vector2f;
import net.sixik.sdmuilib.client.utils.misc.CenterOperators;
import net.sixik.sdmuilib.client.utils.misc.RGB;
import net.sixik.sdmuilib.client.utils.misc.RGBA;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A utility class for rendering graphics in Minecraft.
 */
public class RenderHelper {

    public static double getGuiScale(){
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    public static boolean isGamePaused(){
        return Minecraft.getInstance().isPaused();
    }

    public static Vector2 getScreenSize(){
        return new Vector2(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
    }

    public static Vector2 getScreenCenter(){
        return new Vector2(getScreenSize().x / 2, getScreenSize().y / 2);
    }

    public static Vector2 getScreenCenter(CenterOperators.Type centerType, CenterOperators.Method method){
        return GLHelper.getCenterWithPos(new Vector2(0,0), getScreenSize(), centerType, method);
    }

    public static Vector2 getScreenCenterWithSize(Vector2 size, CenterOperators.Type centerType, CenterOperators.Method method){
        Vector2 p = GLHelper.getCenterWithPos(new Vector2(0,0), getScreenSize(), centerType, method);;
        return p.add(new Vector2(size.x / 2, size.y / 2));
    }

    public static Vector2f getMousePosition(){
        return new Vector2f((float) Minecraft.getInstance().mouseHandler.xpos(), (float) Minecraft.getInstance().mouseHandler.ypos());
    }

    public static int getTextHeight(){
        return Minecraft.getInstance().font.lineHeight;
    }

    public static float getTextHeight(float scale){
        return getTextHeight() * scale;
    }

    public static int getTextWidth(String text){
        return Minecraft.getInstance().font.width(text);
    }

    public static float getTextWidth(String text, float scale){
        return Minecraft.getInstance().font.width(text) * scale;
    }

    public static boolean isMouseOver(Vector2d mousePos, Vector2 pos, Vector2 size) {
        return mousePos.x >= pos.x && mousePos.y >= pos.y && mousePos.x < pos.x + size.x && mousePos.y < pos.y + size.y;
    }

    public static Vector2 getMaxElementsOnSizeZone(Vector2 elementSize, Vector2 screenSize,  int step, CenterOperators.Type type){
        switch (type) {
            case CENTER_X -> {
                return
                        new Vector2(
                                screenSize.x / (elementSize.x + step),
                                0
                        );
            }
            case CENTER_Y -> {
                return new Vector2(
                        0,
                        screenSize.y / (elementSize.y + step)
                );
            }
            default -> {
                return new Vector2(
                        screenSize.x / (elementSize.x + step),
                        screenSize.y / (elementSize.y + step)
                );
            }
        }
    }

    /**
     * Prepares the rendering environment for a texture.
     *
     * @param textureLocation The location of the texture to be rendered.
     */
    public static void prepareTextureRendering(ResourceLocation textureLocation) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, textureLocation);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }

    /**
     * Draws text on the screen using the specified font and color.
     *
     * @param poseStack      The pose stack for rendering.
     * @param x              The x-coordinate of the text's position.
     * @param y              The y-coordinate of the text's position.
     * @param size           The size of the text.
     * @param text           The text to be rendered.
     * @param textColor      The color of the text.
     */
    public static void drawText(GuiGraphics poseStack, int x, int y, float size, Component text, int textColor) {
        drawText(poseStack, Minecraft.getInstance().font, x, y, size, text, textColor);
    }

    /**
     * Draws text on the screen using the specified font and color.
     *
     * @param poseStack      The pose stack for rendering.
     * @param font           The font to be used for rendering the text.
     * @param x              The x-coordinate of the text's position.
     * @param y              The y-coordinate of the text's position.
     * @param size           The size of the text.
     * @param text           The text to be rendered.
     * @param textColor      The color of the text.
     */
    public static void drawText(GuiGraphics poseStack, Font font, int x, int y, float size, Component text, int textColor) {
        poseStack.pose().pushPose();
        poseStack.pose().scale(size, size, 1.0f);
        poseStack.pose().translate(x, y, 0);
        poseStack.drawString(font, text, (int) x, (int) y, textColor);
        poseStack.pose().popPose();
    }

    /**
     * Draws text on the screen using the default font and color.
     *
     * @param graphics       The graphics context for rendering.
     * @param text           The text to be rendered.
     * @param x              The x-coordinate of the text's position.
     * @param y              The y-coordinate of the text's position.
     */
    public static void drawText(GuiGraphics graphics, Component text, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x, y, RGB.create(255, 255, 255).toInt());
    }

    public static void drawText(GuiGraphics graphics, String text, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, RGB.create(255, 255, 255).toInt());
    }

    public static void drawText(GuiGraphics graphics, Component text, int x, int y, RGB rgb) {
        graphics.drawString(Minecraft.getInstance().font, text.getString(), x, y, rgb.toInt());
    }

    public static void drawText(GuiGraphics graphics, String text, int x, int y, RGB rgb) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, rgb.toInt());
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


    public static void addFillTriangleToBuffer(GuiGraphics graphics, BufferBuilder buffer, int x, int y, int w, int h, RGB rgb){
        buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f m = graphics.pose().last().pose();
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if(rgb instanceof RGBA rgba)
            a = rgba.a;


        buffer.vertex(m, x, y, 0.0F).color(r,g,b,a).endVertex();
        buffer.vertex(m, (float) (x + (w / 2)), y + h, 0.0F).color(r,g,b,a).endVertex();
        buffer.vertex(m, x + w, y, 0.0F).color(r,g,b,a).endVertex();
    }

    public static void addFillTriangleToBufferGradient(GuiGraphics graphics, BufferBuilder buffer, int x, int y, int w, int h, RGB startRgb, RGB endRgb){
        buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        RGBA s = startRgb.toARGB();
        RGBA e = endRgb.toARGB();

        Matrix4f m = graphics.pose().last().pose();
        buffer.vertex(m, x, y, 0.0F).color(e.r,e.g,e.b,e.a).endVertex();
        buffer.vertex(m, (float) (x + (w / 2)), y + h, 0.0F).color(s.r, s.g,s.b,s.a).endVertex();
        buffer.vertex(m, x + w, y, 0.0F).color(e.r,e.g,e.b,e.a).endVertex();
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

    public static void drawLine(GuiGraphics graphics, RGB rgb) {
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
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.lineWidth(5f);

        bufferBuilder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);

        bufferBuilder.vertex(m,20, 20, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(m,10,10, 0).color(r, g, b, a).endVertex();

        RenderSystem.lineWidth(1f);

        tesselator.end();
        RenderSystem.disableBlend();
    }

    public static void drawFillArc(GuiGraphics graphics, int cX, int cY, int radius, int start, int end, RGB rgb) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        // Начинаем построение треугольного вентиля (фан) для круга
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f m = graphics.pose().last().pose();

        // Настройка цвета
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        // Вершина центра круга
        bufferBuilder.vertex(m, cX, cY, 0).color(r, g, b, a).endVertex();

        // Создание вершин по краю круга для указанного диапазона углов
        for (int angle = start; angle <= end; angle++) {
            double rad = Math.toRadians(angle);
            float x = (float) (Math.cos(rad) * radius) + cX;
            float y = (float) (Math.sin(rad) * radius) + cY;
            bufferBuilder.vertex(m, x, y, 0).color(r, g, b, a).endVertex();
        }

        // Завершение построения
        tesselator.end();
    }

    public static void drawCircle(GuiGraphics graphics, int x, int y, int radius, RGB rgb) {
        drawArc(graphics, x, y, radius, 0, 360, rgb);
    }

    public static void drawFillCircle(GuiGraphics graphics, int x, int y, int radius, RGB rgb) {
        drawFillArc(graphics, x, y, radius, 0, 360, rgb);
    }

    /**
     * Draws a hollow rectangle (also known as a frame) on the screen using the specified parameters.
     *
     * @param graphics The graphics context for rendering.
     * @param x        The x-coordinate of the top-left corner of the rectangle.
     * @param y        The y-coordinate of the top-left corner of the rectangle.
     * @param w        The width of the rectangle.
     * @param h        The height of the rectangle.
     * @param col      The color of the rectangle.
     * @param roundEdges A boolean indicating whether the corners of the rectangle should be rounded.
     *
     * @return void
     */
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

    public static void pushScale(GuiGraphics guiGraphics, int x, int y, int w, int h, int scale) {
        guiGraphics.pose().pushPose();

        float scaledWidth = w * scale;
        float scaledHeight = h * scale;

        float dx = (w - scaledWidth) / 2.0f;
        float dy = (h - scaledHeight) / 2.0f;

        guiGraphics.pose().translate(x + dx, y + dy, 0);
        guiGraphics.pose().scale(scale, scale, 1.0F);
    }

    public static void pushScale(GuiGraphics guiGraphics, int x, int y, int w, int h, float scale) {
        guiGraphics.pose().pushPose();

        float scaledWidth = w * scale;
        float scaledHeight = h * scale;

        float dx = (w - scaledWidth) / 2.0f;
        float dy = (h - scaledHeight) / 2.0f;

        guiGraphics.pose().translate(x + dx, y + dy, 0);
        guiGraphics.pose().scale(scale, scale, 1.0F);
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
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(angle));
        guiGraphics.pose().translate(-w / 2.0, -h / 2.0, 0);
    }

    public static void pushRotation(GuiGraphics guiGraphics, Vector2 pivot, float angle) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(pivot.x, pivot.y, 0);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(angle));
        guiGraphics.pose().translate(-pivot.x, -pivot.y, 0);
    }

    public static void pushTransform(GuiGraphics guiGraphics, Vector2 pos, Vector2 size, float scale, float rotationAngle) {
        Vector2 center = new Vector2(pos.x + size.x / 2, pos.y + size.y / 2);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(center.x, center.y, 0);
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rotationAngle));
        guiGraphics.pose().translate(-center.x, -center.y, 0);
    }

    public static void pushTransform(GuiGraphics guiGraphics, Vector2 pos, Vector2 size, Vector2 screenSize, float scale, float rotationAngle) {
        Vector2 screenCenter = new Vector2(screenSize.x, screenSize.y);
        Vector2 center = new Vector2(pos.x + size.x / 2, pos.y + size.y / 2);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(screenCenter.x, screenCenter.y, 0);
        guiGraphics.pose().translate(center.x, center.y, 0);
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rotationAngle));
        guiGraphics.pose().translate(-center.x, -center.y, 0);
        guiGraphics.pose().translate(-screenCenter.x, -screenCenter.y, 0);

    }

    public static void popTransform(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
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

    public static void pushColor(RGB rgb){
        float r = (float) rgb.r / 255;
        float g = (float) rgb.g / 255;
        float b = (float) rgb.b / 255;
        float a = 1f;
        if(rgb instanceof RGBA rgba)
            a = (float) rgba.a / 255;
        RenderSystem.setShaderColor(r, g, b, a);

    }

    public static void popColor(){
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static List<String> splitTextToLines(String text, float textScale, int maxWidth) {
        if (text.isEmpty()) return Collections.emptyList();
        if (!text.contains(" ") && !text.contains("\n")) return Collections.singletonList(text);

        List<String> lines = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        int index = 0;
        int wordStartIndex = 0;
        boolean wordProcessing = false;
        char prevSymbol = '0';

        for (char symbol : text.toCharArray()) {
            if (symbol != ' ') {
                wordProcessing = true;
                if (prevSymbol == ' ') {
                    wordStartIndex = index;
                }
            }

            if (symbol == '\n') {
                lines.add(builder.toString());
                builder.delete(0, builder.length());
                index = 0;
                continue;
            }

            if (getTextWidth(builder.toString() + symbol, textScale) <= maxWidth) {
                builder.append(symbol);
            } else {
                if (symbol == '.' || symbol == ',' || symbol == '!' || symbol == '?') {
                    builder.append(symbol);
                }
                if (wordProcessing) {
                    lines.add(builder.toString().substring(0, wordStartIndex));
                    builder.delete(0, wordStartIndex);
                } else {
                    lines.add(builder.toString());
                    builder.delete(0, builder.length());
                }
                if (symbol != ' ') {
                    builder.append(symbol);
                }
                index = builder.length() - 1;
            }

            wordProcessing = false;
            prevSymbol = symbol;
            index++;
        }

        if (builder.length() != 0) {
            lines.add(builder.toString());
        }
        return lines;
    }

    public static void enableScissor(PoseStack poseStack, double x, double y, double width, double height) {
        var mat = poseStack.last().pose();
        var origin = new Vector4f(0, 0, 0, 1);
        origin.mulTranspose(mat);
        var window = Minecraft.getInstance().getWindow();
        var scale = window.getGuiScale();
        RenderSystem.enableScissor(
                (int) (origin.x() * scale),
                window.getHeight() - ((int) ((origin.y() + height) * scale)),
                (int) (width * scale),
                (int) (height * scale)
        );
    }

    public static void disableScissor() {
        RenderSystem.disableScissor();
    }


    @Deprecated
    public static void testBuffer(GuiGraphics graphics, int cX, int cY, int w, int h, RGB rgb){

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        Matrix4f matrix = graphics.pose().last().pose();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        ShapesRender.drawCircle(matrix,bufferBuilder,new Vector2f(cX,cY),50,12, rgb);

        tesselator.end();
    }


    @Nullable
    public static LivingEntity getEntity(EntityType<?> type){
        Minecraft mc  = Minecraft.getInstance();
        if(mc.level != null){
            Entity entity = type.create(mc.level);
            return entity != null ? (LivingEntity) entity : null;
        }
        return null;
    }

    @Deprecated
    public static void drawBlock(GuiGraphics graphics, Block block, int x, int y, int scale){
        // Получаем текущий Minecraft instance и ItemRenderer
        Minecraft mc = Minecraft.getInstance();
        ItemStack blockStack = new ItemStack(block);

        // Сохраняем текущее состояние
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Настройка матрицы для рендеринга блока
        graphics.pose().pushPose();
        graphics.pose().translate(x, y, 100); // Позиция на экране
        graphics.pose().scale(scale, scale, scale); // Масштабирование блока


//        graphics.pose().mulPoseMatrix(new Matrix4f(16.0F, 16.0F, 16.0F)); // Корректируем масштабирование для правильного отображения



        // Восстанавливаем состояние
        graphics.pose().popPose();
        RenderSystem.disableBlend();
    }

    public static Vector2d getEntitySize(LivingEntity livingEntity, double scale) {
        AABB d = livingEntity.getBoundingBox();
        return new Vector2d(d.maxX * scale, d.minY * scale);
    }

    public static void drawLivingEntity(GuiGraphics guiGraphics, int x, int y, double scale, double yaw, double pitch, LivingEntity livingEntity) {
        if (livingEntity.level() == null) return;
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate((float) x, (float) y, 50f);
        poseStack.scale((float) scale, (float) scale, (float) scale);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        // Rotate entity
        poseStack.mulPose(Axis.XP.rotationDegrees(((float) Math.atan((-40 / 40.0F))) * 10.0F));

        livingEntity.yBodyRot = (float) -(yaw / 40.F) * 20.0F;
        livingEntity.setYRot((float) -(yaw / 40.F) * 20.0F);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();


        poseStack.translate(0.0F, livingEntity.getMyRidingOffset(), 0.0F);
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.overrideCameraOrientation(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        entityRenderDispatcher.setRenderShadow(false);
        final MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, bufferSource, 15728880);
        });

        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        poseStack.popPose();
    }

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

    public static void drawRoundedRectUp(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB rgb) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        fillRect(guiGraphics, x, y + radius, width, height - radius, RGBA.create(r,g,b,a));
        // Верхняя и нижняя горизонтальные части
        fillRect(guiGraphics, x + radius, y, width - radius * 2, radius, RGBA.create(r, g, b, a)); // Верхняя часть

        // Отрисовка закругленных углов (дуги)
        drawArc(guiGraphics, x + radius,   y + radius, radius, 270, 180, RGBA.create(r,g,b,a)); // Левый верхний угол
        drawArc(guiGraphics, x + width - radius, y + radius, radius, 0, -90, RGBA.create(r,g,b,a)); // Правый верхний угол
    }

    public static void drawRoundedRectDown(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB rgb) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        fillRect(guiGraphics, x, y, width, height - radius, RGBA.create(r,g,b,a));
        // Верхняя и нижняя горизонтальные части
        fillRect(guiGraphics, x + radius, y + height - radius, width - radius * 2, radius, RGBA.create(r, g, b, a)); // Нижняя часть

        // Отрисовка закругленных углов (дуги)
        drawArc(guiGraphics, x + radius, y + height - radius, radius, -180, -270, RGBA.create(r,g,b,a)); // Левый нижний угол
        drawArc(guiGraphics, x + width - radius, y + height - radius, radius, 90, 0, RGBA.create(r,g,b,a)); // Правый нижний угол

    }

    public static void drawRoundedRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB rgb) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

//        fillRect(guiGraphics, x + radius, y + radius, width - radius * 2, height - radius * 2, RGBA.create(r, g, b, a));

        fillRect(guiGraphics, x, y + radius, width, height - radius * 2, RGBA.create(r,g,b,a));
        // Верхняя и нижняя горизонтальные части
        fillRect(guiGraphics, x + radius, y, width - radius * 2, radius, RGBA.create(r, g, b, a)); // Верхняя часть
        fillRect(guiGraphics, x + radius, y + height - radius, width - radius * 2, radius, RGBA.create(r, g, b, a)); // Нижняя часть

        // Отрисовка закругленных углов (дуги)
        drawArc(guiGraphics, x + radius,   y + radius, radius, 270, 180, RGBA.create(r,g,b,a)); // Левый верхний угол
        drawArc(guiGraphics, x + width - radius, y + radius, radius, 0, -90, RGBA.create(r,g,b,a)); // Правый верхний угол
        drawArc(guiGraphics, x + radius, y + height - radius, radius, -180, -270, RGBA.create(r,g,b,a)); // Левый нижний угол
        drawArc(guiGraphics, x + width - radius, y + height - radius, radius, 90, 0, RGBA.create(r,g,b,a)); // Правый нижний угол
    }

    public static void fillRect(GuiGraphics guiGraphics, int x, int y, int width, int height, RGB rgb) {
        // Метод для заполнения прямоугольной области
        guiGraphics.fill(x, y, x + width, y + height, rgbaToInt(rgb.r, rgb.g, rgb.b, rgb instanceof RGBA ? ((RGBA) rgb).a : 255));
    }

    public static void drawArc(GuiGraphics graphics, int cX, int cY, int radius, int startAngle, int endAngle, RGB rgb) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        Matrix4f m = graphics.pose().last().pose();

        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(m, cX, cY, 0).color(r, g, b, a).endVertex(); // Центр дуги

        for (int i = startAngle; i >= endAngle; i -= 5) {
            double angle = Math.toRadians(i);
            float x = (float) (Math.cos(angle) * radius) + cX;
            float y = (float) (Math.sin(angle) * radius) + cY;
            bufferBuilder.vertex(m, x, y, 0).color(r, g, b, a).endVertex();
        }

        tesselator.end();
        RenderSystem.disableBlend();
    }

    public static void drawRoundedRectWithOutline(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB fillColor, RGB outlineColor, int outlineThickness) {
        // Сначала рисуем заливку
        drawRoundedRect(guiGraphics, x, y, width, height, radius, fillColor);

        // Затем рисуем обводку
        drawRoundedOutline(guiGraphics, x, y, width, height, radius, outlineColor, outlineThickness);
    }

    public static void drawRoundedOutline(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, RGB rgb, int thickness) {
        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        // Верхняя и нижняя прямые
        drawLine(guiGraphics, x + radius, y, x + width - radius, y, thickness, RGBA.create(r, g, b, a)); // Верхняя сторона
        drawLine(guiGraphics, x + radius, y + height, x + width - radius, y + height, thickness, RGBA.create(r, g, b, a)); // Нижняя сторона

        // Левая и правая прямые
        drawLine(guiGraphics, x, y + radius, x, y + height - radius, thickness, RGBA.create(r, g, b, a)); // Левая сторона
        drawLine(guiGraphics, x + width, y + radius, x + width, y + height - radius, thickness, RGBA.create(r, g, b, a)); // Правая сторона

        // Закруглённые углы
        drawArcOutline(guiGraphics, x + radius, y + radius, radius, 270, 180, RGBA.create(r, g, b, a), thickness); // Левый верхний угол
        drawArcOutline(guiGraphics, x + width - radius, y + radius, radius, 0, -90, RGBA.create(r, g, b, a), thickness); // Правый верхний угол
        drawArcOutline(guiGraphics, x + radius, y + height - radius, radius, -180, -270, RGBA.create(r, g, b, a), thickness); // Левый нижний угол
        drawArcOutline(guiGraphics, x + width - radius, y + height - radius, radius, 90, 0, RGBA.create(r, g, b, a), thickness); // Правый нижний угол
    }

    // Метод для отрисовки линии
    public static void drawLine(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int thickness, RGB rgb) {
        // Линия рисуется как заполненный прямоугольник шириной thickness
        guiGraphics.fill(x1, y1, x2, y2 + thickness, rgbaToInt(rgb.r, rgb.g, rgb.b, rgb instanceof RGBA ? ((RGBA) rgb).a : 255));
    }


    public static void drawArcOutline(GuiGraphics graphics, int cX, int cY, int radius, int startAngle, int endAngle, RGB rgb, int thickness) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        Matrix4f m = graphics.pose().last().pose();

        int r = rgb.r;
        int g = rgb.g;
        int b = rgb.b;
        int a = 255;
        if (rgb instanceof RGBA rgba) {
            a = rgba.a;
        }

        bufferBuilder.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        for (int i = startAngle; i >= endAngle; i -= 5) {
            double angle = Math.toRadians(i);
            float x = (float) (Math.cos(angle) * (radius + thickness / 2)) + cX;
            float y = (float) (Math.sin(angle) * (radius + thickness / 2)) + cY;
            bufferBuilder.vertex(m, x, y, 0.0F).color(r, g, b, a).endVertex();
        }
        tesselator.end();
        RenderSystem.disableBlend();
    }

    public static int rgbaToInt(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static void pushScissor(GuiGraphics guiGraphics, Vector2 pos, Vector2 size){
        guiGraphics.enableScissor(pos.x, pos.y, pos.x + size.x, pos.y + size.y);
        guiGraphics.pose().pushPose();
    }

    public static void popScissor(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
        guiGraphics.disableScissor();
    }
}
