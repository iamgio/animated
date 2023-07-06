package eu.iamgio.animated.binding.misc;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import eu.iamgio.animated.transition.Pausable;
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

    private final ObjectProperty<T> value;
    private final AnimationProperty<T> animationProperty;
    private final Property<Function<T, String>> textMapper;
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    /**
     * Instantiates an {@link AnimatedValueLabel}.
     * @param value initial wrapped value
     */
    public AnimatedValueLabel(T value) {
        this.value = new SimpleObjectProperty<>(value);
        this.textMapper = new SimpleObjectProperty<>(Objects::toString);
        this.animationProperty = AnimationProperty.of(this.value);

        // Whenever the wrapped value or the text mapper change, the displayed text is updated.
        this.textProperty().bind(
                Bindings.createStringBinding(
                        () -> this.textMapper.getValue().apply(this.value.get()),
                        this.value, this.textMapper
                )
        );

        animationProperty.pausedProperty().bind(this.paused);
        animationProperty.register(this);
    }

    /**
     * Instantiates an empty {@link AnimatedValueLabel}.
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
