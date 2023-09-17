package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.transition.animations.NullAnimation;
import eu.iamgio.animated.transition.animations.RequiresScene;
import eu.iamgio.animated.util.ReflectionUtils;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Wrapper for {@link AnimationFX} with several properties.
 */
public class Animation {

    private final AnimationFX animationFX;

    private double speed = 1;
    private Duration delay = Duration.ZERO;
    private int cycleCount = 1;

    /**
     * Instantiates a new {@link Animation} that wraps the given {@link AnimationFX}.
     * @param animationFX non-null raw {@link AnimationFX} to wrap
     */
    public Animation(AnimationFX animationFX) {
        this.animationFX = Objects.requireNonNull(animationFX);
    }

    /**
     * Instantiates a new {@link Animation} that wraps the given {@link AnimationFX}.
     * @param type name (case-sensitive) of the raw {@link AnimationFX} to wrap
     * @param speed speed multiplier of the animation
     * @throws IllegalArgumentException if the type does not match any AnimateFX animation
     * @deprecated this method uses unnecessary reflection, hence it should be avoided.
     *             This was intended for FXML compatibility only
     */
    @Deprecated
    public Animation(@NamedArg("type") String type,
                     @NamedArg(value = "speed", defaultValue = "1.0") double speed,
                     @NamedArg(value = "delay", defaultValue = "0ms") Duration delay) {
        this.speed = speed;
        this.delay = delay;

        if (type.equalsIgnoreCase("none")) {
            this.animationFX = Animation.none().getAnimationFX();
            return;
        }

        try {
            this.animationFX = (AnimationFX) ReflectionUtils.findClassInPackages(
                    type,
                    "animatefx.animation",
                    "eu.iamgio.animated.transition.animations"
            ).newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void applyProperties() {
        animationFX.setSpeed(speed);
        animationFX.setDelay(delay);
        animationFX.setCycleCount(cycleCount);
    }

    /**
     * Plays the animation while adding the <tt>target</tt> node to <tt>children</tt>
     * @param target node to animate
     * @param children observable list to add the node to (if not <tt>null</tt>)
     */
    public void playIn(Node target, ObservableList<Node> children) {
        if (children != null) {
            // If the animation has this tag,
            // the node must be added to the scene before playing the animation.
            if (animationFX instanceof RequiresScene) {
                children.add(target);
            } else {
                Platform.runLater(() -> children.add(target));
            }
        }

        animationFX.setNode(target);
        applyProperties();
        animationFX.play();
    }

    /**
     * Plays the animation before removing the <tt>target</tt> node from <tt>children</tt>
     * @param target node to animate
     * @param children observable list to remove the node from (if not <tt>null</tt>)
     */
    public void playOut(Node target, ObservableList<Node> children) {
        animationFX.setNode(target);
        applyProperties();
        if (children != null) {
            animationFX.setOnFinished(e -> children.remove(target));
        }
        animationFX.play();
    }

    /**
     * @return status of the animation
     */
    public javafx.animation.Animation.Status getStatus() {
        if (animationFX != null && animationFX.getTimeline() != null) {
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
     * @return non-null AnimateFX animation
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
     * @return a valid non-null playable {@link Animation} that does not perform any actual animation
     *         and is also affected by {@link #getDelay()}.
     * @see NullAnimation
     */
    public static Animation none() {
        return new Animation(new NullAnimation());
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
