package net.sixik.sdmuilibrary.client.utils.misc;

import net.sixik.sdmuilibrary.client.render.api.ISDMRender;

import java.util.List;

@Deprecated
public interface AnimationContainer extends ISDMRender {

    List<ISDMRender> getWidgets();
}
