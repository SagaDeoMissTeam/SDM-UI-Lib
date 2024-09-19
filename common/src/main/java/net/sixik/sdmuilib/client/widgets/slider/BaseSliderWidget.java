package net.sixik.sdmuilib.client.widgets.slider;

import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.widgets.SDMWidget;

public class BaseSliderWidget extends SDMWidget {


    public float value = 0f;

    public BaseSliderWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }
}
