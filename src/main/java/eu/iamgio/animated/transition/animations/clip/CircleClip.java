package eu.iamgio.animated.transition.animations.clip;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.binding.Curve;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Animation that involves a circular clip with a variable radius.
 *
 * @see CircleClipIn
 * @see CircleClipOut
 */
abstract class CircleClip extends AnimationFX {

    // The width/height of the scene is multiplied by this value
    // to obtain the maximum radius of the circle that allows it
    // to cover the scene completely.
    private static final double MAX_RADIUS_MULTIPLIER = 1.5;

    protected static final Curve DEFAULT_CURVE = Curve.EASE_IN;
    protected static final Duration DEFAULT_DURATION = Duration.seconds(1);

    private final Curve curve;
    private final Duration duration;

    /**
     * @param curve animation curve
     * @param duration animation duration
     */
    CircleClip(Curve curve, Duration duration) {
        this.curve = curve;
        this.duration = duration;
    }

    /**
     * @param scene scene to cover
     * @return the optimal radius that allows the circle, in any position within the scene, to cover it completely
     */
    protected static double calcMaxRadius(Scene scene) {
        return Math.max(scene.getWidth(), scene.getHeight()) * MAX_RADIUS_MULTIPLIER;
    }

    /**
     * @return radius of the circle at the beginning of the animation, with the target node as an argument
     */
    protected abstract double getInitialRadius();

    /**
     * @return radius of the circle at the end of the animation, with the target node as an argument
     */
    protected abstract double getFinalRadius();

    @Override
    protected AnimationFX resetNode() {
        // Never reset.
        return this;
    }

    @Override
    protected void initTimeline() {
        final Scene scene = this.getNode().getScene();

        if (scene == null) {
            throw new IllegalStateException("Target node is not in scene.");
        }

        final double initialRadius = getInitialRadius();
        final double finalRadius = getFinalRadius();

        final Circle clip = new Circle(initialRadius);
        this.getNode().setClip(clip);

        // The final radius of the circle must cover the whole scene.

        this.setTimeline(new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(clip.radiusProperty(), initialRadius)
                ),
                new KeyFrame(
                        this.duration,
                        new KeyValue(clip.radiusProperty(), finalRadius, this.curve.toInterpolator())
                )
        ));
    }
}
