package net.sixik.sdmuilibrary.client.widgets.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;

public class ItemWidget extends SDMWidget {

    public ItemStack itemStack;
    public float zPosition = 100.0f;

    public ItemWidget(ItemStack itemStack, Vector2 position, Vector2 size) {
        super(position, size);
        this.itemStack = itemStack;
    }

    public ItemWidget setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemWidget setzPosition(float zPosition) {
        this.zPosition = zPosition;
        return this;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate((double)x + (double)width / 2.0, (double)y + (double)height / 2.0, zPosition);
        if (width != 16 || height != 16) {
            int s = Math.min(width, height);
            poseStack.scale((float)s / 16.0F, (float)s / 16.0F, (float)s / 16.0F);
        }

        RenderHelper.drawItem(graphics, itemStack, 0, false, (String)null);
        poseStack.popPose();
    }
}
