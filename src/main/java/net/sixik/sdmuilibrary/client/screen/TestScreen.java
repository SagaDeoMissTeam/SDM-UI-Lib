package net.sixik.sdmuilibrary.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;
import net.sixik.sdmuilibrary.client.utils.misc.GradientRBG;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgets.buttons.SimpleButtonWidget;
import net.sixik.sdmuilibrary.client.widgets.list.BaseDropDownListWidget;
import net.sixik.sdmuilibrary.client.widgets.progressBar.BasicProgressBarWidget;
import net.sixik.sdmuilibrary.client.widgets.text.fields.BasicTextFieldWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        List<String> values = List.of("foemfopwef","Очень длинное сообщение пиздец", UUID.randomUUID().toString(),"t","f","y","e","y","i","oo","pp","[[","]]","vv","bb","nn","mm");

        addRenderableWidget(new BaseDropDownListWidget(Vector2.of(100,100), 100, 5, values, "foemfopwef"));




    }

    @Override
    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

    }
}
