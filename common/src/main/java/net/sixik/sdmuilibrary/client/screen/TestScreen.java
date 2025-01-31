package net.sixik.sdmuilibrary.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.utils.DrawDirection;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.LineVectors;
import net.sixik.sdmuilibrary.client.utils.misc.RGBA;
import net.sixik.sdmuilibrary.client.utils.renders.ShapesRenderHelper;
import net.sixik.sdmuilibrary.client.widgets.progressBar.BasicProgressBarWidget;
import net.sixik.sdmuilibrary.client.widgets.text.fields.BasicTextFieldWidget;


public class TestScreen extends BaseScreen {

    public BasicProgressBarWidget progressBarWidget;
    public BasicTextFieldWidget basicTextFieldWidget;

    @Override
    protected void init() {
//        System.out.println("work2");
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
//
//        List<String> values = List.of("foemfopwef","Очень длинное сообщение пиздец", UUID.randomUUID().toString(),"t","f","y","e","y","i","oo","pp","[[","]]","vv","bb","nn","mm");
//
//        RenderWidget widget = addRenderableWidget(new RenderWidget(Vector2.of(100,100), Vector2.of(20,20)));
//
//
//        widget.addRenderableWidget(new ColorButtonWidget(
//                List.of(
//                        RGBA.create(100,200,100,255),
//                        RGBA.create(150,200,100,255),
//                        RGBA.create(5,2,100,255)
//                ),
//                Vector2.of(100,100), Vector2.of(20,20)));

    }



    @Override
    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        //RGBA.create(0,0,0,255).drawStraight(graphics,0,0, LineVectors.HORIZONTALLY,50f,5f);
        //RGBA.create(0,0,0,255).drawMagneticLine(graphics,0,1, LineVectors.VERTICALLY, 50,15, LineVectors.VERTICALLY, 5f);
        ShapesRenderHelper.drawQuad(graphics.pose().last().pose(), new Vector2(50,100),new Vector2(50,50),RGBA.create(0,0,0, 255/2));
        ShapesRenderHelper.drawRoundedRect(graphics,50,50,50,50,3,RGBA.create(0,0,0, 255/2), DrawDirection.RIGHT);

    }
}
