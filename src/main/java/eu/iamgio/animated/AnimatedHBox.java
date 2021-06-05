package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import javafx.scene.layout.HBox;

/**
 * An {@link HBox} with animated children.
 * @author Giorgio Garofalo
 */
public class AnimatedHBox extends HBox implements AnimatedChildren {

    public AnimatedHBox(Animation animationIn, Animation animationOut) {
        AnimatedChildren.register(getChildren(), animationIn, animationOut);
    }

    public AnimatedHBox(SwitchAnimation animation) {
        this(animation.getIn(), animation.getOut());
    }

    public AnimatedHBox(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }
}
