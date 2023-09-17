package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.common.Curve;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Exit animation that features a rectangular clip of increasing size of the target node.
 */
public class RectangleClipOut extends RectangleClip {

    /**
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the rectangle, relative to the scene
     */
    public RectangleClipOut(Curve curve, Duration duration, Pos alignment) {
        super(curve, duration, alignment);
    }

    /**
     * Instantiates a rectangular clip entrance animation.
     * @param alignment position of the rectangle, relative to the scene
     */
    public RectangleClipOut(Pos alignment) {
        this(DEFAULT_EXIT_CURVE, DEFAULT_DURATION, alignment);
    }

    /**
     * Instantiates a rectangular clip entrance animation from the center of the scene.
     */
    public RectangleClipOut() {
        this(DEFAULT_ALIGNMENT);
    }

    @Override
    protected double getInitialWidth() {
        return super.getArea().getWidth();
    }

    @Override
    protected double getInitialHeight() {
        return super.getArea().getHeight();
    }

    @Override
    protected double getFinalWidth() {
        return 0;
    }

    @Override
    protected double getFinalHeight() {
        return 0;
    }
}
