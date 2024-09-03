package net.sixik.sdmuilibrary.client.widgetsFake.progressBar;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgetsFake.SDMFakeWidget;

public class ProgressBarFakeWidget extends SDMFakeWidget {
    public float progress = 0f;

    public RGB backgroundColor = RGB.create(0,0,0);
    public RGB fillColor = RGB.create(255,255,255);

    public ProgressBarFakeWidget setFillColor(RGB fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public ProgressBarFakeWidget setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    public void setComplete(){
        this.progress = 1f;
    }


    public void addProgress(float progress){
        if(this.progress < 1f) {

            this.progress += progress;
            if (this.progress > 1f) {
                this.progress = 1f;
            }
        }
    }

    public int getFilling() {
        return (int) progress >= 1f ? size.x : (int) (size.x * Math.max(0, Math.min(1.0f, progress)));
    }

    @Override
    public void draw(GuiGraphics graphics) {
        backgroundColor.draw(graphics, position.x, position.y, size.x, size.y, 0);
        backgroundColor.draw(graphics, position.x + 1, position.y + 1, getFilling() - 2, size.y - 2, 0);
    }
}
