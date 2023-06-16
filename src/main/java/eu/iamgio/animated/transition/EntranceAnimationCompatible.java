package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import javafx.beans.property.ObjectProperty;

/**
 * Represents an element that features an entrance animation.
 */
public interface EntranceAnimationCompatible {

    /**
     * @return entrance animation
     */
    ObjectProperty<Animation> animationInProperty();

    /**
     * @return entrance animation
     */
    default Animation getIn() {
        return this.animationInProperty().get();
    }

    /**
     * Sets a new entrance animation.
     * @param in new entrance animation
     */
    default void setIn(Animation in) {
        this.animationInProperty().set(in);
    }

    /**
     * Sets a new entrance animation.
     * @param in new raw entrance animation
     */
    default void setIn(AnimationFX in) {
        this.setIn(new Animation(in));
    }
}
