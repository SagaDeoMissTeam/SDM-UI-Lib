package net.sixik.sdmuilib.client.widgets.progressBar;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.utils.RenderHelper;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.widgets.SDMWidget;

public class BasicProgressBarWidget extends SDMWidget {

    public int widgetImageSize = 81;
    public float progress = 0f;

    public BasicProgressBarWidget() {
        this(null, null);
    }

    public BasicProgressBarWidget(Vector2 position) {
        this(position, null);
    }

    public BasicProgressBarWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void resetProgress(){
        this.progress = 0f;
    }

    public void setComplete(){
        this.progress = 1f;
        onComplete(progress);
    }

    public float getProgress() {
        return progress;
    }

    public void onProgressChanged(float progress){

    }

    public void onComplete(float progress){

    }

    public void addProgress(float progress){
        if(this.progress < 1f) {

            this.progress += progress;
            if (this.progress > 1f) {
                this.progress = 1f;
                onComplete(progress);
            }

            onProgressChanged(progress);
        }
    }

    public int getFilling() {
        return (int) progress >= 1f ? width : (int) (width * Math.max(0, Math.min(1.0f, progress)));
    }

    public int getFillingImage() {
        return (int) progress >= 1f? widgetImageSize : (int) (widgetImageSize * Math.max(0, Math.min(1.0f, progress)));
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        RenderHelper.renderTexture(graphics, "sdm_ui_library:textures/widgets_ss.png", x,y,width,height, 169,12, widgetImageSize,15,250,250);
        RenderHelper.renderTexture(graphics, "sdm_ui_library:textures/widgets_ss_2.png", x,y,getFilling(),height, 169,72,getFillingImage(),15,250,250);
    }

}
