package eu.iamgio.animated.transition.animations;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.binding.Curve;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 */
public class CircleClip extends AnimationFX {

    // The width/height of the scene is multiplied by this value
    // to obtain the final radius of the circle that allows it
    // to cover the scene completely.
    private static final double FINAL_RADIUS_MULTIPLIER = 1.5;

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
        // Never reset.
        return this;
    }

    @Override
    protected void initTimeline() {
        final Circle clip = new Circle();
        this.getNode().setClip(clip);

        final Scene scene = this.getNode().getScene();

        if (scene == null) {
            throw new IllegalStateException("Target node is not in scene.");
        }

        // The final radius of the circle must cover the whole scene.

        final double finalRadius = Math.max(scene.getWidth(), scene.getHeight()) * FINAL_RADIUS_MULTIPLIER;

        this.setTimeline(new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(clip.radiusProperty(), 0)
                ),
                new KeyFrame(
                        this.duration,
                        new KeyValue(clip.radiusProperty(), finalRadius, this.curve.toInterpolator())
                )
        ));
    }
}
