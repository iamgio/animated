package eu.iamgio.animated.transition.animations;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.binding.Curve;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 */
public class CircleClip extends AnimationFX {

    private final Curve curve;
    private final Duration duration;

    public CircleClip(Curve curve, Duration duration) {
        this.curve = curve;
        this.duration = duration;
    }

    public CircleClip() {
        this(Curve.EASE_IN, Duration.seconds(1));
    }

    @Override
    protected AnimationFX resetNode() {
        if (this.getNode().getClip() instanceof Circle) {
            final Circle clip = (Circle) this.getNode().getClip();
            clip.setRadius(0);
        }
        return this;
    }

    @Override
    protected void initTimeline() {
        final Circle clip = new Circle();
        this.getNode().setClip(clip);

        this.setTimeline(new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(clip.radiusProperty(), 0)
                ),
                new KeyFrame(
                        this.duration,
                        new KeyValue(clip.radiusProperty(), 1000, this.curve.toInterpolator())
                )
        ));
    }
}
