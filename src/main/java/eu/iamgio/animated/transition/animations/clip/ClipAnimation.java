package eu.iamgio.animated.transition.animations.clip;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.common.Curve;
import eu.iamgio.animated.util.PositionUtils;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Animation that features a shape that masks a node.
 *
 * @param <S> shape type
 */
public abstract class ClipAnimation<S extends Shape> extends AnimationFX {

    protected static final Curve DEFAULT_ENTRANCE_CURVE = Curve.EASE_IN;
    protected static final Curve DEFAULT_EXIT_CURVE = Curve.EASE_OUT;
    protected static final Duration DEFAULT_DURATION = Duration.seconds(1);
    protected static final Pos DEFAULT_ALIGNMENT = Pos.CENTER;

    private final Curve curve;
    private final Duration duration;
    private final Pos alignment;

    /**
     * @param curve animation curve
     * @param duration animation duration
     * @param alignment position of the clip, relative to the scene
     */
    protected ClipAnimation(Curve curve, Duration duration, Pos alignment) {
        this.curve = curve;
        this.duration = duration;
        this.alignment = alignment;
    }

    /**
     * @return a new instance of the clip shape
     */
    protected abstract S createClip();

    /**
     * @param clip current clip
     * @return a new instance of the timeline to play
     */
    protected abstract Timeline createTimeline(S clip);

    /**
     * @return the available area to clip
     */
    protected Bounds getArea() {
        final Bounds layoutBounds = getNode().getLayoutBounds();
        return layoutBounds.getWidth() <= 0 && layoutBounds.getHeight() <= 0
                ? getNode().getScene().getRoot().getLayoutBounds()
                : layoutBounds;
    }

    @Override
    protected AnimationFX resetNode() {
        // This animation is never reset.
        return this;
    }

    @Override
    protected final void initTimeline() {
        final Node node = super.getNode();

        final S clip = this.createClip();

        // The position of the clip depends on the given alignment.
        PositionUtils.bindAlignmentToArea(
                clip.layoutXProperty(), clip.layoutYProperty(),
                node.layoutBoundsProperty(), this.alignment
        );

        node.setClip(clip);

        final Timeline timeline = this.createTimeline(clip);

        // The clip is removed at the end of the animation.
        timeline.setOnFinished(e -> super.getNode().setClip(null));

        super.setTimeline(timeline);
    }

    protected Curve getCurve() {
        return this.curve;
    }

    protected Duration getDuration() {
        return this.duration;
    }

    protected Pos getAlignment() {
        return this.alignment;
    }
}
