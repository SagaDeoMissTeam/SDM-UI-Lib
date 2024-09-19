package net.sixik.sdmuilib.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.misc.RGB;
import net.sixik.sdmuilib.client.widgets.RenderWidget;
import net.sixik.sdmuilib.client.widgets.progressBar.BasicProgressBarWidget;
import net.sixik.sdmuilib.client.widgets.text.fields.BasicTextFieldWidget;

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

//        List<String> values = List.of("foemfopwef","Очень длинное сообщение пиздец", UUID.randomUUID().toString(),"t","f","y","e","y","i","oo","pp","[[","]]","vv","bb","nn","mm");

        RenderWidget widget = addRenderableWidget(new RenderWidget(Vector2.of(100,100), Vector2.of(20,20)));

//
//        widget.addRenderableWidget(new ColorButtonWidget(
//                List.of(
//                        RGBA.create(100,200,100,255),
//                        RGBA.create(150,200,100,255),
//                        RGBA.create(5,2,100,255)
//                ),
//                Vector2.of(100,100), Vector2.of(20,20)));
//
    }



    @Override
    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        RGB.create(200,200,200).drawLine(graphics, 10,10,20,20, 5f);
    }
}
