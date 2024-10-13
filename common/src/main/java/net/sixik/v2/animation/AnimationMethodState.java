package net.sixik.v2.animation;

public class AnimationMethodState extends AnimationState{

    public float speed = 1f;


    public AnimationMethodState setSpeed(float speed) {
        this.speed = speed;
        return this;
    }
}
