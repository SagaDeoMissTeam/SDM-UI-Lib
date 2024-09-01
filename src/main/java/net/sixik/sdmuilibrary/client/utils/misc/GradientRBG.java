package net.sixik.sdmuilibrary.client.utils.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;

public class GradientRBG extends RGB{

    public RGB start;
    public RGB end;

    protected GradientRBG(RGB start, RGB end) {
        super(start.r, start.g, start.b);
        this.start = start;
        this.end = end;
    }

    public static GradientRBG create(RGB start, RGB end){
        return new GradientRBG(start, end);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if(width > 0 && height > 0){
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            BufferBuilder buffer =  Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            RenderHelper.addFillToBufferGradient(graphics, buffer, x, y, width, height, start, end);
        }
    }
}
