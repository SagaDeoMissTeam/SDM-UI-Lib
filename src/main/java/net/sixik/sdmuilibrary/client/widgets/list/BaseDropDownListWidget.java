package net.sixik.sdmuilibrary.client.widgets.list;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class BaseDropDownListWidget<T> extends AbstractButton {

    private Function<T, Component> toNameFunc = t -> Component.literal(t.toString());
    private final List<T> possibleValues;
    private T value;
    private final int maxDisplayed;
    private final int maxScroll;
    private int scroll;
    private int searchTimer;
    private boolean opened;

    public BaseDropDownListWidget(
            Vector2 pos,
            Vector2 size,
            int maxDisplayed,
            Collection<T> possibleValues,
            T value
    ) {
        super(pos.x, pos.y, size.x, size.y, Component.empty());
        this.possibleValues = new ArrayList<>(possibleValues);
        this.value = value;
        maxDisplayed = Math.min(maxDisplayed, possibleValues.size());
        this.maxDisplayed = maxDisplayed;
        this.maxScroll = possibleValues.size() - maxDisplayed;
    }

    @Override
    public void onPress() {

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }
}
