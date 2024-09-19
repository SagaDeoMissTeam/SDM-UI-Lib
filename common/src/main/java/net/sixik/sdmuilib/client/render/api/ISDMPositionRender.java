package net.sixik.sdmuilib.client.render.api;

import net.sixik.sdmuilib.client.utils.math.Vector2;

public interface ISDMPositionRender extends ISDMRender{
    Vector2 getPos();
    Vector2 getSize();
}
