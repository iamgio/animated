package eu.iamgio.animated;

import animatefx.animation.*;

/**
 * Class that takes two {@link Animation}s: one for in, one for out.
 * @author Giorgio Garofalo
 */
public class SwitchAnimation {

    private final Animation animationIn;
    private final Animation animationOut;

    /**
     * Instantiates a {@link SwitchAnimation} and passes its two required animations.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    SwitchAnimation(Animation animationIn, Animation animationOut) {
        this.animationIn = animationIn;
        this.animationOut = animationOut;
    }

    /**
     * Instantiates a {@link SwitchAnimation} and passes its two required animations.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    SwitchAnimation(AnimationFX animationIn, AnimationFX animationOut) {
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
    public SwitchAnimation setSpeed(double speedIn, double speedOut) {
        animationIn.setSpeed(speedIn);
        animationOut.setSpeed(speedOut);
        return this;
    }

    /**
     * @return fade-in / fade-out animation
     * @see FadeIn
     * @see FadeIn
     */
    public static SwitchAnimation fade() {
        return new SwitchAnimation(new FadeIn(), new FadeOut());
    }

    /**
     * @return zoom-in / zoom-out animation
     * @see ZoomIn
     * @see ZoomOut
     */
    public static SwitchAnimation zoom() {
        return new SwitchAnimation(new ZoomIn(), new ZoomOut());
    }

    /**
     * @return rotate-in / rotate-out animation
     * @see RotateIn
     * @see RotateOut
     */
    public static SwitchAnimation rotate() {
        return new SwitchAnimation(new RotateIn(), new RotateOut());
    }

    /**
     * @return bounce-in / bounce-out animation
     * @see BounceIn
     * @see BounceOut
     */
    public static SwitchAnimation bounce() {
        return new SwitchAnimation(new BounceIn(), new BounceOut());
    }

    /**
     * @return roll-in / roll-out animation
     * @see RollIn
     * @see RollOut
     */
    public static SwitchAnimation roll() {
        return new SwitchAnimation(new RollIn(), new RollOut());
    }

    /**
     * @return slide-in-up / slide-out-up animation
     * @see SlideInUp
     * @see SlideOutUp
     */
    public static SwitchAnimation slideUp() {
        return new SwitchAnimation(new SlideInUp(), new SlideOutUp());
    }

    /**
     * @return slide-in-down / slide-out-down animation
     * @see SlideInDown
     * @see SlideOutDown
     */
    public static SwitchAnimation slideDown() {
        return new SwitchAnimation(new SlideInDown(), new SlideOutDown());
    }

    /**
     * @return slide-in-left / slide-out-left animation
     * @see SlideInLeft
     * @see SlideOutLeft
     */
    public static SwitchAnimation slideLeft() {
        return new SwitchAnimation(new SlideInLeft(), new SlideOutRight());
    }

    /**
     * @return slide-in-right / slide-out-right animation
     * @see SlideInRight
     * @see SlideOutRight
     */
    public static SwitchAnimation slideRight() {
        return new SwitchAnimation(new SlideInRight(), new SlideOutRight());
    }
}
