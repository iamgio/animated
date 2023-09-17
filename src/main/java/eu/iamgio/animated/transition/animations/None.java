package eu.iamgio.animated.transition.animations;

import animatefx.animation.AnimationFX;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * An 1-frame-long animation that does not perform any motion or transition.
 */
public class None extends AnimationFX {

    @Override
    protected AnimationFX resetNode() {
        return this;
    }

    @Override
    protected void initTimeline() {
        this.setTimeline(new Timeline(new KeyFrame(Duration.ONE, e -> {})));
    }
}
