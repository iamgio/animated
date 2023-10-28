package eu.iamgio.animated.binding.value;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.event.AnimationEvent;
import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link Label} that animates its text content whenever its wrapped value changes via {@link #setValue(Object)}.
 *
 * @param <T> type of the wrapped value.
 */
public class AnimatedValueLabel<T> extends Label implements AnimatedValue<T> {

    private final AnimatedValue<T> value;
    private final Property<Function<T, String>> textMapper;

    /**
     * Instantiates an {@link AnimatedValueLabel}.
     * @param value initial wrapped value
     */
    public AnimatedValueLabel(@NamedArg("value") T value) {
        this.value = new AnimatedValueImpl<>(value, this);
        this.textMapper = new SimpleObjectProperty<>(Objects::toString);

        this.textProperty().bind(
                Bindings.createStringBinding(
                        () -> this.textMapper.getValue().apply(getAnimationValue()),
                        animationValueProperty(), this.textMapper
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettings(AnimationSettings settings) {
        this.value.setSettings(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimatedValueLabel<T> custom(Function<AnimationSettings, AnimationSettings> settings) {
        this.value.custom(settings);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<T> valueProperty() {
        return this.value.valueProperty();
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
        return this.value.animationValueProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getAnimationValue() {
        return this.value.getAnimationValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationSettings getSettings() {
        return this.value.getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.value.pausedProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationStartedProperty() {
        return this.value.onAnimationStartedProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationEndedProperty() {
        return this.value.onAnimationEndedProperty();
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
}
