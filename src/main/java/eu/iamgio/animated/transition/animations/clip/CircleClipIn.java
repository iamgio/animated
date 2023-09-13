package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.common.Curve;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Animation that features a circular clip of increasing radius of the target node.
 */
public class CircleClipIn extends CircleClip {

    /**
     * Instantiates a circular clip entrance animation.
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the circle, relative to the scene
     */
    public CircleClipIn(Curve curve, Duration duration, Pos alignment) {
        super(curve, duration, alignment);
    }

    /**
     * Instantiates a circular clip entrance animation.
     * @param alignment position of the circle, relative to the scene
     */
    public CircleClipIn(Pos alignment) {
        super(DEFAULT_CURVE, DEFAULT_DURATION, alignment);
    }

    /**
     * Instantiates a circular clip entrance animation from the center of the scene.
     */
    public CircleClipIn() {
        this(Pos.CENTER);
    }

    @Override
    protected double getInitialRadius() {
        return 0;
    }

    @Override
    protected double getFinalRadius() {
        return calcMaxRadius(getNode().getScene());
    }
}
