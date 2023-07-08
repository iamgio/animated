package eu.iamgio.animated.binding.label;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import eu.iamgio.animated.transition.Pausable;
import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
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

    private final Property<T> value;
    private final AnimationProperty<T> animationProperty;
    private final Property<Function<T, String>> textMapper;
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    /**
     * Instantiates an {@link AnimatedValueLabel}.
     * @param value initial wrapped value
     */
    public AnimatedValueLabel(@NamedArg("value") T value) {
        this.value = new SimpleObjectProperty<>(value);
        this.textMapper = new SimpleObjectProperty<>(Objects::toString);

        // This is a parallel property to the value property,
        // which is updated when the other one is updated, but with an animation.
        final ObjectProperty<T> animatedValue = new SimpleObjectProperty<>(value);
        this.value.addListener((observable, oldValue, newValue) -> animatedValue.set(newValue));

        this.animationProperty = AnimationProperty.of(animatedValue);

        // Whenever the wrapped value or the text mapper change, the displayed text is updated.
        this.textProperty().bind(
                Bindings.createStringBinding(
                        () -> this.textMapper.getValue().apply(animatedValue.get()),
                        animatedValue, this.textMapper
                )
        );

        animationProperty.pausedProperty().bind(this.paused);
        animationProperty.register(this);
    }

    /**
     * @return the wrapped value
     */
    public Property<T> valueProperty() {
        return this.value;
    }

    /**
     * @return the wrapped value. Note that it only depends on the user-supplied value via {@link #setValue(Object)},
     *         and not on the current animation frame, which is given by {@link #getAnimationValue()}
     */
    public T getValue() {
        return this.value.getValue();
    }

    /**
     * Sets a new wrapped value and starts the transition.
     * @param value new wrapped value
     */
    public void setValue(T value) {
        this.value.setValue(value);
    }

    /**
     * @return the current value of the wrapped property. While {@link #valueProperty()} only wraps the last user-supplied value,
     *         this method takes into account the latest value produced by the animation, if it is playing.
     */
    public ReadOnlyProperty<T> animationValueProperty() {
        return this.animationProperty.getProperty().getProperty();
    }

    /**
     * @return the current value of the wrapped property. While {@link #getValue()} only returns the last user-supplied value,
     *         this method takes into account the latest value produced by the animation, if it is playing.
     */
    public T getAnimationValue() {
        return this.animationProperty.getProperty().getValue();
    }

    /**
     * @return the function that maps the current wrapped value to the displayed text
     */
    public Property<Function<T, String>> textMapperProperty() {
        return this.textMapper;
    }

    /**
     * @return the function that maps the current wrapped value to the displayed text.
     *         It defaults to {@link Objects#toString(Object)}
     */
    public Function<T, String> getTextMapper() {
        return this.textMapper.getValue();
    }

    /**
     * Sets the function that maps the current wrapped value to the displayed text.
     * @param textMapper new text mapper
     */
    public void setTextMapper(Function<T, String> textMapper) {
        this.textMapper.setValue(textMapper);
    }

    /**
     * @return the current animation settings
     */
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
    public AnimatedValueLabel<T> custom(Function<AnimationSettings, AnimationSettings> settings) {
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
}
