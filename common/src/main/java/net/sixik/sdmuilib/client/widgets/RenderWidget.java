package net.sixik.sdmuilib.client.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class RenderWidget extends SDMWidget {

    public boolean scissor = true;

    public List<SDMWidget> renderables = Lists.newArrayList();
    private final List<NarratableEntry> narratables = com.google.common.collect.Lists.newArrayList();

    public RenderWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        if(visible) {
            for (SDMWidget widget : renderables) {
                renderWidget(widget, graphics, x, y, width, height, mouseX, mouseY, tick);
            }
        }
    }

    public void renderWidget(SDMWidget widget, GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        widget.render(graphics, mouseX, mouseY, tick);
    }

    public  <T extends SDMWidget & GuiEventListener & NarratableEntry> T addRenderableWidget(T p_169406_) {
        this.renderables.add(p_169406_);
        return this.addWidget(p_169406_);
    }

    public <T extends SDMWidget> T addRenderableOnly(T p_254514_) {
        this.renderables.add(p_254514_);
        return p_254514_;
    }

    public <T extends GuiEventListener & NarratableEntry> T addWidget(T p_96625_) {
        this.narratables.add(p_96625_);
        return p_96625_;
    }


    @Override
    public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_) {
        for (SDMWidget renderable : renderables) {
            renderable.mouseScrolled(p_94734_, p_94735_, p_94736_);
        }

        return super.mouseScrolled(p_94734_, p_94735_, p_94736_);
    }

    @Override
    public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_) {
        for (SDMWidget renderable : renderables) {
            renderable.mouseClicked(p_93641_, p_93642_, p_93643_);
        }
        return super.mouseClicked(p_93641_, p_93642_, p_93643_);
    }

    @Override
    public boolean mouseDragged(double p_93645_, double p_93646_, int p_93647_, double p_93648_, double p_93649_) {
        for (SDMWidget renderable : renderables) {
            renderable.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
        }

        return super.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
    }

    @Override
    public boolean mouseReleased(double p_93684_, double p_93685_, int p_93686_) {
        for (SDMWidget renderable : renderables) {
            renderable.mouseReleased(p_93684_, p_93685_, p_93686_);
        }
        return super.mouseReleased(p_93684_, p_93685_, p_93686_);
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        for (SDMWidget renderable : renderables) {
            renderable.mouseMoved(p_94758_, p_94759_);
        }
        super.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
        for (NarratableEntry narratable : narratables) {
            narratable.updateNarration(p_259858_);
        }
    }
}
