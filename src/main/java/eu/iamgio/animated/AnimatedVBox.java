package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import javafx.scene.layout.VBox;

/**
 * A {@link VBox} with animated children.
 * @author Giorgio Garofalo
 */
public class AnimatedVBox extends VBox implements AnimatedChildren {

    public AnimatedVBox(Animation animationIn, Animation animationOut) {
        AnimatedChildren.register(getChildren(), animationIn, animationOut);
    }

    public AnimatedVBox(AnimationPair animation) {
        this(animation.getIn(), animation.getOut());
    }

    public AnimatedVBox(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }
}
