package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.binding.Curve;
import javafx.util.Duration;

/**
 * Animation that features a circular clip of increasing radius of the target node.
 */
public class CircleClipIn extends CircleClip {

    /**
     * Instantiates a circular clip entrance animation.
     * @param curve animation curve
     * @param duration animation duration
     */
    public CircleClipIn(Curve curve, Duration duration) {
        super(curve, duration);
    }

    public CircleClipIn() {
        this(DEFAULT_CURVE, DEFAULT_DURATION);
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
