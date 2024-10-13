package net.sixik.v2;

import net.sixik.v2.enums.CenterOperators;
import net.sixik.v2.widgets.alight.AlightContainerUIComponent;
import net.sixik.v2.widgets.button.CloseGUIButtonUIComponent;
import net.sixik.v2.widgets.screen.UIScreen;

public class TestUIScreen extends UIScreen {

    public AlightContainerUIComponent component;

    @Override
    public boolean onInit() {
        centerOperator = CenterOperators.Type.CENTER_XY;
        setUseScissor();
        return super.onInit();
    }

    @Override
    public void addWidgets() {
        addWidget(component = new AlightContainerUIComponent());
        component.setMatrix(new int[][]{{0,1,2}, {0,1}, {0}});
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
        component.addWidget(new CloseGUIButtonUIComponent());
    }

    @Override
    public void alightWidgets() {
        component.setSize(this.width, this.height);
        super.alightWidgets();
    }
}
