package net.sixik.v2.animation;

public class AnimationState {
    public int x = 0;
    public int y = 0;
    public float scale = 1f;
    public int rotation = 0;

    public AnimationState() {

    }

    public AnimationState(int x, int y, float scale, int rotation) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotation = rotation;
    }

    public AnimationState setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public AnimationState setRotation(int rotation) {
        this.rotation = rotation;
        return this;
    }

    public AnimationState setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
