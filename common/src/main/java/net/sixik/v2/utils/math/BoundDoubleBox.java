package net.sixik.v2.utils.math;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class BoundDoubleBox {

    public double x1;
    public double y1;
    public double z1;
    public double x2;
    public double y2;
    public double z2;

    public BoundDoubleBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }


    public BoundDoubleBox move(Vec3 pos) {
        return move(pos.x, pos.y, pos.z);
    }
    public BoundDoubleBox move(double x, double y, double z) {
        this.x1 += x;
        this.y1 += y;
        this.z1 += z;
        this.x2 += x;
        this.y2 += y;
        this.z2 += z;
        return this;
    }

    public double x1() {
        return x1;
    }

    public double y1() {
        return y1;
    }

    public double z1() {
        return z1;
    }

    public double x2() {
        return x2;
    }

    public double y2() {
        return y2;
    }

    public double z2() {
        return z2;
    }

    public boolean isInside(Vec3i pos) {
        return this.isInside(pos.getX(), pos.getY(), pos.getZ());
    }

    public boolean isInside(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && z >= this.z1 && z <= this.z2 && y >= this.y1 && y <= this.y2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BoundDoubleBox) obj;
        return Double.doubleToLongBits(this.x1) == Double.doubleToLongBits(that.x1) &&
                Double.doubleToLongBits(this.y1) == Double.doubleToLongBits(that.y1) &&
                Double.doubleToLongBits(this.z1) == Double.doubleToLongBits(that.z1) &&
                Double.doubleToLongBits(this.x2) == Double.doubleToLongBits(that.x2) &&
                Double.doubleToLongBits(this.y2) == Double.doubleToLongBits(that.y2) &&
                Double.doubleToLongBits(this.z2) == Double.doubleToLongBits(that.z2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public String toString() {
        return "BoundDoubleBox[" +
                "x1=" + x1 + ", " +
                "y1=" + y1 + ", " +
                "z1=" + z1 + ", " +
                "x2=" + x2 + ", " +
                "y2=" + y2 + ", " +
                "z2=" + z2 + ']';
    }
}
