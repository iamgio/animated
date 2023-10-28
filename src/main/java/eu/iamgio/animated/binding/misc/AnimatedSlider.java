package eu.iamgio.animated.binding.misc;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.binding.event.AnimationEvent;
import eu.iamgio.animated.binding.event.ListenableAnimation;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;

import java.util.function.Function;

/**
 * A {@link Slider} implementation that animates its value when an external change occurs.
 * The animation does not play when the value is changed by mouse-dragging.
 *
 * @see Slider
 */
public final class AnimatedSlider extends Slider implements CustomizableAnimation<AnimatedSlider>, ListenableAnimation {

    private final AnimationProperty<Double> property = AnimationProperty.of(valueProperty());

    /**
     * Instantiates an {@link AnimatedSlider}.
     */
    public AnimatedSlider() {
        super();
        this.init();
    }

    /**
     * Instantiates an {@link AnimatedSlider}.
     * @param min minimum value
     * @param max maximum value
     * @param value initial value
     */
    public AnimatedSlider(double min, double max, double value) {
        super(min, max, value);
        this.init();
    }

    private void init() {
        property.register(this);

        // Animation is paused when mouse-dragging
        setOnMouseDragged(e -> property.pause());
        setOnMouseReleased(e -> property.resume());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettings(AnimationSettings settings) {
        property.setSettings(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimatedSlider custom(Function<AnimationSettings, AnimationSettings> settings) {
        property.custom(settings);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationStartedProperty() {
        return this.property.onAnimationStartedProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationEndedProperty() {
        return this.property.onAnimationEndedProperty();
    }
}
