package net.sixik.sdmuilibrary.client.widgets.slider;

import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;

public class BaseSliderWidget extends SDMWidget {


    public float value = 0f;

    public BaseSliderWidget(Vector2 position, Vector2 size) {
        super(position, size);
    }
}
