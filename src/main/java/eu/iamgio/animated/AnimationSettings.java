package eu.iamgio.animated;

import javafx.util.Duration;

/**
 * @author Giorgio Garofalo
 */
public class AnimationSettings {

    private Duration duration = Duration.seconds(1);

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
}
