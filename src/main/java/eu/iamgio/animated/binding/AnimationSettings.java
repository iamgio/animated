package eu.iamgio.animated.binding;

import eu.iamgio.animated.common.Curve;
import javafx.util.Duration;

/**
 * Data that affects the way an animation is played.
 */
public class AnimationSettings {

    private Duration duration = Duration.seconds(1);
    private Curve curve = Curve.LINEAR;

    /**
     * @return duration of the animation
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Note: using the fluent setter {@link #withDuration(Duration)} is suggested.
     *       This method is kept only for FXML compatibility.
     * @param duration duration of the animation to set
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * @param duration duration of the animation to set
     * @return this for concatenation
     */
    public AnimationSettings withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    /**
     * @return curve of the animation
     */
    public Curve getCurve() {
        return curve;
    }

    /**
     * Note: using the fluent setter {@link #withCurve(Curve)} is suggested.
     *       This method is kept only for FXML compatibility.
     * @param curve curve of the animation to set
     *
     */
    public void setCurve(Curve curve) {
        this.curve = curve;
    }

    /**
     * @param curve curve of the animation to set
     * @return this for concatenation
     */
    public AnimationSettings withCurve(Curve curve) {
        this.curve = curve;
        return this;
    }
}
