package net.sixik.v2.animation;

import net.sixik.v2.animation.methods.AbstractAnimationMethod;

import java.util.LinkedList;

public class AnimationContainer {

    public AnimationState animationState = new AnimationState();
    public LinkedList<AbstractAnimationMethod> methods = new LinkedList<>();

    public void startAnimation() {

    }
}
