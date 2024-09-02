package net.sixik.sdmuilibrary.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;
import net.sixik.sdmuilibrary.client.utils.misc.GradientRBG;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgets.buttons.SimpleButtonWidget;
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
//        addRenderableWidget(basicTextFieldWidget = new BasicTextFieldWidget("Введите текст", 0,0, 100,20) {
//
//        });




    }

    @Override
    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        GradientRBG.create(RGB.fromHex("#b92b27"), RGB.fromHex("#1565C0")).drawCircle(graphics, mouseX, mouseY, 100);
    }
}
