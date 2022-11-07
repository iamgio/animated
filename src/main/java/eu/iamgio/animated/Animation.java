package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Objects;

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

    private void applyProperties() {
        animationFX.setSpeed(speed);
        if(delay != null) animationFX.setDelay(delay);
        animationFX.setCycleCount(cycleCount);
    }

    /**
     * Plays the animation while adding the <tt>target</tt> node to <tt>children</tt>
     * @param target node to animate
     * @param children observable list to add the node to (if not <tt>null</tt>)
     */
    public void playIn(Node target, ObservableList<Node> children) {
        if(animationFX != null) {
            animationFX.setNode(target);
            applyProperties();
            animationFX.play();
        }
        if(children != null) Platform.runLater(() -> children.add(target));
    }

    /**
     * Plays the animation before removing the <tt>target</tt> node from <tt>children</tt>
     * @param target node to animate
     * @param children observable list to remove the node from (if not <tt>null</tt>)
     */
    public void playOut(Node target, ObservableList<Node> children) {
        if(animationFX != null) {
            animationFX.setNode(target);
            applyProperties();
            if(children != null) animationFX.setOnFinished(e -> children.remove(target));
            animationFX.play();
        }
    }

    /**
     * @return status of the animation
     */
    public javafx.animation.Animation.Status getStatus() {
        if(animationFX != null && animationFX.getTimeline() != null) {
            return animationFX.getTimeline().getStatus();
        }
        return javafx.animation.Animation.Status.STOPPED;
    }

    /**
     * @return whether this animation is playing or not
     */
    public boolean isPlaying() {
        return getStatus() == javafx.animation.Animation.Status.RUNNING;
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

    /**
     * Checks that the given animation is not <tt>null</tt>, or throws a {@link NullPointerException} otherwise.
     * @param animation animation to check
     * @return the given animation if not <tt>null</tt>
     */
    public static Animation requireNonNull(Animation animation) {
        return Objects.requireNonNull(animation, "An animation cannot be null.");
    }
}
