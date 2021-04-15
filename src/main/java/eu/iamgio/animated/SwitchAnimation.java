package eu.iamgio.animated;

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
}
