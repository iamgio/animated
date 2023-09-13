package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.binding.Curve;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Animation that features a circular clip of decreasing radius of the target node.
 */
public class CircleClipOut extends CircleClip {

    /**
     * Instantiates a circular clip exit animation.
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the circle, relative to the scene
     */
    public CircleClipOut(Curve curve, Duration duration, Pos alignment) {
        super(curve, duration, alignment);
    }

    /**
     * Instantiates a circular clip exit animation.
     * @param alignment position of the circle, relative to the scene
     */
    public CircleClipOut(Pos alignment) {
        this(DEFAULT_CURVE, DEFAULT_DURATION, alignment);
    }

    /**
     * Instantiates a circular clip exit animation from the center of the scene.
     */
    public CircleClipOut() {
        this(Pos.CENTER);
    }

    @Override
    protected double getInitialRadius() {
        return calcMaxRadius(getNode().getScene());
    }

    @Override
    protected double getFinalRadius() {
        return 0;
    }
}