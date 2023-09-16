package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.common.Curve;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Entrance animation that features a rectangular clip of increasing size of the target node.
 */
public class RectangleClipIn extends RectangleClip {

    /**
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the rectangle, relative to the scene
     */
    public RectangleClipIn(Curve curve, Duration duration, Pos alignment) {
        super(curve, duration, alignment);
    }

    /**
     * Instantiates a rectangular clip entrance animation.
     * @param alignment position of the rectangle, relative to the scene
     */
    public RectangleClipIn(Pos alignment) {
        this(DEFAULT_ENTRANCE_CURVE, DEFAULT_DURATION, alignment);
    }

    /**
     * Instantiates a rectangular clip entrance animation from the center of the scene.
     */
    public RectangleClipIn() {
        this(DEFAULT_ALIGNMENT);
    }

    @Override
    protected double getInitialWidth() {
        return 0;
    }

    @Override
    protected double getInitialHeight() {
        return 0;
    }

    @Override
    protected double getFinalWidth() {
        return getNode().getScene().getWidth();
    }

    @Override
    protected double getFinalHeight() {
        return getNode().getScene().getHeight();
    }
}
