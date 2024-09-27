package net.sixik.sdmuilibrary.client.widgetsFake;

import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.render.api.ISDMRender;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.PositionType;

public abstract class SDMFakeWidget implements ISDMRender {

    public PositionType type = PositionType.ADDITION;
    public Vector2 position = new Vector2(0,0);
    public Vector2 size = new Vector2(10,10);
    public boolean visible = true;

    public SDMFakeWidget(){

    }

    public SDMFakeWidget setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    public SDMFakeWidget setSize(Vector2 size) {
        this.size = size;
        return this;
    }

    public SDMFakeWidget setType(PositionType type) {
        this.type = type;
        return this;
    }

    public SDMFakeWidget setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public abstract void draw(GuiGraphics graphics);

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, float tick) {
        if(visible)
            draw(graphics);
    }
}
