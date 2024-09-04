package net.sixik.sdmuilibrary.client.render.container;


import net.sixik.sdmuilibrary.client.utils.math.Vector2;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class ContainerCache {

    public Vector2 basePosition;
    public Vector2 baseSize;
    public List<Vector2> positionCache = new ArrayList<Vector2>();
    public float zPos = 0.0f;
    protected final List<Float> zPosAdd = new ArrayList<Float>();
}
