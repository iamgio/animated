package eu.iamgio.animated.binding.value;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.event.AnimationEvent;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A value that animates its wrapped content whenever its wrapped value changes via {@link #setValue(Object)}.
 * Note that the animated value ({@link #animationValueProperty()}) must be bound to an external property to be effective.
 *
 * @param <T> type of the wrapped value.
 */
class AnimatedValueImpl<T> implements AnimatedValue<T> {

    private final Property<T> value;
    private final AnimationProperty<T> animationProperty;
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    /**
     * Instantiates an {@link AnimatedValueImpl}.
     * @param value initial wrapped value
     */
    AnimatedValueImpl(T value, Node target) {
        this.value = new SimpleObjectProperty<>(value);

        // This is a parallel property to the value property,
        // which is updated when the other one is updated, but with an animation.
        final ObjectProperty<T> animatedValue = new SimpleObjectProperty<>(value);
        this.value.addListener((observable, oldValue, newValue) -> animatedValue.set(newValue));

        this.animationProperty = AnimationProperty.of(animatedValue);

        animationProperty.pausedProperty().bind(this.paused);
        animationProperty.register(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<T> valueProperty() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() {
        return this.value.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(T value) {
        this.value.setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyProperty<T> animationValueProperty() {
        return this.animationProperty.getProperty().getProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getAnimationValue() {
        return this.animationProperty.getProperty().getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationSettings getSettings() {
        return this.animationProperty.getSettings();
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
    @Override
    public AnimatedValue<T> custom(Function<AnimationSettings, AnimationSettings> settings) {
        this.animationProperty.custom(settings);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.paused;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationStartedProperty() {
        return this.animationProperty.onAnimationStartedProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationEndedProperty() {
        return this.animationProperty.onAnimationEndedProperty();
    }
}
