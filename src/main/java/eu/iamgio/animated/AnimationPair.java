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
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimationPair(Animation animationIn, Animation animationOut) {
        this.animationIn = Animation.requireNonNull(animationIn);
        this.animationOut = Animation.requireNonNull(animationOut);
    }

    /**
     * Instantiates a {@link AnimationPair} and passes its two required animations.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimationPair(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates a {@link AnimationPair} and passes its two required animations.
     * @param animationIn non-null entrance (wrapped) animation
     * @param animationOut non-null exit (unwrapped) animation
     */
    public AnimationPair(Animation animationIn, AnimationFX animationOut) {
        this(animationIn, new Animation(animationOut));
    }

    /**
     * Instantiates a {@link AnimationPair} and passes its two required animations.
     * @param animationIn non-null entrance (unwrapped) animation
     * @param animationOut non-null exit (wrapped) animation
     */
    public AnimationPair(AnimationFX animationIn, Animation animationOut) {
        this(new Animation(animationIn), animationOut);
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
     * @return fade-in-up / fade-out-up animation
     * @see FadeInUp
     * @see FadeOutUp
     */
    public static AnimationPair fadeUp() {
        return new AnimationPair(new FadeInUp(), new FadeOutUp());
    }

    /**
     * @return fade-in-down / fade-out-down animation
     * @see FadeInDown
     * @see FadeOutDown
     */
    public static AnimationPair fadeDown() {
        return new AnimationPair(new FadeInDown(), new FadeOutDown());
    }

    /**
     * @return fade-in-left / fade-out-left animation
     * @see FadeInLeft
     * @see FadeOutLeft
     */
    public static AnimationPair fadeLeft() {
        return new AnimationPair(new FadeInLeft(), new FadeOutLeft());
    }

    /**
     * @return fade-in-right / fade-out-right animation
     * @see FadeInRight
     * @see FadeOutRight
     */
    public static AnimationPair fadeRight() {
        return new AnimationPair(new FadeInRight(), new FadeOutRight());
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
        return new AnimationPair(new SlideInLeft(), new SlideOutLeft());
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
