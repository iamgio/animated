package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.common.Curve;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Animation that features a circular clip with a variable radius.
 *
 * @see CircleClipIn
 * @see CircleClipOut
 */
abstract class CircleClip extends ClipAnimation<Circle> {

    // The width/height of the scene is multiplied by this value
    // to obtain the maximum radius of the circle that allows it
    // to cover the scene completely.
    private static final double MAX_RADIUS_MULTIPLIER = 1.5;

    /**
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the circle, relative to the scene
     */
    CircleClip(Curve curve, Duration duration, Pos alignment) {
        super(curve, duration, alignment);
    }

    /**
     * @param scene scene to cover
     * @return the optimal radius that allows the circle, in any position within the scene, to cover it completely
     */
    protected static double calcMaxRadius(Scene scene) {
        return Math.max(scene.getWidth(), scene.getHeight()) * MAX_RADIUS_MULTIPLIER;
    }

    /**
     * @return radius of the circle at the beginning of the animation
     */
    protected abstract double getInitialRadius();

    /**
     * @return radius of the circle at the end of the animation
     */
    protected abstract double getFinalRadius();

    @Override
    protected Circle createClip() {
        return new Circle(this.getInitialRadius());
    }

    @Override
    protected Timeline createTimeline(Circle clip) {
        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(clip.radiusProperty(), getInitialRadius())
                ),
                new KeyFrame(
                        super.getDuration(),
                        new KeyValue(clip.radiusProperty(), getFinalRadius(), super.getCurve().toInterpolator())
                )
        );
    }
}
