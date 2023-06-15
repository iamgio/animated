package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import javafx.beans.property.ObjectProperty;

/**
 * Represents an element that features an exit animation.
 */
public interface ExitAnimationCompatible {

    /**
     * @return exit animation
     */
    ObjectProperty<Animation> animationOutProperty();

    /**
     * @return exit animation
     */
    default Animation getOut() {
        return this.animationOutProperty().get();
    }

    /**
     * Sets a new exit animation.
     * @param out new exit animation
     */
    default void setOut(Animation out) {
        this.animationOutProperty().set(out);
    }

    /**
     * Sets a new exit animation.
     * @param in raw new exit animation
     */
    default void setOut(AnimationFX in) {
        this.animationOutProperty().set(new Animation(in));
    }
}
