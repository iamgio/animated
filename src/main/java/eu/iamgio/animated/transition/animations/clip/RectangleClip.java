package eu.iamgio.animated.transition.animations.clip;

import eu.iamgio.animated.common.Curve;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Animation that features a rectangular clip with of variable size.
 *
 * @see RectangleClipIn
 * @see RectangleClipOut
 */
abstract class RectangleClip extends ClipAnimation<Rectangle> {

    /**
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the rectangle, relative to the scene
     */
    RectangleClip(Curve curve, Duration duration, Pos alignment) {
        super(curve, duration, alignment);
    }

    /**
     * @return width of the rectangle at the beginning of the animation
     */
    protected abstract double getInitialWidth();

    /**
     * @return height of the rectangle at the beginning of the animation
     */
    protected abstract double getInitialHeight();

    /**
     * @return width of the rectangle at the end of the animation
     */
    protected abstract double getFinalWidth();

    /**
     * @return height of the rectangle at the end of the animation
     */
    protected abstract double getFinalHeight();

    @Override
    protected Rectangle createClip() {
        return new Rectangle(this.getInitialWidth(), this.getInitialHeight());
    }

    @Override
    protected Timeline createTimeline(Rectangle clip) {
        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(clip.widthProperty(), this.getInitialWidth()),
                        new KeyValue(clip.heightProperty(), this.getInitialHeight())
                ),
                new KeyFrame(
                        super.getDuration(),
                        new KeyValue(clip.widthProperty(), this.getFinalWidth(), super.getCurve().toInterpolator()),
                        new KeyValue(clip.heightProperty(), this.getFinalHeight(), super.getCurve().toInterpolator())
                )
        );
    }
}