package eu.iamgio.animated.transition.animations.clip;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.common.Curve;
import eu.iamgio.animated.util.PosUtils;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Animation that features a shape that masks a node.
 *
 * @param <S> shape type
 */
public abstract class ClipAnimation<S extends Shape> extends AnimationFX {

    protected static final Curve DEFAULT_CURVE = Curve.EASE_IN;
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

    @Override
    protected AnimationFX resetNode() {
        // This animation is never reset.
        return this;
    }

    @Override
    protected final void initTimeline() {
        final Scene scene = super.getNode().getScene();

        if (scene == null) {
            throw new IllegalStateException("Target node is not in scene.");
        }

        final S clip = this.createClip();

        // The position of the clip depends on the given alignment
        PosUtils.bindAlignmentToScene(clip.layoutXProperty(), clip.layoutYProperty(), alignment, scene);

        super.getNode().setClip(clip);
        super.setTimeline(this.createTimeline(clip));
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
