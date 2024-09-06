package net.sixik.sdmuilibrary.client.screen.examples;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.screen.BaseScreen;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.TextHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.CenterOperators;
import net.sixik.sdmuilibrary.client.utils.misc.Colors;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;
import net.sixik.sdmuilibrary.client.widgets.RenderWidget;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;
import net.sixik.sdmuilibrary.client.widgets.buttons.BasicButtonWidget;
import net.sixik.sdmuilibrary.client.widgets.buttons.SimpleButtonWidget;
import net.sixik.sdmuilibrary.client.widgets.scroll.ScrollWidget;

import java.util.ArrayList;
import java.util.List;

public class GameUIScreen extends BaseScreen {

    public static List<String> list = List.of(
            "Fobos", "Я большой мальчик", "Тестовое сообщение"
    );

    public int selectedTab = 0;
    public int selectedButtonOnPage = -1;

    public int countButtons = 6;
    public Vector2 buttonSize;

    public RenderWidget page1Container;

    @Override
    protected void addWidgets() {
        for (int i = 0; i < countButtons; i++) {
            int d1 = i;
            Vector2 position = new Vector2(this.position.x + 1 + size.x / countButtons * i, this.position.y - buttonSize.y);
            SimpleButtonWidget buttonWidget = addRenderableWidget(new SimpleButtonWidget(Component.literal("Tab" + (i + 1)), position, buttonSize){
                @Override
                public void onClick(double mouseX, double mouseY) {
                    if(selectedTab != d1) {
                        selectedTab = d1;
                        selectedButtonOnPage = -1;
                    }
                }

                @Override
                public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
                    if(selectedTab == d1) {
                        super.draw(graphics, x, y - 10, width, height + 10, mouseX, mouseY, tick);
                    } else {
                        super.draw(graphics, x, y, width, height, mouseX, mouseY, tick);
                    }
                }

                @Override
                public CenterOperators.Type getCenteredType() {
                    return CenterOperators.Type.CENTER_XY;
                }
            });
        }

        createPage1();
        createPage2();
    }

    @Override
    protected void rebuildProperty() {
        super.rebuildProperty();
        Vector2 screenSize = size;
        size = size.multiply(4).divide(6);
        position = screenSize.divide(2).subtract(new Vector2(size.x / 2, size.y / 2));
        buttonSize = new Vector2(size.x / countButtons, 20);
    }

    @Override
    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        page1Container.visible = selectedTab == 0;

        drawBackground(graphics, mouseX, mouseY, partialTicks);
    }


    public void drawBackground(GuiGraphics graphics, int mouseX, int mouseY,float partialTicks){
        Colors.POLAR_NIGHT_0.draw(graphics, position.x, position.y, size.x, size.y, partialTicks);
        RenderHelper.drawHollowRect(graphics, position.x - 1, position.y - 1, size.x + 2, size.y + 2, Colors.POLAR_NIGHT_1, false);
        drawPage(graphics, mouseX, mouseY, partialTicks);
    }

    public void drawPage(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks){
        Vector2 pos = position.add(new Vector2(0,10));

        if(selectedTab == 0){
            int _sizeXMax = size.x - (size.x / 6 * 2);
            Colors.POLAR_NIGHT_4.draw(graphics, (pos.x + size.x) - _sizeXMax, pos.y - 10, _sizeXMax, size.y, partialTicks);
            if(selectedButtonOnPage != -1){
                pos = new Vector2((pos.x + size.x) - _sizeXMax, pos.y).add(new Vector2(5, 5));
                String text = list.get(selectedButtonOnPage);
                TextHelper.drawText(graphics, text, pos.x,pos.y);
            }
//            TextHelper.drawText(graphics, "Тестовое сообщение", pos.x ,pos.y);
        }
    }

    public void createPage1(){
        page1Container = new RenderWidget(position, new Vector2(size.x - (size.x / 6 * 2), size.y));


        int pageButtons = 200;
        Vector2 pageButtonSize = new Vector2(16,16);
        Vector2 pos = new Vector2(position.x + 10, position.y + 10);

        Vector2 _size = new Vector2(pageButtonSize.x,pageButtonSize.y);
        int _sizeXMax = page1Container.getWidth();

        for (int i = 0; i < pageButtons; i++) {
            int d1 = i;

            if (i != 0) {
                if (_size.add(new Vector2(32, 0)).add(new Vector2(2, 0)).x >= _sizeXMax) {
                    _size = pageButtonSize.add(new Vector2(2, 2));
                    pos = new Vector2(position.x + 10, pos.y + 2 + pageButtonSize.y);
                } else {
                    _size = _size.add(new Vector2(32 + 2, 0));
                    pos = pos.add(new Vector2(pageButtonSize.x, 0)).add(new Vector2(2, 0));
                }
            }



            page1Container.addRenderableWidget(
                new BasicButtonWidget(pos, pageButtonSize) {
                    @Override
                    public void onClick(double mouseX, double mouseY) {
                        if(selectedButtonOnPage != d1) {
                            //selectedButtonOnPage = d1;
                        }
                    }

                    @Override
                    public void drawBackground(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
                        if(selectedButtonOnPage != -1 && selectedButtonOnPage == d1) {
                            Colors.UI_GOLD_1.draw(graphics, x, y, width, height, tick);
                        }
                        else
                            Colors.UI_GOLD_0.draw(graphics, x, y, width, height, tick);
                    }
                }
            );
        }
        addRenderableWidget(page1Container);
    }

    public void createPage2(){

    }

}
