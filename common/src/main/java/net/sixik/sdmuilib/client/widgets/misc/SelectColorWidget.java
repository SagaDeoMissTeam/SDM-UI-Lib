package net.sixik.sdmuilib.client.widgets.misc;

import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.misc.RGBA;
import net.sixik.sdmuilib.client.widgets.RenderWidget;

public class SelectColorWidget extends RenderWidget {

    public RGBA color = RGBA.create(255,255,255,255);


    public SelectColorWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }


}
