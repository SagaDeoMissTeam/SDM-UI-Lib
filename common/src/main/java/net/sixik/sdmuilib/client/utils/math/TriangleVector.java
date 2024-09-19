package net.sixik.sdmuilib.client.utils.math;

import java.util.ArrayList;
import java.util.List;

public class TriangleVector {

    public Vector2f pos1;
    public Vector2f pos2;
    public Vector2f pos3;

    protected TriangleVector(Vector2f pos1, Vector2f pos2, Vector2f pos3) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
    }

    public static List<TriangleVector> create(Vector2f... positions) {
        int count = positions.length / 3;
        if(count == 0) return new ArrayList<>();

        List<TriangleVector> result = new ArrayList<>();

        List<Vector2f> d1 = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            if(d1.size() == 3) {
                result.add(TriangleVector.create(d1.get(0), d1.get(1), d1.get(2)));
                d1.clear();
            }
            d1.add(positions[i]);
        }

        return result;
    }

    public static TriangleVector create(Vector2f pos1, Vector2f pos2, Vector2f pos3){
        return new TriangleVector(pos1, pos2, pos3);
    }
}
