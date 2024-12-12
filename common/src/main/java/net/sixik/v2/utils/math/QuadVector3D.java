package net.sixik.v2.utils.math;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public class QuadVector3D {

    public Vec3 pos1;
    public Vec3 pos2;
    public Vec3 pos3;
    public Vec3 pos4;

    public QuadVector3D(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 pos4) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
        this.pos4 = pos4;
    }

    public static List<QuadVector3D> create(Vec3... positions) {
        int count = positions.length / 3;
        if(count == 0) return List.of();

        List<QuadVector3D> result = List.of();

        List<Vec3> d1 = List.of();

        for (int i = 0; i < positions.length; i++) {
            if (i % 3 == 2) {
                result.add(new QuadVector3D(d1.get(0), d1.get(1), d1.get(2), positions[i]));
                d1.clear();
            }
            d1.add(positions[i]);
        }

        return result;
    }


    public static QuadVector3D create(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 pos4){
        return new QuadVector3D(pos1, pos2, pos3, pos4);
    }
}
