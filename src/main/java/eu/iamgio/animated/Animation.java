package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import javafx.util.Duration;

/**
 * Wrapper for {@link AnimationFX} with several properties.
 * @author Giorgio Garofalo
 */
public class Animation {

    private final AnimationFX animationFX;

    private double speed = 1;
    private Duration delay;
    private int cycleCount = 1;

    public Animation(AnimationFX animationFX) {
        this.animationFX = animationFX;
    }

    void applyProperties() {
        animationFX.setSpeed(speed);
        if(delay != null) animationFX.setDelay(delay);
        animationFX.setCycleCount(cycleCount);
    }

    /**
     * @return AnimateFX animation
     */
    public AnimationFX getAnimationFX() {
        return animationFX;
    }

    /**
     * @return speed multiplier (1 = normal speed)
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed speed multiplier to set
     * @return this for concatenation
     */
    public Animation setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * @return delay before the animation is played
     */
    public Duration getDelay() {
        return delay;
    }

    /**
     * @param delay delay to set
     * @return this for concatenation
     */
    public Animation setDelay(Duration delay) {
        this.delay = delay;
        return this;
    }

    /**
     * @return times the animation should be played. Defaults to 1
     */
    public int getCycleCount() {
        return cycleCount;
    }

    /**
     * @param cycleCount cycles count to set
     * @return this for concatenation
     */
    public Animation setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
        return this;
    }
}
