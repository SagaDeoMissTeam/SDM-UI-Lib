package net.sixik.sdmuilibrary.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgets.progressBar.BasicProgressBarWidget;
import net.sixik.sdmuilibrary.client.widgets.text.fields.BasicTextFieldWidget;

public class TestScreen extends BaseScreen {

    public BasicProgressBarWidget progressBarWidget;
    public BasicTextFieldWidget basicTextFieldWidget;

    @Override
    protected void init() {
//        progressBarWidget = addRenderableWidget(new BasicProgressBarWidget(new Vector2(20, 20), new Vector2(100, 20)) {
//            @Override
//            public void onProgressChanged(float progress) {
//                System.out.println(this.getProgress());
//            }
//        });
//
        addRenderableWidget(basicTextFieldWidget = new BasicTextFieldWidget("Введите текст", 0,0, 100,20) {

        });




    }

    @Override
    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RGB.create(100,100,100).drawCircle(graphics, 50, 50, 120);
    }
}
