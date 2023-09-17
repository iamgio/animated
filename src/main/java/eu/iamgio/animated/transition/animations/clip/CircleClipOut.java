package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.common.Curve;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Exit animation that features a circular clip of decreasing radius of the target node.
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
        this(DEFAULT_EXIT_CURVE, DEFAULT_DURATION, alignment);
    }

    /**
     * Instantiates a circular clip exit animation from the center of the scene.
     */
    public CircleClipOut() {
        this(DEFAULT_ALIGNMENT);
    }

    @Override
    protected double getInitialRadius() {
        return super.calcMaxRadius();
    }

    @Override
    protected double getFinalRadius() {
        return 0;
    }
}