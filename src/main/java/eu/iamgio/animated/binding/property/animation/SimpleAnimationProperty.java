package eu.iamgio.animated.binding.property.animation;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.event.AnimationEvent;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * The base implementation of {@link AnimationProperty} that plays a timeline-based animation
 * whenever the value of the wrapped property changes.
 * @param <T> type of the wrapped value
 * @author Giorgio Garofalo
 */
public class SimpleAnimationProperty<T> extends AnimationProperty<T> {

    // Animation timeline
    private final Timeline timeline;

    // Last time an animation frame was played (in millis)
    private double lastUpdate;

    // Last value the timeline changed
    private T lastValue;

    // Whether the changes should be handled (internally handled)
    private boolean handleChanges = false;

    /**
     * Instantiates an implicitly animated property
     * @param property target property
     * @param settings animation settings
     */
    public SimpleAnimationProperty(PropertyWrapper<T> property, AnimationSettings settings) {
        super(property, settings);
        this.timeline = new Timeline();

        timeline.currentTimeProperty().addListener(o -> {
            lastUpdate = timeline.getCurrentTime().toMillis();
            lastValue = property.getValue();
        });

        timeline.setOnFinished(e -> fireEvent(onAnimationEndedProperty(), new AnimationEvent(false)));
    }

    /**
     * Instantiates an implicitly animated property
     * @param property target property
     */
    public SimpleAnimationProperty(PropertyWrapper<T> property) {
        this(property, new AnimationSettings());
    }

    /**
     * Plays the animation
     * @param value new property value
     * @param interrupted whether the animation was interrupted before it could finish as expected
     */
    private void handleChanges(T value, boolean interrupted) {
        // Temporarily stop the timeline in case it is currently running
        if (interrupted) {
            timeline.stop();
            this.fireEvent(onAnimationEndedProperty(), new AnimationEvent(true));
        }

        final AnimationSettings settings = getSettings();
        Interpolator interpolator = settings.getCurve().toInterpolator();

        // Set keyframes
        timeline.getKeyFrames().setAll(
                new KeyFrame(settings.getDuration(), new KeyValue(getProperty().getProperty(), value, interpolator))
        );

        // Play the animation
        timeline.play();

        this.fireEvent(onAnimationStartedProperty(), new AnimationEvent(interrupted));
    }

    private boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    /**
     * @return whether the current property change was fired by the animation or by an external source
     */
    private boolean isAnimationFrame(T oldPropertyValue, T newPropertyValue) {
        return timeline.getCurrentTime().toMillis() == lastUpdate && (newPropertyValue.equals(lastValue) || oldPropertyValue.equals(lastValue));
    }

    /**
     * {@inheritDoc}
     */
    public void register(Node target) {
        getProperty().addListener(((observable, oldValue, newValue) -> {
            if (isPaused() || (target != null && target.getScene() == null)) {
                return;
            }

            boolean running = isRunning();
            if (!running || !isAnimationFrame(oldValue, newValue)) {
                if (handleChanges ^= true) {
                    getProperty().set(oldValue);
                    handleChanges(newValue, running);
                }
            }
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachTo(Animated animated) {
        this.register(animated.getChild());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> AnimationProperty<T> addBinding(Property<V> targetProperty, Function<T, V> mapper) {
        getProperty().bindMapped(targetProperty, mapper);
        return this;
    }

    private void fireEvent(ObjectProperty<EventHandler<AnimationEvent>> handlerProperty, AnimationEvent event) {
        EventHandler<? super AnimationEvent> handler = handlerProperty.get();
        if (handler != null) {
            handler.handle(event);
        }
    }
}
