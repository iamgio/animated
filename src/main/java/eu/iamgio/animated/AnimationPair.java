package eu.iamgio.animated;

import animatefx.animation.*;

/**
 * Class that takes two {@link Animation}s: one for in, one for out.
 * @author Giorgio Garofalo
 */
public class AnimationPair {

    private final Animation animationIn;
    private final Animation animationOut;

    /**
     * Instantiates a {@link AnimationPair} and passes its two required animations.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    AnimationPair(Animation animationIn, Animation animationOut) {
        this.animationIn = animationIn;
        this.animationOut = animationOut;
    }

    /**
     * Instantiates a {@link AnimationPair} and passes its two required animations.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    AnimationPair(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * @return entrance animation
     */
    public Animation getIn() {
        return animationIn;
    }

    /**
     * @return exit animation
     */
    public Animation getOut() {
        return animationOut;
    }

    /**
     * @param speedIn speed multiplier to set to the entrance animation
     * @param speedOut speed multiplier to set to the exit animation
     * @return this for concatenation
     */
    public AnimationPair setSpeed(double speedIn, double speedOut) {
        animationIn.setSpeed(speedIn);
        animationOut.setSpeed(speedOut);
        return this;
    }

    /**
     * @return fade-in / fade-out animation
     * @see FadeIn
     * @see FadeIn
     */
    public static AnimationPair fade() {
        return new AnimationPair(new FadeIn(), new FadeOut());
    }

    /**
     * @return zoom-in / zoom-out animation
     * @see ZoomIn
     * @see ZoomOut
     */
    public static AnimationPair zoom() {
        return new AnimationPair(new ZoomIn(), new ZoomOut());
    }

    /**
     * @return rotate-in / rotate-out animation
     * @see RotateIn
     * @see RotateOut
     */
    public static AnimationPair rotate() {
        return new AnimationPair(new RotateIn(), new RotateOut());
    }

    /**
     * @return bounce-in / bounce-out animation
     * @see BounceIn
     * @see BounceOut
     */
    public static AnimationPair bounce() {
        return new AnimationPair(new BounceIn(), new BounceOut());
    }

    /**
     * @return roll-in / roll-out animation
     * @see RollIn
     * @see RollOut
     */
    public static AnimationPair roll() {
        return new AnimationPair(new RollIn(), new RollOut());
    }

    /**
     * @return slide-in-up / slide-out-up animation
     * @see SlideInUp
     * @see SlideOutUp
     */
    public static AnimationPair slideUp() {
        return new AnimationPair(new SlideInUp(), new SlideOutUp());
    }

    /**
     * @return slide-in-down / slide-out-down animation
     * @see SlideInDown
     * @see SlideOutDown
     */
    public static AnimationPair slideDown() {
        return new AnimationPair(new SlideInDown(), new SlideOutDown());
    }

    /**
     * @return slide-in-left / slide-out-left animation
     * @see SlideInLeft
     * @see SlideOutLeft
     */
    public static AnimationPair slideLeft() {
        return new AnimationPair(new SlideInLeft(), new SlideOutRight());
    }

    /**
     * @return slide-in-right / slide-out-right animation
     * @see SlideInRight
     * @see SlideOutRight
     */
    public static AnimationPair slideRight() {
        return new AnimationPair(new SlideInRight(), new SlideOutRight());
    }
}
