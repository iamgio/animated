package eu.iamgio.animated.binding.misc;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import eu.iamgio.animated.transition.Pausable;
import javafx.beans.property.*;
import javafx.scene.control.Label;

import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link Label} that animates its text content whenever its wrapped value changes via {@link #setValue(Object)}.
 *
 * @param <T> type of the wrapped value.
 */
public class AnimatedValueLabel<T> extends Label implements CustomizableAnimation<AnimatedValueLabel<T>>, Pausable {

    private final ObjectProperty<T> value;
    private final AnimationProperty<T> animationProperty;
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    /**
     * Instantiates an {@link AnimatedValueLabel}.
     * @param value initial wrapped value
     * @param toString string representation mapping of the current value
     */
    public AnimatedValueLabel(T value, Function<T, String> toString) {
        this.value = new SimpleObjectProperty<>(value);
        this.animationProperty = AnimationProperty.of(this.value);

        animationProperty.addBinding(this.textProperty(), toString);
        animationProperty.register(this);
    }

    /**
     * Instantiates an {@link AnimatedValueLabel} whose string mapping happens via {@link Objects#toString(Object)}.
     * @param value initial wrapped value
     */
    public AnimatedValueLabel(T value) {
        this(value, Objects::toString);
    }

    /**
     * Instantiates an empty {@link AnimatedValueLabel} whose string mapping happens via {@link Objects#toString(Object)}.
     */
    public AnimatedValueLabel() {
        this(null);
    }

    /**
     * @return the wrapped value
     */
    public Property<T> valueProperty() {
        return this.value;
    }

    /**
     * @return the wrapped value
     */
    public T getValue() {
        return this.value.get();
    }

    /**
     * Sets a new wrapped value and starts the transition.
     * @param value new wrapped value
     */
    public void setValue(T value) {
        this.value.set(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettings(AnimationSettings settings) {
        this.animationProperty.setSettings(settings);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends AnimatedValueLabel<T>> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        this.animationProperty.custom(settings);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.paused;
    }
}
