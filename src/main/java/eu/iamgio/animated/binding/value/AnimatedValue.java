package eu.iamgio.animated.binding.value;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.common.Pausable;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;

/**
 * An object that wraps a value, and exposes a parallel value that is updated with an animated transition
 * whenever the wrapped value is updated via {@link #setValue(Object)}.
 * {@link #animationValueProperty()} can be bound to any other external property to provide
 * an animated effect without affecting the wrapped value.
 */
public interface AnimatedValue<T> extends CustomizableAnimation<AnimatedValue<T>>, Pausable {

    /**
     * @return the wrapped value
     */
    Property<T> valueProperty();

    /**
     * @return the wrapped value. Note that it only depends on the user-supplied value via {@link #setValue(Object)},
     *         and not on the current animation frame, which is given by {@link #getAnimationValue()}
     */
    T getValue();

    /**
     * Sets a new wrapped value and starts the transition.
     * @param value new wrapped value
     */
    void setValue(T value);

    /**
     * @return the current value of the wrapped property. While {@link #valueProperty()} only wraps the last user-supplied value,
     *         this method takes into account the latest value produced by the animation, if it is playing.
     */
    ReadOnlyProperty<T> animationValueProperty();

    /**
     * @return the current value of the wrapped property. While {@link #getValue()} only returns the last user-supplied value,
     *         this method takes into account the latest value produced by the animation, if it is playing.
     */
    T getAnimationValue();

    /**
     * @return the current animation settings
     */
    AnimationSettings getSettings();
}
