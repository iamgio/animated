package eu.iamgio.animated.binding;

import javafx.util.Duration;

/**
 * @author Giorgio Garofalo
 */
public class AnimationSettings {

    private Duration duration = Duration.seconds(1);
    private Curve curve = Curve.LINEAR;

    /**
     * @param duration duration of the animation to set
     * @return this for concatenation
     */
    public AnimationSettings withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    /**
     * @return duration of the animation
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * @param curve curve of the animation to set
     * @return this for concatenation
     */
    public AnimationSettings withCurve(Curve curve) {
        this.curve = curve;
        return this;
    }

    /**
     * @return curve of the animation
     */
    public Curve getCurve() {
        return curve;
    }
}
