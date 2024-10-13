package net.sixik.v2.widgets.prgressbar;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.widgets.UIComponent;

public class ProgressBarUIComponent extends UIComponent {
    public int progressValue = 0;
    public int minProgressValue = 0;
    public int maxProgressValue = 1;

    public ProgressBarUIComponent() {

    }

    public ProgressBarUIComponent setMaxProgressValue(int value) {
        if(value > minProgressValue)
            this.maxProgressValue = value;
        else {
            this.maxProgressValue = minProgressValue + 1;
            throw new RuntimeException("Try to set maxValue who less than minValue");
        }
        return this;
    }

    public ProgressBarUIComponent setMinProgressValue(int value) {
        this.minProgressValue = value;
        return this;
    }

    public ProgressBarUIComponent setProgressValue(int value) {
        this.progressValue = Math.min(maxProgressValue, Math.max(minProgressValue, value));
        return this;
    }

    @Override
    public void drawBackground(GuiGraphics graphics, int x, int y, int w, int h) {
        drawProgressBar(graphics, x,y,w,h);
    }

    public void drawProgressBar(GuiGraphics graphics, int x, int y, int w, int h) {
        RGBA.create(0,0,0,255/3).draw(graphics,x,y,w,h);

        int safeProgressValue = Math.min(progressValue, maxProgressValue);
        float progressWidth = (float)(w - 2) * safeProgressValue / maxProgressValue;

        RGBA.create(255, 0, 0, 255).draw(graphics, x + 1, y + 1, (int)progressWidth, h - 2);
    }
}
