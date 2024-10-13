package net.sixik.v2.color;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sixik.v2.render.ItemRenderHelper;

public class ItemColor extends RGBA {

    public ItemStack itemStack;

    protected ItemColor(ItemStack itemStack) {
        super(0,0,0,0);
        this.itemStack = itemStack;
    }

    public static ItemColor fromStack(ItemStack stack) {
        return new ItemColor(stack);
    }

    public static ItemColor fromStack(Item stack) {
        return new ItemColor(stack.getDefaultInstance());
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate((double)x + (double)width / 2.0, (double)y + (double)height / 2.0, 100.0);
        if (width != 16 || height != 16) {
            int s = Math.min(width, height);
            poseStack.scale((float)s / 16.0F, (float)s / 16.0F, (float)s / 16.0F);
        }
        ItemRenderHelper.drawItem(graphics, this.itemStack, 0, true, (String)null);
        poseStack.popPose();
    }

    @Override
    public void drawCircle(GuiGraphics graphics, int x, int y, int radius, int segments) {

    }

    @Override
    public void drawLine(GuiGraphics graphics, int x, int y, int x2, int y2, float lineWidth) {

    }

    @Override
    public void drawRoundFill(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius) {

    }

    @Override
    public void drawTriangle(GuiGraphics graphics, int x, int y, int w, int h) {

    }
}
