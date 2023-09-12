package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.binding.Curve;
import javafx.util.Duration;

/**
 * Animation that features a circular clip of decreasing radius of the target node.
 */
public class CircleClipOut extends CircleClip {

    /**
     * Instantiates a circular clip exit animation.
     * @param curve animation curve
     * @param duration animation duration
     */
    public CircleClipOut(Curve curve, Duration duration) {
        super(curve, duration);
    }

    public CircleClipOut() {
        this(DEFAULT_CURVE, DEFAULT_DURATION);
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