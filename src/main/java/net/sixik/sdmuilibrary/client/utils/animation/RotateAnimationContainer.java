package net.sixik.sdmuilibrary.client.utils.animation;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.GLHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.AnimationContainer;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;

import java.util.ArrayList;
import java.util.List;

public class RotateAnimationContainer implements AnimationContainer {

    public float rotateSpeed = 0.1f;
    public float rotation = 0f;

    private final List<ISDMRender> widgets = new ArrayList<>();

    public static RotateAnimationContainer create(){
        return new RotateAnimationContainer();
    }

    public RotateAnimationContainer addWidget(ISDMRender widget){
        this.widgets.add(widget);
        return this;
    }

    public RotateAnimationContainer addWidget(ISDMRender... widget){
        this.widgets.addAll(new ArrayList<>(List.of(widget)));
        return this;
    }

    @Override
    public List<ISDMRender> getWidgets() {
        return widgets;
    }

    @Override
    public AnimationType getType() {
        return AnimationType.ALL_OBJECT;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {

        for (ISDMRender widget : getWidgets()) {
            graphics.pose().pushPose();

            switch (getType()){
                case ALL_OBJECT -> {
                    if(widget instanceof SDMWidget sdmWidget){
                        GLHelper.pushTransform(
                                graphics,
                                new Vector2(sdmWidget.getX(), sdmWidget.getY()),
                                new Vector2(sdmWidget.getWidth(), sdmWidget.getHeight()),
                                1f,
                                rotation
                        );
                    } else {
                        GLHelper.pushTransform(
                                graphics,
                                new Vector2(x, y),
                                new Vector2(width, height),
                                1f,
                                rotation
                        );
                    }

                    GLHelper.popTransform(graphics);
                }
                case CUSTOM -> {
                    customAnimation(widget, graphics, x,y,width,height,rotation);
                }
            }

            graphics.pose().popPose();

        }

        rotation += rotateSpeed;
    }

    @Override
    public void customAnimation(ISDMRender widget, GuiGraphics graphics, int x, int y, int width, int height, float tick) {

    }
}
